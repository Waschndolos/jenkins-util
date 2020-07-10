package de.jenkinsutil.service.folder;

import de.jenkinsutil.config.FolderMonitorConfig;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class FolderSizeCalculator implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderSizeCalculator.class);

    private final FolderMonitorConfig config;
    private final MeterRegistry registry;
    private final Map<String, Meter.Id> dynamicallyAddedGauges = new HashMap<>();
    private final Map<String, BigInteger> folders = new ConcurrentHashMap<>();

    public FolderSizeCalculator(FolderMonitorConfig config, MeterRegistry registry) {
        this.config = config;
        this.registry = registry;
    }

    public void init() throws IOException {
        initFoldersToCheck();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this, 0, config.getScanIntervalHours(), TimeUnit.HOURS);
    }

    public void initFoldersToCheck() throws IOException {
        if (folders.isEmpty()) {
            List<String> foldersToCheck = getFolderPaths();

            LOGGER.info("Checking {}", foldersToCheck);
            for (String absolutePath : foldersToCheck) {
                folders.put(absolutePath, BigInteger.valueOf(-1));
            }
        }
    }

    private List<String> getFolderPaths() throws IOException {
        int minDepth = config.getDepth();
        int maxDepth = config.getDepth();
        Path rootPath = Paths.get(config.getBaseFolder());
        int rootPathDepth = rootPath.getNameCount();

        return Files.walk(rootPath, maxDepth)
                .filter(e -> e.toFile().isDirectory())
                .filter(e -> e.getNameCount() - rootPathDepth >= minDepth)
                .map(path -> path.toFile().getAbsolutePath())
                .collect(Collectors.toList());
    }

    private void updateWatchlist() {
        List<String> foldersToCheck;
        try {
            foldersToCheck = getFolderPaths();
        } catch (IOException e) {
            LOGGER.error("Unable to update watch list. Using initial List.", e);
            return;
        }

        for (String absolutePath : foldersToCheck) {
            if (!folders.containsKey(absolutePath)) {
                LOGGER.info("Found new Folder {}. Adding to monitoring...", absolutePath);
                folders.put(absolutePath, BigInteger.valueOf(-1));

                Gauge gauge = Gauge.builder("folder.size.mb", this, (c) -> c.getFolderSize(absolutePath))
                        .tag("absolute", absolutePath)
                        .tag("folderName", new File(absolutePath).getName())
                        .register(registry);

                dynamicallyAddedGauges.put(absolutePath, gauge.getId());
            }
        }

        for (String key : folders.keySet()) {
            if (!foldersToCheck.contains(key)) {
                LOGGER.info("Folder {} no longer exists. Removing from monitoring...", key);
                Meter.Id id = dynamicallyAddedGauges.get(key);
                folders.remove(key);
                if (id != null) {
                    registry.remove(id);
                }
            }
        }

    }

    public double getFolderSize(String folder) {
        BigInteger size = folders.get(folder);
        if (size == null) {
            return -1;
        } else {
            return size.doubleValue();
        }
    }

    public Set<String> getFoldersToCheck() {
        return folders.keySet();
    }

    @Override
    public void run() {
        updateWatchlist();
        long start = System.currentTimeMillis();
        LOGGER.info("Recalculating sizes of {} folders within {}...", folders.size(), config.getBaseFolder());
        folders.keySet().parallelStream().forEach(s -> {
            File file = new File(s);
            BigInteger sizeInBytes = FileUtils.sizeOfDirectoryAsBigInteger(file);
            BigInteger sizeInMB = sizeInBytes.divide(BigInteger.valueOf(1024)).divide(BigInteger.valueOf(1024));
            LOGGER.debug("Calculated size of {}={}", s, sizeInMB);
            folders.put(file.getAbsolutePath(), sizeInMB);
        });
        LOGGER.info("Recalculating took {} ms.", (System.currentTimeMillis() - start));
    }
}
