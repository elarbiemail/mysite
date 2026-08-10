package com.inspectapp;

import com.inspectapp.database.DatabaseManager;
import com.inspectapp.ui.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * iNSPECTAPP - Plateforme de gestion 360° pour les inspecteurs pédagogiques
 * Application principale JavaFX
 */
public class MainApp extends Application {
    
    private static final String APP_TITLE = "iNSPECTAPP - Gestion des Inspections Pédagogiques";
    private static final double MIN_WIDTH = 1400;
    private static final double MIN_HEIGHT = 900;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialiser la base de données
        DatabaseManager.getInstance();
        
        // Charger le FXML principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        BorderPane root = loader.load();
        
        // Créer la scène avec le style MacGlass
        Scene scene = new Scene(root, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(
            getClass().getResource("/css/styles.css")).toExternalForm());
        
        // Configurer la fenêtre principale
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        
        // Icône de l'application (optionnelle)
        try {
            Image icon = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/icon.png")));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            // Icône non trouvée, continuer sans
        }
        
        primaryStage.show();
        
        // Récupérer le contrôleur et initialiser
        MainController controller = loader.getController();
        controller.initialize();
    }
    
    @Override
    public void stop() {
        // Fermer la connexion à la base de données
        DatabaseManager.getInstance().close();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
