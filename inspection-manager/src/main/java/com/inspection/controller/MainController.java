package com.inspection.controller;

import com.inspection.model.Enseignant;
import com.inspection.model.Tache;
import com.inspection.model.TaskType;
import com.inspection.service.DatabaseService;
import com.inspection.service.WordDocumentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur principal de l'application
 */
public class MainController {
    private DatabaseService dbService;
    private WordDocumentService wordService;
    private BorderPane rootLayout;
    private Stage primaryStage;
    private ObservableList<Enseignant> enseignantsData = FXCollections.observableArrayList();
    private ObservableList<Tache> tachesData = FXCollections.observableArrayList();

    public MainController() {
        dbService = new DatabaseService();
        wordService = new WordDocumentService();
    }

    public void init(Stage stage) {
        this.primaryStage = stage;
        rootLayout = new BorderPane();
        Scene scene = new Scene(rootLayout, 1200, 800);

        stage.setTitle("Gestion des Inspections Pédagogiques");
        stage.setScene(scene);
        stage.show();

        setupUI();
        loadData();
    }

    private void setupUI() {
        // Menu Bar
        MenuBar menuBar = createMenuBar();
        rootLayout.setTop(menuBar);

        // Sidebar avec navigation
        VBox sidebar = createSidebar();
        rootLayout.setLeft(sidebar);

        // Contenu principal - Tableau des tâches
        VBox mainContent = createMainContent();
        rootLayout.setCenter(mainContent);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Fichier");
        MenuItem exportTemplate = new MenuItem("Créer modèle Word");
        exportTemplate.setOnAction(e -> createTemplate());
        fileMenu.getItems().add(exportTemplate);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitItem);

        Menu helpMenu = new Menu("Aide");
        MenuItem aboutItem = new MenuItem("À propos");
        aboutItem.setOnAction(e -> showAbout());
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #f5f5f5;");

        Label titleLabel = new Label("Navigation");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Button btnTaches = new Button("📋 Toutes les tâches");
        btnTaches.setMaxWidth(Double.MAX_VALUE);
        btnTaches.setOnAction(e -> showAllTaches());

        Button btnVisites = new Button("👁️ Visites");
        btnVisites.setMaxWidth(Double.MAX_VALUE);
        btnVisites.setOnAction(e -> filterByType(TaskType.VISITE));

        Button btnInspections = new Button("✅ Inspections");
        btnInspections.setMaxWidth(Double.MAX_VALUE);
        btnInspections.setOnAction(e -> filterByType(TaskType.INSPECTION));

        Button btnLecons = new Button("🧪 Leçons expérimentales");
        btnLecons.setMaxWidth(Double.MAX_VALUE);
        btnLecons.setOnAction(e -> filterByType(TaskType.LECON_EXPERIMENTALE));

        Button btnReunions = new Button("👥 Réunions pédagogiques");
        btnReunions.setMaxWidth(Double.MAX_VALUE);
        btnReunions.setOnAction(e -> filterByType(TaskType.REUNION_PEDAGOGIQUE));

        Button btnEnseignants = new Button("👨‍🏫 Enseignants");
        btnEnseignants.setMaxWidth(Double.MAX_VALUE);
        btnEnseignants.setOnAction(e -> showEnseignantsView());

        sidebar.getChildren().addAll(titleLabel, new Separator(),
                btnTaches, btnVisites, btnInspections, btnLecons, btnReunions,
                new Separator(), btnEnseignants);

        return sidebar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(10);
        mainContent.setPadding(new Insets(20));

        Label titleLabel = new Label("Tableau de bord - Tâches");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Barre d'outils
        HBox toolbar = new HBox(10);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        Button btnNewTask = new Button("+ Nouvelle tâche");
        btnNewTask.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnNewTask.setOnAction(e -> showTaskDialog(null));

        Button btnRefresh = new Button("🔄 Actualiser");
        btnRefresh.setOnAction(e -> loadData());

        toolbar.getChildren().addAll(btnNewTask, btnRefresh);

        // Tableau des tâches
        TableView<Tache> tableView = createTacheTable();

