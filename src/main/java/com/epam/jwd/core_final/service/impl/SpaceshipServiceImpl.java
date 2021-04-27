package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.annotation.ISingleton;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.serviceexception.StreamServiceException;
import com.epam.jwd.core_final.exception.serviceexception.UpdateServiceException;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;
import javassist.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;


@SuppressWarnings("all")
@ISingleton
public final class SpaceshipServiceImpl implements SpaceshipService {
    private static SpaceshipService instance;
    IAppLogger logger = AppLogger.getInstance();
    private List<Spaceship> ships = Optional.ofNullable((List<Spaceship>) NassaContext.getInstance()
            .retrieveBaseEntityList(Spaceship.class)).orElseThrow(IllegalStateException::new);

    private SpaceshipServiceImpl() {
    }

    public static SpaceshipService getInstance() {
        if (instance == null) {
            instance = new SpaceshipServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return ships;
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Predicate<? extends Spaceship> criteria) {
        return ships.stream()
                .filter((Predicate<? super Spaceship>) criteria).collect(Collectors.<Spaceship>toList());

    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Predicate<? extends Spaceship> criteria) {
        return ships.stream()
                .filter((Predicate<? super Spaceship>) criteria).findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        Spaceship replacedShip = null;
        try {
            replacedShip = ships.stream().filter(item -> item.getName().equals(spaceship.getName()))
                    .findFirst().orElseThrow(() -> new NotFoundException("Member not found"));
            ships.set(ships.indexOf(replacedShip), spaceship);
        } catch (NotFoundException e) {
            logger.log(ERROR, "Member not found", e);
        }
        return spaceship;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship, boolean assign) throws RuntimeException {
        try {
            spaceship.setReadyForNextMissions(!assign);
        } catch (UpdateServiceException e) {
            logger.log(ERROR, "Can't update spaceship", e);
        }
        updateSpaceshipDetails(spaceship);
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        if (ships.stream().filter(item -> item.getName().equals(spaceship.getName()))
                .findFirst().isEmpty()) {
            ships.add(spaceship);
        } else try {
            throw new StreamServiceException("Spaceship probaly exists");
        } catch (StreamServiceException e) {
            logger.log(ERROR, "Spaceship probaly exists", e);
        }
        return spaceship;
    }

    @Override
    public Map<Role, Short> getCrewStructure(Spaceship spaceship) {
        return spaceship.getCrewStructure();
    }

    @Override
    public void assignMembersToShipCrew(Spaceship spaceship, CrewMember... members) {
        for (CrewMember member : members) {
            spaceship.getAssignedCrew().add(member);
            member.setReadyForNextMissions(false);
        }
    }

    @Override
    public String getName(Spaceship ship) {
        return ship.getName();
    }
}
