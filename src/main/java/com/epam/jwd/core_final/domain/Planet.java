package com.epam.jwd.core_final.domain;

import lombok.Getter;

/**
 * Expected fields:
 * <p>
 * location could be a simple class Point with 2 coordinates
 */
@Getter
public class Planet extends AbstractBaseEntity {

    private Point point;
    private boolean isVisited;

    private boolean isAssigned;

    public Planet(long id, String name, long x, long y) {
        this.id = id;
        this.name = name;
        this.point = new Point(x, y);
    }


    @Getter
    public class Point {
        private long x;
        private long y;


        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
     }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
}
