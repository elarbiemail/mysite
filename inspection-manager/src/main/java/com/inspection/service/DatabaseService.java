package com.inspection.service;

import com.inspection.model.Enseignant;
import com.inspection.model.Tache;
import com.inspection.model.TaskType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion de la base de données SQLite
 */
public class DatabaseService {
    private static final String DB_URL = "jdbc:sqlite:inspection_manager.db";
    private Connection connection;

    public DatabaseService() {
        initDatabase();
    }
    
    /**
     * Récupère la connexion à la base de données
     */
    public Connection getConnection() {
        return connection;
    }

    private void initDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        String createEnseignantsTable = """
            CREATE TABLE IF NOT EXISTS enseignants (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT NOT NULL,
                prenom TEXT NOT NULL,
                email TEXT,
                telephone TEXT,
                matiere TEXT,
                etablissement TEXT,
                grade TEXT
            )
            """;

        String createTachesTable = """
            CREATE TABLE IF NOT EXISTS taches (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type TEXT NOT NULL,
                enseignant_id INTEGER,
                date TEXT,
                heure TEXT,
                lieu TEXT,
                objet TEXT,
                planifiee BOOLEAN DEFAULT 0,
                rapport_redige BOOLEAN DEFAULT 0,
                contenu_rapport TEXT,
                date_rapport TEXT,
                FOREIGN KEY (enseignant_id) REFERENCES enseignants(id)
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createEnseignantsTable);
            stmt.execute(createTachesTable);
        }
    }

    // CRUD Enseignants
    public int saveEnseignant(Enseignant enseignant) throws SQLException {
        String sql;
        if (enseignant.getId() == 0) {
            sql = "INSERT INTO enseignants (nom, prenom, email, telephone, matiere, etablissement, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE enseignants SET nom=?, prenom=?, email=?, telephone=?, matiere=?, etablissement=?, grade=? WHERE id=?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getPrenom());
            pstmt.setString(3, enseignant.getEmail());
            pstmt.setString(4, enseignant.getTelephone());
            pstmt.setString(5, enseignant.getMatiere());
            pstmt.setString(6, enseignant.getEtablissement());
            pstmt.setString(7, enseignant.getGrade());
            if (enseignant.getId() != 0) {
                pstmt.setInt(8, enseignant.getId());
            }
            pstmt.executeUpdate();

            if (enseignant.getId() == 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    enseignant.setId(rs.getInt(1));
                }
            }
            return enseignant.getId();
        }
    }

    public List<Enseignant> getAllEnseignants() throws SQLException {
        List<Enseignant> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM enseignants ORDER BY nom, prenom";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Enseignant e = new Enseignant();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setTelephone(rs.getString("telephone"));
                e.setMatiere(rs.getString("matiere"));
                e.setEtablissement(rs.getString("etablissement"));
                e.setGrade(rs.getString("grade"));
                enseignants.add(e);
            }
        }
        return enseignants;
    }

    public void deleteEnseignant(int id) throws SQLException {
        String sql = "DELETE FROM enseignants WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // CRUD Tâches
    public int saveTache(Tache tache) throws SQLException {
        String sql;
        if (tache.getId() == 0) {
            sql = "INSERT INTO taches (type, enseignant_id, date, heure, lieu, objet, planifiee, rapport_redige, contenu_rapport, date_rapport) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE taches SET type=?, enseignant_id=?, date=?, heure=?, lieu=?, objet=?, planifiee=?, rapport_redige=?, contenu_rapport=?, date_rapport=? WHERE id=?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, tache.getType().name());
            pstmt.setInt(2, tache.getEnseignant() != null ? tache.getEnseignant().getId() : 0);
            pstmt.setString(3, tache.getDate() != null ? tache.getDate().toString() : null);
            pstmt.setString(4, tache.getHeure() != null ? tache.getHeure().toString() : null);
            pstmt.setString(5, tache.getLieu());
            pstmt.setString(6, tache.getObjet());
            pstmt.setBoolean(7, tache.isPlanifiee());
            pstmt.setBoolean(8, tache.isRapportRedige());
            pstmt.setString(9, tache.getContenuRapport());
            pstmt.setString(10, tache.getDateRapport() != null ? tache.getDateRapport().toString() : null);
            if (tache.getId() != 0) {
                pstmt.setInt(11, tache.getId());
            }
            pstmt.executeUpdate();

            if (tache.getId() == 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    tache.setId(rs.getInt(1));
                }
            }
            return tache.getId();
        }
    }

    public List<Tache> getAllTaches() throws SQLException {
        List<Tache> taches = new ArrayList<>();
        String sql = "SELECT * FROM taches ORDER BY date DESC, heure DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tache t = new Tache();
                t.setId(rs.getInt("id"));
                t.setType(TaskType.valueOf(rs.getString("type")));
                int enseignantId = rs.getInt("enseignant_id");
                if (enseignantId > 0) {
                    t.setEnseignant(getEnseignantById(enseignantId));
                }
                String dateStr = rs.getString("date");
                t.setDate(dateStr != null ? LocalDate.parse(dateStr) : null);
                String heureStr = rs.getString("heure");
                t.setHeure(heureStr != null ? LocalTime.parse(heureStr) : null);
                t.setLieu(rs.getString("lieu"));
                t.setObjet(rs.getString("objet"));
                t.setPlanifiee(rs.getBoolean("planifiee"));
                t.setRapportRedige(rs.getBoolean("rapport_redige"));
                t.setContenuRapport(rs.getString("contenu_rapport"));
                String dateRapportStr = rs.getString("date_rapport");
                t.setDateRapport(dateRapportStr != null ? LocalDate.parse(dateRapportStr) : null);
                taches.add(t);
            }
        }
        return taches;
    }

    public List<Tache> getTachesByType(TaskType type) throws SQLException {
        List<Tache> taches = new ArrayList<>();
        String sql = "SELECT * FROM taches WHERE type=? ORDER BY date DESC, heure DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            pstmt.setString(1, type.name());
            while (rs.next()) {
                Tache t = new Tache();
                t.setId(rs.getInt("id"));
                t.setType(TaskType.valueOf(rs.getString("type")));
                int enseignantId = rs.getInt("enseignant_id");
                if (enseignantId > 0) {
                    t.setEnseignant(getEnseignantById(enseignantId));
                }
                String dateStr = rs.getString("date");
                t.setDate(dateStr != null ? LocalDate.parse(dateStr) : null);
                String heureStr = rs.getString("heure");
                t.setHeure(heureStr != null ? LocalTime.parse(heureStr) : null);
                t.setLieu(rs.getString("lieu"));
                t.setObjet(rs.getString("objet"));
                t.setPlanifiee(rs.getBoolean("planifiee"));
                t.setRapportRedige(rs.getBoolean("rapport_redige"));
                t.setContenuRapport(rs.getString("contenu_rapport"));
                String dateRapportStr = rs.getString("date_rapport");
                t.setDateRapport(dateRapportStr != null ? LocalDate.parse(dateRapportStr) : null);
                taches.add(t);
            }
        }
        return taches;
    }

    public void deleteTache(int id) throws SQLException {
        String sql = "DELETE FROM taches WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private Enseignant getEnseignantById(int id) throws SQLException {
        String sql = "SELECT * FROM enseignants WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Enseignant e = new Enseignant();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setTelephone(rs.getString("telephone"));
                e.setMatiere(rs.getString("matiere"));
                e.setEtablissement(rs.getString("etablissement"));
                e.setGrade(rs.getString("grade"));
                return e;
            }
        }
        return null;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
