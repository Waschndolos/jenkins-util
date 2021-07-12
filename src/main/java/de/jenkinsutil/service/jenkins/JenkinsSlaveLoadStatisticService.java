package de.jenkinsutil.service.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.ComputerSet;
import com.offbytwo.jenkins.model.ComputerWithDetails;
import com.offbytwo.jenkins.model.Executor;
import de.jenkinsutil.config.JenkinsMonitorConfig;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@Service
public class JenkinsSlaveLoadStatisticService extends JenkinsMetricService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsSlaveLoadStatisticService.class);

    public double calculateLoadStatistic(ComputerWithDetails computer) {
        LOGGER.info("Calculating Load Statistic of {}", computer.getDisplayName());
        List<Executor> executors = computer.getExecutors();

        boolean isIdle = true;
        for (Executor executor : executors) {
            if (executor != null && !executor.getIdle()) {
                isIdle = false;
            }
        }
        return isIdle ? 0.0 : 1.0;
    }


    public void registerBuildersInternal(MeterRegistry registry) throws IOException, URISyntaxException {
        List<JenkinsMonitorConfig> configs = configuration.getJenkins();

        if (configs == null) {
            return;
        }

        for (JenkinsMonitorConfig config : configs) {
            registerURL(registry, config);
        }
    }

    private void registerURL(MeterRegistry registry, JenkinsMonitorConfig config) throws URISyntaxException, IOException {
        String jenkinsUrl = config.getJenkinsUrl();
        JenkinsServer server = new JenkinsServer(new URI(jenkinsUrl));
        ComputerSet computerSet = server.getComputerSet();
        for (ComputerWithDetails c : computerSet.getComputers()) {
            Gauge.builder("agent.building", this, (statisticService) -> statisticService.calculateLoadStatistic(c)).tag("computer", c.getDisplayName()).register(registry);
        }
    }

}
