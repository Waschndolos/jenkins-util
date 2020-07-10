package de.jenkinsutil.service.jenkins;


import de.jenkinsutil.config.MetricsConfiguration;
import de.jenkinsutil.service.MetricProvider;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JenkinsMetricService implements MetricProvider {

    protected MetricsConfiguration configuration;

    @Autowired
    public void setConfiguration(MetricsConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void registerBuilders(MeterRegistry registry) throws Exception {
        registerBuildersInternal(registry);
    }

    protected abstract void registerBuildersInternal(MeterRegistry registry) throws Exception;
}
