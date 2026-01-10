package dao;

import model.Emprunt;
import dao.DBConnection;
import dao.LivreDAO;
import dao.UtilisateurDAO;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO pour la table emprunts
 */
public class EmpruntDAO {

    private static final Logger LOGGER = Logger.getLogger(EmpruntDAO.class.getName());

    /**
     * Enregistrer un emprunt
     */
    public void enregistrerEmprunt(Emprunt e) {
        String sql = """
            INSERT INTO emprunts (id_utilisateur, id_livre, date_emprunt, date_retour_prevue)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, e.getId_utilisateur());
            ps.setInt(2, e.getId_livre());
            ps.setDate(3, Date.valueOf(e.getDate_emprunt()));
            ps.setDate(4, Date.valueOf(e.getDate_retour_prevue()));

            ps.executeUpdate();
            LOGGER.log(Level.INFO, "Emprunt enregistré pour utilisateur {0} et livre {1}",
                    new Object[]{e.getId_utilisateur(), e.getId_livre()});

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'enregistrement de l'emprunt", ex);
        }
    }

    /**
     * Enregistrer le retour d'un livre
     */
    public void enregistrerRetour(int id_emprunt, LocalDate dateRetour) {
        String sql = """
            UPDATE emprunts
            SET date_retour_effective = ?
            WHERE id_emprunt = ?
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(dateRetour));
            ps.setInt(2, id_emprunt);

            ps.executeUpdate();
            LOGGER.log(Level.INFO, "Retour enregistré pour l'emprunt {0}", id_emprunt);

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'enregistrement du retour", ex);
        }
    }

    /**
     * Récupérer les emprunts en cours
     */
    public List<Emprunt> getEmpruntsEnCours() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = """
            SELECT id_emprunt, id_utilisateur, id_livre, date_emprunt, date_retour_prevue, penalite
            FROM emprunts
            WHERE date_retour_effective IS NULL
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Emprunt e = new Emprunt();
                e.setId_emprunt(rs.getInt("id_emprunt"));
                e.setId_utilisateur(rs.getInt("id_utilisateur"));
                e.setId_livre(rs.getInt("id_livre"));
                
                Date dateEmpruntSql = rs.getDate("date_emprunt");
                if (dateEmpruntSql != null) e.setDate_emprunt(dateEmpruntSql.toLocalDate());

                Date dateRetourSql = rs.getDate("date_retour_prevue");
                if (dateRetourSql != null) e.setDate_retour_prevue(dateRetourSql.toLocalDate());

                e.setPenalite(rs.getDouble("penalite"));

                emprunts.add(e);
            }

            LOGGER.log(Level.INFO, "Nombre d'emprunts en cours récupérés : {0}", emprunts.size());

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des emprunts en cours", ex);
        }

        return emprunts;
    }

    /**
     * Historique des emprunts
     */
    public List<Emprunt> getHistoriqueEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = """
            SELECT id_emprunt, id_utilisateur, id_livre, date_emprunt, date_retour_prevue, date_retour_effective, penalite
            FROM emprunts
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Emprunt e = new Emprunt();
                e.setId_emprunt(rs.getInt("id_emprunt"));
                e.setId_utilisateur(rs.getInt("id_utilisateur"));
                e.setId_livre(rs.getInt("id_livre"));

                Date dateEmpruntSql = rs.getDate("date_emprunt");
                if (dateEmpruntSql != null) e.setDate_emprunt(dateEmpruntSql.toLocalDate());

                Date dateRetourPrevSql = rs.getDate("date_retour_prevue");
                if (dateRetourPrevSql != null) e.setDate_retour_prevue(dateRetourPrevSql.toLocalDate());

                Date dateRetourEffSql = rs.getDate("date_retour_effective");
                if (dateRetourEffSql != null) e.setDate_retour_effective(dateRetourEffSql.toLocalDate());

                e.setPenalite(rs.getDouble("penalite"));

                emprunts.add(e);
            }

            LOGGER.log(Level.INFO, "Historique complet des emprunts récupéré, total : {0}", emprunts.size());

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération de l'historique des emprunts", ex);
        }

        return emprunts;
    }
}