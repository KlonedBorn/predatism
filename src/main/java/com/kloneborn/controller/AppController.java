package com.kloneborn.controller;

import com.kloneborn.model.World;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class AppController {
    @FXML
    private Canvas worldCanvas;

    @FXML
    void initialize() {
        World world = new World(worldCanvas);
        world.start();
    }
}