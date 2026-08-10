package com.inspectapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle de données pour un Enseignant
 */
public class Teacher {
    private int id;
    
    // État civil
    private String nom;
    private String prenom;
    private String nomArabe;
    private String prenomArabe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String cin;
    private String ppr; // Matricule
    private String genre; // M, F
    
    // Contact
    private String telephone;
    private String email;
    private String adresse;
    
    // Carrière
    private String grade;
    private String echelle;
    private String echelon;
    private LocalDate dateRecrutement;
    private LocalDate dateGradeActuel;
    private String specialite;
    private String discipline;
    
    // Statut
    private String statut; // TITULAIRE, STAGIAIRE, CONTRACTUEL
    private boolean estStagiaire;
    private boolean estPromouvable;
    
    // Affectation
    private int etablissementId;
    private String etablissementNom;
    private String directionProvinciale;
    private String zoneGeographique;
    private String codeGresa;
    
    // Suivi Pédagogique
    private LocalDate derniereInspection;
    private double derniereNote;
    private int nombreVisites;
    private List<InspectionHistory> historiqueInspections;
    
    public Teacher() {
        this.historiqueInspections = new ArrayList<>();
        this.estStagiaire = false;
        this.estPromouvable = false;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getNomArabe() { return nomArabe; }
    public void setNomArabe(String nomArabe) { this.nomArabe = nomArabe; }
    
    public String getPrenomArabe() { return prenomArabe; }
    public void setPrenomArabe(String prenomArabe) { this.prenomArabe = prenomArabe; }
    
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }
    
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
    
    public String getPpr() { return ppr; }
    public void setPpr(String ppr) { this.ppr = ppr; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    public String getEchelle() { return echelle; }
    public void setEchelle(String echelle) { this.echelle = echelle; }
    
    public String getEchelon() { return echelon; }
    public void setEchelon(String echelon) { this.echelon = echelon; }
    
    public LocalDate getDateRecrutement() { return dateRecrutement; }
    public void setDateRecrutement(LocalDate dateRecrutement) { this.dateRecrutement = dateRecrutement; }
    
    public LocalDate getDateGradeActuel() { return dateGradeActuel; }
    public void setDateGradeActuel(LocalDate dateGradeActuel) { this.dateGradeActuel = dateGradeActuel; }
    
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    
    public String getDiscipline() { return discipline; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public boolean isEstStagiaire() { return estStagiaire; }
    public void setEstStagiaire(boolean estStagiaire) { this.estStagiaire = estStagiaire; }
    
    public boolean isEstPromouvable() { return estPromouvable; }
    public void setEstPromouvable(boolean estPromouvable) { this.estPromouvable = estPromouvable; }
    
    public int getEtablissementId() { return etablissementId; }
    public void setEtablissementId(int etablissementId) { this.etablissementId = etablissementId; }
    
    public String getEtablissementNom() { return etablissementNom; }
    public void setEtablissementNom(String etablissementNom) { this.etablissementNom = etablissementNom; }
    
    public String getDirectionProvinciale() { return directionProvinciale; }
    public void setDirectionProvinciale(String directionProvinciale) { this.directionProvinciale = directionProvinciale; }
    
    public String getZoneGeographique() { return zoneGeographique; }
    public void setZoneGeographique(String zoneGeographique) { this.zoneGeographique = zoneGeographique; }
    
    public String getCodeGresa() { return codeGresa; }
    public void setCodeGresa(String codeGresa) { this.codeGresa = codeGresa; }
    
    public LocalDate getDerniereInspection() { return derniereInspection; }
    public void setDerniereInspection(LocalDate derniereInspection) { this.derniereInspection = derniereInspection; }
    
    public double getDerniereNote() { return derniereNote; }
    public void setDerniereNote(double derniereNote) { this.derniereNote = derniereNote; }
    
    public int getNombreVisites() { return nombreVisites; }
    public void setNombreVisites(int nombreVisites) { this.nombreVisites = nombreVisites; }
    
    public List<InspectionHistory> getHistoriqueInspections() { return historiqueInspections; }
    public void setHistoriqueInspections(List<InspectionHistory> historiqueInspections) { this.historiqueInspections = historiqueInspections; }
    
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public String getNomCompletArabe() {
        if (prenomArabe != null && nomArabe != null) {
            return nomArabe + " " + prenomArabe;
        }
        return nom + " " + prenom;
    }
    
    public long getAnneesAnciennete() {
        if (dateRecrutement != null) {
            return java.time.Years.between(dateRecrutement, LocalDate.now()).getYears();
        }
        return 0;
    }
    
    public long getMoisDepuisDerniereInspection() {
        if (derniereInspection != null) {
            return java.time.Months.between(derniereInspection, LocalDate.now()).getMonths();
        }
        return -1; // Jamais inspecté
    }
}
