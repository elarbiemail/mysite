package com.inspectapp.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Contrôleur de la barre latérale (Sidebar)
 */
public class SidebarController {
    
    @FXML private Button btnDashboard;
    @FXML private Button btnBilans;
    @FXML private Button btnProgramme;
    @FXML private Button btnEnseignants;
    @FXML private Button btnZones;
    @FXML private Button btnTaches;
    @FXML private Button btnAnnuaire;
    @FXML private Button btnBibliotheque;
    @FXML private Button btnCorrespondanceAR;
    @FXML private Button btnModeles;
    @FXML private Button btnExports;
    @FXML private Button btnProfil;
    @FXML private Button btnParametres;
    @FXML private Button btnIAAssistant;
    
    private MainController mainController;
    
    @FXML
    public void initialize() {
        System.out.println("Initialisation de la sidebar...");
    }
    
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
    
    @FXML
    private void handleDashboard() {
        System.out.println("Navigation vers Tableau de Bord");
        if (mainController != null) mainController.loadDashboard();
    }
    
    @FXML
    private void handleBilans() {
        System.out.println("Navigation vers Bilans & Stats");
    }
    
    @FXML
    private void handleProgramme() {
        System.out.println("Navigation vers Programme Annuel");
    }
    
    @FXML
    private void handleEnseignants() {
        System.out.println("Navigation vers Enseignants");
        if (mainController != null) mainController.loadEnseignants();
    }
    
    @FXML
    private void handleZones() {
        System.out.println("Navigation vers Zones & Établissements");
        if (mainController != null) mainController.loadZones();
    }
    
    @FXML
    private void handleTaches() {
        System.out.println("Navigation vers Tâches");
        if (mainController != null) mainController.loadTaches();
    }
    
    @FXML
    private void handleAnnuaire() {
        System.out.println("Navigation vers Annuaire");
    }
    
    @FXML
    private void handleBibliotheque() {
        System.out.println("Navigation vers Bibliothèque");
        if (mainController != null) mainController.loadDocuments();
    }
    
    @FXML
    private void handleCorrespondanceAR() {
        System.out.println("Navigation vers Correspondance AR");
    }
    
    @FXML
    private void handleModeles() {
        System.out.println("Navigation vers Modèles Word");
    }
    
    @FXML
    private void handleExports() {
        System.out.println("Navigation vers Exports");
    }
    
    @FXML
    private void handleProfil() {
        System.out.println("Navigation vers Profil");
        if (mainController != null) mainController.loadProfil();
    }
    
    @FXML
    private void handleParametres() {
        System.out.println("Navigation vers Paramètres");
        if (mainController != null) mainController.loadParametres();
    }
    
    @FXML
    private void handleIAAssistant() {
        System.out.println("Ouverture de l'Assistant IA");
    }
}
