package com.kloneborn.model;

import com.kloneborn.app.Configuration;
import com.kloneborn.util.SpatialPartitionGrid.SpatialQueryData;

import javafx.geometry.Point2D;

public class Prey extends Creature implements Consumable, Eater<Pellet> {
    private double eatDistance = Configuration.PREY_EAT_DISTANCE;
    private double seekDistance = Configuration.PREY_SEEK_DISTANCE;

    public Prey(World world, Point2D position, double radius) {
        super(world, position, radius);
        setFill(Configuration.PREY_FILL_COLOR);
        setRadius(Configuration.PREY_RADIUS);
        setSaturation(Math.random() * (1 - Configuration.HUNGER_THRESHOLD) + Configuration.HUNGER_THRESHOLD);
    }

    @Override
    protected void decision() {
        SpatialQueryData q = world.getSpatialGrid().query(this);
        Pellet closet = null;
        double closetDistance = Double.MAX_VALUE;
        if (isReadyToReproduce() && !isHungry() && world.getEntities().size() + world.getCreated().size() + 1 < Configuration.MAX_ENTITIES) {
            Prey child = new Prey(world, getSpawnLocation(position, 45.0), Configuration.PREY_RADIUS);
            world.getCreated().add(child);
            setReproductionCooldown(Configuration.PREY_REPRODUCTION_COOLDOWN);
        }
        for (Entity e : q.entities) {
            if (isHungry() && e instanceof Pellet) {
                double distance = position.distance(e.getPosition()) - (getRadius() + e.getRadius());
                if (closet == null || distance < closetDistance) {
                    closet = (Pellet) e;
                    closetDistance = distance;
                }
            } else if (e instanceof Predator) {
                avoid(e.getPosition(), 100.0 - e.getRadius());
            }
        }
        if (closet != null) {
            if (closetDistance <= seekDistance) {
                seek(closet.getPosition());
            }
            if (closetDistance <= eatDistance) {
                eat(closet);
            }
        } else {
            wander();
        }
    }

    private Point2D getSpawnLocation(Point2D position, double d) {
        double angle = Math.random() * 360.0;
        double radius = Math.random() * d;
        return new Point2D(position.getX() + radius * Math.cos(Math.toRadians(angle)),
                position.getY() + radius * Math.sin(Math.toRadians(angle)));
    }

    private void eat(Pellet e) {
        e.setAlive(false);
        saturation = 1.0;
    }
}