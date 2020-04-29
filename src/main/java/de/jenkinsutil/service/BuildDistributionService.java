package de.jenkinsutil.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jenkinsutil.payload.in.JenkinsBuildDistribution;
import de.jenkinsutil.payload.mapping.BuildDistributionMapper;
import de.jenkinsutil.payload.out.SeriesHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class BuildDistributionService {

    private final BuildDistributionMapper buildDistributionMapper;

    public BuildDistributionService(BuildDistributionMapper buildDistributionMapper) {
        this.buildDistributionMapper = buildDistributionMapper;
    }

    public Optional<SeriesHolder> getBuildDays() throws IOException {
        JenkinsBuildDistribution jenkinsBuildDistribution = readBuildDistribution();
        return Optional.of(buildDistributionMapper.convert(jenkinsBuildDistribution));
    }

    private JenkinsBuildDistribution readBuildDistribution() throws IOException {
        File jsonFile = new File("/home/manuel/Dokumente/jenkins_build_overview.json");
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFile, JenkinsBuildDistribution.class);
    }
}
