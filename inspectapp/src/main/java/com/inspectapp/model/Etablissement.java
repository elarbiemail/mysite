package com.inspectapp.model;

/**
 * Modèle de données pour un Établissement scolaire
 */
public class Etablissement {
    private int id;
    private String nom;
    private String nomArabe;
    private String codeGresa;
    private String typeEtablissement; // ECOLE_PRIMAIRE, COLLEGE, LYCEE
    private String adresse;
    private String telephone;
    private String email;
    private String directeurNom;
    private String directeurTelephone;
    private int zoneId;
    private String zoneNom;
    private String directionProvinciale;
    private String academie;
    private double latitude;
    private double longitude;
    private int nombreEnseignants;
    private int nombreEleves;
    
    public Etablissement() {}
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getNomArabe() { return nomArabe; }
    public void setNomArabe(String nomArabe) { this.nomArabe = nomArabe; }
    
    public String getCodeGresa() { return codeGresa; }
    public void setCodeGresa(String codeGresa) { this.codeGresa = codeGresa; }
    
    public String getTypeEtablissement() { return typeEtablissement; }
    public void setTypeEtablissement(String typeEtablissement) { this.typeEtablissement = typeEtablissement; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDirecteurNom() { return directeurNom; }
    public void setDirecteurNom(String directeurNom) { this.directeurNom = directeurNom; }
    
    public String getDirecteurTelephone() { return directeurTelephone; }
    public void setDirecteurTelephone(String directeurTelephone) { this.directeurTelephone = directeurTelephone; }
    
    public int getZoneId() { return zoneId; }
    public void setZoneId(int zoneId) { this.zoneId = zoneId; }
    
    public String getZoneNom() { return zoneNom; }
    public void setZoneNom(String zoneNom) { this.zoneNom = zoneNom; }
    
    public String getDirectionProvinciale() { return directionProvinciale; }
    public void setDirectionProvinciale(String directionProvinciale) { this.directionProvinciale = directionProvinciale; }
    
    public String getAcademie() { return academie; }
    public void setAcademie(String academie) { this.academie = academie; }
    
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    
    public int getNombreEnseignants() { return nombreEnseignants; }
    public void setNombreEnseignants(int nombreEnseignants) { this.nombreEnseignants = nombreEnseignants; }
    
    public int getNombreEleves() { return nombreEleves; }
    public void setNombreEleves(int nombreEleves) { this.nombreEleves = nombreEleves; }
}
