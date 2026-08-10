package com.inspectapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modèle de données pour une Tâche (visite, réunion, commission, etc.)
 */
public class Task {
    private int id;
    private String titre;
    private String description;
    private LocalDate dateEcheance;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private String typeTache; // VISITE_CLASSE, REUNION, COMMISSION, FORMATION, INSPECTION, LECON_EXPERIMENTALE
    private String priorite; // URGENTE, NORMALE, BASSE
    private String statut; // A_FAIRE, EN_COURS, TERMINEE
    private int teacherId; // Enseignant concerné (si applicable)
    private String teacherNom;
    private int etablissementId;
    private String etablissementNom;
    private String zoneGeographique;
    private String lieu;
    private String participants; // Liste des participants pour les réunions
    private boolean estRecurrente;
    private String frequenceRecurrence; // QUOTIDIENNE, HEBDOMADAIRE, MENSUELLE
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String notes;
    
    public Task() {
        this.statut = "A_FAIRE";
        this.priorite = "NORMALE";
        this.dateCreation = LocalDateTime.now();
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDate getDateEcheance() { return dateEcheance; }
    public void setDateEcheance(LocalDate dateEcheance) { this.dateEcheance = dateEcheance; }
    
    public LocalDateTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalDateTime heureDebut) { this.heureDebut = heureDebut; }
    
    public LocalDateTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalDateTime heureFin) { this.heureFin = heureFin; }
    
    public String getTypeTache() { return typeTache; }
    public void setTypeTache(String typeTache) { this.typeTache = typeTache; }
    
    public String getPriorite() { return priorite; }
    public void setPriorite(String priorite) { this.priorite = priorite; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    
    public String getTeacherNom() { return teacherNom; }
    public void setTeacherNom(String teacherNom) { this.teacherNom = teacherNom; }
    
    public int getEtablissementId() { return etablissementId; }
    public void setEtablissementId(int etablissementId) { this.etablissementId = etablissementId; }
    
    public String getEtablissementNom() { return etablissementNom; }
    public void setEtablissementNom(String etablissementNom) { this.etablissementNom = etablissementNom; }
    
    public String getZoneGeographique() { return zoneGeographique; }
    public void setZoneGeographique(String zoneGeographique) { this.zoneGeographique = zoneGeographique; }
    
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    
    public String getParticipants() { return participants; }
    public void setParticipants(String participants) { this.participants = participants; }
    
    public boolean isEstRecurrente() { return estRecurrente; }
    public void setEstRecurrente(boolean estRecurrente) { this.estRecurrente = estRecurrente; }
    
    public String getFrequenceRecurrence() { return frequenceRecurrence; }
    public void setFrequenceRecurrence(String frequenceRecurrence) { this.frequenceRecurrence = frequenceRecurrence; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public boolean isUrgente() {
        return "URGENTE".equals(priorite);
    }
    
    public boolean isTerminee() {
        return "TERMINEE".equals(statut);
    }
}
