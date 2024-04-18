package com.kloneborn.model;

import com.kloneborn.app.Configuration;
import com.kloneborn.util.VectorUtil;

import javafx.geometry.Point2D;

public class Pellet extends Entity implements Consumable {

    private long consumedCooldown = 0;

    public Pellet(World world, Point2D position, double radius) {
        super(world, position, radius);
        setFill(Configuration.PELLET_FILL_COLOR);
        setRadius(Configuration.PELLET_SIZE);
    }

    @Override
    public void update() {
        if (alive && consumedCooldown <= 0) {
            setPosition(VectorUtil.getRandomPoint(world.getConstraints()));
            alive = false;
            consumedCooldown = 0;
        } else if (alive) {
            consumedCooldown -= Configuration.PELLET_RESPAWN_TICK;
        }
    }
}