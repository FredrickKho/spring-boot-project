package com.fredrick.financial_management.enumeration;

public enum ItemCategory {
    FOODANDDRINK("Food or drink "),
    OTHER("Other"),
    CLOTHES("Clothes"),
    ENTERTAINMENT("Entertainment"),
    TRANSPORTATION("Transportation"),
    UTILITY("Utility"),
    SERVICE("Service"),
    HEALTH("Health/Medicine"),
    SHOPPING("Shopping"),
    INSURANCE("Insurance"),
    SUPPLY("Supply"),
    EDUCATION("Education"),
    GIFT("Gift/Donation");
    private String name;

    ItemCategory(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
