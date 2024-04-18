package com.kloneborn.util;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

public class VectorUtil {
    public static final Point2D randomVelocity(double minX, double maxX, double minY, double maxY) {
        double x = minX + Math.random() * (maxX - minX);
        double y = minY + Math.random() * (maxY - minY);
        return new Point2D(x, y);
    }

    public static final Point2D randomVelocity(double maxX, double maxY) {
        return randomVelocity(-maxX, maxX, -maxY, maxY);
    }

    public static Point2D getRandomPoint(Bounds b) {
        double x = b.getMinX() + Math.random() * b.getWidth();
        double y = b.getMinY() + Math.random() * b.getHeight();
        return new Point2D(x, y);
    }

    public static double wrap(double min, double max, double var) {
        return (var - min) % (max - min) + min;
    }

    public static double dot(Point2D n1, Point2D n2) {
        return n1.getX() * n2.getX() + n1.getY() * n2.getY();
    }
}
