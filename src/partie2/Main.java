package partie2;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static ArrayList<String> contenu;

	public static void main(String[] args) {
		//Test lire fichier style.txt
		contenu = OutilsFichier.Lire("valeurs.txt");
		
		List<List<String>> contenuSepare = separerPartiesContenu(contenu);
		
	}
	
	
	public static List<List<String>> separerPartiesContenu(ArrayList<String> contenu){
		List<List<String>> contenuSeprare = new ArrayList<List<String>>(); 
		
		contenuSeprare.add(contenu.subList(contenu.lastIndexOf("Clients :") + 1, contenu.lastIndexOf("Plats :")));
		contenuSeprare.add(contenu.subList(contenu.lastIndexOf("Plats :") + 1, contenu.lastIndexOf("Commandes :")));
		contenuSeprare.add(contenu.subList(contenu.lastIndexOf("Commandes :") + 1, contenu.lastIndexOf("Fin")));
		
		return contenuSeprare;
		
	}

	
	public static Client[] creerClient(String[] tabClientsString) {
		Client[] tabClients = new Client[tabClientsString.length];
		for (int i = 0; i < tabClients.length; i++) {
			tabClients[i] = new Client(tabClientsString[i]);
		}
		
		return tabClients;
		
	}


}

