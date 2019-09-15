package com.company.library.DTO;

public class ChartObject {

    private long y;
    private String label;


    public ChartObject(long y, String label) {
        this.y = y;
        this.label = label;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
