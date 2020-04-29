package de.jenkinsutil.payload.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildJob {

    @JsonProperty("x")
    private String nodeName;

    @JsonProperty("y")
    private long[] startAndEnd = new long[2];

//    @JsonProperty("fillColor")
//    private String fillColor;

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setStart(long start) {
        startAndEnd[0] = start;
    }

    public void setEnd(long end) {
        startAndEnd[1] = end;
    }


}