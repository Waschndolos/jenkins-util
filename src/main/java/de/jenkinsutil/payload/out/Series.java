package de.jenkinsutil.payload.out;

import java.util.ArrayList;
import java.util.List;

public class Series {

    private String name;
    private List<BuildJob> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BuildJob> getData() {
        return data;
    }

    public void setData(List<BuildJob> data) {
        this.data = data;
    }

    public void addBuildJob(BuildJob buildJob) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(buildJob);
    }
}
