package com.inspection.model;

/**
 * Classe représentant un inspecteur pédagogique
 * Entité centrale du système contenant toutes les informations administratives et professionnelles
 */
public class Inspector {
    private int id;
    
    // Identité Administrative
    private String doti; // Numéro de sommation
    private String cin;
    private String rib;
    private String matriculePPR;
    
    // Informations personnelles
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String lieuNaissance;
    private String adresse;
    private String email;
    private String telephone;
    
    // Carrière
    private String grade; // Échelle/échelon
    private String titre; // Inspecteur Principal, etc.
    private String discipline;
    private String dateRecrutement;
    private String dateNomination;
    
    // Affectation
    private String academie; // AREF
    private String directionProvinciale;
    private String region;
    
    // Couverture pédagogique
    private String cycles; // Primaire, Collège, Lycée (séparés par des virgules)
    private String zones; // Zones affectées
    
    // Identité Visuelle (chemins vers les fichiers)
    private String photoProfilPath;
    private String signaturePath;
    private String cachetPath;
    
    // Sécurité
    private String passwordHash;
    private String questionSecrete;
    private String reponseSecrete;
    
    // Préférences
    private String theme; // Clair/Sombre
    private String couleurAccentuation;
    private boolean iaCloudEnabled;
    private String iaLocalUrl; // URL pour Ollama/LM Studio
    
    public Inspector() {
        this.theme = "clair";
        this.couleurAccentuation = "#4CAF50";
        this.iaCloudEnabled = false;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getDoti() { return doti; }
    public void setDoti(String doti) { this.doti = doti; }
    
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
    
    public String getRib() { return rib; }
    public void setRib(String rib) { this.rib = rib; }
    
    public String getMatriculePPR() { return matriculePPR; }
    public void setMatriculePPR(String matriculePPR) { this.matriculePPR = matriculePPR; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDiscipline() { return discipline; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }
    
    public String getDateRecrutement() { return dateRecrutement; }
    public void setDateRecrutement(String dateRecrutement) { this.dateRecrutement = dateRecrutement; }
    
    public String getDateNomination() { return dateNomination; }
    public void setDateNomination(String dateNomination) { this.dateNomination = dateNomination; }
    
    public String getAcademie() { return academie; }
    public void setAcademie(String academie) { this.academie = academie; }
    
    public String getDirectionProvinciale() { return directionProvinciale; }
    public void setDirectionProvinciale(String directionProvinciale) { this.directionProvinciale = directionProvinciale; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public String getCycles() { return cycles; }
    public void setCycles(String cycles) { this.cycles = cycles; }
    
    public String getZones() { return zones; }
    public void setZones(String zones) { this.zones = zones; }
    
    public String getPhotoProfilPath() { return photoProfilPath; }
    public void setPhotoProfilPath(String photoProfilPath) { this.photoProfilPath = photoProfilPath; }
    
    public String getSignaturePath() { return signaturePath; }
    public void setSignaturePath(String signaturePath) { this.signaturePath = signaturePath; }
    
    public String getCachetPath() { return cachetPath; }
    public void setCachetPath(String cachetPath) { this.cachetPath = cachetPath; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getQuestionSecrete() { return questionSecrete; }
    public void setQuestionSecrete(String questionSecrete) { this.questionSecrete = questionSecrete; }
    
    public String getReponseSecrete() { return reponseSecrete; }
    public void setReponseSecrete(String reponseSecrete) { this.reponseSecrete = reponseSecrete; }
    
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    
    public String getCouleurAccentuation() { return couleurAccentuation; }
    public void setCouleurAccentuation(String couleurAccentuation) { this.couleurAccentuation = couleurAccentuation; }
    
    public boolean isIaCloudEnabled() { return iaCloudEnabled; }
    public void setIaCloudEnabled(boolean iaCloudEnabled) { this.iaCloudEnabled = iaCloudEnabled; }
    
    public String getIaLocalUrl() { return iaLocalUrl; }
    public void setIaLocalUrl(String iaLocalUrl) { this.iaLocalUrl = iaLocalUrl; }
    
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    @Override
    public String toString() {
        return titre + " " + getNomComplet();
    }
}
