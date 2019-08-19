package com.company.library.enums;

public enum OrderBy {
    ID("id"), TITLE("title");
    private String OrderByCode;

    OrderBy(String orderBy) {
        this.OrderByCode = orderBy;
    }

    public String getOrderByCode() {
        return this.OrderByCode;
    }
}