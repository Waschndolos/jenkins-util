package de.jenkinsutil.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jenkinsutil.payload.in.JenkinsBuildDistribution;
import de.jenkinsutil.payload.mapping.BuildDistributionMapper;
import de.jenkinsutil.payload.out.BuildDay;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BuildDistributionService {

    private final BuildDistributionMapper buildDistributionMapper;

    public BuildDistributionService(BuildDistributionMapper buildDistributionMapper) {
        this.buildDistributionMapper = buildDistributionMapper;
    }

    public Optional<List<BuildDay>> getBuildDays() {

        File jsonFile = new File("/home/manuel/Dokumente/jenkins_build_overview.json");

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JenkinsBuildDistribution jenkinsBuildDistribution = mapper.readValue(jsonFile, JenkinsBuildDistribution.class);
            return Optional.of(buildDistributionMapper.convert(jenkinsBuildDistribution));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: implement
        return Optional.empty();
    }
}
