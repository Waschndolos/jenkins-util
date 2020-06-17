package de.jenkinsutil.payload.mapping;

public class Filter {

    private final String builtOn;
    private final String jobName;
    private final String lookbackDays;

    public Filter(String builtOn, String jobName, String lookbackDays) {
        this.builtOn = builtOn;
        this.jobName = jobName;
        this.lookbackDays = lookbackDays;
    }

    public String getBuiltOn() {
        return builtOn;
    }

    public String getJobName() {
        return jobName;
    }

    public String getLookbackDays() {
        return lookbackDays;
    }
}
