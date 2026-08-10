package com.inspection;

import com.inspection.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application principale de gestion des inspections pédagogiques
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainController controller = new MainController();
        controller.init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