        mainContent.getChildren().addAll(titleLabel, toolbar, tableView);
        return mainContent;
    }

    private TableView<Tache> createTacheTable() {
        TableView<Tache> table = new TableView<>();
        table.setItems(tachesData);

        TableColumn<Tache, TaskType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(150);

        TableColumn<Tache, String> enseignantCol = new TableColumn<>("Enseignant");
        enseignantCol.setCellValueFactory(cellData -> {
            Tache tache = cellData.getValue();
            String value = tache.getEnseignant() != null ? 
                tache.getEnseignant().getNomComplet() : "N/A";
            return new javafx.beans.property.SimpleStringProperty(value);
        });
        enseignantCol.setPrefWidth(200);

        TableColumn<Tache, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(120);

        TableColumn<Tache, String> lieuCol = new TableColumn<>("Lieu");
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        lieuCol.setPrefWidth(150);

        TableColumn<Tache, Boolean> rapportCol = new TableColumn<>("Rapport");
        rapportCol.setCellValueFactory(new PropertyValueFactory<>("rapportRedige"));
        rapportCol.setPrefWidth(80);

        // Colonne d'actions
        TableColumn<Tache, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("✏️");
            private final Button btnReport = new Button("📄");
            private final Button btnDelete = new Button("🗑️");
            private final HBox buttons = new HBox(5, btnEdit, btnReport, btnDelete);

            {
                btnEdit.setOnAction(e -> {
                    Tache tache = getTableView().getItems().get(getIndex());
                    showTaskDialog(tache);
                });
                btnReport.setOnAction(e -> {
                    Tache tache = getTableView().getItems().get(getIndex());
                    generateReport(tache);
                });
                btnDelete.setOnAction(e -> {
                    Tache tache = getTableView().getItems().get(getIndex());
                    deleteTache(tache);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttons);
                }
            }
        });
        actionCol.setPrefWidth(200);

        table.getColumns().addAll(typeCol, enseignantCol, dateCol, lieuCol, rapportCol, actionCol);
        return table;
    }

    private void showTaskDialog(Tache existingTache) {
        Dialog<Tache> dialog = new Dialog<>();
        dialog.setTitle(existingTache == null ? "Nouvelle tâche" : "Modifier la tâche");
        dialog.setHeaderText("Informations de la tâche");

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        ComboBox<TaskType> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(TaskType.values());
        typeCombo.setValue(existingTache != null ? existingTache.getType() : TaskType.VISITE);

        ComboBox<Enseignant> enseignantCombo = new ComboBox<>();
        try {
            enseignantCombo.getItems().addAll(dbService.getAllEnseignants());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (existingTache != null && existingTache.getEnseignant() != null) {
            enseignantCombo.setValue(existingTache.getEnseignant());
        }

        DatePicker datePicker = new DatePicker(existingTache != null ? existingTache.getDate() : LocalDate.now());
        TextField heureField = new TextField(existingTache != null && existingTache.getHeure() != null ? 
            existingTache.getHeure().toString() : "10:00");
        TextField lieuField = new TextField(existingTache != null ? existingTache.getLieu() : "");
        TextField objetField = new TextField(existingTache != null ? existingTache.getObjet() : "");

        grid.add(new Label("Type:"), 0, 0);
        grid.add(typeCombo, 1, 0);
        grid.add(new Label("Enseignant:"), 0, 1);
        grid.add(enseignantCombo, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Heure:"), 0, 3);
        grid.add(heureField, 1, 3);
        grid.add(new Label("Lieu:"), 0, 4);
        grid.add(lieuField, 1, 4);
        grid.add(new Label("Objet:"), 0, 5);
        grid.add(objetField, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Tache tache = existingTache != null ? existingTache : new Tache();
                tache.setType(typeCombo.getValue());
                tache.setEnseignant(enseignantCombo.getValue());
                tache.setDate(datePicker.getValue());
                try {
                    tache.setHeure(LocalTime.parse(heureField.getText()));
                } catch (Exception e) {
                    tache.setHeure(LocalTime.of(10, 0));
                }
                tache.setLieu(lieuField.getText());
                tache.setObjet(objetField.getText());
                tache.setPlanifiee(true);
                return tache;
            }
            return null;
        });

        Optional<Tache> result = dialog.showAndWait();
        result.ifPresent(tache -> {
            try {
                dbService.saveTache(tache);
                loadData();
            } catch (SQLException e) {
                showError("Erreur lors de l'enregistrement: " + e.getMessage());
            }
        });
    }

    private void generateReport(Tache tache) {
        // Ouvrir un éditeur WYSIWYG pour rédiger le rapport
        Dialog<String> reportDialog = new Dialog<>();
        reportDialog.setTitle("Rédiger le rapport");
        reportDialog.setHeaderText("Rapport pour: " + tache.getType().getLabel() + 
            " - " + (tache.getEnseignant() != null ? tache.getEnseignant().getNomComplet() : ""));

        ButtonType saveButton = new ButtonType("Enregistrer et générer Word", ButtonBar.ButtonData.OK_DONE);
        reportDialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        // Utiliser une TextArea simple pour le contenu HTML
        TextArea contentArea = new TextArea();
        contentArea.setPrefSize(600, 400);
        contentArea.setWrapText(true);
        contentArea.setText(tache.getContenuRapport() != null ? tache.getContenuRapport() : "");
        
        // Ajouter des instructions
        VBox content = new VBox(10);
        Label instruction = new Label("Rédigez votre rapport (vous pouvez utiliser du HTML simple):");
        content.getChildren().addAll(instruction, contentArea);
        
        reportDialog.getDialogPane().setContent(content);

        reportDialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                return contentArea.getText();
            }
            return null;
        });

        Optional<String> result = reportDialog.showAndWait();
        result.ifPresent(contenu -> {
            try {
                tache.setContenuRapport(contenu);
                tache.setRapportRedige(true);
                tache.setDateRapport(LocalDate.now());
                dbService.saveTache(tache);
                
                // Générer le document Word
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Enregistrer le rapport Word");
                fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Documents Word (*.docx)", "*.docx"));
                
                String defaultName = "Rapport_" + tache.getType() + "_" + 
                    (tache.getEnseignant() != null ? tache.getEnseignant().getNom() : "") + 
                    ".docx";
                fileChooser.setInitialFileName(defaultName);
                
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    wordService.generateRapport(tache, null, file.getAbsolutePath());
                    showInfo("Rapport généré avec succès: " + file.getName());
                    loadData();
                }
            } catch (SQLException | IOException e) {
                showError("Erreur: " + e.getMessage());
            }
        });
    }

    private void deleteTache(Tache tache) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer cette tâche?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                dbService.deleteTache(tache.getId());
                loadData();
            } catch (SQLException e) {
                showError("Erreur: " + e.getMessage());
            }
        }
    }

    private void showEnseignantsView() {
        VBox view = new VBox(10);
        view.setPadding(new Insets(20));

        Label titleLabel = new Label("Gestion des enseignants");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox toolbar = new HBox(10);
        Button btnAdd = new Button("+ Nouvel enseignant");
        btnAdd.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnAdd.setOnAction(e -> showEnseignantDialog(null));
        toolbar.getChildren().add(btnAdd);

        TableView<Enseignant> table = new TableView<>();
        table.setItems(enseignantsData);

        TableColumn<Enseignant, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomCol.setPrefWidth(150);

        TableColumn<Enseignant, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        prenomCol.setPrefWidth(120);

        TableColumn<Enseignant, String> matiereCol = new TableColumn<>("Matière");
        matiereCol.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        matiereCol.setPrefWidth(150);

        TableColumn<Enseignant, String> etablissementCol = new TableColumn<>("Établissement");
        etablissementCol.setCellValueFactory(new PropertyValueFactory<>("etablissement"));
        etablissementCol.setPrefWidth(200);

        TableColumn<Enseignant, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("✏️");
            private final Button btnDelete = new Button("🗑️");
            private final HBox buttons = new HBox(5, btnEdit, btnDelete);

            {
                btnEdit.setOnAction(e -> {
                    Enseignant ens = getTableView().getItems().get(getIndex());
                    showEnseignantDialog(ens);
                });
                btnDelete.setOnAction(e -> {
                    Enseignant ens = getTableView().getItems().get(getIndex());
                    deleteEnseignant(ens);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });

        table.getColumns().addAll(nomCol, prenomCol, matiereCol, etablissementCol, actionCol);
        view.getChildren().addAll(titleLabel, toolbar, table);
        rootLayout.setCenter(view);
    }

    private void showEnseignantDialog(Enseignant existing) {
        Dialog<Enseignant> dialog = new Dialog<>();
        dialog.setTitle(existing == null ? "Nouvel enseignant" : "Modifier enseignant");

        ButtonType saveBtn = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nomField = new TextField(existing != null ? existing.getNom() : "");
        TextField prenomField = new TextField(existing != null ? existing.getPrenom() : "");
        TextField emailField = new TextField(existing != null ? existing.getEmail() : "");
        TextField telField = new TextField(existing != null ? existing.getTelephone() : "");
        TextField matiereField = new TextField(existing != null ? existing.getMatiere() : "");
        TextField etablissementField = new TextField(existing != null ? existing.getEtablissement() : "");
        TextField gradeField = new TextField(existing != null ? existing.getGrade() : "");

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Téléphone:"), 0, 3);
        grid.add(telField, 1, 3);
        grid.add(new Label("Matière:"), 0, 4);
        grid.add(matiereField, 1, 4);
        grid.add(new Label("Établissement:"), 0, 5);
        grid.add(etablissementField, 1, 5);
        grid.add(new Label("Grade:"), 0, 6);
        grid.add(gradeField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtn) {
                Enseignant e = existing != null ? existing : new Enseignant();
                e.setNom(nomField.getText());
                e.setPrenom(prenomField.getText());
                e.setEmail(emailField.getText());
                e.setTelephone(telField.getText());
                e.setMatiere(matiereField.getText());
                e.setEtablissement(etablissementField.getText());
                e.setGrade(gradeField.getText());
                return e;
            }
            return null;
        });

        Optional<Enseignant> result = dialog.showAndWait();
        result.ifPresent(enseignant -> {
            try {
                dbService.saveEnseignant(enseignant);
                loadData();
                showEnseignantsView();
            } catch (SQLException ex) {
                showError("Erreur: " + ex.getMessage());
            }
        });
    }

    private void deleteEnseignant(Enseignant enseignant) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer cet enseignant?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                dbService.deleteEnseignant(enseignant.getId());
                loadData();
                showEnseignantsView();
            } catch (SQLException e) {
                showError("Erreur: " + e.getMessage());
            }
        }
    }

    private void showAllTaches() {
        loadData();
        VBox mainContent = createMainContent();
        rootLayout.setCenter(mainContent);
    }

    private void filterByType(TaskType type) {
        try {
            tachesData.setAll(dbService.getTachesByType(type));
        } catch (SQLException e) {
            showError("Erreur: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            enseignantsData.setAll(dbService.getAllEnseignants());
            tachesData.setAll(dbService.getAllTaches());
        } catch (SQLException e) {
            showError("Erreur de chargement: " + e.getMessage());
        }
    }

    private void createTemplate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le modèle Word");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Documents Word (*.docx)", "*.docx"));
        fileChooser.setInitialFileName("modele_rapport.docx");

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                wordService.createTemplate(file.getAbsolutePath());
                showInfo("Modèle créé avec succès: " + file.getName());
            } catch (IOException e) {
                showError("Erreur: " + e.getMessage());
            }
        }
    }

    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("Gestion des Inspections Pédagogiques");
        alert.setContentText("Application de gestion des tâches d'un inspecteur pédagogique\n" +
                           "Version 1.0\n\n" +
                           "Fonctionnalités:\n" +
                           "- Gestion des visites, inspections, leçons expérimentales, réunions\n" +
                           "- Gestion des enseignants\n" +
                           "- Rédaction de rapports avec éditeur WYSIWYG\n" +
                           "- Génération de documents Word à partir de modèles");
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
