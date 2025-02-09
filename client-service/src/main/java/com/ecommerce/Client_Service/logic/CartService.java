package com.ecommerce.Client_Service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.Client_Service.dataaccess.CartRepository;
import com.ecommerce.Client_Service.model.Cart;
import com.ecommerce.Client_Service.model.Client;
import com.ecommerce.Client_Service.model.ProduitDTO;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

 
    private final RestTemplate restTemplate;
    
    @Autowired
	private CartRepository cartRepository;
    
    @Autowired
	private ClientService clientService;

    public CartService(CartRepository cartRepository, RestTemplate restTemplate) {
        this.cartRepository = cartRepository;
        this.restTemplate = restTemplate;
    }

    public void addToCart(int clientId, int produitId, int quantity) {
    	
        // Call Produit Microservice to get product details
    	
        String productServiceUrl = "http://localhost:8082/produits/" + produitId;
        
        ProduitDTO product = restTemplate.getForObject(productServiceUrl, ProduitDTO.class);
        

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // Check if the product already exists in the cart
        List<Cart> cartItems = cartRepository.findByClientId(clientId);
        Optional<Cart> existingItem = cartItems.stream()
                .filter(item -> item.getProductId()==produitId)
                .findFirst();
        
        if (existingItem.isPresent()) {
            Cart item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartRepository.save(item);
        } else {
            Cart newItem = new Cart();
            newItem.setProductId(produitId);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrix());
            newItem.setClientId(clientId);

            cartRepository.save(newItem);
        }
    }

    public List<Cart> getCart(int clientId) {
        return cartRepository.findByClientId(clientId);
    }

    public void removeItem(int cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public double calculateTotal(int clientId) {
        return getCart(clientId).stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
