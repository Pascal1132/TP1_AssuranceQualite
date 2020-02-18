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
	
	

}

