package com.inspectapp.service;

import com.inspectapp.database.DatabaseManager;
import com.inspectapp.model.Teacher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private final DatabaseManager dbManager;
    private final Gson gson;
    
    public TeacherService() {
        this.dbManager = DatabaseManager.getInstance();
        this.gson = new Gson();
    }
    
    public int save(Teacher teacher) throws SQLException {
        String sql = """
            INSERT INTO teachers (nom, prenom, nom_arabe, prenom_arabe, date_naissance, lieu_naissance,
                cin, ppr, genre, telephone, email, adresse, grade, echelle, echelon,
                date_recrutement, date_grade_actuel, specialite, discipline, statut,
                est_stagiaire, est_promouvable, etablissement_id, etablissement_nom,
                direction_provinciale, zone_geographique, code_gresa, derniere_inspection,
                derniere_note, nombre_visites, historique_json)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, teacher.getNom());
            stmt.setString(2, teacher.getPrenom());
            stmt.setString(3, teacher.getNomArabe());
            stmt.setString(4, teacher.getPrenomArabe());
            stmt.setString(5, teacher.getDateNaissance() != null ? teacher.getDateNaissance().toString() : null);
            stmt.setString(6, teacher.getLieuNaissance());
            stmt.setString(7, teacher.getCin());
            stmt.setString(8, teacher.getPpr());
            stmt.setString(9, teacher.getGenre());
            stmt.setString(10, teacher.getTelephone());
            stmt.setString(11, teacher.getEmail());
            stmt.setString(12, teacher.getAdresse());
            stmt.setString(13, teacher.getGrade());
            stmt.setString(14, teacher.getEchelle());
            stmt.setString(15, teacher.getEchelon());
            stmt.setString(16, teacher.getDateRecrutement() != null ? teacher.getDateRecrutement().toString() : null);
            stmt.setString(17, teacher.getDateGradeActuel() != null ? teacher.getDateGradeActuel().toString() : null);
            stmt.setString(18, teacher.getSpecialite());
            stmt.setString(19, teacher.getDiscipline());
            stmt.setString(20, teacher.getStatut());
            stmt.setInt(21, teacher.isEstStagiaire() ? 1 : 0);
            stmt.setInt(22, teacher.isEstPromouvable() ? 1 : 0);
            stmt.setInt(23, teacher.getEtablissementId());
            stmt.setString(24, teacher.getEtablissementNom());
            stmt.setString(25, teacher.getDirectionProvinciale());
            stmt.setString(26, teacher.getZoneGeographique());
            stmt.setString(27, teacher.getCodeGresa());
            stmt.setString(28, teacher.getDerniereInspection() != null ? teacher.getDerniereInspection().toString() : null);
            stmt.setDouble(29, teacher.getDerniereNote());
            stmt.setInt(30, teacher.getNombreVisites());
            stmt.setString(31, gson.toJson(teacher.getHistoriqueInspections()));
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }
    
    public void update(Teacher teacher) throws SQLException {
        String sql = """
            UPDATE teachers SET nom=?, prenom=?, nom_arabe=?, prenom_arabe=?, date_naissance=?,
                lieu_naissance=?, cin=?, ppr=?, genre=?, telephone=?, email=?, adresse=?,
                grade=?, echelle=?, echelon=?, date_recrutement=?, date_grade_actuel=?,
                specialite=?, discipline=?, statut=?, est_stagiaire=?, est_promouvable=?,
                etablissement_id=?, etablissement_nom=?, direction_provinciale=?,
                zone_geographique=?, code_gresa=?, derniere_inspection=?, derniere_note=?,
                nombre_visites=?, historique_json=?
            WHERE id=?
        """;
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, teacher.getNom());
            stmt.setString(2, teacher.getPrenom());
            stmt.setString(3, teacher.getNomArabe());
            stmt.setString(4, teacher.getPrenomArabe());
            stmt.setString(5, teacher.getDateNaissance() != null ? teacher.getDateNaissance().toString() : null);
            stmt.setString(6, teacher.getLieuNaissance());
            stmt.setString(7, teacher.getCin());
            stmt.setString(8, teacher.getPpr());
            stmt.setString(9, teacher.getGenre());
            stmt.setString(10, teacher.getTelephone());
            stmt.setString(11, teacher.getEmail());
            stmt.setString(12, teacher.getAdresse());
            stmt.setString(13, teacher.getGrade());
            stmt.setString(14, teacher.getEchelle());
            stmt.setString(15, teacher.getEchelon());
            stmt.setString(16, teacher.getDateRecrutement() != null ? teacher.getDateRecrutement().toString() : null);
            stmt.setString(17, teacher.getDateGradeActuel() != null ? teacher.getDateGradeActuel().toString() : null);
            stmt.setString(18, teacher.getSpecialite());
            stmt.setString(19, teacher.getDiscipline());
            stmt.setString(20, teacher.getStatut());
            stmt.setInt(21, teacher.isEstStagiaire() ? 1 : 0);
            stmt.setInt(22, teacher.isEstPromouvable() ? 1 : 0);
            stmt.setInt(23, teacher.getEtablissementId());
            stmt.setString(24, teacher.getEtablissementNom());
            stmt.setString(25, teacher.getDirectionProvinciale());
            stmt.setString(26, teacher.getZoneGeographique());
            stmt.setString(27, teacher.getCodeGresa());
            stmt.setString(28, teacher.getDerniereInspection() != null ? teacher.getDerniereInspection().toString() : null);
            stmt.setDouble(29, teacher.getDerniereNote());
            stmt.setInt(30, teacher.getNombreVisites());
            stmt.setString(31, gson.toJson(teacher.getHistoriqueInspections()));
            stmt.setInt(32, teacher.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public List<Teacher> getAll() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers ORDER BY nom, prenom";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                teachers.add(mapResultSetToTeacher(rs));
            }
        }
        return teachers;
    }
    
    public Teacher getById(int id) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTeacher(rs);
                }
            }
        }
        return null;
    }
    
    public List<Teacher> search(String query) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = """
            SELECT * FROM teachers 
            WHERE nom LIKE ? OR prenom LIKE ? OR ppr LIKE ? OR cin LIKE ? OR discipline LIKE ?
            ORDER BY nom, prenom
        """;
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + query.toLowerCase() + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            stmt.setString(5, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    teachers.add(mapResultSetToTeacher(rs));
                }
            }
        }
        return teachers;
    }
    
    public List<Teacher> getByEtablissement(int etablissementId) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers WHERE etablissement_id = ? ORDER BY nom, prenom";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, etablissementId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    teachers.add(mapResultSetToTeacher(rs));
                }
            }
        }
        return teachers;
    }
    
    public List<Teacher> getStagiaires() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers WHERE est_stagiaire = 1 ORDER BY nom, prenom";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                teachers.add(mapResultSetToTeacher(rs));
            }
        }
        return teachers;
    }
    
    public List<Teacher> getPromouvables() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers WHERE est_promouvable = 1 ORDER BY nom, prenom";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                teachers.add(mapResultSetToTeacher(rs));
            }
        }
        return teachers;
    }
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM teachers WHERE id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM teachers";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    public int getStagiaireCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM teachers WHERE est_stagiaire = 1";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    private Teacher mapResultSetToTeacher(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getInt("id"));
        teacher.setNom(rs.getString("nom"));
        teacher.setPrenom(rs.getString("prenom"));
        teacher.setNomArabe(rs.getString("nom_arabe"));
        teacher.setPrenomArabe(rs.getString("prenom_arabe"));
        teacher.setDateNaissance(parseDate(rs.getString("date_naissance")));
        teacher.setLieuNaissance(rs.getString("lieu_naissance"));
        teacher.setCin(rs.getString("cin"));
        teacher.setPpr(rs.getString("ppr"));
        teacher.setGenre(rs.getString("genre"));
        teacher.setTelephone(rs.getString("telephone"));
        teacher.setEmail(rs.getString("email"));
        teacher.setAdresse(rs.getString("adresse"));
        teacher.setGrade(rs.getString("grade"));
        teacher.setEchelle(rs.getString("echelle"));
        teacher.setEchelon(rs.getString("echelon"));
        teacher.setDateRecrutement(parseDate(rs.getString("date_recrutement")));
        teacher.setDateGradeActuel(parseDate(rs.getString("date_grade_actuel")));
        teacher.setSpecialite(rs.getString("specialite"));
        teacher.setDiscipline(rs.getString("discipline"));
        teacher.setStatut(rs.getString("statut"));
        teacher.setEstStagiaire(rs.getInt("est_stagiaire") == 1);
        teacher.setEstPromouvable(rs.getInt("est_promouvable") == 1);
        teacher.setEtablissementId(rs.getInt("etablissement_id"));
        teacher.setEtablissementNom(rs.getString("etablissement_nom"));
        teacher.setDirectionProvinciale(rs.getString("direction_provinciale"));
        teacher.setZoneGeographique(rs.getString("zone_geographique"));
        teacher.setCodeGresa(rs.getString("code_gresa"));
        teacher.setDerniereInspection(parseDate(rs.getString("derniere_inspection")));
        teacher.setDerniereNote(rs.getDouble("derniere_note"));
        teacher.setNombreVisites(rs.getInt("nombre_visites"));
        
        String historiqueJson = rs.getString("historique_json");
        if (historiqueJson != null && !historiqueJson.isEmpty()) {
            teacher.setHistoriqueInspections(gson.fromJson(historiqueJson, new TypeToken<List>(){}.getType()));
        }
        
        return teacher;
    }
    
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
