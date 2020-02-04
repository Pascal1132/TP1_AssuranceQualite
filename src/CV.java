
public class CV {
    public String Nom = "";
    public String Prenom = "";
    public String Formation = "";
    public int Experience = 0;
    public String[] Competences = {"", ""};
    public String Attentes = "";
    
    public CV(String nom, String prenom, String formation, int experience, String[] competences, String attentes) {
        Nom = nom;
        Prenom = prenom;
        Formation = formation;
        Experience = experience;
        Competences = competences;
        Attentes = attentes;
    }
    
    public void affiche() {
        System.out.println("Nom: " + Nom + "\nPrenom: " + Prenom + "\nFormation: " + Formation
                + "\nExp�rience: " + Experience + " ans\nComp�tences: " + Competences + "\nAttentes: " + Attentes);
            
    }
    
    public static void main(String[] args) {
        System.out.println("Bienvenue chez Barette!");
        CV bergeronCV =new CV("C�drik","Bergeron","�boueur 101",4,new String[] {"Cr�ation de tables de chevets", "Peinture � l'acrylique"},"Cours passionnant");
        CV parentCV =new CV("Pascal","Parent","Machiniste",1,new String[] {"Design d'application", "Musique"},"Cours qui est moins plate que celui de fran�ais de la derniere session.");
        bergeronCV.affiche();
        parentCV.affiche();
        
    }

}
 