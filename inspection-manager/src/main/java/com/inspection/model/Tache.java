package com.inspection.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe représentant une tâche (visite, inspection, leçon expérimentale, réunion)
 * Avec statut et priorité pour la gestion complète du planning
 */
public class Tache {
    private int id;
    private TaskType type;
    private Enseignant enseignant;
    private Etablissement etablissement;
    private LocalDate date;
    private LocalTime heure;
    private String lieu;
    private String objet;
    private boolean planifiee;
    private boolean rapportRedige;
    private String contenuRapport; // HTML pour le WYSIWYG
    private LocalDate dateRapport;
    
    // Nouveaux champs pour la gestion avancée
    private TaskStatut statut; // À faire, En cours, Terminé
    private Priorite priorite; // Basse, Moyenne, Haute, Urgente
    private String notes; // Notes personnelles de l'inspecteur
    private LocalDate dateEcheance;
    private boolean archivee;
    
    public Tache() {
        this.planifiee = false;
        this.rapportRedige = false;
        this.statut = TaskStatut.A_FAIRE;
        this.priorite = Priorite.MOYENNE;
    }

    public Tache(TaskType type, Enseignant enseignant, LocalDate date, LocalTime heure, String lieu, String objet) {
        this.type = type;
        this.enseignant = enseignant;
        this.date = date;
        this.heure = heure;
        this.lieu = lieu;
        this.objet = objet;
        this.planifiee = true;
        this.rapportRedige = false;
        this.statut = TaskStatut.A_FAIRE;
        this.priorite = Priorite.MOYENNE;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public TaskType getType() { return type; }
    public void setType(TaskType type) { this.type = type; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }

    public Etablissement getEtablissement() { return etablissement; }
    public void setEtablissement(Etablissement etablissement) { this.etablissement = etablissement; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getObjet() { return objet; }
    public void setObjet(String objet) { this.objet = objet; }

    public boolean isPlanifiee() { return planifiee; }
    public void setPlanifiee(boolean planifiee) { this.planifiee = planifiee; }

    public boolean isRapportRedige() { return rapportRedige; }
    public void setRapportRedige(boolean rapportRedige) { this.rapportRedige = rapportRedige; }

    public String getContenuRapport() { return contenuRapport; }
    public void setContenuRapport(String contenuRapport) { this.contenuRapport = contenuRapport; }

    public LocalDate getDateRapport() { return dateRapport; }
    public void setDateRapport(LocalDate dateRapport) { this.dateRapport = dateRapport; }

    public TaskStatut getStatut() { return statut; }
    public void setStatut(TaskStatut statut) { this.statut = statut; }

    public Priorite getPriorite() { return priorite; }
    public void setPriorite(Priorite priorite) { this.priorite = priorite; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getDateEcheance() { return dateEcheance; }
    public void setDateEcheance(LocalDate dateEcheance) { this.dateEcheance = dateEcheance; }

    public boolean isArchivee() { return archivee; }
    public void setArchivee(boolean archivee) { this.archivee = archivee; }

    @Override
    public String toString() {
        return type.getLabel() + " - " + (enseignant != null ? enseignant.getNomComplet() : "N/A") + 
               " - " + (date != null ? date.toString() : "Non planifié");
    }
    
    /**
     * Enumération des statuts de tâche
     */
    public enum TaskStatut {
        A_FAIRE("À faire"),
        EN_COURS("En cours"),
        TERMINE("Terminé"),
        ANNULE("Annulé");
        
        private final String label;
        
        TaskStatut(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
        
        @Override
        public String toString() {
            return label;
        }
    }
    
    /**
     * Enumération des priorités
     */
    public enum Priorite {
        BASSE("Basse"),
        MOYENNE("Moyenne"),
        HAUTE("Haute"),
        URGENTE("Urgente");
        
        private final String label;
        
        Priorite(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
        
        @Override
        public String toString() {
            return label;
        }
    }
}
