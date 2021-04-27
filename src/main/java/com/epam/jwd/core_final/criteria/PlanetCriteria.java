package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Planet;

import java.util.function.Predicate;

public class PlanetCriteria extends Criteria<Planet>{
    public static Predicate<Planet> isNotVisited(Boolean visited)
    {
        return p -> p.isVisited() != visited;
    }

    public static Predicate<Planet> isNotAssigned(Boolean assigned)
    {
        return p -> p.isAssigned() != assigned;
    }
}
