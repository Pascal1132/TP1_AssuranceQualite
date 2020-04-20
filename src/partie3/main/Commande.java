package partie3.main;

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

	public String getNomPlat() {
		return this.nomPlat;
	}

	public int getQuantite() {
		return this.quantite;
	}
}
