package com.inspectapp.database;

import java.sql.*;
import java.nio.file.Paths;
import java.io.File;

/**
 * Gestionnaire de la base de données SQLite locale
 */
public class DatabaseManager {
    private static final String DB_NAME = "inspectapp.db";
    private static DatabaseManager instance;
    private Connection connection;
    
    private DatabaseManager() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            reconnect();
        }
        return connection;
    }
    
    private void reconnect() throws SQLException {
        String dbPath = getDatabasePath();
        String url = "jdbc:sqlite:" + dbPath;
        connection = DriverManager.getConnection(url);
        connection.setAutoCommit(true);
    }
    
    private String getDatabasePath() {
        String userHome = System.getProperty("user.home");
        File appDir = new File(userHome, ".inspectapp");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return Paths.get(appDir.getAbsolutePath(), DB_NAME).toString();
    }
    
    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            reconnect();
            createTables();
            System.out.println("Base de données initialisée avec succès: " + getDatabasePath());
        } catch (Exception e) {
            System.err.println("Erreur d'initialisation de la base de données: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();
        
        // Table Inspector
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS inspector (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                doti TEXT, cin TEXT, rib TEXT, matricule_ppr TEXT,
                nom TEXT, prenom TEXT, nom_arabe TEXT, prenom_arabe TEXT,
                date_naissance TEXT, lieu_naissance TEXT,
                grade TEXT, titre TEXT, discipline TEXT, date_recrutement TEXT,
                academie TEXT, direction_provinciale TEXT, region TEXT,
                cycles TEXT, zones_affectees TEXT,
                photo_profil_path TEXT, signature_path TEXT, cachet_path TEXT,
                password_hash TEXT, question_secrete TEXT, reponse_secrete TEXT,
                theme TEXT, couleur_accentuation TEXT,
                ia_provider TEXT, ia_api_key TEXT, ia_local_url TEXT,
                en_tete_francais TEXT, en_tete_arabe TEXT
            )
        """);
        
        // Table Teachers
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS teachers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT, prenom TEXT, nom_arabe TEXT, prenom_arabe TEXT,
                date_naissance TEXT, lieu_naissance TEXT, cin TEXT, ppr TEXT, genre TEXT,
                telephone TEXT, email TEXT, adresse TEXT,
                grade TEXT, echelle TEXT, echelon TEXT, date_recrutement TEXT, date_grade_actuel TEXT,
                specialite TEXT, discipline TEXT,
                statut TEXT, est_stagiaire INTEGER, est_promouvable INTEGER,
                etablissement_id INTEGER, etablissement_nom TEXT,
                direction_provinciale TEXT, zone_geographique TEXT, code_gresa TEXT,
                derniere_inspection TEXT, derniere_note REAL, nombre_visites INTEGER,
                historique_json TEXT
            )
        """);
        
        // Table InspectionHistory
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS inspection_history (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                teacher_id INTEGER,
                date_inspection TEXT, type_visite TEXT, note REAL,
                rapport TEXT, rapport_path TEXT,
                observations TEXT, recommandations TEXT,
                est_signe INTEGER, date_signature TEXT,
                FOREIGN KEY (teacher_id) REFERENCES teachers(id)
            )
        """);
        
        // Table Tasks
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS tasks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titre TEXT, description TEXT, date_echeance TEXT,
                heure_debut TEXT, heure_fin TEXT, type_tache TEXT,
                priorite TEXT, statut TEXT,
                teacher_id INTEGER, teacher_nom TEXT,
                etablissement_id INTEGER, etablissement_nom TEXT,
                zone_geographique TEXT, lieu TEXT, participants TEXT,
                est_recurrente INTEGER, frequence_recurrence TEXT,
                date_creation TEXT, date_modification TEXT, notes TEXT
            )
        """);
        
        // Table Etablissements
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS etablissements (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT, nom_arabe TEXT, code_gresa TEXT,
                type_etablissement TEXT, adresse TEXT, telephone TEXT, email TEXT,
                directeur_nom TEXT, directeur_telephone TEXT,
                zone_id INTEGER, zone_nom TEXT, direction_provinciale TEXT, academie TEXT,
                latitude REAL, longitude REAL,
                nombre_enseignants INTEGER, nombre_eleves INTEGER
            )
        """);
        
        // Table Documents
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS documents (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titre TEXT, type_document TEXT, chemin_fichier TEXT,
                date_creation TEXT, teacher_id INTEGER, task_id INTEGER,
                contenu TEXT, modele_utilise TEXT
            )
        """);
        
        // Table Zones
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS zones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT, nom_arabe TEXT, type_zone TEXT,
                direction_provinciale TEXT, academie TEXT,
                parent_zone_id INTEGER
            )
        """);
        
        // Table Contacts (Annuaire)
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS contacts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT, prenom TEXT, fonction TEXT,
                organisme TEXT, telephone TEXT, email TEXT,
                adresse TEXT, zone_id INTEGER, notes TEXT
            )
        """);
        
        stmt.close();
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
