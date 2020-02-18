package partie2;

import java.util.ArrayList;

public class Main {

	private static ArrayList<String> contenu;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test lire fichier style.txt
		contenu = OutilsFichier.Lire("style.txt");
		
		String[][] test = separerPartiesContenu(contenu);
		
		
		//Tests
		for (String string : test[1]) {
			System.out.println(string);
		}
		
	}
	
	public static String[][] separerPartiesContenu(ArrayList<String> contenu){
		String[][] contenuSeprare = null;
		
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

}

