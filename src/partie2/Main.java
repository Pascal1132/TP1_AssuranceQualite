package partie2;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class Main {
	public final static String TITRE_FACTURE = "Bienvenue chez Barette !\nFactures:";

	private static ArrayList<String> contenu;
	public static ArrayList<Client> listeClients;
	public static ArrayList<Plat> listePlats;
	public static ArrayList<Commande> listeCommandes;

	public static void main(String[] args) {
		// Test lire fichier style.txt
		contenu = OutilsFichier.lire("valeurs.txt");
		
		// Séparer les parties du contenu du fichier
		ArrayList<List<String>> contenuSepare = separerPartiesContenu(contenu);
		
		// Récupérer la partie du fichier contenant l'erreur (Si il y en a une)
		int erreur = recupererPartieErreur(contenuSepare);
		
		if(erreur==-1) {
			// Aucune erreur
			
			// Créer les listes des objets
			creerObjets(contenuSepare);
			
			// Creer la facture
			ArrayList<String> facture = calculerFacture();
			
			//Afficher la facture
			ecrireEtAfficherFactureFichier(facture);
			
		}else {
			// Erreur dans le format du fichier
			System.out.println("Le fichier ne respecte pas le format" +
					" demandé !\nErreur arrivée dans la partie de la liste numéro : " + erreur );
		}
		
	}


	private static void ecrireEtAfficherFactureFichier(ArrayList<String> facture) {
		
		System.out.println(TITRE_FACTURE);
		for (String string : facture) {
			System.out.println(string);
		}
	}
	
	
	private static void creerObjets(ArrayList<List<String>> contenuSepare) {
		listeClients = creerTabClients( contenuSepare.get(0));
		listePlats = creerTabPlats(contenuSepare.get(1));
		listeCommandes = creerTabCommandes(contenuSepare.get(2));	
	}
	
	private static ArrayList<String> calculerFacture() {
		ArrayList<String> facture = new ArrayList<String>();
		for (Client client : listeClients) {
			double totalClient = calculerTotal(client);
			facture.add(client.getNom() + " " + formaterTotal(totalClient));
		}
		
		return facture;
		
	}

	private static String formaterTotal(double total) {
		
		NumberFormat nbFormat =NumberFormat.getNumberInstance(Locale.CANADA);
		nbFormat.setMinimumIntegerDigits(1);
		nbFormat.setMinimumFractionDigits(2);
		nbFormat.setMaximumFractionDigits(2);
		
		return nbFormat.format(total)+ "$";
		
	}

	private static double calculerTotal(Client client) {
		double total = 0;
		for (Commande commande : listeCommandes) {
			if(commande.getNomClient().equals(client.getNom())) {
				
				int indicePlat = recupererIndicePlat(commande);
				
				
				Plat plat = listePlats.get(indicePlat);
				total+= plat.getPrixPlat()*commande.getQuantite();
				
			}
		}
		return total;
	}


	private static int recupererIndicePlat(Commande commande) {
		int indicePlat = -1;
		for (int i = 0; i < listePlats.size(); i++) {
			if(listePlats.get(i).getNomPlat().equals(commande.getNomPlat())) {
				indicePlat = i;
				break;
			}
			
		}
		return indicePlat;
	}


	public static ArrayList<List<String>> separerPartiesContenu(ArrayList<String> contenu){
		ArrayList<List<String>> contenuSeprare = new ArrayList<List<String>>(); 
		try {
			contenuSeprare.add(contenu.subList(contenu.lastIndexOf("Clients :") + 1, contenu.lastIndexOf("Plats :")));
			contenuSeprare.add(contenu.subList(contenu.lastIndexOf("Plats :") + 1, contenu.lastIndexOf("Commandes :")));
			contenuSeprare.add(contenu.subList(contenu.lastIndexOf("Commandes :") + 1, contenu.lastIndexOf("Fin")));
		} catch (Exception e) {
			System.out.println("\nLe fichier ne respecte pas le format demandé !");
		}
		
	
		
		return contenuSeprare;
		
	}

	
	public static ArrayList<Client> creerTabClients(List<String> list) {
		ArrayList<Client> tabClients = new ArrayList<Client>();
		for (int i = 0; i < list.size(); i++) {
			tabClients.add( new Client(list.get(i)));
		}
		
		return tabClients;
		
	}
	
	public static ArrayList<Plat> creerTabPlats(List<String> list) {
		ArrayList<Plat> tabPlats = new ArrayList<Plat>();
		for (int i = 0; i < list.size(); i++) {
			String[] chaineSeparee = list.get(i).split(" ");
			tabPlats.add(new Plat(chaineSeparee[0], Double.parseDouble(chaineSeparee[1])));
			
		}
		return tabPlats;
		
		
	}
	
	public static ArrayList<Commande> creerTabCommandes(List<String> list) {
		ArrayList<Commande> tabCommandes = new ArrayList<Commande>();
		for (int i = 0; i < list.size(); i++) {
			String[] chaineSeparee = list.get(i).split(" ");
			tabCommandes.add( new Commande(chaineSeparee[0], chaineSeparee[1], Integer.parseInt(chaineSeparee[2])));
		}
		return tabCommandes;
		
		
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
	public static int recupererPartieErreur(ArrayList<List<String>> contenuSepare) {
		int ligneErreur=-1;
		for (int i = 0; i < contenuSepare.size(); i++) {
			for (int j = 0; j < contenuSepare.get(i).size(); j++) {
				int nbEspace = compterEspace(contenuSepare.get(i).get(j));
				
				if(nbEspace != i+1)ligneErreur=i+1;
				
				/*
				 * Pour test, effectuer ça:
				 * System.out.println(i+" -> " + "("+contenuSepare.get(i).get(j)+") -> " +nbEspace);
				 * 
				 * */
			}
			
		}
		
		return ligneErreur;
		
	}
	public static int compterEspace(String chaine) {
		
		chaine = chaine.trim();
		String[] chaineSeparee = chaine.split(" ");
		return chaineSeparee.length;
		
	}


}

