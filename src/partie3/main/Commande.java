package partie3.main;

public class Commande {
	public String nomClient = "";
	public String nomPlat = "";
	public double quantite = 0;

	public Commande(String nomClient, String nomPlat, double d) {
		this.nomClient = nomClient;
		this.nomPlat = nomPlat;
		this.quantite = d;
	}

	public String getNomClient() {
		return this.nomClient;
	}

	public String getNomPlat() {
		return this.nomPlat;
	}

	public double getQuantite() {
		return this.quantite;
	}
}
