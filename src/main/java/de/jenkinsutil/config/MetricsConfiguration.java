package de.jenkinsutil.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MetricsConfiguration {

    @Value("${monitor.jenkins.url}")
    private String jenkinsUrl;

    @Value("${monitor.jenkins.enabled}")
    private boolean monitoringJenkinsEnabled;

    @Value("${monitor.folder.enabled}")
    private boolean monitoringFolderEnabled;

    @Value("${monitor.folder.base}")
    private String baseFolder;

    @Value("${monitor.folder.depth}")
    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }

    public boolean isMonitoringJenkinsEnabled() {
        return monitoringJenkinsEnabled;
    }

    public void setMonitoringJenkinsEnabled(boolean monitoringJenkinsEnabled) {
        this.monitoringJenkinsEnabled = monitoringJenkinsEnabled;
    }

    public boolean isMonitoringFolderEnabled() {
        return monitoringFolderEnabled;
    }

    public void setMonitoringFolderEnabled(boolean monitoringFolderEnabled) {
        this.monitoringFolderEnabled = monitoringFolderEnabled;
    }

}
