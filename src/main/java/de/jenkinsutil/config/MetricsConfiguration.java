package de.jenkinsutil.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "metrics-config")
public class MetricsConfiguration {

    private List<FolderMonitorConfig> folder;

    private List<JenkinsMonitorConfig> jenkins;

    private List<FileMonitorConfig> files;

    public List<FileMonitorConfig> getFiles() {
        return files;
    }

    public void setFiles(List<FileMonitorConfig> files) {
        this.files = files;
    }

    public List<FolderMonitorConfig> getFolder() {
        return folder;
    }

    public void setFolder(List<FolderMonitorConfig> folder) {
        this.folder = folder;
    }

    public List<JenkinsMonitorConfig> getJenkins() {
        return jenkins;
    }

    public void setJenkins(List<JenkinsMonitorConfig> jenkins) {
        this.jenkins = jenkins;
    }
}
