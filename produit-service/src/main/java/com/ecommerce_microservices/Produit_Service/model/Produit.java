package com.ecommerce_microservices.Produit_Service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="produit")
public class Produit {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int produitId;
		
		@Enumerated(EnumType.STRING) 
		private ProduitType type;
		
		private String titre;
		private int reference;
		private String description;
		private double prix;
		private String imagepath;
		private int quantity;
		
		
		public Produit() {
	    }
	   
	    
		public Produit(ProduitType type, int reference, String description, int prix,String imagepath ,String titre, int quantity) {
			super();
			this.type=type;
			this.reference = reference;
			this.description = description;
			this.prix = prix;
			this.imagepath =imagepath;
			this.titre =titre;
			this.quantity =quantity;
		}


		public Produit(int produitId, ProduitType type, int reference, String description, int prix,String imagepath,String titre, int quantity) {
			super();
			this.produitId = produitId;
			this.type = type;
			this.reference = reference;
			this.description = description;
			this.prix = prix;
			this.imagepath =imagepath;
			this.titre =titre;
			this.quantity =quantity;
		}
		
		
		public int getProduitId() {
			return produitId;
		}
		public void setProduitId(int produitId) {
			this.produitId = produitId;
		}
		 public ProduitType getType() {
		        return type;
		    }

		    public void setType(ProduitType type) {
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
		public String getImagepath() {
			return imagepath;
		}
		public void setImagepath(String imagepath) {
			this.imagepath = imagepath;
		}


		public String getTitre() {
			return titre;
		}


		public void setTitre(String titre) {
			this.titre = titre;
		}
		  public int getQuantity() { 
		    	return quantity; 
		    	}
		    
		    public void setQuantity(int quantity) {
		    	this.quantity = quantity; 
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


