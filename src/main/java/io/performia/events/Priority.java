package io.performia.events;

public enum Priority {

    HIGH(1), // Called first
    NORMAL(0),
    LOW(-1); // Called last

    private int value;
    Priority(int value) {
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
