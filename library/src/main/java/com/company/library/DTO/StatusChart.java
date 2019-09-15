package com.company.library.DTO;

public class StatusChart {
    private long y;
    private String name;

    public StatusChart(long y, String name) {
        this.y = y;
        this.name = name;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
