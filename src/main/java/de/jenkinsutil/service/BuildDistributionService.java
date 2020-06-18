package de.jenkinsutil.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jenkinsutil.payload.in.JenkinsBuildDistribution;
import de.jenkinsutil.payload.mapping.BuildDistributionMapper;
import de.jenkinsutil.payload.mapping.Filter;
import de.jenkinsutil.payload.mapping.RowMapper;
import de.jenkinsutil.payload.out.Row;
import de.jenkinsutil.payload.out.SeriesHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BuildDistributionService {

    private final BuildDistributionMapper buildDistributionMapper;

    private final RowMapper rowMapper;

    public BuildDistributionService(BuildDistributionMapper buildDistributionMapper, RowMapper rowMapper) {
        this.buildDistributionMapper = buildDistributionMapper;
        this.rowMapper = rowMapper;
    }

    public Optional<SeriesHolder> getBuildDays(String builtOn, String jobName, String lookbackDays) throws IOException {
        JenkinsBuildDistribution jenkinsBuildDistribution = readBuildDistribution();
        return Optional.of(buildDistributionMapper.convert(jenkinsBuildDistribution, new Filter(builtOn, jobName, lookbackDays)));
    }

    public Optional<List<Row>> getRows(String builtOn, String jobName, String lookbackDays) throws IOException {
        JenkinsBuildDistribution jenkinsBuildDistribution = readBuildDistribution();
        return Optional.of(rowMapper.convert(jenkinsBuildDistribution, new Filter(builtOn, jobName, lookbackDays)));
    }

    private JenkinsBuildDistribution readBuildDistribution() throws IOException {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject("http://my.jenkins:8080/api/json?tree=jobs[name,builds[number,result,duration,builtOn,timestamp]]", JenkinsBuildDistribution.class);
        File jsonFile = new File("/home/manuel/Dokumente/jenkins_build_overview.json");
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFile, JenkinsBuildDistribution.class);
    }
}
