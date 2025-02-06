package com.example.ValorantAPI;

public class Abilities {

    private String slot;
    private String name;
    private String description;

    // Constructor
    public Abilities(String slot, String name, String description) {
        this.slot = slot;
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
