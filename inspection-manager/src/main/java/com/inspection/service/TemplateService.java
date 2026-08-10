package com.inspection.service;

import com.inspection.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des modèles de documents Word
 */
public class TemplateService {
    private DatabaseService dbService;

    public TemplateService(DatabaseService dbService) {
        this.dbService = dbService;
    }

    /**
     * Enregistre un modèle de document dans la base de données
     */
    public int saveTemplate(Document template) throws SQLException {
        String sql;
        if (template.getId() == 0) {
            sql = """
                INSERT INTO templates (type, titre, destinataire, langue, statut, tags, chemin_fichier)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        } else {
            sql = """
                UPDATE templates SET type=?, titre=?, destinataire=?, langue=?, statut=?, tags=?, chemin_fichier=?
                WHERE id=?
                """;
        }

        try (PreparedStatement pstmt = dbService.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, template.getType());
            pstmt.setString(2, template.getTitre());
            pstmt.setString(3, template.getDestinataire());
            pstmt.setString(4, template.getLangue());
            pstmt.setString(5, template.getStatut());
            pstmt.setString(6, template.getTags());
            pstmt.setString(7, template.getCheminFichier());
            if (template.getId() != 0) {
                pstmt.setInt(8, template.getId());
            }
            pstmt.executeUpdate();

            if (template.getId() == 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    template.setId(rs.getInt(1));
                }
            }
            return template.getId();
        }
    }

    /**
     * Récupère tous les modèles de documents
     */
    public List<Document> getAllTemplates() throws SQLException {
        List<Document> templates = new ArrayList<>();
        String sql = "SELECT * FROM templates ORDER BY type, titre";

        try (Statement stmt = dbService.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Document doc = new Document();
                doc.setId(rs.getInt("id"));
                doc.setType(rs.getString("type"));
                doc.setTitre(rs.getString("titre"));
                doc.setDestinataire(rs.getString("destinataire"));
                doc.setLangue(rs.getString("langue"));
                doc.setStatut(rs.getString("statut"));
                doc.setTags(rs.getString("tags"));
                doc.setCheminFichier(rs.getString("chemin_fichier"));
                templates.add(doc);
            }
        }
        return templates;
    }

    /**
     * Récupère les modèles par type
     */
    public List<Document> getTemplatesByType(String type) throws SQLException {
        List<Document> templates = new ArrayList<>();
        String sql = "SELECT * FROM templates WHERE type=? ORDER BY titre";

        try (PreparedStatement pstmt = dbService.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Document doc = new Document();
                doc.setId(rs.getInt("id"));
                doc.setType(rs.getString("type"));
                doc.setTitre(rs.getString("titre"));
                doc.setDestinataire(rs.getString("destinataire"));
                doc.setLangue(rs.getString("langue"));
                doc.setStatut(rs.getString("statut"));
                doc.setTags(rs.getString("tags"));
                doc.setCheminFichier(rs.getString("chemin_fichier"));
                templates.add(doc);
            }
        }
        return templates;
    }

    /**
     * Supprime un modèle
     */
    public void deleteTemplate(int id) throws SQLException {
        String sql = "DELETE FROM templates WHERE id=?";
        try (PreparedStatement pstmt = dbService.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Crée un modèle par défaut pour chaque type de document
     */
    public void createDefaultTemplates(String basePath) throws Exception {
        // Modèle de rapport de visite
        Document rapportVisite = new Document("Rapport", "Rapport de Visite", "");
        rapportVisite.setTags("visite,inspection,enseignant");
        rapportVisite.setCheminFichier(basePath + "/modele_rapport_visite.docx");
        saveTemplate(rapportVisite);

        // Modèle de rapport d'inspection
        Document rapportInspection = new Document("Rapport", "Rapport d'Inspection", "");
        rapportInspection.setTags("inspection,evaluation,enseignant");
        rapportInspection.setCheminFichier(basePath + "/modele_rapport_inspection.docx");
        saveTemplate(rapportInspection);

        // Modèle de courrier en français
        Document courrierFR = new Document("Courrier", "Courrier Officiel", "");
        courrierFR.setLangue("FR");
        courrierFR.setTags("courrier,correspondance");
        courrierFR.setCheminFichier(basePath + "/modele_courrier_fr.docx");
        saveTemplate(courrierFR);

        // Modèle de courrier en arabe
        Document courrierAR = new Document("Courrier", "مراسلة رسمية", "");
        courrierAR.setLangue("AR");
        courrierAR.setTags("مراسلة,مخاطبة");
        courrierAR.setCheminFichier(basePath + "/modele_courrier_ar.docx");
        saveTemplate(courrierAR);

        // Modèle d'attestation
        Document attestation = new Document("Attestation", "Attestation", "");
        attestation.setTags("attestation,certificat");
        attestation.setCheminFichier(basePath + "/modele_attestation.docx");
        saveTemplate(attestation);

        // Modèle de bordereau
        Document bordereau = new Document("Bordereau", "Bordereau d'Envoi", "");
        bordereau.setTags("bordereau,envoi");
        bordereau.setCheminFichier(basePath + "/modele_bordereau.docx");
        saveTemplate(bordereau);
    }
}
