package com.epam.jwd.core_final.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */

@Getter
@SuppressWarnings("unused")
public class FlightMission extends AbstractBaseEntity {
    // todo
    private final LocalDate startDate;


    private LocalDate endDate;
    private final Long distance;
    private Spaceship assignedSpaceShip;
    private List<CrewMember> assignedCrew; //Probably this field d'better move to Spaceship class
    private MissionResult missionResult;
    private final Planet from;
    private final Planet to;

    public FlightMission(long id, String name, Spaceship ship, LocalDate startDate,
                         LocalDate endDate, Long distance, MissionResult missionResult, Planet from, Planet to) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignedSpaceShip = ship;
        this.distance = distance;
        this.missionResult = missionResult;
        this.from = from;
        this.to = to;
    }

    public void setAssignedSpaceShift(Spaceship assignedSpaceShift) {
        this.assignedSpaceShip = assignedSpaceShift;
    }

    public void setAssignedCrew(List<CrewMember> assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }
}
