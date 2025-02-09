package com.WebAppService.ecommerce.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.WebAppService.ecommerce.Model.Client;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	private final String CLIENT_SERVICE_URL = "http://localhost:8081/clients";
	
    private final RestTemplate restTemplate = new RestTemplate();
    
	@GetMapping("/login")
    public String showLoginForm() {
		System.out.println("login show");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
    	System.out.println("username");
    	System.out.println(username);
    	System.out.println("password");
    	System.out.println(password);
    	String url = CLIENT_SERVICE_URL +  "/checkUser/{login}/{password}";
    	System.out.println(url);
    	
    	Client client = restTemplate.getForObject(url, Client.class, username, password);
    	System.out.println("login dans authController");
    	
        if (client != null) {
        	System.out.println("client authentifié");
        	System.out.println("redirection checkout");
            session.setAttribute("client", client);
            
            return "checkout"; // Redirection vers une page sécurisée après connexion
        }
        else {
        	System.out.println("client non trouvé ");
        	
        }
        return "register"; // Rediriger vers la page de login en cas d'échec
    }
}