package partie2;

import java.util.ArrayList;

public class Main {

	private static ArrayList<String> contenu;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test lire fichier style.txt
		contenu = OutilsFichier.Lire("style.txt");
		
		//String[][] test = separerPartiesContenu(contenu);
		
		
		for (String string : contenu) {
			System.out.println(string);
		}
		
		
	}
	
	
	public static String[][] separerPartiesContenu(ArrayList<String> contenu){
		String[][] contenuSeprare = null;
		
		//contenu.subList(fromIndex, toIndex)
		
		for(int i = 0; i < contenu.size(); i++) {
			int pos = 0;
			if (i > contenu.indexOf("Clients :") && i < contenu.indexOf("Plats :")) {
				contenuSeprare[0][pos] = contenu.get(i);
			} else if (i > contenu.indexOf("Plats :") && i < contenu.indexOf("Commandes :")) {
				contenuSeprare[0][pos] = contenu.get(i);
			} else if (i > contenu.indexOf("Commandes :") && i < contenu.indexOf("Fin")) {
				contenuSeprare[0][pos] = contenu.get(i);
			}
			
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


}

