package com.example.EnterpriseResourcePlanningTESTS.enums;

public enum Type {
    URGENT ("URGENT"),
    IMPORTANT ("IMPORTANT"),
    NORMAL ("NORMAL");
    private final String name;
    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

