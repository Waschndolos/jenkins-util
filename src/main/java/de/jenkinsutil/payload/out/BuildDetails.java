package de.jenkinsutil.payload.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BuildDetails {

    // this is the slave name
    @JsonProperty("name")
    private String slave;

//    private String jobName;

    // this is the start time
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime buildStartTime;

    // this is the duration in seconds
    @JsonProperty("value")
    private int durationInSeconds;

    public BuildDetails() {
    }

//    public String getJobName() {
//        return jobName;
//    }
//
//    public void setJobName(String jobName) {
//        this.jobName = jobName;
//    }

    public String getSlave() {
        return slave;
    }

    public void setSlave(String slave) {
        this.slave = slave;
    }

    public LocalDateTime getBuildStartTime() {
        return buildStartTime;
    }

    public void setBuildStartTime(LocalDateTime buildStartTime) {
        this.buildStartTime = buildStartTime;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
}
