package de.jenkinsutil.service;

import de.jenkinsutil.service.file.FileSizeService;
import de.jenkinsutil.service.folder.FolderSizeService;
import de.jenkinsutil.service.jenkins.JenkinsSlaveLoadStatisticService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {



    public MetricsService(MeterRegistry meterRegistry, JenkinsSlaveLoadStatisticService jenkinsSlaveLoadStatisticService, FolderSizeService folderSizeService, FileSizeService fileSizeService) throws Exception {
        jenkinsSlaveLoadStatisticService.registerBuilders(meterRegistry);
        folderSizeService.registerBuilders(meterRegistry);
        fileSizeService.registerBuilders(meterRegistry);
    }
}
