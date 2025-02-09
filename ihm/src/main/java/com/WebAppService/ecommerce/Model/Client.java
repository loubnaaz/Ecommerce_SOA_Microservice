package com.WebAppService.ecommerce.Model;


public class Client {

	private int clientId;
	private String nom;
	private String prenom;
	private String description;
	private int telephone;
	private String ville;
    private String email;
	private String password;
	
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTelephone() {
		return telephone;
	}
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", nom=" + nom + ", prenom=" + prenom + ", description=" + description
				+ ", telephone=" + telephone + ", ville=" + ville + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
