package com.inspectapp.model;

import java.time.LocalDate;

/**
 * Historique d'une inspection ou visite pédagogique
 */
public class InspectionHistory {
    private int id;
    private int teacherId;
    private LocalDate dateInspection;
    private String typeVisite; // INSPECTION, VISITE, LECON_EXPERIMENTALE, REUNION
    private double note;
    private String rapport; // Contenu du rapport
    private String rapportPath; // Chemin vers le document généré
    private String observations;
    private String recommandations;
    private boolean estSigne;
    private LocalDate dateSignature;
    
    public InspectionHistory() {}
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    
    public LocalDate getDateInspection() { return dateInspection; }
    public void setDateInspection(LocalDate dateInspection) { this.dateInspection = dateInspection; }
    
    public String getTypeVisite() { return typeVisite; }
    public void setTypeVisite(String typeVisite) { this.typeVisite = typeVisite; }
    
    public double getNote() { return note; }
    public void setNote(double note) { this.note = note; }
    
    public String getRapport() { return rapport; }
    public void setRapport(String rapport) { this.rapport = rapport; }
    
    public String getRapportPath() { return rapportPath; }
    public void setRapportPath(String rapportPath) { this.rapportPath = rapportPath; }
    
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    
    public String getRecommandations() { return recommandations; }
    public void setRecommandations(String recommandations) { this.recommandations = recommandations; }
    
    public boolean isEstSigne() { return estSigne; }
    public void setEstSigne(boolean estSigne) { this.estSigne = estSigne; }
    
    public LocalDate getDateSignature() { return dateSignature; }
    public void setDateSignature(LocalDate dateSignature) { this.dateSignature = dateSignature; }
}
