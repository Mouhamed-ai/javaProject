package app;

import java.util.List;

import dao.LivreDAO;
import model.Livre;

public class TestLivre {

    public static void main(String[] args) {
        LivreDAO dao = new LivreDAO();

        System.out.println("===== DÉBUT DU TEST LIVRE =====");

        // 1. TEST : AJOUTER UN LIVRE
        System.out.println("\n1. Test de l'ajout :");
        // On met l'ID à 0 car il sera généré par MySQL
        Livre nouveauLivre = new Livre(0, "L'Étranger", "Albert Camus", "978001", 10, 10);
        dao.ajouterLivre(nouveauLivre);
        
        // Si l'ID a bien été récupéré via getGeneratedKeys, il ne sera plus 0
        int idGenere = nouveauLivre.getIdLivre();
        System.out.println("Livre ajouté avec succès ! ID généré : " + idGenere);

        // 2. TEST : AFFICHER TOUS LES LIVRES
        System.out.println("\n2. Liste de tous les livres :");
        afficherListe(dao.getAllLivres());

        // 3. TEST : RECHERCHER UN LIVRE (par titre ou auteur)
        System.out.println("\n3. Test de la recherche (mot-clé 'Camus') :");
        afficherListe(dao.rechercherLivres("Camus"));

        // 4. TEST : MODIFIER UN LIVRE
        System.out.println("\n4. Test de la modification :");
        if (idGenere != 0) {
            nouveauLivre.setTitre("L'Étranger (Édition Spéciale)");
            nouveauLivre.setQuantiteTotale(15);
            dao.modifierLivre(nouveauLivre);
            System.out.println("Livre modifié !");
        }

        // 5. TEST : SUPPRIMER UN LIVRE
        System.out.println("\n5. Test de la suppression (ID : " + idGenere + ") :");
        if (idGenere != 0) {
            // Décommentez la ligne suivante si vous voulez vraiment tester la suppression
            dao.supprimerLivre(idGenere); 
             System.out.println("Livre supprimé !");
        }

        System.out.println("\n===== FIN DU TEST LIVRE =====");
    }

    // Méthode utilitaire pour afficher une liste de livres
    private static void afficherListe(List<Livre> livres) {
        if (livres.isEmpty()) {
            System.out.println("Aucun livre trouvé.");
        } else {
            for (Livre l : livres) {
                System.out.println("ID: " + l.getIdLivre() + 
                                   " | Titre: " + l.getTitre() + 
                                   " | Auteur: " + l.getAuteur() + 
                                   " | ISBN: " + l.getIsbn() + 
                                   " | Quantité: " + l.getQuantiteTotale());
            }
        }
    }
}