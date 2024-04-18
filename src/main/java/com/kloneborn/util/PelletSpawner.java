package com.kloneborn.util;

import com.kloneborn.app.Configuration;
import com.kloneborn.model.Pellet;
import com.kloneborn.model.World;

import javafx.geometry.Point2D;

public class PelletSpawner extends Spawner<Pellet> {

    public PelletSpawner(World world) {
        super(world);
    }

    @Override
    public Pellet spawnAt(Point2D point) {
        return new Pellet(world, point, Configuration.PELLET_SIZE);
    }
}