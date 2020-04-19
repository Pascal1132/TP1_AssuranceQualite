package partie3.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import partie3.main.ErreurFichier;
import partie3.main.TypeErreurs;

public class Main {
	private static final double TAUX_TPS = 0.05;

	private static final double TAUX_TVQ = 0.0975;

	private static final String FICHIER_ENTREE = "valeurs.txt";

	public static String fichierSortie = "Facture.txt";

	public final static String TITRE_FACTURE = "Bienvenue chez Barette !\nFactures:";

	private static ArrayList<String> contenu;
	public static ArrayList<Client> listeClients;
	public static ArrayList<Plat> listePlats;
	public static ArrayList<Commande> listeCommandes;
	public static ArrayList<ErreurFichier> erreurs= new ArrayList<ErreurFichier>();
	

	private static int indiceInfo = 0;

	

	public static void main(String[] args) {
		// Test lire fichier style.txt
		AfficherInfos("Lecture du fichier " + FICHIER_ENTREE + ".");
		contenu = OutilsFichier.lire(FICHIER_ENTREE);

		if (contenu.size() != 0) {
			// Séparer les parties du contenu du fichier
			AfficherInfos("Séparation des parties.");
			ArrayList<List<String>> contenuSepare = separerPartiesContenu(contenu);
			verificationErreur(contenuSepare);

		}

	}

	private static void verificationErreur(ArrayList<List<String>> contenuSepare) {
		// Récupérer la partie du fichier contenant l'erreur (Si il y en a une)

		detecterErreursCommandes(contenuSepare);
		 
		if (erreurs.size()==0) {
			// Aucune erreur
			creationFacture(contenuSepare);
		} else {
			
			gestionErreur();
		}
	}

	private static void creationFacture(ArrayList<List<String>> contenuSepare) {
		// Créer les listes des objets
		AfficherInfos("Création des objets");
		creerObjets(contenuSepare);

		// Creer la facture
		AfficherInfos("Calcul de la facture");
		ArrayList<String> facture = calculerFacture();

		// Afficher la facture
		AfficherInfos("Affichage de la facture inscrite dans le fichier " + fichierSortie);
		ecrireEtAfficherFactureFichier(facture);
	}

	private static void gestionErreur() {
		//TODO
		//System.err.println("Le fichier ne respecte pas le format" + " demandé !");
		for (ErreurFichier erreur : erreurs) {
			System.out.println("Erreur survenue à la ligne "+erreur.getLigne()+" de type "+ erreur.getType()+ " ("+erreur.getMotErrone()+")");
		}
	}

	private static void AfficherInfos(String chaine) {
		System.out.println("[" + indiceInfo + "] " + chaine);
		indiceInfo++;
	}

	private static void ecrireEtAfficherFactureFichier(ArrayList<String> facture) {

		String factureEnChaine = TITRE_FACTURE;

		for (String ligne : facture) {
			factureEnChaine += "\n" + ligne;
		}

		OutilsFichier.ecrire(fichierSortie, factureEnChaine);
		// Ouvrir le fichier Facture.txt
		try {
			Desktop.getDesktop().open(new File(fichierSortie));
		} catch (IOException e) {
			
			System.err.println("Erreur dans l'ouverture du fichier " + fichierSortie);
		}
	}

	public static void creerObjets(ArrayList<List<String>> contenuSepare) {
		listeClients = creerTabClients(contenuSepare.get(0));
		listePlats = creerTabPlats(contenuSepare.get(1));
		listeCommandes = creerTabCommandes(contenuSepare.get(2));
	}

	public static ArrayList<String> calculerFacture() {
		ArrayList<String> facture = new ArrayList<String>();
		for (Client client : listeClients) {
			double totalClient = calculerTotal(client);
			facture.add(client.getNom() + " " + formaterTotal(totalClient));
		}

		return facture;

	}

	private static String formaterTotal(double total) {

		NumberFormat nbFormat = NumberFormat.getNumberInstance(Locale.CANADA);
		nbFormat.setMinimumIntegerDigits(1);
		nbFormat.setMinimumFractionDigits(2);
		nbFormat.setMaximumFractionDigits(2);

		return nbFormat.format(total) + "$";

	}

	private static double calculerTotal(Client client) {
		double total = 0;
		for (Commande commande : listeCommandes) {
			if (commande.getNomClient().equals(client.getNom())) {

				try {
					int indicePlat = recupererIndicePlat(commande);
					Plat plat = listePlats.get(indicePlat);
					total += plat.getPrixPlat() * commande.getQuantite();
				} catch (Exception e) {
					System.err.println("Le plat " + commande.getNomPlat() + " n'existe pas.");

				}

			}
		}
		return total;
	}

	private static int recupererIndicePlat(Commande commande) {
		int indicePlat = -1;
		for (int i = 0; i < listePlats.size(); i++) {
			if (listePlats.get(i).getNomPlat().equals(commande.getNomPlat())) {
				indicePlat = i;
				break;
			}

		}
		return indicePlat;
	}

	public static ArrayList<List<String>> separerPartiesContenu(ArrayList<String> contenu) {
		ArrayList<List<String>> contenuSepare = new ArrayList<List<String>>();
		try {
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Clients :") + 1, contenu.lastIndexOf("Plats :")));
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Plats :") + 1, contenu.lastIndexOf("Commandes :")));
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Commandes :") + 1, contenu.lastIndexOf("Fin")));
		} catch (Exception e) {
			erreurs.add(new ErreurFichier(-1, TypeErreurs.FORMAT_INCORRECT, ""));
		}

		return contenuSepare;

	}

	public static ArrayList<Client> creerTabClients(List<String> list) {
		ArrayList<Client> tabClients = new ArrayList<Client>();
		for (int i = 0; i < list.size(); i++) {
			tabClients.add(new Client(list.get(i)));
		}

		return tabClients;

	}

	public static ArrayList<Plat> creerTabPlats(List<String> list) {
		ArrayList<Plat> tabPlats = new ArrayList<Plat>();
		for (int i = 0; i < list.size(); i++) {
			String[] chaineSeparee = list.get(i).split(" ");

			try {
				tabPlats.add(new Plat(chaineSeparee[0], Double.parseDouble(chaineSeparee[1])));
			} catch (NumberFormatException e) {
				System.err.println("Impossible de transformer la chaine \"" + chaineSeparee[1] + "\" en double.");
			}

		}
		return tabPlats;

	}

	public static ArrayList<Commande> creerTabCommandes(List<String> list) {
		ArrayList<Commande> tabCommandes = new ArrayList<Commande>();
		for (int i = 0; i < list.size(); i++) {
			String[] chaineSeparee = list.get(i).split(" ");
			try {
				tabCommandes.add(new Commande(chaineSeparee[0], chaineSeparee[1], Integer.parseInt(chaineSeparee[2])));
			} catch (NumberFormatException e) {
				System.err.println("Impossible de transformer la chaine \"" + chaineSeparee[2] + "\" en entier.");
			}

		}
		return tabCommandes;

	}

	
	public static void detecterErreursCommandes(ArrayList<List<String>> contenuSepare) {
		//TODO
	}
	
	public static double calculerTaxes(double montant) {
		return montant*TAUX_TPS + montant*TAUX_TVQ;
	}
	/*
	 * Détermine si le format du fichier est valide.
	 * 
	 * @param contenu du fichier séparé en parties.
	 * 
	 * @return la ligne de l'erreur.
	 * 
	 * 
	 */
	public static int compterEspace(String chaine) {

		chaine = chaine.trim();
		String[] chaineSeparee = chaine.split(" ");
		return chaineSeparee.length;

	}

}
