package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.CrewMember;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface CrewService {

    List<CrewMember> findAllCrewMembers();

    List<CrewMember> findAllCrewMembersByCriteria(Predicate<? extends CrewMember> criteria);

    Optional<CrewMember> findCrewMemberByCriteria(Predicate<? extends CrewMember> criteria);

    CrewMember updateCrewMemberDetails(CrewMember crewMember);

    // todo create custom exception for case, when crewMember is not able to be assigned
    void assignReadyStatus(CrewMember crewMember, Boolean status) throws RuntimeException;

    void assingLostStatus(CrewMember member, Boolean status);

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // crewmember unique criteria - only name!
    CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException;
}