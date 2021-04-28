package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.annotation.ISingleton;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.serviceexception.UpdateServiceException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

@SuppressWarnings("all")
@ISingleton
public final class CrewServiceImpl implements CrewService {
    private static CrewService instance;
    IAppLogger logger = AppLogger.getInstance();
    private List<CrewMember> members = Optional.ofNullable((List<CrewMember>) NassaContext.getInstance().
            retrieveBaseEntityList(CrewMember.class)).orElseThrow(IllegalStateException::new);

    private CrewServiceImpl() {
    }

    CrewServiceImpl(List<CrewMember> members) {
        this.members = members;
    }

    public static CrewService getInstance() {
        if (instance == null) {
            instance = new CrewServiceImpl();
        }
        return instance;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return members;
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Predicate<? extends CrewMember> criteria) {
        return members.stream()
                .filter((Predicate<? super CrewMember>) criteria).collect(Collectors.<CrewMember>toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Predicate<? extends CrewMember> criteria) {
        return members.stream()
                .filter((Predicate<? super CrewMember>) criteria).findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        CrewMember replacedMember = null;
        try {
            replacedMember = members.stream().filter(item -> item.getName().equals(crewMember.getName()))
                    .findFirst().orElseThrow(() -> new NotFoundException("Member not found"));
            members.set(members.indexOf(replacedMember), crewMember);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return replacedMember;
    }

    @Override
    public void assignReadyStatus(CrewMember crewMember, Boolean status) throws RuntimeException {
        try {
            crewMember.setReadyForNextMissions(status);
        } catch (UpdateServiceException e) {
            logger.log(ERROR, "Can't update spaceship", e);
        }
        updateCrewMemberDetails(crewMember);
    }

    @Override
    public void assingLostStatus(CrewMember member, Boolean status){
        member.setMissing(status);
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        if (members.stream().filter(item -> item.getName().equals(crewMember.getName()))
                .findFirst().isEmpty()) members.add(crewMember);
        else throw new UpdateServiceException("Can't add a crew member to list");
        return crewMember;
    }
}
