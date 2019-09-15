package com.company.library.enums;

public enum OrderBy {
    ID("id"), TITLE("title"), VALUE("averageStars"), BORROW_COUNT("borrowCount");
    private String OrderByCode;

    OrderBy(String orderBy) {
        this.OrderByCode = orderBy;
    }

    public String getOrderByCode() {
        return this.OrderByCode;
    }
}