package de.jenkinsutil.service;

import de.jenkinsutil.config.MetricsConfiguration;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class FolderSizeService implements MetricProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderSizeService.class);

    private final Map<String, BigInteger> folderSize = new HashMap<>();

    private final MetricsConfiguration configuration;

    public FolderSizeService(MetricsConfiguration configuration) {
        this.configuration = configuration;
    }

    public Set<String> getFoldersToCheck() throws IOException {
        if (folderSize.isEmpty()) {
            int minDepth = configuration.getDepth();
            int maxDepth = configuration.getDepth();
            Path rootPath = Paths.get(configuration.getBaseFolder());
            int rootPathDepth = rootPath.getNameCount();

            List<Path> foldersToCheck = Files.walk(rootPath, maxDepth)
                    .filter(e -> e.toFile().isDirectory())
                    .filter(e -> e.getNameCount() - rootPathDepth >= minDepth)
                    .collect(Collectors.toList());

            LOGGER.info("Checking {}", foldersToCheck);
            for (Path p : foldersToCheck) {
                folderSize.put(p.toFile().getAbsolutePath(), null);
            }

            return folderSize.keySet();
        }
        return new HashSet<>();
    }

    @Scheduled(cron = "${monitor.folder.cron}")
    public void calculateSize() {
        LOGGER.info("Calculating size of the following folders: " + folderSize.keySet());
        folderSize.keySet().parallelStream().forEach( s -> {
            CompletableFuture.supplyAsync(() -> {
                File file = new File(s);
                BigInteger sizeInBytes = FileUtils.sizeOfDirectoryAsBigInteger(file);
                BigInteger sizeInMB = sizeInBytes.divide(BigInteger.valueOf(1024)).divide(BigInteger.valueOf(1024));
                LOGGER.info("Calculated size of {}={}", s, sizeInMB);
                folderSize.put(file.getAbsolutePath(), sizeInMB);
                return true;
            });
        });
    }

    public double getFolderSize(String folder) {
        BigInteger size = folderSize.get(folder);
        if (size == null) {
            return -1;
        } else {
            return size.doubleValue();
        }
    }

    @Override
    public boolean isEnabled() {
        return configuration.isMonitoringFolderEnabled();
    }

    @Override
    public void registerBuilders(MeterRegistry registry) throws Exception {
        if (!isEnabled()) {
            LOGGER.info("monitor.folder.enabled is set to false. Ignoring Folder Metrics");
            return;
        }
        for (String s : getFoldersToCheck()) {
            String relativeFolder = calculateRelativeFolder(s);

            Gauge.builder("folder.size.mb", this, (folderSizeService) -> folderSizeService.getFolderSize(s))
                    .tag("absolute", s)
                    .tag("relative", relativeFolder)
                    .register(registry);
        }
        calculateSize();
    }

    private String calculateRelativeFolder(String s) {
        File f = new File(s);
        List<String> names = new ArrayList<>();

        names.add(f.getName());
        File parentFile = f.getParentFile();
        for (int i = 0; i <configuration.getDepth(); i++) {
            names.add(parentFile.getName());
            parentFile = parentFile.getParentFile();
        }
        Collections.reverse(names);
        return String.join("\\", names);
    }
}
