package de.jenkinsutil.controller;

import de.jenkinsutil.payload.out.BuildDistribution;
import de.jenkinsutil.service.BuildDistributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller("/api/v1/jenkins/build")
public class BuildDistributionController {

    private final BuildDistributionService buildDistributionService;

    public BuildDistributionController(BuildDistributionService buildDistributionService) {
        this.buildDistributionService = buildDistributionService;
    }

    @GetMapping("/distribution")
    public ResponseEntity<BuildDistribution> getBuildDistribution() {
        Optional<BuildDistribution> optional = buildDistributionService.getBuildDistribution();
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
