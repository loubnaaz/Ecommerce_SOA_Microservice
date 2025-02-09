package com.ecommerce.Client_Service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cart {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long cartId;

	    private int productId;
	    private int quantity;
	    private double price;
	    private int clientId;

		

		public Cart() {
			super();
		}

		
		public Cart(int productId, int quantity, double price, int client) {
			super();
			this.productId = productId;
			this.quantity = quantity;
			this.price = price;
			this.clientId = client;
		}


		public Long getCartId() {
			return cartId;
		}

		public void setCartId(Long cartId) {
			this.cartId = cartId;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}


		public int getClientId() {
			return clientId;
		}


		public void setClientId(int clientId) {
			this.clientId = clientId;
		}

		
	   
	}

