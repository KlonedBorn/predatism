package com.kloneborn.util;

import com.kloneborn.app.Configuration;
import com.kloneborn.model.Prey;
import com.kloneborn.model.World;

import javafx.geometry.Point2D;

public class PreySpawner extends Spawner<Prey> {

    public PreySpawner(World world) {
        super(world);
    }

    @Override
    public Prey spawnAt(Point2D point) {
        return new Prey(world, point, Configuration.PREY_RADIUS);
    }
}