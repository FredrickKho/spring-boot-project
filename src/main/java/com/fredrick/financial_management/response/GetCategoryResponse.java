package com.fredrick.financial_management.response;

public class GetCategoryResponse {
    private String name;

    public GetCategoryResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
