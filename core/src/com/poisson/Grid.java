package com.poisson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Grid {

    private final int gridWidth;
    private final int gridHeight;
    private final double cellSize;
    private Point[] points;

    public Grid(int width, int height, double cellSize) {
        this.gridWidth = (int) Math.ceil(width / cellSize);
        this.gridHeight = (int) Math.ceil(height / cellSize);
        this.cellSize = cellSize;
        this.points = new Point[gridWidth * gridHeight];
    }

    public void add(Point point) {

        addPoint(getGridX(point), getGridY(point), point);
    }

    public Collection<Point> getNeighbors(Point point, int radius) {

        int gridX = getGridX(point);
        int minX = Math.max(gridX - radius, 0);
        int maxX = Math.min(gridX + radius, gridWidth - 1);

        int gridY = getGridY(point);
        int minY = Math.max(gridY - radius, 0);
        int maxY = Math.min(gridY + radius, gridHeight - 1);

        Collection<Point> neighbors = new ArrayList<>();
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Optional.ofNullable(getPoint(x, y)).ifPresent(neighbors::add);
            }
        }
        return neighbors;
    }

    private int getGridX(Point point) {

        return (int) (point.getX() / cellSize);
    }

    private int getGridY(Point point) {

        return (int) (point.getY() / cellSize);
    }

    private void addPoint(int gridX, int gridY, Point point) {

        points[gridY * gridWidth + gridX] = point;
    }

    private Point getPoint(int gridX, int gridY) {

        return points[gridY * gridWidth + gridX];
    }
}
