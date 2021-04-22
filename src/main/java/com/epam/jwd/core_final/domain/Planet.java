package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * location could be a simple class Point with 2 coordinates
 */
public class Planet extends AbstractBaseEntity {

    private Point point;

    public Planet(Long x, Long y) {
        this.point = new Point(x, y);
    }

    private Point getPoint() {
        return point;
    }

    class Point {
        private long x;
        private long y;


        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }
    }
}
