package de.jenkinsutil.controller;

import de.jenkinsutil.payload.out.Row;
import de.jenkinsutil.payload.out.SeriesHolder;
import de.jenkinsutil.service.BuildDistributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jenkins/build")
public class BuildDistributionController {

    private final BuildDistributionService buildDistributionService;

    public BuildDistributionController(BuildDistributionService buildDistributionService) {
        this.buildDistributionService = buildDistributionService;
    }

    @GetMapping("/distribution")
    public ResponseEntity<List<Row>> getBuildDistribution(
            @RequestParam(name = "builtOn", required = false) String builtOn,
            @RequestParam(name = "jobName", required = false) String jobName,
            @RequestParam(name = "lookbackDays", required = false) String lookbackDays
    ) throws IOException {
        Optional<List<Row>> optional = buildDistributionService.getRows(builtOn, jobName, lookbackDays);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

}
