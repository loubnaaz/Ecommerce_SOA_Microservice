package com.ecommerce.Client_Service.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Client_Service.logic.CartService;
import com.ecommerce.Client_Service.model.Cart;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam int clientId, @RequestParam int productId, @RequestParam int quantity) {
        cartService.addToCart(clientId, productId, quantity);
        return "Added to cart";
    }

    @GetMapping("/{clientId}")
    public List<Cart> getCart(@PathVariable int clientId) {
        return cartService.getCart(clientId);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public String removeItem(@PathVariable int cartItemId) {
        cartService.removeItem(cartItemId);
        return "Item removed";
    }

    @GetMapping("/total/{clientId}")
    public double getTotal(@PathVariable int clientId) {
        return cartService.calculateTotal(clientId);
    }
    
    @GetMapping("/checkout")
	public String checkout(HttpSession session) {
	    String token = (String) session.getAttribute("token");

	    if (token == null) {
	        System.out.println("❌ No token found in session - Redirecting to login");  // Debugging
	        return "redirect:/login";
	    }

	    System.out.println("✅ Token exists: " + token);
	    return "checkout";  // Render checkout page
	}
}