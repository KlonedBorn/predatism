package com.kloneborn.util;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import com.kloneborn.model.Entity;

import javafx.geometry.Bounds;

/**
 * Divides a 2D plane into quadrants then stores a list in each quadrant of
 * entities who's position falls within it
 */
public class SpatialPartitionGrid {
    private final Bounds bounds;
    private final int partition;
    private final int partitionSize;
    private final double cellWidth, cellHeight;
    private final SpatialCell[] grid;

    public SpatialPartitionGrid(Bounds bounds, int partition) {
        this.bounds = bounds;
        this.partition = partition;
        this.partitionSize = partition * partition;
        cellWidth = bounds.getWidth() / partition;
        cellHeight = bounds.getHeight() / partition;
        grid = new SpatialCell[partitionSize + 1];
        for (int i = 0; i <= partitionSize; i++) {
            grid[i] = new SpatialCell();
        }
    }

    public boolean inBounds(Entity v) {
        return bounds.contains(v.getPosition());
    }

    public void remove(Entity v) {
        if (v.gridIndex >= 0 && v.gridIndex <= partitionSize) {
            grid[v.gridIndex].remove(v);
            v.gridIndex = -1;
        }
    }

    public void update(Entity v) {
        int index = getIndex(v);
        if (index != v.gridIndex) {
            if (v.gridIndex >= 0 && v.gridIndex <= partitionSize) {
                grid[v.gridIndex].remove(v);
            }
            v.gridIndex = index;
        }
        grid[index].update(v);
    }

    public boolean update(int depth) {
        for (int x = 0; x <= partition; x++) {
            for (int y = 0; y <= partition; y++) {
                List<Entity> entities = new ArrayList<>();
                for (int i = -depth; i <= depth; i++) {
                    for (int j = -depth; j <= depth; j++) {
                        int cx = x + i;
                        int cy = y + j;
                        if (cx >= 0 && cx <= partition && cy >= 0 && cy <= partition) {
                            SpatialCell neighboor = grid[getIndex(cx, cy)];
                            entities.addAll(neighboor.entities);
                        }
                    }
                }
                grid[getIndex(x, y)].data = new SpatialQueryData(entities);
            }
        }
        return true;
    }

    public SpatialQueryData query(Entity v) {
        return grid[getIndex(v)].data;
    }

    private int getIndex(int gridX, int gridY) {
        int index = gridX + gridY * partition;
        return Math.max(0, Math.min(index, partitionSize));
    }

    private int getIndex(Entity v) {
        int gridX = (int) Math.floor(v.getPosition().getX() / cellWidth);
        int gridY = (int) Math.floor(v.getPosition().getY() / cellHeight);
        return Math.max(0, Math.min(gridX + gridY * partition, partitionSize));
    }

    private class SpatialCell {
        public final List<Entity> entities = new ArrayList<>();
        public SpatialQueryData data;

        public void update(Entity v) {
            if (entities.contains(v)) {
                return;
            }
            entities.add(v);
        }

        public void remove(Entity v) {
            entities.remove(v);
        }
    }

    // the data that is stored in the grid should be unmodifiable
    public class SpatialQueryData {
        public final List<Entity> entities;

        public SpatialQueryData(List<Entity> entities) {
            this.entities = Collections.unmodifiableList(entities);
        }
    }

    public Bounds getBounds() {
        return bounds;
    }

    public int getPartition() {
        return partition;
    }

    public int getPartitionSize() {
        return partitionSize;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public SpatialCell[] getGrid() {
        return grid;
    }

    public SpatialQueryData getQueryData(Entity e) {
        return grid[getIndex(e)].data;
    }
}