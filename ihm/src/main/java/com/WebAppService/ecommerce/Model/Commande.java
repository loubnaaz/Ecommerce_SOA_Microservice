package com.WebAppService.ecommerce.Model;

public class Commande {

	private Produit produit;
    private int quantity;
    private double prixTotal;
    private int clientId;
    
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public double getPrixTotal() {
		return prixTotal;
	}
	public void setPrixTotal(double d) {
		this.prixTotal = d;
	}
	@Override
	public String toString() {
		return "Commande [produit=" + produit + ", quantity=" + quantity + ", prixTotal=" + prixTotal + ", clientId="
				+ clientId + "]";
	}
    
    
}
