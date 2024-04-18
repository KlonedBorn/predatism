package com.kloneborn.util;

import java.util.ArrayList;
import java.util.List;

import com.kloneborn.model.World;

import javafx.geometry.Point2D;

public abstract class Spawner<T> {
    protected final World world;
    protected double spacing = 0.0;

    public Spawner(World world) {
        this.world = world;
    }

    abstract public T spawnAt(Point2D point);

    public List<T> spawnRandom(int count) {
        List<T> list = new ArrayList<T>(count);
        for (int i = 0; i < count; ++i) {
            list.add(spawnAt(VectorUtil.getRandomPoint(world.getConstraints())));
        }
        return list;
    }

    public List<T> spawnAtPoints(List<Point2D> points) {
        List<T> list = new ArrayList<T>(points.size());
        for (Point2D point : points) {
            list.add(spawnAt(point));
        }
        return list;
    }
}