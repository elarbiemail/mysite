package com.inspectapp.service;

import com.inspectapp.model.Inspector;
import com.inspectapp.model.Teacher;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service de génération de documents Word (.docx)
 * Permet de fusionner des données dans des modèles de documents
 */
public class DocumentGenerationService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.*?)\\}\\}");
    
    /**
     * Génère un rapport d'inspection à partir d'un modèle
     */
    public String generateInspectionReport(Teacher teacher, Inspector inspector, 
                                           String rapportContent, String outputPath) throws IOException {
        
        // Créer un nouveau document Word
        XWPFDocument document = new XWPFDocument();
        
        // En-tête officiel
        createHeader(document, inspector);
        
        // Titre du rapport
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("RAPPORT D'INSPECTION PÉDAGOGIQUE");
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setFontFamily("Arial");
        document.createParagraph(); // Espacement
        
        // Informations sur l'enseignant
        createTeacherInfoSection(document, teacher);
        
        // Contenu du rapport (WYSIWYG)
        createRapportContent(document, rapportContent);
        
        // Recommandations
        createRecommendationsSection(document, teacher);
        
        // Pied de page avec signature
        createFooterWithSignature(document, inspector);
        
        // Sauvegarder le document
        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            document.write(out);
        }
        
        document.close();
        return outputPath;
    }
    
    /**
     * Génère un document à partir d'un modèle .docx avec fusion de données
     */
    public String generateFromTemplate(String templatePath, Map<String, String> data, 
                                       String outputPath) throws IOException {
        
        XWPFDocument document = new XWPFDocument(new java.io.FileInputStream(templatePath));
        
        // Remplacer les placeholders dans les paragraphes
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replacePlaceholdersInParagraph(paragraph, data);
        }
        
        // Remplacer les placeholders dans les tableaux
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replacePlaceholdersInParagraph(paragraph, data);
                    }
                }
            }
        }
        
        // Sauvegarder le document généré
        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            document.write(out);
        }
        
        document.close();
        return outputPath;
    }
    
    /**
     * Génère une attestation de présence
     */
    public String generateAttestation(Teacher teacher, Inspector inspector,
                                      String typeEvenement, LocalDate date, String outputPath) throws IOException {
        
        XWPFDocument document = new XWPFDocument();
        
        // En-tête
        createHeader(document, inspector);
        document.createParagraph();
        
        // Titre
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("ATTESTATION DE PARTICIPATION");
        titleRun.setBold(true);
        titleRun.setFontSize(14);
        document.createParagraph();
        
        // Corps du texte
        XWPFParagraph bodyPara = document.createParagraph();
        XWPFRun bodyRun = bodyPara.createRun();
        bodyRun.setText("Je soussigné(e), ");
        bodyRun.addBreak();
        bodyRun.setText(inspector.getNomComplet() + ", Inspecteur Pédagogique,");
        bodyRun.addBreak();
        bodyRun.setText("atteste que M./Mme " + teacher.getNomComplet() + " a participé à ");
        bodyRun.addBreak();
        bodyRun.setText(typeEvenement + " le " + date.format(DATE_FORMATTER) + ".");
        bodyRun.addBreak();
        bodyRun.setText("Délivré pour servir et valoir ce que de droit.");
        
        document.createParagraph();
        document.createParagraph();
        
        // Signature
        createFooterWithSignature(document, inspector);
        
        // Sauvegarder
        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            document.write(out);
        }
        
        document.close();
        return outputPath;
    }
    
    /**
     * Génère un bordereau d'envoi
     */
    public String generateBordereau(Inspector inspector, List<Teacher> teachers,
                                    String objet, LocalDate date, String outputPath) throws IOException {
        
        Map<String, String> data = new HashMap<>();
        data.put("inspecteur_nom", inspector.getNomComplet());
        data.put("inspecteur_grade", inspector.getGrade());
        data.put("academie", inspector.getAcademie());
        data.put("date", date.format(DATE_FORMATTER));
        data.put("objet", objet);
        data.put("nombre_enseignants", String.valueOf(teachers.size()));
        
        // Liste des enseignants
        StringBuilder listeEnseignants = new StringBuilder();
        for (int i = 0; i < teachers.size(); i++) {
            Teacher t = teachers.get(i);
            listeEnseignants.append((i + 1))
                           .append(". ")
                           .append(t.getNomComplet())
                           .append(" - ")
                           .append(t.getEtablissementNom())
                           .append("\n");
        }
        data.put("liste_enseignants", listeEnseignants.toString());
        
        return generateFromTemplate(getDefaultBordereauTemplate(), data, outputPath);
    }
    
    private void createHeader(XWPFDocument document, Inspector inspector) {
        XWPFParagraph headerPara = document.createParagraph();
        headerPara.setAlignment(ParagraphAlignment.LEFT);
        
        XWPFRun run = headerPara.createRun();
        run.setText("Ministère de l'Éducation Nationale");
        run.setBold(true);
        run.setFontSize(12);
        run.addBreak();
        run.setText(inspector.getAcademie());
        run.addBreak();
        run.setText(inspector.getDirectionProvinciale());
        run.addBreak();
        run.setText("Inspection Pédagogique de " + inspector.getDiscipline());
        run.addBreak();
        run.addBreak();
        run.setText("Référence: " + inspector.getDoti());
    }
    
    private void createTeacherInfoSection(XWPFDocument document, Teacher teacher) {
        XWPFParagraph infoPara = document.createParagraph();
        XWPFRun run = infoPara.createRun();
        run.setBold(true);
        run.setText("INFORMATIONS SUR L'ENSEIGNANT:");
        
        document.createParagraph();
        
        XWPFParagraph detailsPara = document.createParagraph();
        XWPFRun detailsRun = detailsPara.createRun();
        detailsRun.setText("Nom et Prénom: " + teacher.getNomComplet());
        detailsRun.addBreak();
        detailsRun.setText("PPR: " + teacher.getPpr());
        detailsRun.addBreak();
        detailsRun.setText("Grade: " + teacher.getGrade());
        detailsRun.addBreak();
        detailsRun.setText("Établissement: " + teacher.getEtablissementNom());
        detailsRun.addBreak();
        detailsRun.setText("Date de la visite: " + LocalDate.now().format(DATE_FORMATTER));
    }
    
    private void createRapportContent(XWPFDocument document, String content) {
        document.createParagraph();
        
        XWPFParagraph rapportPara = document.createParagraph();
        XWPFRun rapportRun = rapportPara.createRun();
        rapportRun.setBold(true);
        rapportRun.setText("RAPPORT DÉTAILLÉ:");
        
        document.createParagraph();
        
        // Ajouter le contenu HTML/texte du rapport WYSIWYG
        XWPFParagraph contentPara = document.createParagraph();
        XWPFRun contentRun = contentPara.createRun();
        contentRun.setText(content != null ? content : "Contenu du rapport...");
    }
    
    private void createRecommendationsSection(XWPFDocument document, Teacher teacher) {
        document.createParagraph();
        
        XWPFParagraph recoPara = document.createParagraph();
        XWPFRun recoRun = recoPara.createRun();
        recoRun.setBold(true);
        recoRun.setText("RECOMMANDATIONS:");
        
        document.createParagraph();
        
        XWPFParagraph recommendationsPara = document.createParagraph();
        XWPFRun recommendationsRun = recommendationsPara.createRun();
        recommendationsRun.setText("- Continuer les efforts pédagogiques");
        recommendationsRun.addBreak();
        recommendationsRun.setText("- Développer l'utilisation des TICE");
        recommendationsRun.addBreak();
        recommendationsRun.setText("- Renforcer l'évaluation formative");
    }
    
    private void createFooterWithSignature(XWPFDocument document, Inspector inspector) {
        document.createParagraph();
        document.createParagraph();
        document.createParagraph();
        
        XWPFParagraph signaturePara = document.createParagraph();
        signaturePara.setAlignment(ParagraphAlignment.RIGHT);
        
        XWPFRun signatureRun = signaturePara.createRun();
        signatureRun.setText("L'Inspecteur Pédagogique,");
        signatureRun.addBreak();
        signatureRun.addBreak();
        signatureRun.addBreak();
        signatureRun.setText(inspector.getNomComplet());
        signatureRun.setBold(true);
        
        // Note: Pour ajouter une image de signature, utiliser:
        // if (inspector.getSignaturePath() != null) {
        //     FileInputStream fis = new FileInputStream(inspector.getSignaturePath());
        //     signatureRun.addPicture(fis, PictureType.PNG.getValue(), "signature", 
        //                             Units.toEMU(150), Units.toEMU(50));
        // }
    }
    
    private void replacePlaceholdersInParagraph(XWPFParagraph paragraph, Map<String, String> data) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) return;
        
        // Concaténer tout le texte du paragraphe
        StringBuilder fullText = new StringBuilder();
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                fullText.append(text);
            }
        }
        
        String paragraphText = fullText.toString();
        
        // Trouver et remplacer tous les placeholders
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(paragraphText);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            String replacement = data.getOrDefault(placeholder, matcher.group(0));
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        
        // Mettre à jour le premier run avec le texte complet remplacé
        // et supprimer les autres runs
        if (runs.size() > 0) {
            XWPFRun firstRun = runs.get(0);
            firstRun.setText(sb.toString(), 0);
            
            for (int i = 1; i < runs.size(); i++) {
                runs.get(i).setText("", 0);
            }
        }
    }
    
    private String getDefaultBordereauTemplate() {
        // Retourne le chemin vers le modèle par défaut de bordereau
        // Dans une implémentation réelle, ce fichier serait dans resources/templates
        return System.getProperty("user.home") + "/.inspectapp/templates/bordereau_template.docx";
    }
}
