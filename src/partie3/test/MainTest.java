package partie3.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import partie3.main.Main;
import partie3.main.ErreurFichier;
import partie3.main.TypeErreurs;

public class MainTest {
	
	// Tests de détection d'erreurs de commandes dans le fichier

	@Test
	public void testNomClientNonExistant() {
		
		ArrayList<String> contenu = new ArrayList<String>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5","Commandes :","Roger Poutine 1", "Fin"));
		ArrayList<List<String>> contenuSepare = Main.separerPartiesContenu(contenu);
		Main.detecterErreursCommandes(contenuSepare);
		
		assertEquals("Roger", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.CLIENT_INEXISTANT, Main.erreurs.get(0).getType());
	}
	
	@Test
	public void testPlatNonExistant() {
		
		ArrayList<String> contenu = new ArrayList<String>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5","Commandes :","Roger HotDog 1", "Fin"));
		ArrayList<List<String>> contenuSepare = Main.separerPartiesContenu(contenu);
		Main.detecterErreursCommandes(contenuSepare);
		
		
		assertEquals("HotDog", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.PLAT_INEXISTANT, Main.erreurs.get(0).getType());
	}
	@Test
	public void testQuantitePlatNegative() {
		
		ArrayList<String> contenu = new ArrayList<String>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5","Commandes :","Roger Poutine -5", "Fin"));
		ArrayList<List<String>> contenuSepare = Main.separerPartiesContenu(contenu);
		Main.detecterErreursCommandes(contenuSepare);
	
		assertEquals("-5", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.QUANTITE_PLAT_NEGATIVE, Main.erreurs.get(0).getType());
	}
	@Test
	public void testQuantitePlatDecimal() {
		
		ArrayList<String> contenu = new ArrayList<String>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 10.5","Commandes :","Roger Poutine 2.25", "Fin"));
		ArrayList<List<String>> contenuSepare = Main.separerPartiesContenu(contenu);
		Main.detecterErreursCommandes(contenuSepare);
		assertEquals("10.5", Main.erreurs.get(0).getMotErrone());
		assertEquals(5, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.QUANTITE_PLAT_DECIMAL, Main.erreurs.get(0).getType());
		
	}
	@Test
	public void testPrixNegatif() {
		
		ArrayList<String> contenu = new ArrayList<String>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine -10","Commandes :","Roger Poutine 2", "Fin"));
		ArrayList<List<String>> contenuSepare = Main.separerPartiesContenu(contenu);
		Main.detecterErreursCommandes(contenuSepare);
		assertEquals("-10", Main.erreurs.get(0).getMotErrone());
		assertEquals(3, Main.erreurs.get(0).getLigne());
		assertEquals(TypeErreurs.PRIX_PLAT_NEGATIF, Main.erreurs.get(0).getType());
	}
	
	// Test de la taxe
	@Test
	public void testCalculerTaxes() {
		double montant =15.59;
		assertEquals(17,92, Main.calculerTaxes(montant));
		
	}
	
	// Tests de la sortie
	
	// Calculer la facture
	@Test
	public void testCalculerFacture() {
		ArrayList<String> contenu = new ArrayList<String>(Arrays.asList("Clients :", "Laurie", "Plats :", "Poutine 21.59","Commandes :","Laurie Poutine 5", "Fin"));
		ArrayList<List<String>> contenuSepare = Main.separerPartiesContenu(contenu);
		Main.creerObjets(contenuSepare);
		ArrayList<String> facture = Main.calculerFacture();
		System.out.println(facture.toString());
		assertEquals("[Laurie 124,11]", facture.toString());
		
	}
	
	//Test ecrirEtAfficherFacture()
	@Test
	public void testEcrireAfficherFacture() {
		
	}
	
	
	

}
