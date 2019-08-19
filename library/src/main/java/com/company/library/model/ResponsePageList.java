package com.company.library.model;

import java.util.List;

public class ResponsePageList {
    private int nrOfElements;
    private List<Book> pageList;

    public int getNrOfElements() {
        return nrOfElements;
    }

    public void setNrOfElements(int nrOfElements) {
        this.nrOfElements = nrOfElements;
    }

    public List<Book> getPageList() {
        return pageList;
    }

    public void setPageList(List<Book> pageList) {
        this.pageList = pageList;
    }
}
