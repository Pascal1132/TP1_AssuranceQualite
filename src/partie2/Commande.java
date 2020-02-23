package partie2;

public class Commande {
	public String nomClient = "";
	public String nomPlat = "";
	public int quantite = 0;
	
	public Commande(String nomClient, String nomPlat, int quantite) {
		this.nomClient = nomClient;
		this.nomPlat = nomPlat;
		this.quantite = quantite;
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
	
	public int getQuantite() {
		return this.quantite;
	}
	
	public void setQuantite( int quantite ) {
		this.quantite = quantite;
	}
}
