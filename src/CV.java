
public class CV {
    public String Nom = "";
    public String Prenom = "";
    public String Formation = "";
    public int Experience = 0;
    public String[] Competences = {"", ""};
    public String Attentes = "";
    
    public CV() {
        
    }
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
                + "\nExpérience: " + Experience + " ans\nCompétences: " + Competences + "\nAttentes: " + Attentes);
            
    }
    
    public static void main(String[] args) {
        System.out.println("Bienvenue chez Barette!");
        
        
    }

}
 