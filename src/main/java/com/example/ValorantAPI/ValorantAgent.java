package com.example.ValorantAPI;

import java.util.List;

public class ValorantAgent {

    private String uuid;
    private String displayName;
    private String description;
    private String role;
    private List<Abilities> abilities;

    // Constructor
    public ValorantAgent(String uuid, String displayName, String description, String role, List<Abilities> abilities) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.description = description;
        this.role = role;
        this.abilities = abilities;

    }

    // Getters
    public String getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getRole() {
        return role;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "ValorantAgent{" +
                "uuid='" + uuid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", role='" + role + '\'' +
                ", abilities=" + abilities +
                '}';
    }

}

