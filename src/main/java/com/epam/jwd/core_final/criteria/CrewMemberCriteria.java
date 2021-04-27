package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;

import java.util.function.Predicate;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    public static Predicate<CrewMember> fetchByRole(Role role)
    {
        return p -> p.getRole() == role;
    }

    public static Predicate<CrewMember> fetchReady(Boolean isReady)
    {
        return p -> p.isReadyForNextMissions() == isReady;
    }
}
