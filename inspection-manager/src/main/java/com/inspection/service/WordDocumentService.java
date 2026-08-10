package com.inspection.service;

import com.inspection.model.Enseignant;
import com.inspection.model.Tache;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service de génération de documents Word à partir d'un modèle
 */
public class WordDocumentService {

    /**
     * Génère un rapport Word pour une tâche donnée à partir d'un modèle
     */
    public void generateRapport(Tache tache, String templatePath, String outputPath) throws IOException {
        // Charger le modèle
        XWPFDocument document;
        try (FileInputStream fis = new FileInputStream(templatePath)) {
            document = new XWPFDocument(fis);
        } catch (IOException e) {
            // Si le modèle n'existe pas, créer un nouveau document
            document = createDefaultTemplate(tache);
        }

        // Remplacer les placeholders par les données de la tâche
        replacePlaceholders(document, tache);

        // Sauvegarder le document
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            document.write(fos);
        }
        document.close();
    }

    /**
     * Crée un modèle par défaut si aucun modèle n'est fourni
     */
    private XWPFDocument createDefaultTemplate(Tache tache) {
        XWPFDocument document = new XWPFDocument();

        // Titre
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setText("RAPPORT DE " + tache.getType().getLabel().toUpperCase());

        // Saut de ligne
        document.createParagraph();

        // Informations sur l'enseignant
        addSection(document, "INFORMATIONS SUR L'ENSEIGNANT");
        Enseignant enseignant = tache.getEnseignant();
        if (enseignant != null) {
            addParagraph(document, "Nom et prénom: " + enseignant.getNomComplet());
            addParagraph(document, "Matière: " + enseignant.getMatiere());
            addParagraph(document, "Établissement: " + enseignant.getEtablissement());
            addParagraph(document, "Grade: " + enseignant.getGrade());
            addParagraph(document, "Email: " + enseignant.getEmail());
            addParagraph(document, "Téléphone: " + enseignant.getTelephone());
        }

        // Saut de ligne
        document.createParagraph();

        // Informations sur la tâche
        addSection(document, "DÉTAILS DE LA " + tache.getType().getLabel().toUpperCase());
        addParagraph(document, "Date: " + (tache.getDate() != null ? tache.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
        addParagraph(document, "Heure: " + (tache.getHeure() != null ? tache.getHeure().toString() : "N/A"));
        addParagraph(document, "Lieu: " + tache.getLieu());
        addParagraph(document, "Objet: " + tache.getObjet());

        // Saut de ligne
        document.createParagraph();

        // Rapport
        addSection(document, "RAPPORT");
        if (tache.getContenuRapport() != null && !tache.getContenuRapport().isEmpty()) {
            // Convertir HTML simple en texte pour le document Word
            String contenuText = tache.getContenuRapport()
                    .replaceAll("<br>", "\n")
                    .replaceAll("<p>", "")
                    .replaceAll("</p>", "\n\n")
                    .replaceAll("<[^>]*>", "");
            addParagraph(document, contenuText);
        } else {
            addParagraph(document, "[Contenu du rapport à rédiger]");
        }

        // Saut de ligne
        document.createParagraph();

        // Date et signature
        addParagraph(document, "Fait le: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        document.createParagraph();
        addParagraph(document, "Signature de l'inspecteur:");
        document.createParagraph();
        document.createParagraph();
        addParagraph(document, "_________________________");

        return document;
    }

    /**
     * Ajoute une section avec titre
     */
    private void addSection(XWPFDocument document, String title) {
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setBold(true);
        run.setFontSize(12);
        run.setText(title);
    }

    /**
     * Ajoute un paragraphe de texte
     */
    private void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setText(text);
    }

    /**
     * Remplace les placeholders dans le document Word
     */
    private void replacePlaceholders(XWPFDocument document, Tache tache) {
        Map<String, String> placeholders = new HashMap<>();

        // Données de l'enseignant
        if (tache.getEnseignant() != null) {
            placeholders.put("${enseignant.nom}", tache.getEnseignant().getNom());
            placeholders.put("${enseignant.prenom}", tache.getEnseignant().getPrenom());
            placeholders.put("${enseignant.nomComplet}", tache.getEnseignant().getNomComplet());
            placeholders.put("${enseignant.matiere}", tache.getEnseignant().getMatiere());
            placeholders.put("${enseignant.etablissement}", tache.getEnseignant().getEtablissement());
            placeholders.put("${enseignant.grade}", tache.getEnseignant().getGrade());
            placeholders.put("${enseignant.email}", tache.getEnseignant().getEmail());
            placeholders.put("${enseignant.telephone}", tache.getEnseignant().getTelephone());
        }

        // Données de la tâche
        placeholders.put("${tache.type}", tache.getType().getLabel());
        placeholders.put("${tache.date}", tache.getDate() != null ? 
            tache.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A");
        placeholders.put("${tache.heure}", tache.getHeure() != null ? tache.getHeure().toString() : "N/A");
        placeholders.put("${tache.lieu}", tache.getLieu());
        placeholders.put("${tache.objet}", tache.getObjet());
        placeholders.put("${tache.contenuRapport}", tache.getContenuRapport() != null ? 
            tache.getContenuRapport().replaceAll("<[^>]*>", "") : "");
        placeholders.put("${dateGeneration}", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Parcourir tous les paragraphes et remplacer les placeholders
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replaceInParagraph(paragraph, placeholders);
        }

        // Parcourir tous les tableaux
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceInParagraph(paragraph, placeholders);
                    }
                }
            }
        }
    }

    /**
     * Remplace les placeholders dans un paragraphe
     */
    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> placeholders) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) {
            return;
        }

        // Concaténer tout le texte du paragraphe
        StringBuilder fullText = new StringBuilder();
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                fullText.append(text);
            }
        }

        String paragraphText = fullText.toString();

        // Remplacer tous les placeholders
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            paragraphText = paragraphText.replace(entry.getKey(), entry.getValue());
        }

        // Mettre à jour le premier run avec le texte complet et supprimer les autres
        if (!runs.isEmpty()) {
            XWPFRun firstRun = runs.get(0);
            firstRun.setText(paragraphText, 0);
            for (int i = 1; i < runs.size(); i++) {
                paragraph.removeRun(i);
            }
        }
    }

    /**
     * Crée un modèle Word par défaut qui sera utilisé comme template
     */
    public void createTemplate(String outputPath) throws IOException {
        XWPFDocument document = new XWPFDocument();

        // Titre
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setText("MODÈLE DE RAPPORT - INSPECTION PÉDAGOGIQUE");

        document.createParagraph();

        // Instructions
        XWPFParagraph instructions = document.createParagraph();
        XWPFRun instRun = instructions.createRun();
        instRun.setItalic(true);
        instRun.setText("Instructions: Les champs entre ${} seront automatiquement remplis. Vous pouvez modifier ce modèle selon vos besoins.");

        document.createParagraph();

        // Section Enseignant
        addSection(document, "INFORMATIONS SUR L'ENSEIGNANT");
        addParagraph(document, "Nom et prénom: ${enseignant.nomComplet}");
        addParagraph(document, "Matière: ${enseignant.matiere}");
        addParagraph(document, "Établissement: ${enseignant.etablissement}");
        addParagraph(document, "Grade: ${enseignant.grade}");
        addParagraph(document, "Email: ${enseignant.email}");
        addParagraph(document, "Téléphone: ${enseignant.telephone}");

        document.createParagraph();

        // Section Tâche
        addSection(document, "DÉTAILS DE LA MISSION");
        addParagraph(document, "Type de mission: ${tache.type}");
        addParagraph(document, "Date: ${tache.date}");
        addParagraph(document, "Heure: ${tache.heure}");
        addParagraph(document, "Lieu: ${tache.lieu}");
        addParagraph(document, "Objet: ${tache.objet}");

        document.createParagraph();

        // Section Rapport
        addSection(document, "RAPPORT");
        addParagraph(document, "${tache.contenuRapport}");

        document.createParagraph();
        document.createParagraph();

        // Signature
        addParagraph(document, "Fait le: ${dateGeneration}");
        document.createParagraph();
        addParagraph(document, "Signature de l'inspecteur:");
        document.createParagraph();
        document.createParagraph();
        addParagraph(document, "_________________________");

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            document.write(fos);
        }
        document.close();
    }
}
