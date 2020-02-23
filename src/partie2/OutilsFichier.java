package partie2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OutilsFichier {
	public static ArrayList<String> Lire(String fichier) {
		ArrayList<String> contenu = new ArrayList<String>();
		
		try {
		      File myObj = new File(fichier);
		      
		      Scanner myReader = new Scanner(myObj, "UTF-8");
		      while (myReader.hasNextLine()) {
		    	  
		        String data = myReader.nextLine();
		        contenu.add(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("Erreur de lecture du fichier. Impossible de trouver le fichier nommé \""+ fichier +"\"");
		      
		    }
		return contenu;
	}
	
}
