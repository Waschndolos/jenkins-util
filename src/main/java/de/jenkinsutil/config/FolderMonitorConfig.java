package de.jenkinsutil.config;

public class FolderMonitorConfig {

    private String baseFolder;
    private int depth;
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
