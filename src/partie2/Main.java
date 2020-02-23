package partie2;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static ArrayList<String> contenu;
	private ArrayList<Client> listeClients;
	private ArrayList<Plat> listePlats;
	private ArrayList<Commande> listeCommandes;

	public static void main(String[] args) {
		// Test lire fichier style.txt
		contenu = OutilsFichier.Lire("valeurs.txt");
		
		// Séparer les parties du contenu du fichier
		ArrayList<List<String>> contenuSepare = separerPartiesContenu(contenu);
		
		// Récupérer la partie du fichier contenant l'erreur (Si il y en a une)
		int erreur = recupererPartieErreur(contenuSepare);
		
		if(erreur==-1) {
			// Aucune erreur
			
			// Créer les listes des objets
			creerObjets(contenuSepare);
		}else {
			// Erreur dans le format du fichier
			System.out.println("Le fichier ne respecte pas le format" +
					" demandé !\nErreur arrivée dans la partie de la liste numéro : " + erreur );
		}
		
	}
	
	
	private static void creerObjets(ArrayList<List<String>> contenuSepare) {
		
		
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

	
	public static Client[] creerClient(String[] tabClientsString) {
		Client[] tabClients = new Client[tabClientsString.length];
		for (int i = 0; i < tabClients.length; i++) {
			tabClients[i] = new Client(tabClientsString[i]);
		}
		
		return tabClients;
		
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

