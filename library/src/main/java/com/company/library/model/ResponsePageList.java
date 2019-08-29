package com.company.library.model;

import java.util.List;

public class ResponsePageList<T> {
    private int nrOfElements;
    private List<T> pageList;

    public int getNrOfElements() {
        return nrOfElements;
    }

    public void setNrOfElements(int nrOfElements) {
        this.nrOfElements = nrOfElements;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
