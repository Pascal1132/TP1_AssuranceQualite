package partie2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OutilsFichier {
	public static void Lire(String fichier) {
		try {
		      File myObj = new File(fichier);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        System.out.println(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
}
