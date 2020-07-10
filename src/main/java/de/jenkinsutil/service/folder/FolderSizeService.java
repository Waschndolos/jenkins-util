package de.jenkinsutil.service.folder;

import de.jenkinsutil.config.FolderMonitorConfig;
import de.jenkinsutil.config.MetricsConfiguration;
import de.jenkinsutil.service.MetricProvider;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@EnableScheduling
public class FolderSizeService implements MetricProvider {

    private final MetricsConfiguration configuration;

    public FolderSizeService(MetricsConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void registerBuilders(MeterRegistry registry) throws Exception {

        List<FolderMonitorConfig> folderMonitorConfigs = configuration.getFolder();

        for (FolderMonitorConfig config: folderMonitorConfigs) {
            FolderSizeCalculator calculator = new FolderSizeCalculator(config, registry);
            calculator.init();
            for (String s : calculator.getFoldersToCheck()) {

                Gauge.builder("folder.size.mb", calculator, (c) -> c.getFolderSize(s))
                        .tag("absolute", s)
                        .tag("folderName", new File(s).getName())
                        .register(registry);
            }
        }
    }

}
