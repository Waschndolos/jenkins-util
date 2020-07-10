package de.jenkinsutil.service.file;

import de.jenkinsutil.config.FileMonitorConfig;
import de.jenkinsutil.service.folder.FolderSizeCalculator;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileSizeCalculator implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderSizeCalculator.class);

    private final FileMonitorConfig config;
    private final MeterRegistry registry;
    private final Map<String, Meter.Id> dynamicallyAddedGauges = new HashMap<>();
    private final Map<String, BigInteger> files = new ConcurrentHashMap<>();

    public FileSizeCalculator(FileMonitorConfig config, MeterRegistry registry) {
        this.config = config;
        this.registry = registry;
    }

    public void init() {
        initFilesToCheck();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this, 0, config.getScanIntervalHours(), TimeUnit.HOURS);
    }

    public void initFilesToCheck() {
        if (files.isEmpty()) {
            for (String absolutePath : getAllFilesToWatch()) {
                this.files.put(absolutePath, BigInteger.valueOf(-1));
            }
        }
    }

    private List<String> getAllFilesToWatch() {
        Path rootPath = Paths.get(config.getBaseFolder());

        String fileExtension = config.getFileExtension();
        if (fileExtension.startsWith(".")) {
            fileExtension = fileExtension.substring(1);
        }

        return FileUtils.listFiles(
                rootPath.toFile(),
                new RegexFileFilter(".*\\." + fileExtension),
                DirectoryFileFilter.DIRECTORY
        ).stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

    }

    private void updateWatchlist() {
        List<String> filesToCheck = getAllFilesToWatch();

        for (String absolutePath : filesToCheck) {
            if (!files.containsKey(absolutePath)) {
                LOGGER.info("Found new File {}. Adding to monitoring...", absolutePath);
                files.put(absolutePath, BigInteger.valueOf(-1));
                Gauge gauge = Gauge.builder("file.size.mb", this, (c) -> c.getFileSize(absolutePath))
                        .tag("absolute", absolutePath)
                        .tag("fileName", new File(absolutePath).getName())
                        .register(registry);

                dynamicallyAddedGauges.put(absolutePath, gauge.getId());
            }
        }

        for (String key : files.keySet()) {
            if (!filesToCheck.contains(key)) {
                LOGGER.info("File {} no longer exists. Removing from monitoring...", key);
                Meter.Id id = dynamicallyAddedGauges.get(key);
                files.remove(key);
                if (id != null) {
                    registry.remove(id);
                }
            }
        }

    }

    public double getFileSize(String absoluteFilePath) {
        BigInteger size = files.get(absoluteFilePath);
        if (size == null) {
            return -1;
        } else {
            return size.doubleValue();
        }
    }

    public Set<String> getFilesToCheck() {
        return files.keySet();
    }

    @Override
    public void run() {
        updateWatchlist();
        long start = System.currentTimeMillis();
        LOGGER.info("Recalculating sizes of {} files within {}...", files.size(), config.getBaseFolder());
        files.keySet().parallelStream().forEach(s -> {
            File file = new File(s);
            BigInteger sizeInBytes = FileUtils.sizeOfAsBigInteger(file);
            BigInteger sizeInKB = sizeInBytes.divide(BigInteger.valueOf(1024));
            LOGGER.debug("Calculated size of {}={} KB", s, sizeInKB);
            files.put(file.getAbsolutePath(), sizeInKB);
        });
        LOGGER.info("Recalculating took {} ms.", (System.currentTimeMillis() - start));
    }
}
