package partie1;

public class CV {
    public String nom = "";
    public String prenom = "";
    public String formation = "";
    public int experience = 0;
    public String[] competences = {"", ""};
    public String attentes = "";
    
    public CV(String nom, String prenom, String formation, int experience, String[] competences, String attentes) {
        this.nom = nom;
        this.prenom = prenom;
        this.formation = formation;
        this.experience = experience;
        this.competences = competences;
        this.attentes = attentes;
    }
    
    public void affiche() {
        String competencesString = "";
        
        for (String competence : competences) {
			competencesString += competence + ";";
		}
		System.out.println("\nNom: " + nom + "\nPrenom: " + prenom + "\nFormation: " + formation
                + "\nExp�rience: " + experience + " ans\nComp�tences: "+ competencesString + "\nAttentes: " + attentes);
            
    }
    
    public static void main(String[] args) {
        System.out.println("Bienvenue chez Barette!");
        CV bergeronCV =new CV("C�drik","Bergeron","�boueur 101",4,new String[] {"Cr�ation de tables de chevets", "Peinture � l'acrylique"},"Cours passionnant");
        CV parentCV =new CV("Pascal","Parent","Machiniste",1,new String[] {"Design d'application", "Musique"},"Cours qui est moins plate que celui de fran�ais de la derniere session.");
        bergeronCV.affiche();
        
        parentCV.affiche();
        
    }

}
 