package com.poisson;

public class Point {

    private int x;
    private int y;

    public Point(double x, double y) {
        this((int) x, (int) y);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDistanceSquared(Point point) {

        int dx = x - point.getX();
        int dy = y - point.getY();
        return dx * dx + dy * dy;
    }
}
