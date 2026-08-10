package com.inspectapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle de données pour un Inspecteur Pédagogique
 */
public class Inspector {
    private int id;
    
    // Identité Administrative
    private String doti; // Numéro de sommation
    private String cin;
    private String rib;
    private String matriculePPR;
    private String nom;
    private String prenom;
    private String nomArabe;
    private String prenomArabe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    
    // Carrière
    private String grade; // échelle/échelon
    private String titre; // Inspecteur Principal, etc.
    private String discipline;
    private LocalDate dateRecrutement;
    
    // Affectation
    private String academie; // AREF
    private String directionProvinciale;
    private String region;
    
    // Couverture
    private List<String> cycles; // Primaire, Collège, Lycée
    private List<String> zonesAffectees;
    
    // Identité Visuelle (chemins vers fichiers)
    private String photoProfilPath;
    private String signaturePath;
    private String cachetPath;
    
    // Sécurité
    private String passwordHash;
    private String questionSecrete;
    private String reponseSecrete;
    
    // Paramètres d'interface
    private String theme; // LIGHT, DARK
    private String couleurAccentuation;
    
    // Configuration IA
    private String iaProvider; // CLOUD, LOCAL
    private String iaApiKey;
    private String iaLocalUrl;
    
    // En-têtes officiels
    private String enTeteFrancais;
    private String enTeteArabe;
    
    public Inspector() {
        this.cycles = new ArrayList<>();
        this.zonesAffectees = new ArrayList<>();
        this.theme = "LIGHT";
        this.couleurAccentuation = "BLUE";
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
    
    public String getNomArabe() { return nomArabe; }
    public void setNomArabe(String nomArabe) { this.nomArabe = nomArabe; }
    
    public String getPrenomArabe() { return prenomArabe; }
    public void setPrenomArabe(String prenomArabe) { this.prenomArabe = prenomArabe; }
    
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDiscipline() { return discipline; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }
    
    public LocalDate getDateRecrutement() { return dateRecrutement; }
    public void setDateRecrutement(LocalDate dateRecrutement) { this.dateRecrutement = dateRecrutement; }
    
    public String getAcademie() { return academie; }
    public void setAcademie(String academie) { this.academie = academie; }
    
    public String getDirectionProvinciale() { return directionProvinciale; }
    public void setDirectionProvinciale(String directionProvinciale) { this.directionProvinciale = directionProvinciale; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public List<String> getCycles() { return cycles; }
    public void setCycles(List<String> cycles) { this.cycles = cycles; }
    
    public List<String> getZonesAffectees() { return zonesAffectees; }
    public void setZonesAffectees(List<String> zonesAffectees) { this.zonesAffectees = zonesAffectees; }
    
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
    
    public String getIaProvider() { return iaProvider; }
    public void setIaProvider(String iaProvider) { this.iaProvider = iaProvider; }
    
    public String getIaApiKey() { return iaApiKey; }
    public void setIaApiKey(String iaApiKey) { this.iaApiKey = iaApiKey; }
    
    public String getIaLocalUrl() { return iaLocalUrl; }
    public void setIaLocalUrl(String iaLocalUrl) { this.iaLocalUrl = iaLocalUrl; }
    
    public String getEnTeteFrancais() { return enTeteFrancais; }
    public void setEnTeteFrancais(String enTeteFrancais) { this.enTeteFrancais = enTeteFrancais; }
    
    public String getEnTeteArabe() { return enTeteArabe; }
    public void setEnTeteArabe(String enTeteArabe) { this.enTeteArabe = enTeteArabe; }
    
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public String getInitiales() {
        if (prenom != null && nom != null && !prenom.isEmpty() && !nom.isEmpty()) {
            return (prenom.substring(0, 1) + nom.substring(0, 1)).toUpperCase();
        }
        return "??";
    }
}
