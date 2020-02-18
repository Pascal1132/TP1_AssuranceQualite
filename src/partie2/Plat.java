package partie2;

public class Plat {
	public String nomPlat = "";
	public int prixPlat = 0;
	
	public Plat(String nomPlat, int prixPlat) {
		this.nomPlat = nomPlat;
		this.prixPlat = prixPlat;
	}
	
	public String getNomPlat() {
		return this.nomPlat;
	}
	
	public void setNomPlat( String nom ) {
		this.nomPlat = nom;
	}
	
	public int getPrixPlat() {
		return this.prixPlat;
	}
	
	public void setPrixPlat( int prix ) {
		this.prixPlat = prix;
	}
}
