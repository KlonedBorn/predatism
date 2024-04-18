package com.kloneborn.model;

import com.kloneborn.app.Configuration;

import javafx.geometry.Point2D;

public abstract class Vehicle extends Entity {

    protected Point2D velocity = new Point2D(0, 0);
    protected Point2D acceleration = new Point2D(0, 0);
    protected double maxVelocity = Configuration.VELOCITY;
    protected double maxForce = Configuration.MAX_FORCE;

    public Vehicle(World world, Point2D position, double radius) {
        super(world, position, radius);
    }

    protected abstract void decision();

    public void update() {
        decision();
        position = position.add(velocity);
        velocity = velocity.add(acceleration);
        acceleration = new Point2D(0, 0);
        world.resolveEdge(this);
    }

    public void arrive(Point2D target, double distance, double strenght) {
        Point2D desired = target.subtract(position);
        double dis = target.distance(position);
        if (dis < distance) {
            desired = desired.normalize();
            desired = desired.multiply(maxVelocity);
            Point2D steer = desired.subtract(velocity);
            steer = steer.normalize();
            steer = steer.multiply(strenght * (dis / distance));
            applyForce(steer);
        }
    }

    public void arrive(Point2D target, double distance) {
        arrive(target, distance, maxForce);
    }

    public void avoid(Point2D target, double distance, double strength) {
        Point2D desired = target.subtract(position).multiply(-1.0);
        double dis = target.distance(position);
        if (dis < distance) {
            desired = desired.normalize().multiply(maxVelocity);
            Point2D steer = desired.subtract(velocity);
            steer = steer.normalize().multiply(strength * (dis / distance));
            applyForce(steer);
        }
    }

    public void avoid(Point2D target, double distance) {
        avoid(target, distance, maxForce);
    }

    public void seek(Point2D target, double strenght) {
        Point2D desired = target.subtract(position);
        desired = desired.normalize();
        desired = desired.multiply(maxVelocity);
        Point2D steer = desired.subtract(velocity);
        steer = steer.normalize();
        steer = steer.multiply(strenght);
        applyForce(steer);
    }

    public void seek(Point2D target) {
        seek(target, maxForce);
    }

    public void applyForce(Point2D force) {
        acceleration = acceleration.add(force);
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public double getMaxForce() {
        return maxForce;
    }

    public void setMaxForce(double maxForce) {
        this.maxForce = maxForce;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Point2D acceleration) {
        this.acceleration = acceleration;
    }
}