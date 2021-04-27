package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@SuppressWarnings("all")
public final class MissionCompleter extends MissionMaintainer {
    private static MissionCompleter instance;

    private MissionCompleter() {
    }

    public static MissionCompleter getInstance() {
        if (instance == null) instance = new MissionCompleter();
        return instance;
    }

    List<Spaceship> shipComplete(boolean isSmartComplect) {
        List<Spaceship> complectedShip = new ArrayList<>();
        List<Spaceship> startShips = ships;
        Role rareRole = Role.FLIGHT_ENGINEER;
        if (isSmartComplect) startShips = sort(ships, rareRole);
        for (Spaceship ship : startShips) {
            if (assignCrewToShip(ship)) complectedShip.add(ship);
        }
        return complectedShip;
    }

    private List<Spaceship> sort(List<Spaceship> ships, Role role) {
        return ships.stream().sorted(Comparator.comparingInt(item -> item.getCrewStructure().get(role)))
                .collect(Collectors.toList());
    }

    private boolean assignCrewToShip(Spaceship ship) {
        Map<Role, Short> crewStructure = shipService.getCrewStructure(ship);
        for (Map.Entry<Role, Short> role : crewStructure.entrySet()) {
            List<CrewMember> aviableMembers = new ArrayList<>(crewService
                    .findAllCrewMembersByCriteria(CrewMemberCriteria
                            .fetchReady(true).and(CrewMemberCriteria.fetchByRole(role.getKey()))));
            if (role.getValue() >= aviableMembers.size()) {
                return false;
            }
        }
        for (Map.Entry<Role, Short> role : crewStructure.entrySet()) {
            List<CrewMember> avialableMembers = new ArrayList<>(crewService
                    .findAllCrewMembersByCriteria(CrewMemberCriteria
                            .fetchReady(true).and(CrewMemberCriteria.fetchByRole(role.getKey()))));
            for (int i = 0; i < role.getValue(); i++) {
                shipService.assignMembersToShipCrew(ship, avialableMembers.get(i));
            }
        }
        return true;
    }
}
