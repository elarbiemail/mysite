package com.inspection.service;

import com.inspection.model.Inspector;

import java.sql.*;
import java.util.Optional;

/**
 * Service de gestion du profil de l'inspecteur
 */
public class InspectorService {
    private DatabaseService dbService;

    public InspectorService(DatabaseService dbService) {
        this.dbService = dbService;
    }

    /**
     * Initialise la table des inspecteurs si elle n'existe pas
     */
    public void initTable() throws SQLException {
        String createTable = """
            CREATE TABLE IF NOT EXISTS inspector (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                doti TEXT,
                cin TEXT,
                rib TEXT,
                matricule_ppr TEXT,
                nom TEXT NOT NULL,
                prenom TEXT NOT NULL,
                date_naissance TEXT,
                lieu_naissance TEXT,
                adresse TEXT,
                email TEXT,
                telephone TEXT,
                grade TEXT,
                titre TEXT,
                discipline TEXT,
                date_recrutement TEXT,
                date_nomination TEXT,
                academie TEXT,
                direction_provinciale TEXT,
                region TEXT,
                cycles TEXT,
                zones TEXT,
                photo_profil_path TEXT,
                signature_path TEXT,
                cachet_path TEXT,
                password_hash TEXT,
                question_secrete TEXT,
                reponse_secrete TEXT,
                theme TEXT DEFAULT 'clair',
                couleur_accentuation TEXT DEFAULT '#4CAF50',
                ia_cloud_enabled BOOLEAN DEFAULT 0,
                ia_local_url TEXT
            )
            """;

        try (Statement stmt = dbService.getConnection().createStatement()) {
            stmt.execute(createTable);
        }
    }

