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
	private static final double TAUX_TPS = 0.0500;

	private static final double TAUX_TVQ = 0.09975;

	private static final String FICHIER_ENTREE = "valeurs.txt";

	public static String fichierSortie = "Facture.txt";

	public final static String TITRE_FACTURE = "Bienvenue chez Barette !\nFactures:";

	private static ArrayList<String> contenu;
	public static ArrayList<Client> listeClients;
	public static ArrayList<Plat> listePlats;
	public static ArrayList<Commande> listeCommandes;
	public static int ligneDebutClients;
	public static int ligneDebutPlats;
	public static int ligneDebutCommandes;
	public static ArrayList<ErreurFichier> erreurs= new ArrayList<ErreurFichier>();
	

	

	public static void main(String[] args) {
		// Test lire fichier style.txt
		contenu = OutilsFichier.lire(FICHIER_ENTREE);

		if (contenu.size() != 0) {
			// Séparer les parties du contenu du fichier
			ArrayList<List<String>> contenuSepare = separerPartiesContenu(contenu);
			verificationErreur(contenuSepare);

		}else {
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
			//recherche dans tab listeClients
			boolean estClientPresent=false;
			for (Client client : listeClients) {
				if(client.getNom().equalsIgnoreCase(listeCommandes.get(i).getNomClient()))estClientPresent=true;
				
			}
			boolean estPlatPresent=false;
			for (Plat plat : listePlats) {
				if(plat.getNomPlat().equalsIgnoreCase(listeCommandes.get(i).getNomPlat()))estPlatPresent=true;
			}
			
			if(!estClientPresent) {
				erreurs.add(new ErreurFichier(ligneDebutCommandes+i, TypeErreurs.CLIENT_INEXISTANT, listeCommandes.get(i).nomClient));
			}
			if(!estPlatPresent) {
				erreurs.add(new ErreurFichier(ligneDebutCommandes+i, TypeErreurs.PLAT_INEXISTANT, listeCommandes.get(i).getNomPlat()));
			}
			if(listeCommandes.get(i).getQuantite()<0) {
				erreurs.add(new ErreurFichier(ligneDebutCommandes+i, TypeErreurs.QUANTITE_PLAT_NEGATIVE, String.valueOf(Math.round(listeCommandes.get(i).getQuantite()))));
			}
			if((int)listeCommandes.get(i).getQuantite()!=listeCommandes.get(i).getQuantite()) {
				erreurs.add(new ErreurFichier(ligneDebutCommandes+i, TypeErreurs.QUANTITE_PLAT_DECIMAL, String.valueOf(listeCommandes.get(i).getQuantite())));
			}
			
		}
		
		
	}
	public static void creationFacture(ArrayList<List<String>> contenuSepare) {
		// Creer la facture
		ArrayList<String> facture = calculerFacture();

		// Afficher la facture
		if(erreurs.size()==0) {
			ecrireEtAfficherFactureFichier(facture);
		}
		
		else gestionErreur();
	}



	private static void ecrireEtAfficherFactureFichier(ArrayList<String> facture) {

		String factureEnChaine = TITRE_FACTURE;

		for (String ligne : facture) {
			factureEnChaine += "\n" + ligne;
		}

		OutilsFichier.ecrire(fichierSortie, factureEnChaine);
		System.out.println("------------------------\n"+factureEnChaine);
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
			// Si le client n'a pas un total de zéro
			if(totalClient !=0)
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
					double sousTotal = plat.getPrixPlat() * commande.getQuantite();
					
					total = calculerTaxes(sousTotal)+sousTotal;
				} catch (Exception e) {
					

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
			ligneDebutClients= contenu.lastIndexOf("Clients :")+1;
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Plats :") + 1, contenu.lastIndexOf("Commandes :")));
			ligneDebutPlats=contenu.lastIndexOf("Plats :")+1;
			contenuSepare.add(contenu.subList(contenu.lastIndexOf("Commandes :") + 1, contenu.lastIndexOf("Fin")));
			ligneDebutCommandes=contenu.lastIndexOf("Commandes :")+1;
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
				double prix=Double.parseDouble(chaineSeparee[1]);
				if(prix>=0) {
					tabPlats.add(new Plat(chaineSeparee[0],prix));
				}else {
					erreurs.add(new ErreurFichier(ligneDebutPlats+i, TypeErreurs.PRIX_PLAT_NEGATIF, chaineSeparee[1]));
				}
				
			} catch (NumberFormatException e) {
				erreurs.add(new ErreurFichier(ligneDebutPlats+i, TypeErreurs.PRIX_NON_DOUBLE, chaineSeparee[1]));
			}catch(Exception e) {
				erreurs.add(new ErreurFichier(ligneDebutPlats+i, TypeErreurs.FORMAT_INCORRECT, ""));
			}

		}
		return tabPlats;

	}

	public static ArrayList<Commande> creerTabCommandes(List<String> list) {
		ArrayList<Commande> tabCommandes = new ArrayList<Commande>();
		for (int i = 0; i < list.size(); i++) {
			String[] chaineSeparee = list.get(i).split(" ");
			try {
				tabCommandes.add(new Commande(chaineSeparee[0], chaineSeparee[1], Double.parseDouble(chaineSeparee[2])));
			}catch(IndexOutOfBoundsException e) {
				erreurs.add(new ErreurFichier(i, TypeErreurs.FORMAT_INCORRECT, ""));
			}

		}
		return tabCommandes;

	}
	
	public static double calculerTaxes(double montant) {
		double taxe = Math.round(montant*(TAUX_TPS)*100)/100.00;
		taxe += Math.round(montant*(TAUX_TVQ)*100)/100.00;
		
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

		chaine = chaine.trim();
		String[] chaineSeparee = chaine.split(" ");
		return chaineSeparee.length;

	}
	
	public static String gestionErreur() {
		String erreursEnString = "";
		for (ErreurFichier erreur : erreurs) {
			switch (erreur.getType()) {
			case FORMAT_INCORRECT:
				erreursEnString+="Le format du fichier n'est pas valide. Erreur survenue à la ligne "+(erreur.getLigne()+1)+"\n";
				break;
			case CLIENT_INEXISTANT:
				erreursEnString+="Le client '"+erreur.getMotErrone()+"' n'est pas valide à la ligne numéro "+(erreur.getLigne()+1)+"\n";
				break;
			case PLAT_INEXISTANT:
				erreursEnString+="Le plat '"+erreur.getMotErrone()+"' n'est pas valide à la ligne numéro "+(erreur.getLigne()+1)+"\n";
				break;
			case QUANTITE_PLAT_NEGATIVE:
				erreursEnString+="La quantité du plat '"+erreur.getMotErrone()+"' ne doit pas être négative à la ligne numéro "+(erreur.getLigne()+1)+"\n";
				break;
			case QUANTITE_PLAT_DECIMAL:
				erreursEnString+="La quantité du plat '"+erreur.getMotErrone()+"' ne doit pas être décimale à la ligne numéro "+(erreur.getLigne()+1)+"\n";
				break;
			case PRIX_PLAT_NEGATIF:
				erreursEnString+="Le prix du plat '"+erreur.getMotErrone()+"' ne doit pas être négative à la ligne numéro "+(erreur.getLigne()+1)+"\n";
				break;
			case PRIX_NON_DOUBLE:
				erreursEnString+="Le prix du plat '"+erreur.getMotErrone()+"' doit être de type double à la ligne numéro "+(erreur.getLigne()+1)+"\n";
				break;
			}
			
		}
		System.out.println("Erreurs:\n"+erreursEnString);
		OutilsFichier.ecrire(fichierSortie, "Erreurs:\n"+erreursEnString);
		return "Erreurs:\n"+erreursEnString;
	}

}
