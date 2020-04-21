package partie3.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OutilsFichier {
	private OutilsFichier() {
		throw new IllegalStateException("Utility class");
	}

	public static ArrayList<String> lire(String fichier) {
		final ArrayList<String> contenu = new ArrayList<>();

		try {
			final File myObj = new File(fichier);

			final Scanner myReader = new Scanner(myObj, "UTF-8");
			while (myReader.hasNextLine()) {

				final String data = myReader.nextLine();
				contenu.add(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.err.println(new StringBuilder()
					.append("Erreur de lecture du fichier. Impossible de trouver le fichier nommé \"").append(fichier)
					.append("\"").toString());

		}
		return contenu;
	}

	public static void ecrire(String fichier, String contenu) {

		final BufferedWriter writer;

		try {
			writer = new BufferedWriter(new FileWriter(fichier));
			writer.write(contenu);
			writer.close();
		} catch (IOException e) {
			System.err.println(new StringBuilder().append("Erreur d'écriture du fichier (").append(fichier).append(")")
					.toString());
		}

	}

}
