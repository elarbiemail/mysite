package com.inspection.model;

/**
 * Classe représentant un enseignant
 */
public class Enseignant {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matiere;
    private String etablissement;
    private String grade;

    public Enseignant() {
    }

    public Enseignant(String nom, String prenom, String email, String telephone, 
                     String matiere, String etablissement, String grade) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.matiere = matiere;
        this.etablissement = etablissement;
        this.grade = grade;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    public String getEtablissement() { return etablissement; }
    public void setEtablissement(String etablissement) { this.etablissement = etablissement; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }
}
