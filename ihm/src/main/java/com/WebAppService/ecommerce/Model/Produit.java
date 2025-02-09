package com.WebAppService.ecommerce.Model;

public class Produit {
    private int produitId;
    private String titre;
    private String type;
    private Double prix;
    private int reference;
	private String description;
	private String imagepath;
    private int quantity;  // Ensure quantity exists

    // Getters and Setters
    public int getProduitId() { 
    	return produitId; 
    	}
    
    public void setProduitId(
    		int produitId) { 
    	this.produitId = produitId; 
    	}

    
    public String getTitre() { 
    	return titre; 
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

	public String getImagepath() {
		return imagepath;
	}

	public void setImagpath(String imagepath) {
		this.imagepath = imagepath;
	}

	public void setTitre(String titre) { 
    	this.titre = titre; 
    	}

    public Double getPrix() { 
    	return prix; 
    	}
    
    public void setPrix(Double prix) {
    	this.prix = prix; 
    	}

    public int getQuantity() { 
    	return quantity; 
    	}
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity; 
    	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
	@Override
    public String toString() {
        return "Produit{" +
                "produitId=" + produitId +
                ", titre='" + titre + '\'' +
                ", type='" + type + '\'' +
                ", prix=" + prix +
                ", reference=" + reference +
                ", description='" + description + '\'' +
                ", imagePath='" + imagepath + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
