package partie2;

import java.util.ArrayList;

public class Main {

	private static ArrayList<String> contenu;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test lire fichier style.txt
		contenu = OutilsFichier.Lire("style.txt");
		
		//Tests
		for (String string : contenu) {
			System.out.println(string);
		}
		
	}
	
	public static String[][] separerPartiesContenu(ArrayList<String> contenu){
		
		return null;
		
	}
	
	public static Client[] creerClient(String[] tabClientsString) {
		Client[] tabClients = new Client[tabClientsString.length];
		for (int i = 0; i < tabClients.length; i++) {
			tabClients[i] = new Client(tabClientsString[i]);
		}
		
		return tabClients;
		

	}

}

