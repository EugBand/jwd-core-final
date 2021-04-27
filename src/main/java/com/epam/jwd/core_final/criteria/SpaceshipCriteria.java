package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Spaceship;

import java.util.function.Predicate;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship>{
    public static Predicate<Spaceship> isNotAssigned(Boolean assigned)
    {
        return p -> p.isReadyForNextMissions() == assigned;
    }
}
