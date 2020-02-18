package partie2;

public class Commande {
	public String nomClient = "";
	public String nomPlat = "";
	public int prixPlat = 0;
	
	public Commande(String nomClient, String nomPlat, int prixPlat) {
		this.nomClient = nomClient;
		this.nomPlat = nomPlat;
		this.prixPlat = prixPlat;
	}
	public String getNomClient() {
		return this.nomClient;
	}
	
	public void setNomClient( String nom ) {
		this.nomClient = nom;
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
