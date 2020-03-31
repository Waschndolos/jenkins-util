package de.jenkinsutil.payload.in;

import java.util.List;

public class JenkinsBuildDistribution {
    private List<JenkinsJob> jobs;

    public JenkinsBuildDistribution() {
    }

    public List<JenkinsJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<JenkinsJob> jobs) {
        this.jobs = jobs;
    }
}
