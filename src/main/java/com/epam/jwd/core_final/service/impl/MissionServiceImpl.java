package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.annotation.ISingleton;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.service.MissionService;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("all")
@ISingleton
public final class MissionServiceImpl implements MissionService {
    private static MissionServiceImpl instance;
    private List<FlightMission> missions = Optional.ofNullable((List<FlightMission>) NassaContext.getInstance()
            .retrieveBaseEntityList(FlightMission.class)).orElseThrow(IllegalStateException::new);

    private MissionServiceImpl() {

    }

    public static MissionService getInstance() {
        if (instance == null) {
            instance = new MissionServiceImpl();
        }
        return instance;
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return missions;
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        return missions.stream()
                .filter((Predicate<? super FlightMission>) criteria).collect(Collectors.<FlightMission>toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return missions.stream()
                .filter((Predicate<? super FlightMission>) criteria).findFirst();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) {
        FlightMission replacedMission = null;
        try {
            replacedMission = missions.stream().filter(item -> item.getName().equals(flightMission.getName()))
                    .findFirst().orElseThrow(() -> new NotFoundException("Member not found"));
            missions.set(missions.indexOf(replacedMission), flightMission);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return flightMission;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        if (missions.stream().filter(item -> item.getName().equals(flightMission.getName()))
                .findFirst().isEmpty()) missions.add(flightMission);
        return flightMission;
    }
}
