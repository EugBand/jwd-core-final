package com.epam.jwd.core_final.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
@Getter
public class Spaceship extends AbstractBaseEntity {
    //todo
    private Map<Role, Short> crewStructure;
    private long flightDistance;
    private boolean isReadyForNextMissions = true;

    private List<CrewMember> assignedCrew = new ArrayList<>();

    public Spaceship(long id, String name, Map<Role, Short> crewStructure, long flightDistance) {
        this.id = id;
        this.name = name;
        this.crewStructure = crewStructure;
        this.flightDistance = flightDistance;
    }

    public Boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

}
