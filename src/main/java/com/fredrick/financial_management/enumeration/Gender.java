package com.fredrick.financial_management.enumeration;


import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"),FEMALE("Female");
    private final String name;
    Gender(String name) {
        this.name = name;
    }
    public static Gender getGenderByName(String gender) {
        if(gender == null || gender.equals(""))
            return null;
        for (Gender genders : Gender.values()) {
            if (genders.getName().toLowerCase().contains(gender.toLowerCase())) {
                return genders;
            }
        }
        return null;
    }
}
