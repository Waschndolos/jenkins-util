package de.jenkinsutil.payload.mapping;

import de.jenkinsutil.payload.in.JenkinsBuild;
import de.jenkinsutil.payload.in.JenkinsBuildDistribution;
import de.jenkinsutil.payload.in.JenkinsJob;
import de.jenkinsutil.payload.out.BuildDay;
import de.jenkinsutil.payload.out.BuildDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class BuildDistributionMapper {

    public static final int MILLISECONDS_IN_SECOND = 1000;

    public List<BuildDay> convert(JenkinsBuildDistribution input) {

        List<BuildDay> buildDays = new ArrayList<>();

        List<JenkinsJob> jobs = input.getJobs();
        for (JenkinsJob job : jobs) {

            List<JenkinsBuild> builds = job.getBuilds();
            for (JenkinsBuild build: builds) {
                long timestamp = build.getTimestamp();
                LocalDateTime buildStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
                LocalDate localDate = buildStart.toLocalDate();
                if (buildStart.isBefore(LocalDateTime.now().minusYears(1))) {
                    continue;
                }
                Optional<BuildDay> optional = buildDays.stream().filter(day -> day.getDate().isEqual(localDate)).findAny();
                if (optional.isPresent()) {
                    BuildDay buildDay = optional.get();
                    BuildDetails details = new BuildDetails();
//                    details.setJobName(job.getName() + build.getNumber());
                    details.setSlave(StringUtils.isEmpty(build.getBuiltOn()) ? "unknown" : build.getBuiltOn());
                    details.setBuildStartTime(buildStart);
                    details.setDurationInSeconds(Math.toIntExact(build.getDuration() / MILLISECONDS_IN_SECOND));
                    buildDay.addBuildDetail(details);
                } else {
                    BuildDay buildDay = new BuildDay();
                    buildDay.setDate(localDate);
                    BuildDetails details = new BuildDetails();
//                    details.setJobName(job.getName() + build.getNumber());
                    details.setSlave(StringUtils.isEmpty(build.getBuiltOn()) ? "unknown" : build.getBuiltOn());

                    details.setBuildStartTime(buildStart);
                    details.setDurationInSeconds(Math.toIntExact(build.getDuration() / MILLISECONDS_IN_SECOND));
                    buildDay.addBuildDetail(details);
                    buildDays.add(buildDay);
                }
            }
        }

        return buildDays;
    }
}
