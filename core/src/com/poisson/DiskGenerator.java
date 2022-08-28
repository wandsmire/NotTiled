package com.poisson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiskGenerator {

    private static final double SQRT_OF_2 = Math.sqrt(2);
    private final int width;
    private final int height;
    private final double minimumDistance;
    private final int newPointsCount;
    private final Grid grid;
    private final Random random;
    private List<Point> processList;
    private List<Point> samplePoints;

    public DiskGenerator(int width, int height, double minimumDistance, int newPointsCount) {

        this.width = width;
        this.height = height;
        this.minimumDistance = minimumDistance;
        this.newPointsCount = newPointsCount;
        this.grid = new Grid(width, height, minimumDistance / SQRT_OF_2);
        this.random = new Random(0);
    }

    public List<Point> generate() {

        this.processList = new ArrayList<>();
        this.samplePoints = new ArrayList<>();

        addPoint(new Point(random.nextInt(width), random.nextInt(height)));

        while (!processList.isEmpty()) {
            Point point = takeFromProcessList();
            for (int i = 0; i < newPointsCount; i++) {
                Point newPoint = generateRandomPointAround(point);
                if (isValid(newPoint) && !hasCloseNeighbors(newPoint)) {
                    addPoint(newPoint);
                }
            }
        }

        return samplePoints;
    }

    private void addPoint(Point point) {

        processList.add(point);
        samplePoints.add(point);
        grid.add(point);
    }

    private boolean hasCloseNeighbors(Point point) {

        for (Point neighbor : grid.getNeighbors(point, 2)) {
            if (neighbor.getDistanceSquared(point) < minimumDistance * minimumDistance) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(Point point) {

        return point.getX() >= 0 && point.getX() < width//
                && point.getY() >= 0 && point.getY() < height;
    }

    private Point generateRandomPointAround(Point point) {

        double r1 = random.nextDouble();
        double r2 = random.nextDouble();
        double radius = minimumDistance * (r1 + 1);
        double angle = 2 * Math.PI * r2;
        double x = point.getX() + radius * Math.cos(angle);
        double y = point.getY() + radius * Math.sin(angle);
        return new Point(x, y);
    }

    private Point takeFromProcessList() {

        int index = random.nextInt(processList.size());
        return processList.remove(index);
    }
}
