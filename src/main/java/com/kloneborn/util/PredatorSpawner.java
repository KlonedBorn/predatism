package com.kloneborn.util;

import com.kloneborn.app.Configuration;
import com.kloneborn.model.Predator;
import com.kloneborn.model.World;

import javafx.geometry.Point2D;

public class PredatorSpawner extends Spawner<Predator> {

    public PredatorSpawner(World world) {
        super(world);
    }

    @Override
    public Predator spawnAt(Point2D point) {
        return new Predator(world, point, Configuration.PREDATOR_RADIUS);
    }
}