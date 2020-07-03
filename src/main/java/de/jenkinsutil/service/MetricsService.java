package de.jenkinsutil.service;

import de.jenkinsutil.service.jenkins.JenkinsSlaveLoadStatisticService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    public MetricsService(MeterRegistry meterRegistry, JenkinsSlaveLoadStatisticService jenkinsSlaveLoadStatisticService, FolderSizeService folderSizeService) throws Exception {
        jenkinsSlaveLoadStatisticService.registerBuilders(meterRegistry);
        folderSizeService.registerBuilders(meterRegistry);
    }
}
