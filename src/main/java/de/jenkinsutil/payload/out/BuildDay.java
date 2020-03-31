package de.jenkinsutil.payload.out;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BuildDay {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private int total = 0;
    private List<BuildDetails> details = new ArrayList<>();

    public BuildDay() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public List<BuildDetails> getDetails() {
        return details;
    }

    public void addBuildDetail(BuildDetails details) {
        this.details.add(details);
        this.total+=details.getDurationInSeconds();
    }

    public void setDetails(List<BuildDetails> details) {
        this.details = details;
    }
}
