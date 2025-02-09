package com.ecommerce.Client_Service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="client")
public class Client {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientId;
	private String nom;
	private String prenom;
	private String description;
	private int telephone;
	private String ville;
	@Column
    private String email;
	@Column
	private String password;
	
	public Client() {}
	
	
	  public Client(String email, String password) {
	        this.email = email;
	        this.password = password;
	    }
	  
	public Client(String nom, String prenom, String description, int telephone, String ville, String password , String email) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.description = description;
		this.telephone = telephone;
		this.ville = ville;
		this.password = password;
		this.email = email;
	}



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


}
