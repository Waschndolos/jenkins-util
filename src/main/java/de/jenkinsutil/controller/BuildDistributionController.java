package de.jenkinsutil.controller;

import de.jenkinsutil.payload.out.BuildDay;
import de.jenkinsutil.service.BuildDistributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<BuildDay>> getBuildDistribution() {
        Optional<List<BuildDay>> optional = buildDistributionService.getBuildDays();
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
