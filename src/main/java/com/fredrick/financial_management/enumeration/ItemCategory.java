package com.fredrick.financial_management.enumeration;

public enum ItemCategory {
    FOOD_OR_DRINK("Food or drink "),
    OTHER("Other"),
    CLOTHES("Clothes"),
    ENTERTAINMENT("Entertainment"),
    TRANSPORTATION("Transportation"),
    UTILITY("Utility"),
    SERVICE("Service"),
    HEALTH("Health"),
    SHOPPING("Shopping"),
    INSURANCE("Insurance"),
    SUPPLY("Supply"),
    EDUCATION("Education"),
    GIFT_OR_DONATION("Gift or donation");
    private String name;

    ItemCategory(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public static ItemCategory getCategoryByName(String categoryName) {
        if(categoryName == null || categoryName.equals(""))
            return null;
        for (ItemCategory category : ItemCategory.values()) {
            if (category.getName().toLowerCase().contains(categoryName.toLowerCase())) {
                return category;
            }
        }
        return null;
    }
}
