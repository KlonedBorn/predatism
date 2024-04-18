package com.kloneborn.app;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Configuration {
    public static final Paint WORLD_BACKGROUND_COLOR = Color.LIGHTGREEN;
    public static final Paint PREY_FILL_COLOR = Color.DODGERBLUE;
    public static final Paint PREDATOR_FILL_COLOR = Color.RED;
    public static final Paint PELLET_FILL_COLOR = Color.FORESTGREEN;
    public static final long PELLET_RESPAWN_TICK = 10;
    public static final long PELLET_RESPAWN_COOLDOWN = 1000;
    public static final int TARGET_FPS = 60;
    public static final int SPATIAL_PARTITION_GRID_SIZE = 16;
    public static final int PREY_INIT_COUNT = 3000;
    public static final int PREDATOR_INIT_COUNT = 500;
    public static final int PELLET_INIT_COUNT = 400;
    public static final int CREATURE_INIT_COUNT = 1000;
    public static final double WORLD_SIZE = 2000.0;
    public static final double WANDER_RADIUS = 10.0;
    public static final double WANDER_DISTANCE = 40;
    public static final double WANDER_ANGLE_CHANGE = 25.0;
    public static final double VELOCITY = 3.0;
    public static final double PREY_SEEK_DISTANCE = 60.0;
    public static final double PREY_RADIUS = 5.0;
    public static final double PREY_EAT_DISTANCE = 10.0;
    public static final double PREDATOR_RADIUS = 6.0;
    public static final double PREDATOR_MAX_VELOCITY = VELOCITY * 1.2;
    public static final double PREDATOR_KILL_DISTANCE = 10.0;
    public static final double PREDATOR_CHASE_DISTANCE = 150.0;
    public static final double PERCEPTION_DISTANCE = 100.0;
    public static final double PELLET_SIZE = 4.0;
    public static final double MAX_FORCE = .2;
    public static final double INTERACTION_RADIUS = 10.0;
    public static final double HUNGER_THRESHOLD = 0.3;
    public static final double DEATH_THRESHOLD = 0.1;
    public static final double CREATURE_SIZE = 6.0;
    public static final double CREATURE_SATURATION = 1.0;
    public static final double CREATURE_REPRODUCTION_COOLDOWN = 1200;
    public static final double CREATURE_METABOLIC_RATE = 0.0015;
    public static final int MAX_ENTITIES = 4500;
    public static final int PREY_REPRODUCTION_COOLDOWN = 600;
}
