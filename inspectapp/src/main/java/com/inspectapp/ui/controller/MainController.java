package com.inspectapp.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur principal de l'application iNSPECTAPP
 */
public class MainController implements Initializable {
    
    @FXML
    private BorderPane rootPane;
    
    @FXML
    private ScrollPane contentScrollPane;
    
    @FXML
    private VBox contentContainer;
    
    private SidebarController sidebarController;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialisation du contrôleur principal...");
    }
    
    public void initialize() {
        // Chargé via FXML
        System.out.println("iNSPECTAPP démarré avec succès!");
    }
    
    public void loadDashboard() {
        System.out.println("Chargement du tableau de bord...");
        // TODO: Implémenter le chargement du dashboard
    }
    
    public void loadEnseignants() {
        System.out.println("Chargement de la liste des enseignants...");
        // TODO: Implémenter le chargement des enseignants
    }
    
    public void loadTaches() {
        System.out.println("Chargement des tâches...");
        // TODO: Implémenter le chargement des tâches
    }
    
    public void loadZones() {
        System.out.println("Chargement des zones et établissements...");
        // TODO: Implémenter le chargement des zones
    }
    
    public void loadDocuments() {
        System.out.println("Chargement de la bibliothèque de documents...");
        // TODO: Implémenter le chargement des documents
    }
    
    public void loadProfil() {
        System.out.println("Chargement du profil...");
        // TODO: Implémenter le chargement du profil
    }
    
    public void loadParametres() {
        System.out.println("Chargement des paramètres...");
        // TODO: Implémenter le chargement des paramètres
    }
    
    public void setSidebarController(SidebarController controller) {
        this.sidebarController = controller;
    }
}
