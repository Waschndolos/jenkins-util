package de.jenkinsutil.payload.mapping;

import de.jenkinsutil.payload.in.JenkinsBuild;
import de.jenkinsutil.payload.in.JenkinsBuildDistribution;
import de.jenkinsutil.payload.in.JenkinsJob;
import de.jenkinsutil.payload.out.BuildJob;
import de.jenkinsutil.payload.out.Series;
import de.jenkinsutil.payload.out.SeriesHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class BuildDistributionMapper {

    public SeriesHolder convert(JenkinsBuildDistribution input) {

        SeriesHolder holder = new SeriesHolder();


        Map<String, Series> seriesMap = new HashMap<>();
        List<JenkinsJob> jobs = input.getJobs();
        for (JenkinsJob job : jobs) {

            String seriesName = job.getName().toLowerCase().contains("test") ? "test" : "other";

            Series s = getSeriesFromMap(seriesName, seriesMap);
            List<JenkinsBuild> builds = job.getBuilds();
            for (JenkinsBuild build: builds) {
                long timestamp = build.getTimestamp();
                LocalDateTime buildStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
                if (buildStart.isBefore(LocalDateTime.now().minusDays(2))) {
                    continue;
                }

                BuildJob buildJob = new BuildJob();
                buildJob.setNodeName(getBuildOn(build));
                buildJob.setStart(build.getTimestamp());
                buildJob.setEnd(build.getTimestamp() + build.getDuration());
                s.addBuildJob(buildJob);
            }


        }

        List<Series> series = new ArrayList<>(seriesMap.values());
        holder.setSeries(series);
        return holder;
    }

    private Series getSeriesFromMap(String key, Map<String, Series> seriesMap) {
        if (!seriesMap.containsKey(key)) {
            Series series = new Series();
            series.setName(key);
            seriesMap.put(key, series);
        }
        return seriesMap.get(key);
    }

    private String getBuildOn(JenkinsBuild build) {
        return build.getBuiltOn() == null ? "unknown" : build.getBuiltOn();
    }


}
