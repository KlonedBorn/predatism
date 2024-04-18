package com.kloneborn.model;

import java.util.ArrayList;
import java.util.List;

import com.kloneborn.app.Configuration;
import com.kloneborn.util.DrawUtils;
import com.kloneborn.util.EdgePolicy;
import com.kloneborn.util.PelletSpawner;
import com.kloneborn.util.PredatorSpawner;
import com.kloneborn.util.PreySpawner;
import com.kloneborn.util.SpatialPartitionGrid;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class World {
    private final Bounds constraints;
    private final Canvas display;
    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> created = new ArrayList<>();
    private final List<Entity> removed = new ArrayList<>();
    private final SpatialPartitionGrid spatialGrid;
    private Timeline timeline;
    private EdgePolicy policy = EdgePolicy.BORDER;

    public World(double width, double height, Canvas display) {
        this.constraints = new BoundingBox(0, 0, width, height);
        display.setWidth(constraints.getWidth());
        display.setHeight(constraints.getHeight());
        this.display = display;
        this.spatialGrid = new SpatialPartitionGrid(constraints, Configuration.SPATIAL_PARTITION_GRID_SIZE);
        entities.addAll(new PelletSpawner(this).spawnRandom(Configuration.PELLET_INIT_COUNT));
        entities.addAll(new PreySpawner(this).spawnRandom(Configuration.PREY_INIT_COUNT));
        entities.addAll(new PredatorSpawner(this).spawnRandom(Configuration.PREDATOR_INIT_COUNT));
        for (Entity e : entities) {
            spatialGrid.update(e);
        }
    }

    public World(Canvas display) {
        this(Configuration.WORLD_SIZE, Configuration.WORLD_SIZE, display);
    }

    public Canvas getDisplay() {
        return display;
    }

    public void update(double dt) {
        spatialGrid.update(1);
        removed.clear();
        created.clear();
        for (Entity e : entities) {
            e.update();
            spatialGrid.update(e);
            if (!e.isAlive()) {
                if (e instanceof Pellet)
                    continue;
                removed.add(e);
                spatialGrid.remove(e);
            }
        }
        entities.removeAll(removed);
        entities.addAll(created);
        created.forEach(c -> spatialGrid.update(c));
        render();
    }

    private void render() {
        GraphicsContext gc = display.getGraphicsContext2D();
        gc.setFill(Configuration.WORLD_BACKGROUND_COLOR);
        gc.fillRect(0, 0, constraints.getWidth(), constraints.getHeight());
        DrawUtils.drawGrid(gc, spatialGrid.getCellWidth(), spatialGrid.getCellHeight(), display.getWidth(),
                display.getHeight(), Color.GRAY);
        for (Entity e : entities) {
            e.render(gc);
        }
    }

    public void start() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0 / Configuration.TARGET_FPS), event -> {
            update(0.0);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public void resolveEdge(Vehicle e) {
        switch (policy) {
            case BORDER: {
                if (e.getPosition().getX() - e.getRadius() < 0) {
                    e.setPosition(new Point2D(e.getRadius(), e.getPosition().getY()));
                    e.setVelocity(new Point2D(-e.getVelocity().getX(), e.getVelocity().getY()));
                }
                if (e.getPosition().getX() + e.getRadius() > constraints.getWidth()) {
                    e.setPosition(new Point2D(constraints.getWidth() - e.getRadius(), e.getPosition().getY()));
                    e.setVelocity(new Point2D(-e.getVelocity().getX(), e.getVelocity().getY()));
                }
                if (e.getPosition().getY() - e.getRadius() < 0) {
                    e.setPosition(new Point2D(e.getPosition().getX(), e.getRadius()));
                    e.setVelocity(new Point2D(e.getVelocity().getX(), -e.getVelocity().getY()));
                }
                if (e.getPosition().getY() + e.getRadius() > constraints.getHeight()) {
                    e.setPosition(new Point2D(e.getPosition().getX(), constraints.getHeight() - e.getRadius()));
                    e.setVelocity(new Point2D(e.getVelocity().getX(), -e.getVelocity().getY()));
                }
            }
            case INFINITE:
                break;
            case TOROID:
                break;
            default:
                break;
        }
    }

    public Bounds getConstraints() {
        return constraints;
    }

    public SpatialPartitionGrid getSpatialGrid() {
        return spatialGrid;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public EdgePolicy getPolicy() {
        return policy;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getCreated() {
        return created;
    }

    public List<Entity> getRemoved() {
        return removed;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setPolicy(EdgePolicy policy) {
        this.policy = policy;
    }

}