package partie3.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import partie3.main.ErreurFichier;
import partie3.main.Main;
import partie3.main.OutilsFichier;
import partie3.main.TypeErreurs;

public class MainTest {

	// Tests de détection d'erreurs de commandes dans le fichier

	@Test
	public void testNomClientNonExistant() {
		mainPourTests(new ArrayList<>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Poutine 1", "Fin")));

		assertEquals("Roger", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.CLIENT_INEXISTANT, Main.erreurs.get(0).getType());
	}

	@Test
	public void testPlatNonExistant() {
		mainPourTests(new ArrayList<>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5", "Commandes :",
				"Laurie HotDog 1", "Fin")));
		assertEquals("HotDog", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.PLAT_INEXISTANT, Main.erreurs.get(0).getType());
	}

	@Test
	public void testQuantitePlatNegative() {
		mainPourTests(new ArrayList<>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5", "Commandes :",
				"Laurie Poutine -5", "Fin")));

		assertEquals("-5", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.QUANTITE_PLAT_NEGATIVE, Main.erreurs.get(0).getType());
	}

	@Test
	public void testQuantitePlatDecimal() {

		mainPourTests(new ArrayList<>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5", "Commandes :",
				"Laurie Poutine 2.25", "Fin")));
		assertEquals("2.25", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.QUANTITE_PLAT_DECIMAL, Main.erreurs.get(0).getType());

	}

	@Test
	public void testPrixNegatif() {
		mainPourTests(new ArrayList<>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine -10", "Commandes :",
				"Laurie Poutine 2", "Fin")));
		assertEquals("-10", Main.erreurs.get(0).getMotErrone());
		assertEquals(3, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.PRIX_PLAT_NEGATIF, Main.erreurs.get(0).getType());
	}

	// Test de l'affichage des erreurs
	@Test
	public void testGestionErreur() {
		Main.erreurs = new ArrayList<>();
		Main.erreurs.add(new ErreurFichier(0, TypeErreurs.FORMAT_INCORRECT, ""));
		Main.erreurs.add(new ErreurFichier(1, TypeErreurs.CLIENT_INEXISTANT, "a"));
		Main.erreurs.add(new ErreurFichier(2, TypeErreurs.QUANTITE_PLAT_NEGATIVE, "b"));
		final String retour = Main.gestionErreur();
		assertEquals(
				new StringBuilder().append("Erreurs:\n")
						.append("Le format du fichier n'est pas valide. Erreur survenue à la ligne 1\n")
						.append("Le client 'a' n'est pas valide à la ligne numéro 2\n")
						.append("La quantité du plat 'b' ne doit pas être négative à la ligne numéro 3\n").toString(),
				retour);
	}

	// Test de la taxe
	@Test
	public void testCalculerTaxes() {

		final double montant = 15.5;
		assertEquals(2.33, Main.calculerTaxes(montant), 0.01);

	}

	// Test de la séparation des espace d'une chaine
	@Test
	public void testCompterEspace() {
		final int nbEspace = Main.compterPartiesChaine("l i l j h r           ");
		assertEquals(6, nbEspace);
	}

	// Test avec client qui n'a pas de commande
	@Test
	public void testClientTotalZero() {
		final ArrayList<List<String>> contenuSepare = mainPourTests(new ArrayList<>(
				Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 21.59", "Commandes :", "Fin")));
		Main.fichierSortie = "testClientTotalZero.txt";
		Main.creationFacture(contenuSepare);
		final ArrayList<String> retour = OutilsFichier.lire(Main.fichierSortie);

		assertArrayEquals(new Object[] { "Bienvenue chez Barette !", "Factures:" }, retour.toArray());
	}

	// Calculer la facture
	@Test
	public void testCalculerFacture() {
		mainPourTests(new ArrayList<>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 21.59", "Commandes :",
				"Laurie Poutine 5", "Fin")));
		final ArrayList<String> facture = Main.calculerFacture();
		assertEquals("[Laurie 124.12$]", facture.toString());

	}

	@Test
	public void testCreationFacture() throws IOException {
		final ArrayList<List<String>> contenuSepare = mainPourTests(new ArrayList<>(Arrays.asList("Clients :", "Laurie",
				"Plats :", "Poutine 21.59", "Commandes :", "Laurie Poutine 5", "Fin")));
		Main.fichierSortie = "testCreationFacture.txt";
		Main.creationFacture(contenuSepare);
		final ArrayList<String> retour = OutilsFichier.lire(Main.fichierSortie);
		assertArrayEquals(new Object[] { "Bienvenue chez Barette !", "Factures:", "Laurie 124.12$" }, retour.toArray());
	}

	public ArrayList<List<String>> mainPourTests(ArrayList<String> contenu) {
		Main.erreurs = new ArrayList<>();
		final ArrayList<List<String>> contenuSepare = Main.separerPartiesContenu(contenu);
		Main.creerObjets(contenuSepare);
		Main.detecterErreursCommandes(contenuSepare);
		return contenuSepare;
	}

}
