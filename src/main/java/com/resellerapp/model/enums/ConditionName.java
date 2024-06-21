package com.resellerapp.model.enums;

public enum ConditionName {

    EXCELLENT ("Excellent"),
    GOOD ("Good"),
    ACCEPTABLE ("Acceptable");

    private String value;

    private ConditionName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
