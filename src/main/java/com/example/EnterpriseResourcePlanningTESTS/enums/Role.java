package com.example.EnterpriseResourcePlanningTESTS.enums;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}