package com.kloneborn;

import java.net.URL;
import java.util.Properties;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class App extends javafx.application.Application {

    public static final Properties props = new Properties();

    @Override
    public void start(Stage stage) throws Exception {
        props.load(APP_PROPERTIES.openStream());
        stage.setScene(FXMLLoader.load(APP_FXML));
        stage.setTitle(props.getProperty("app.name") + " " + props.getProperty("app.version"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static final URL APP_FXML = App.class.getResource("view/app.fxml");
    private static final URL APP_PROPERTIES = App.class.getResource("app.properties");
}