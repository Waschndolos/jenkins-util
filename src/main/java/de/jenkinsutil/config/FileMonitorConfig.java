package de.jenkinsutil.config;

public class FileMonitorConfig {

    private String baseFolder;
    private String fileExtension;
    private int scanIntervalHours;

    public int getScanIntervalHours() {
        return scanIntervalHours;
    }

    public void setScanIntervalHours(int scanIntervalHours) {
        this.scanIntervalHours = scanIntervalHours;
    }

    public String getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
