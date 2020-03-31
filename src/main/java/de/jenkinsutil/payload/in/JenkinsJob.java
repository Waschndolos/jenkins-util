package de.jenkinsutil.payload.in;

import java.util.List;

public class JenkinsJob {
    private String name;
    private String url;
    private List<JenkinsBuild> builds;

    public JenkinsJob() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<JenkinsBuild> getBuilds() {
        return builds;
    }

    public void setBuilds(List<JenkinsBuild> builds) {
        this.builds = builds;
    }
}
