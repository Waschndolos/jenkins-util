package de.jenkinsutil.config;

public class JenkinsMonitorConfig {

    private String jenkinsUrl;

    public JenkinsMonitorConfig(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }

    public JenkinsMonitorConfig() { }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }
}
