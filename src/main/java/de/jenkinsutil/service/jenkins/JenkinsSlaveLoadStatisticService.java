package de.jenkinsutil.service.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.ComputerSet;
import com.offbytwo.jenkins.model.ComputerWithDetails;
import com.offbytwo.jenkins.model.Executor;
import com.offbytwo.jenkins.model.Job;
import de.jenkinsutil.config.JenkinsMonitorConfig;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Service
public class JenkinsSlaveLoadStatisticService extends JenkinsMetricService {

    public double calculateLoadStatistic(ComputerWithDetails computer) {
        List<Executor> executors = computer.getExecutors();

        List<Job> jobsBuildingOnComputer = new ArrayList<>();
        for (Executor executor : executors) {
            Job currentWorkUnit = executor.getCurrentExecutable();
            if (currentWorkUnit != null) {
                jobsBuildingOnComputer.add(currentWorkUnit);
            }
        }
        return jobsBuildingOnComputer.isEmpty() ? 0.0 : 1.0;
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
            Gauge.builder("slave.building", this, (statisticService) -> statisticService.calculateLoadStatistic(c)).tag("computer", c.getDisplayName()).register(registry);
        }
    }

}
