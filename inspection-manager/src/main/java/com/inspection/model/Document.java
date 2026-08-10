package com.inspection.model;

import java.time.LocalDate;

/**
 * Classe représentant un document généré (rapport, courrier, attestation)
 */
public class Document {
    private int id;
    private String type; // Rapport, Courrier, Attestation, Bordereau
    private String titre;
    private LocalDate dateCreation;
    String destinataire;
    private String cheminFichier;
    private int tacheId; // Lien vers la tâche associée
    private int enseignantId; // Lien vers l'enseignant concerné
    private String langue; // FR ou AR
    private String statut; // Brouillon, Validé, Envoyé
    private String tags; // Mots-clés pour la recherche
    
    public Document() {
        this.dateCreation = LocalDate.now();
        this.langue = "FR";
        this.statut = "Brouillon";
    }
    
    public Document(String type, String titre, String destinataire) {
        this();
        this.type = type;
        this.titre = titre;
        this.destinataire = destinataire;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    
    public String getDestinataire() { return destinataire; }
    public void setDestinataire(String destinataire) { this.destinataire = destinataire; }
    
    public String getCheminFichier() { return cheminFichier; }
    public void setCheminFichier(String cheminFichier) { this.cheminFichier = cheminFichier; }
    
    public int getTacheId() { return tacheId; }
    public void setTacheId(int tacheId) { this.tacheId = tacheId; }
    
    public int getEnseignantId() { return enseignantId; }
    public void setEnseignantId(int enseignantId) { this.enseignantId = enseignantId; }
    
    public String getLangue() { return langue; }
    public void setLangue(String langue) { this.langue = langue; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    
    @Override
    public String toString() {
        return type + " - " + titre;
    }
}
