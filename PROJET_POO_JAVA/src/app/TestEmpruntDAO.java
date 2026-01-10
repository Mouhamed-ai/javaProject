package app;

import java.time.LocalDate;
import java.util.List;

import dao.EmpruntDAO;
import model.Emprunt;

public class TestEmpruntDAO {
    public static void main(String[] args) {

        EmpruntDAO dao = new EmpruntDAO();

        System.out.println("=== TEST 1 : AFFICHER LES EMPRUNTS EXISTANTS ===");
        List<Emprunt> enCours = dao.getEmpruntsEnCours();
        for (Emprunt e : enCours) {
            System.out.printf("ID emprunt: %d | Utilisateur: %d | Livre: %d | Retour prévu: %s%n",
                    e.getId_emprunt(),
                    e.getId_utilisateur(),
                    e.getId_livre(),
                    e.getDate_retour_prevue()
            );
        }

        System.out.println("\n=== TEST 2 : AJOUTER UN NOUVEL EMPRUNT ===");
        try {
            Emprunt nouvelEmprunt = new Emprunt();
            nouvelEmprunt.setId_utilisateur(1); // Aminata Sambe
            nouvelEmprunt.setId_livre(3);       // Physique Fondamentale
            nouvelEmprunt.setDate_emprunt(LocalDate.now());
            nouvelEmprunt.setDate_retour_prevue(LocalDate.now().plusDays(14));

            dao.enregistrerEmprunt(nouvelEmprunt);
            System.out.println("✅ Nouvel emprunt enregistré avec succès");
        } catch (Exception ex) {
            System.err.println("❌ Erreur lors de l'enregistrement du nouvel emprunt");
            ex.printStackTrace();
        }

        System.out.println("\n=== TEST 3 : ENREGISTRER UN RETOUR ===");
        try {
            if (!enCours.isEmpty()) {
                Emprunt premier = enCours.get(0); // on prend un emprunt existant
                dao.enregistrerRetour(premier.getId_emprunt(), LocalDate.now());
                System.out.println("✅ Retour enregistré pour l'emprunt ID: " + premier.getId_emprunt());
            } else {
                System.out.println("⚠️ Aucun emprunt en cours pour tester le retour");
            }
        } catch (Exception ex) {
            System.err.println("❌ Erreur lors de l'enregistrement du retour");
            ex.printStackTrace();
        }

        System.out.println("\n=== TEST 4 : AFFICHER L'HISTORIQUE COMPLET DES EMPRUNTS ===");
        List<Emprunt> historique = dao.getHistoriqueEmprunts();
        for (Emprunt e : historique) {
            System.out.printf(
                    "ID: %d | Utilisateur: %d | Livre: %d | Emprunt: %s | Retour prévu: %s | Retour effectif: %s | Pénalité: %.2f%n",
                    e.getId_emprunt(),
                    e.getId_utilisateur(),
                    e.getId_livre(),
                    e.getDate_emprunt(),
                    e.getDate_retour_prevue(),
                    e.getDate_retour_effective(),
                    e.getPenalite()
            );
        }

        System.out.println("\n✅ TEST EMPRUNTDAO TERMINÉ");
    }
}