    /**
     * Sauvegarde ou met à jour le profil de l'inspecteur
     */
    public int saveInspector(Inspector inspector) throws SQLException {
        initTable();
        
        // Vérifier s'il existe déjà un inspecteur
        Inspector existing = getInspector();
        
        String sql;
        if (existing == null) {
            sql = """
                INSERT INTO inspector (doti, cin, rib, matricule_ppr, nom, prenom, 
                    date_naissance, lieu_naissance, adresse, email, telephone,
                    grade, titre, discipline, date_recrutement, date_nomination,
                    academie, direction_provinciale, region, cycles, zones,
                    photo_profil_path, signature_path, cachet_path,
                    password_hash, question_secrete, reponse_secrete,
                    theme, couleur_accentuation, ia_cloud_enabled, ia_local_url)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        } else {
            sql = """
                UPDATE inspector SET doti=?, cin=?, rib=?, matricule_ppr=?, nom=?, prenom=?,
                    date_naissance=?, lieu_naissance=?, adresse=?, email=?, telephone=?,
                    grade=?, titre=?, discipline=?, date_recrutement=?, date_nomination=?,
                    academie=?, direction_provinciale=?, region=?, cycles=?, zones=?,
                    photo_profil_path=?, signature_path=?, cachet_path=?,
                    password_hash=?, question_secrete=?, reponse_secrete=?,
                    theme=?, couleur_accentuation=?, ia_cloud_enabled=?, ia_local_url=?
                WHERE id=?
                """;
        }

        try (PreparedStatement pstmt = dbService.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, inspector.getDoti());
            pstmt.setString(2, inspector.getCin());
            pstmt.setString(3, inspector.getRib());
            pstmt.setString(4, inspector.getMatriculePPR());
            pstmt.setString(5, inspector.getNom());
            pstmt.setString(6, inspector.getPrenom());
            pstmt.setString(7, inspector.getDateNaissance());
            pstmt.setString(8, inspector.getLieuNaissance());
            pstmt.setString(9, inspector.getAdresse());
            pstmt.setString(10, inspector.getEmail());
            pstmt.setString(11, inspector.getTelephone());
            pstmt.setString(12, inspector.getGrade());
            pstmt.setString(13, inspector.getTitre());
            pstmt.setString(14, inspector.getDiscipline());
            pstmt.setString(15, inspector.getDateRecrutement());
            pstmt.setString(16, inspector.getDateNomination());
            pstmt.setString(17, inspector.getAcademie());
            pstmt.setString(18, inspector.getDirectionProvinciale());
            pstmt.setString(19, inspector.getRegion());
            pstmt.setString(20, inspector.getCycles());
            pstmt.setString(21, inspector.getZones());
            pstmt.setString(22, inspector.getPhotoProfilPath());
            pstmt.setString(23, inspector.getSignaturePath());
            pstmt.setString(24, inspector.getCachetPath());
            pstmt.setString(25, inspector.getPasswordHash());
            pstmt.setString(26, inspector.getQuestionSecrete());
            pstmt.setString(27, inspector.getReponseSecrete());
            pstmt.setString(28, inspector.getTheme());
            pstmt.setString(29, inspector.getCouleurAccentuation());
            pstmt.setBoolean(30, inspector.isIaCloudEnabled());
            pstmt.setString(31, inspector.getIaLocalUrl());
            
            if (existing != null) {
                pstmt.setInt(32, existing.getId());
            }
            
            pstmt.executeUpdate();

            if (existing == null) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    inspector.setId(rs.getInt(1));
                }
            } else {
                inspector.setId(existing.getId());
            }
            return inspector.getId();
        }
    }

    /**
     * Récupère le profil de l'inspecteur
     */
    public Inspector getInspector() throws SQLException {
        initTable();
        
        String sql = "SELECT * FROM inspector LIMIT 1";
        
        try (Statement stmt = dbService.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                Inspector inspector = new Inspector();
                inspector.setId(rs.getInt("id"));
                inspector.setDoti(rs.getString("doti"));
                inspector.setCin(rs.getString("cin"));
                inspector.setRib(rs.getString("rib"));
                inspector.setMatriculePPR(rs.getString("matricule_ppr"));
                inspector.setNom(rs.getString("nom"));
                inspector.setPrenom(rs.getString("prenom"));
                inspector.setDateNaissance(rs.getString("date_naissance"));
                inspector.setLieuNaissance(rs.getString("lieu_naissance"));
                inspector.setAdresse(rs.getString("adresse"));
                inspector.setEmail(rs.getString("email"));
                inspector.setTelephone(rs.getString("telephone"));
                inspector.setGrade(rs.getString("grade"));
                inspector.setTitre(rs.getString("titre"));
                inspector.setDiscipline(rs.getString("discipline"));
                inspector.setDateRecrutement(rs.getString("date_recrutement"));
                inspector.setDateNomination(rs.getString("date_nomination"));
                inspector.setAcademie(rs.getString("academie"));
                inspector.setDirectionProvinciale(rs.getString("direction_provinciale"));
                inspector.setRegion(rs.getString("region"));
                inspector.setCycles(rs.getString("cycles"));
                inspector.setZones(rs.getString("zones"));
                inspector.setPhotoProfilPath(rs.getString("photo_profil_path"));
                inspector.setSignaturePath(rs.getString("signature_path"));
                inspector.setCachetPath(rs.getString("cachet_path"));
                inspector.setPasswordHash(rs.getString("password_hash"));
                inspector.setQuestionSecrete(rs.getString("question_secrete"));
                inspector.setReponseSecrete(rs.getString("reponse_secrete"));
                inspector.setTheme(rs.getString("theme"));
                inspector.setCouleurAccentuation(rs.getString("couleur_accentuation"));
                inspector.setIaCloudEnabled(rs.getBoolean("ia_cloud_enabled"));
                inspector.setIaLocalUrl(rs.getString("ia_local_url"));
                return inspector;
            }
        }
        return null;
    }

    /**
     * Met à jour uniquement les préférences de l'inspecteur
     */
    public void updatePreferences(String theme, String couleurAccentuation, 
                                  boolean iaCloudEnabled, String iaLocalUrl) throws SQLException {
        Inspector inspector = getInspector();
        if (inspector != null) {
            inspector.setTheme(theme);
            inspector.setCouleurAccentuation(couleurAccentuation);
            inspector.setIaCloudEnabled(iaCloudEnabled);
            inspector.setIaLocalUrl(iaLocalUrl);
            saveInspector(inspector);
        }
    }

    /**
     * Définit le chemin vers la signature de l'inspecteur
     */
    public void setSignaturePath(String path) throws SQLException {
        Inspector inspector = getInspector();
        if (inspector != null) {
            inspector.setSignaturePath(path);
            saveInspector(inspector);
        }
    }

    /**
     * Définit le chemin vers le cachet de l'inspecteur
     */
    public void setCachetPath(String path) throws SQLException {
        Inspector inspector = getInspector();
        if (inspector != null) {
            inspector.setCachetPath(path);
            saveInspector(inspector);
        }
    }
}
