package de.jenkinsutil.service.jenkins;


import com.offbytwo.jenkins.JenkinsServer;
import de.jenkinsutil.config.MetricsConfiguration;
import de.jenkinsutil.service.MetricProvider;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class JenkinsMetricService implements MetricProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsMetricService.class);

    protected List<Meter.Builder> builders = new ArrayList<>();

    protected MetricsConfiguration configuration;

    @Autowired
    public void setConfiguration(MetricsConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean isEnabled() {
        return configuration.isMonitoringJenkinsEnabled();
    }

    @Override
    public void registerBuilders(MeterRegistry registry) throws Exception {
        if (!isEnabled()) {
            LOGGER.info("monitor.jenkins.enabled is set to false. Ignoring Jenkins Metrics");
            return;
        }
        registerBuildersInternal(registry);
    }

    protected abstract void registerBuildersInternal(MeterRegistry registry) throws Exception;
}
