package com.kloneborn.model;

import com.kloneborn.app.Configuration;
import com.kloneborn.util.SpatialPartitionGrid.SpatialQueryData;

import javafx.geometry.Point2D;

public class Predator extends Creature implements Eater<Prey> {

    private double chaseDistance = Configuration.PREDATOR_CHASE_DISTANCE;
    private double killDistance = Configuration.PREDATOR_KILL_DISTANCE;

    public Predator(World world, Point2D position, double radius) {
        super(world, position, radius);
        setFill(Configuration.PREDATOR_FILL_COLOR);
        setRadius(Configuration.PREDATOR_RADIUS);
        setMaxVelocity(Configuration.PREDATOR_MAX_VELOCITY);
        setSaturation(Math.random() * (1 - Configuration.HUNGER_THRESHOLD) + Configuration.HUNGER_THRESHOLD);
    }

    @Override
    protected void decision() {
        SpatialQueryData q = world.getSpatialGrid().query(this);
        if (isHungry()) {
            Prey closet = null;
            double closetDistance = Double.MAX_VALUE;
            for (Entity e : q.entities) {
                if (e instanceof Prey) {
                    double distance = position.distance(e.getPosition()) - (getRadius() + e.getRadius());
                    if (closet == null || distance < closetDistance) {
                        closet = (Prey) e;
                        closetDistance = distance;
                    }
                }
            }
            if (closet != null) {
                if (closetDistance <= chaseDistance) {
                    seek(closet.getPosition());
                }
                if (closetDistance <= killDistance) {
                    eat(closet);
                }
            }
        } else
            wander();
    }

    private void eat(Prey e) {
        e.setAlive(false);
        saturation = 1.0;
    }

}
