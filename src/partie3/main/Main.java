package partie3.main;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
	private static final double TAUX_TPS = 0.0500;

	private static final double TAUX_TVQ = 0.09975;

	private static final String FICHIER_ENTREE = "valeurs.txt";

	public static String fichierSortie = "Facture.txt";

	public static final String TITRE_FACTURE = "Bienvenue chez Barette !\nFactures:";

	private static ArrayList<String> contenu;
	public static ArrayList<Client> listeClients;
	public static ArrayList<Plat> listePlats;
	public static ArrayList<Commande> listeCommandes;
	public static int ligneDebutClients;
	public static int ligneDebutPlats;
	public static int ligneDebutCommandes;
	public static ArrayList<ErreurFichier> erreurs = new ArrayList<>();

	public static void main(String[] args) {
		// Test lire fichier style.txt
		contenu = OutilsFichier.lire(FICHIER_ENTREE);

		if (contenu.size() != 0) {
			// Séparer les parties du contenu du fichier
			final ArrayList<List<String>> contenuSepare = separerPartiesContenu(contenu);
			verificationErreur(contenuSepare);

		} else {
			System.out.println("Fichier vide");
		}

	}

	private static void verificationErreur(ArrayList<List<String>> contenuSepare) {
		// Récupérer la partie du fichier contenant l'erreur (Si il y en a une)
		// Créer les listes des objets
		creerObjets(contenuSepare);
		detecterErreursCommandes(contenuSepare);
		creationFacture(contenuSepare);

	}

	public static void detecterErreursCommandes(ArrayList<List<String>> contenuSepare) {
		for (int i = 0; i < listeCommandes.size(); i++) {
			// recherche dans tab listeClients
			detecterClientErreur(i);
			detecterPlatErreurs(i);
			detecterQuantiteErreurs(i);

		}

	}

	private static void detecterQuantiteErreurs(int i) {
		if (listeCommandes.get(i).getQuantite() < 0) {
			erreurs.add(new ErreurFichier(ligneDebutCommandes + i, TypeErreurs.QUANTITE_PLAT_NEGATIVE,
					String.valueOf(Math.round(listeCommandes.get(i).getQuantite()))));
		}
		if ((int) listeCommandes.get(i).getQuantite() != listeCommandes.get(i).getQuantite()) {
			erreurs.add(new ErreurFichier(ligneDebutCommandes + i, TypeErreurs.QUANTITE_PLAT_DECIMAL,
					String.valueOf(listeCommandes.get(i).getQuantite())));
		}
	}

	private static void detecterPlatErreurs(int i) {
		boolean estPlatPresent = false;
		for (Plat plat : listePlats) {
			if (plat.getNomPlat().equalsIgnoreCase(listeCommandes.get(i).getNomPlat())) {
				estPlatPresent = true;
			}
		}
		if (!estPlatPresent) {
			erreurs.add(new ErreurFichier(ligneDebutCommandes + i, TypeErreurs.PLAT_INEXISTANT,
					listeCommandes.get(i).getNomPlat()));
		}
	}

	private static void detecterClientErreur(int i) {

		boolean estClientPresent = false;
		for (Client client : listeClients) {
			if (client.getNom().equalsIgnoreCase(listeCommandes.get(i).getNomClient())) {
				estClientPresent = true;
			}

		}
		if (!estClientPresent) {
			erreurs.add(new ErreurFichier(ligneDebutCommandes + i, TypeErreurs.CLIENT_INEXISTANT,
					listeCommandes.get(i).nomClient));
		}
	}

	public static void creationFacture(ArrayList<List<String>> contenuSepare) {
		// Creer la facture
		final ArrayList<String> facture = calculerFacture();

		// Afficher la facture
		if (erreurs.size() == 0) {
			ecrireEtAfficherFactureFichier(facture);
		} else {
			gestionErreur();
		}
	}

	private static void ecrireEtAfficherFactureFichier(ArrayList<String> facture) {

		String factureEnChaine = TITRE_FACTURE;

		for (String ligne : facture) {
			factureEnChaine += "\n" + ligne;
		}

		OutilsFichier.ecrire(fichierSortie, factureEnChaine);
		System.out.println("------------------------\n" + factureEnChaine);
	}

	public static void creerObjets(ArrayList<List<String>> contenuSepare) {

		listeClients = creerTabClients(contenuSepare.get(0));
		listePlats = creerTabPlats(contenuSepare.get(1));
		listeCommandes = creerTabCommandes(contenuSepare.get(2));
	}

	public static ArrayList<String> calculerFacture() {
		final ArrayList<String> facture = new ArrayList<>();
		listeClients.forEach(client -> {
			final double totalClient = calculerTotal(client);
			// Si le client n'a pas un total de zéro
			if (totalClient != 0) {
				facture.add(new StringBuilder().append(client.getNom()).append(" ").append(formaterTotal(totalClient))
						.toString());
			}
		});

		return facture;

	}

	private static String formaterTotal(double total) {

		final NumberFormat nbFormat = NumberFormat.getNumberInstance(Locale.CANADA);
		nbFormat.setMinimumIntegerDigits(1);
		nbFormat.setMinimumFractionDigits(2);
		nbFormat.setMaximumFractionDigits(2);

		return nbFormat.format(total) + "$";

	}

	private static double calculerTotal(Client client) {
		double total = 0;
		for (Commande commande : listeCommandes) {
			if (commande.getNomClient().equals(client.getNom())) {

				final int indicePlat = recupererIndicePlat(commande);
				final Plat plat = listePlats.get(indicePlat);
				final double sousTotal = plat.getPrixPlat() * commande.getQuantite();

				total = calculerTaxes(sousTotal) + sousTotal;

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
		final ArrayList<List<String>> contenuSepare = new ArrayList<>();
		try {
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Clients :") + 1, contenu.lastIndexOf("Plats :")));
			ligneDebutClients = contenu.lastIndexOf("Clients :") + 1;
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Plats :") + 1, contenu.lastIndexOf("Commandes :")));
			ligneDebutPlats = contenu.lastIndexOf("Plats :") + 1;
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Commandes :") + 1, contenu.lastIndexOf("Fin")));
			ligneDebutCommandes = contenu.lastIndexOf("Commandes :") + 1;
		} catch (Exception e) {
			erreurs.add(new ErreurFichier(-1, TypeErreurs.FORMAT_INCORRECT, ""));
		}

		return contenuSepare;

	}

	public static ArrayList<Client> creerTabClients(List<String> list) {
		final ArrayList<Client> tabClients = new ArrayList<>();
		list.forEach(aList -> tabClients.add(new Client(aList)));

		return tabClients;

	}

	public static ArrayList<Plat> creerTabPlats(List<String> list) {
		final ArrayList<Plat> tabPlats = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			final String[] chaineSeparee = list.get(i).split(" ");

			try {
				final double prix = Double.parseDouble(chaineSeparee[1]);
				if (prix >= 0) {
					tabPlats.add(new Plat(chaineSeparee[0], prix));
				} else {
					erreurs.add(
							new ErreurFichier(ligneDebutPlats + i, TypeErreurs.PRIX_PLAT_NEGATIF, chaineSeparee[1]));
				}

			} catch (NumberFormatException e) {
				erreurs.add(new ErreurFichier(ligneDebutPlats + i, TypeErreurs.PRIX_NON_DOUBLE, chaineSeparee[1]));
			} catch (Exception e) {
				erreurs.add(new ErreurFichier(ligneDebutPlats + i, TypeErreurs.FORMAT_INCORRECT, ""));
			}

		}
		return tabPlats;

	}

	public static ArrayList<Commande> creerTabCommandes(List<String> list) {
		final ArrayList<Commande> tabCommandes = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			final String[] chaineSeparee = list.get(i).split(" ");
			try {
				tabCommandes
						.add(new Commande(chaineSeparee[0], chaineSeparee[1], Double.parseDouble(chaineSeparee[2])));
			} catch (IndexOutOfBoundsException e) {
				erreurs.add(new ErreurFichier(i, TypeErreurs.FORMAT_INCORRECT, ""));
			}

		}
		return tabCommandes;

	}

	public static double calculerTaxes(double montant) {
		double taxe = Math.round(montant * (TAUX_TPS) * 100) / 100.00;
		taxe += Math.round(montant * (TAUX_TVQ) * 100) / 100.00;

		return taxe;
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
	public static int compterPartiesChaine(String chaine) {

		final String chaineTrimee = chaine.trim();
		final String[] chaineSeparee = chaineTrimee.split(" ");
		return chaineSeparee.length;

	}

	public static String gestionErreur() {
		String erreursEnString = "";
		for (ErreurFichier erreur : erreurs) {
			switch (erreur.getType()) {
			case FORMAT_INCORRECT:
				erreursEnString += new StringBuilder()
						.append("Le format du fichier n'est pas valide. Erreur survenue à la ligne ")
						.append(erreur.getLigne() + 1).append("\n").toString();
				break;
			case CLIENT_INEXISTANT:
				erreursEnString += new StringBuilder().append("Le client '").append(erreur.getMotErrone())
						.append("' n'est pas valide à la ligne numéro ").append(erreur.getLigne() + 1).append("\n")
						.toString();
				break;
			case PLAT_INEXISTANT:
				erreursEnString += new StringBuilder().append("Le plat '").append(erreur.getMotErrone())
						.append("' n'est pas valide à la ligne numéro ").append(erreur.getLigne() + 1).append("\n")
						.toString();
				break;
			case QUANTITE_PLAT_NEGATIVE:
				erreursEnString += new StringBuilder().append("La quantité du plat '").append(erreur.getMotErrone())
						.append("' ne doit pas être négative à la ligne numéro ").append(erreur.getLigne() + 1)
						.append("\n").toString();
				break;
			case QUANTITE_PLAT_DECIMAL:
				erreursEnString += new StringBuilder().append("La quantité du plat '").append(erreur.getMotErrone())
						.append("' ne doit pas être décimale à la ligne numéro ").append(erreur.getLigne() + 1)
						.append("\n").toString();
				break;
			case PRIX_PLAT_NEGATIF:
				erreursEnString += new StringBuilder().append("Le prix du plat '").append(erreur.getMotErrone())
						.append("' ne doit pas être négative à la ligne numéro ").append(erreur.getLigne() + 1)
						.append("\n").toString();
				break;
			case PRIX_NON_DOUBLE:
				erreursEnString += new StringBuilder().append("Le prix du plat '").append(erreur.getMotErrone())
						.append("' doit être de type double à la ligne numéro ").append(erreur.getLigne() + 1)
						.append("\n").toString();
				break;
			}

		}
		System.out.println("Erreurs:\n" + erreursEnString);
		OutilsFichier.ecrire(fichierSortie, "Erreurs:\n" + erreursEnString);
		return "Erreurs:\n" + erreursEnString;
	}

}
