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

    public SeriesHolder convert(JenkinsBuildDistribution input, String filter) {

        SeriesHolder holder = new SeriesHolder();

        Map<String, Series> seriesMap = new HashMap<>();
        List<JenkinsJob> jobs = input.getJobs();
        for (JenkinsJob job : jobs) {
            List<String> jobFilter = getJobFilter(filter);
            if (!jobFilter.isEmpty()) {
                if (jobFilter.stream().noneMatch(j -> job.getName().startsWith(j))) {
                    continue;
                }
            }

            String seriesName = getSeriesName(job.getName());

            Series s = getSeriesFromMap(seriesName, seriesMap);
            List<JenkinsBuild> builds = job.getBuilds();
            for (JenkinsBuild build : builds) {
                if (build.getBuiltOn() == null) {
                    continue;
                }

                List<String> builtOnFilter = getBuiltOnFilter(filter);
                if (!builtOnFilter.isEmpty()) {
                    if (builtOnFilter.stream().noneMatch(b -> build.getBuiltOn().startsWith(b))) {
                        continue;
                    }
                }

                long timestamp = build.getTimestamp();
                LocalDateTime buildStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
                if (buildStart.isBefore(LocalDateTime.now().minusDays(2))) {
                    continue;
                }

                BuildJob buildJob = new BuildJob();
                buildJob.setNodeName(getBuildOn(build));
                buildJob.setJobName(job.getName());
                buildJob.setStart(build.getTimestamp());
                buildJob.setEnd(build.getTimestamp() + build.getDuration());
                s.addBuildJob(buildJob);
            }

        }

        List<Series> series = new ArrayList<>(seriesMap.values());
        holder.setSeries(series);
        return holder;
    }

    private String getSeriesName(String jobName) {
        if (jobName.toLowerCase().contains("test")) {
           return "TEST";
        } else if (jobName.toLowerCase().contains("autosar")) {
            return "AUTOSAR";
        } else if (jobName.toLowerCase().contains("ci")) {
            return "CI";
        } else {
            return "OTHER";
        }
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


    private List<String> getBuiltOnFilter(String filter) {
        if (filter != null) {
            String[] split = filter.split("&");
            for (String f : split) {
                String[] filterToken = f.split("=");
                if (filterToken[0].equals("builtOn")) {
                    return Arrays.asList(filterToken[1].split(","));
                }
            }
        }
        return new ArrayList<>();
    }

    private List<String> getJobFilter(String filter) {
        if (filter != null) {
            String[] split = filter.split("&");
            for (String f : split) {
                String[] filterToken = f.split("=");
                if (filterToken[0].equals("jobName")) {
                    return Arrays.asList(filterToken[1].split(","));
                }
            }
        }
        return new ArrayList<>();
    }

}
