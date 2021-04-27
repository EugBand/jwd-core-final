package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface SpaceshipService {

    List<Spaceship> findAllSpaceships();

    List<Spaceship> findAllSpaceshipsByCriteria(Predicate<? extends Spaceship> criteria);

    Optional<Spaceship> findSpaceshipByCriteria(Predicate<? extends Spaceship> criteria);

    Spaceship updateSpaceshipDetails(Spaceship spaceship);

    // todo create custom exception for case, when spaceship is not able to be assigned
    void assignSpaceshipOnMission(Spaceship ship, boolean assign) throws RuntimeException;

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // spaceship unique criteria - only name!
    Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException;

    Map<Role, Short> getCrewStructure(Spaceship spaceship);

    void assignMembersToShipCrew(Spaceship spaceship, CrewMember... members);

    String getName(Spaceship ship);
}
