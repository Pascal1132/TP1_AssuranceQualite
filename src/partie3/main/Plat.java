package partie3.main;

public class Plat {
	public String nomPlat = "";
	public double prixPlat = 0;
	
	
	public Plat(String nomPlat, double d) {
		this.nomPlat = nomPlat;
		this.prixPlat = d;
	}
	
	public Plat(String nomPlat) {
		this.nomPlat = nomPlat;
	}

	public String getNomPlat() {
		return this.nomPlat;
	}
	

	public double getPrixPlat() {
		return this.prixPlat;
	}
	

	
}
