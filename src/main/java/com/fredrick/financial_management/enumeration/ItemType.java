package com.fredrick.financial_management.enumeration;

public enum ItemType {
    EARNING("Earning"),SPENDING("Spending");
    private String name;

    ItemType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public static ItemType getTypeByName(String type) {
        if(type == null || type.equals(""))
            return null;
        for (ItemType types : ItemType.values()) {
            if (types.getName().toLowerCase().contains(type.toLowerCase())) {
                return types;
            }
        }
        return null;
    }
}
