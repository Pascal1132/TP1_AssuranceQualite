package partie3.main;

public class ErreurFichier {
	//Ligne de l'erreur rencontrée
	private int ligne = -1;
	// Code de l'erreur présent
	private TypeErreurs type;
	private String motErrone;
	
	public ErreurFichier(int ligne, TypeErreurs type,String motErrone){
		this.setType(type);
		this.setLigne(ligne);
		this.setMotErrone(motErrone);
	}
	public TypeErreurs getType() {
		return type;
	}
	public void setType(TypeErreurs type) {
		this.type = type;
	}
	public int getLigne() {
		return ligne;
	}
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}
	public String getMotErrone() {
		return motErrone;
	}
	public void setMotErrone(String motErrone) {
		this.motErrone = motErrone;
	}
}
