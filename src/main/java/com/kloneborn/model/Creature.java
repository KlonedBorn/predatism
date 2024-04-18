package com.kloneborn.model;

import com.kloneborn.app.Configuration;
import com.kloneborn.util.VectorUtil;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Creature extends Vehicle {
    protected double wanderDistance = Configuration.WANDER_DISTANCE;
    protected double wanderRadius = Configuration.WANDER_RADIUS;
    protected double wanderAngleChange = Configuration.WANDER_ANGLE_CHANGE;
    protected double awarenessRadius = Configuration.PERCEPTION_DISTANCE;
    protected CreatureState state = CreatureState.IDLE;
    protected double interactRadius = Configuration.INTERACTION_RADIUS;
    protected double metabolism = Configuration.CREATURE_METABOLIC_RATE;
    protected double saturation = Configuration.CREATURE_SATURATION;
    protected double reproductionCooldown = Configuration.CREATURE_REPRODUCTION_COOLDOWN;
    protected boolean readyToReproduce = false;
    private double wanderAngle = 0.0;
    protected long age = 0;

    public Creature(World world, Point2D position, double radius) {
        super(world, position, radius);
        this.world = world;
    }

    @Override
    public void update() {
        super.update();
        saturation = Math.max(0, saturation - metabolism);
        if (saturation <= Configuration.DEATH_THRESHOLD)
            alive = false;
        if (!readyToReproduce) {
            reproductionCooldown--;
            if (reproductionCooldown <= 0) {
                readyToReproduce = true;
            }
        }
    }

    public void wander() {
        Point2D next = position.add(velocity.normalize().multiply(wanderDistance));
        wanderAngle = VectorUtil.wrap(0.0, 360.0,
                wanderAngle + Math.random() * wanderAngleChange - (wanderAngleChange / 2.0));
        double x = next.getX() + (wanderRadius * Math.cos(wanderAngle)) * Math.random();
        double y = next.getY() + (wanderRadius * Math.sin(wanderAngle)) * Math.random();
        Point2D target = new Point2D(x, y);
        seek(target);
    }

    @Override
    public void render(GraphicsContext gc) {
        Paint fill = getFill();
        setFill(isHungry() ? Color.YELLOW : fill);
        super.render(gc);
        setFill(fill);
    }

    public boolean isHungry() {
        return saturation <= Configuration.HUNGER_THRESHOLD;
    }

    public World getWorld() {
        return world;
    }

    public double getInteractRadius() {
        return interactRadius;
    }

    public void setInteractRadius(double interactRadius) {
        this.interactRadius = interactRadius;
    }

    public double getMetabolism() {
        return metabolism;
    }

    public void setMetabolism(double metabolism) {
        this.metabolism = metabolism;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public CreatureState getState() {
        return state;
    }

    public void setState(CreatureState state) {
        this.state = state;
    }

    public static enum CreatureState {
        IDLE(0.0, 0),
        WANDERING(1.0, 1),
        FORAGING(4.0, 2),
        FLEEING(10.0, 3),
        STARVING(11.0, 4),
        REPRODUCING(12.0, 5);

        private final double strenght;
        private final int priorityLevel;

        private CreatureState(double strenght, int priorityLevel) {
            this.strenght = strenght;
            this.priorityLevel = priorityLevel;
        }

        public double getStrenght() {
            return strenght;
        }

        public int getPriorityLevel() {
            return priorityLevel;
        }
    }

    public double getWanderDistance() {
        return wanderDistance;
    }

    public void setWanderDistance(double wanderDistance) {
        this.wanderDistance = wanderDistance;
    }

    public double getWanderRadius() {
        return wanderRadius;
    }

    public void setWanderRadius(double wanderRadius) {
        this.wanderRadius = wanderRadius;
    }

    public double getWanderAngleChange() {
        return wanderAngleChange;
    }

    public void setWanderAngleChange(double wanderAngleChange) {
        this.wanderAngleChange = wanderAngleChange;
    }

    public double getAwarenessRadius() {
        return awarenessRadius;
    }

    public void setAwarenessRadius(double awarenessRadius) {
        this.awarenessRadius = awarenessRadius;
    }

    public double getWanderAngle() {
        return wanderAngle;
    }

    public void setWanderAngle(double wanderAngle) {
        this.wanderAngle = wanderAngle;
    }

    public boolean isReadyToReproduce() {
        return readyToReproduce;
    }

    public double getReproductionCooldown() {
        return reproductionCooldown;
    }

    public void setReproductionCooldown(double reproductionCooldown) {
        this.reproductionCooldown = reproductionCooldown;
    }

    public void setReadyToReproduce(boolean readyToReproduce) {
        this.readyToReproduce = readyToReproduce;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

}
