package de.jenkinsutil.service.file;

import de.jenkinsutil.config.FileMonitorConfig;
import de.jenkinsutil.config.MetricsConfiguration;
import de.jenkinsutil.service.MetricProvider;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class FileSizeService implements MetricProvider {

    private final MetricsConfiguration configuration;

    public FileSizeService(MetricsConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void registerBuilders(MeterRegistry registry) throws Exception {
        List<FileMonitorConfig> fileMonitorConfigs = configuration.getFiles();

        for (FileMonitorConfig config: fileMonitorConfigs) {
            FileSizeCalculator calculator = new FileSizeCalculator(config, registry);
            calculator.init();
            for (String s : calculator.getFilesToCheck()) {

                Gauge.builder("file.size.kb", calculator, (c) -> c.getFileSize(s))
                        .tag("absolute", s)
                        .tag("fileName", new File(s).getName())
                        .register(registry);
            }
        }
    }
}
