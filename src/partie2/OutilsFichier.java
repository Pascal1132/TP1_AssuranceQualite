package partie2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OutilsFichier {
	public static ArrayList<String> lire(String fichier) {
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
		      System.err.println("Erreur de lecture du fichier. Impossible de trouver le fichier nommé \""+ fichier +"\"");
		      
		    }
		return contenu;
	}
	public static void ecrire(String fichier, String contenu) {
		
	    BufferedWriter writer;
		
	    try {
	    	writer = new BufferedWriter(new FileWriter(fichier));
			writer.write(contenu);
			writer.close();
		} catch (IOException e) {
			System.err.println("Erreur d'écriture du fichier ("+fichier+")");
		}
	     
	    
	}
	
}
