package com.kloneborn.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Entity {
    protected World world;
    protected Point2D position = new Point2D(0, 0);
    protected double radius = 5.0;
    protected boolean alive = true;
    protected Paint fill = Color.GREY;
    public int gridIndex = -1;

    public Entity(World world, Point2D position, double radius) {
        this.world = world;
        this.position = position;
        this.radius = radius;
    }

    abstract public void update();

    public void render(GraphicsContext gc) {
        gc.setFill(fill);
        gc.fillOval(position.getX() - radius, position.getY() - radius, radius * 2, radius * 2);
    };

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getGridIndex() {
        return gridIndex;
    }

    public void setGridIndex(int gridIndex) {
        this.gridIndex = gridIndex;
    }

    public Paint getFill() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill = fill;
    }
}
