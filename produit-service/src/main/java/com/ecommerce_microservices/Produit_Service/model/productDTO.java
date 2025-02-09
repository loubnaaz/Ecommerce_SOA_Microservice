package com.ecommerce_microservices.Produit_Service.model;

import org.springframework.web.multipart.MultipartFile;

public class productDTO {
	
	private String type;
	private int reference;
	private String description;
	private double prix;
	private String titre;
	private int quantity;
	
	private MultipartFile imageFile;

	public String getType() {
		return type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}

	
	
	
}

