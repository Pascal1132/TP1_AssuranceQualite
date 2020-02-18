package partie2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OutilsFichier {
	public static ArrayList<String> Lire(String fichier) {
		ArrayList<String> contenu = new ArrayList<String>();
		System.out.println("Lecture du fichier");
		try {
		      File myObj = new File(fichier);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        contenu.add(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return contenu;
	}
	
}
