package com.kloneborn.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DrawUtils {
    public static final void drawGrid(GraphicsContext gc, double cellWidth, double cellHeight, double canvasWidth,
            double canvasHeight, Paint lineColor) {
        if (lineColor == null)
            lineColor = Color.GRAY;
        gc.setStroke(lineColor);
        gc.setLineWidth(0.5);
        for (double x = cellWidth; x < canvasWidth; x += cellWidth) {
            gc.strokeLine(x, 0, x, canvasHeight);
        }
        for (double y = cellHeight; y < canvasHeight; y += cellHeight) {
            gc.strokeLine(0, y, canvasWidth, y);
        }
    }
}
