package de.jenkinsutil.service;

import io.micrometer.core.instrument.MeterRegistry;

public interface MetricProvider {

    void registerBuilders(MeterRegistry registry) throws Exception;
}
