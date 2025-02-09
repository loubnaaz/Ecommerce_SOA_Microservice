package com.WebAppService.ecommerce.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
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
    
    // ✅ Register a new client via client microservice
    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody Client client) {
        String apiUrl = CLIENT_SERVICE_URL + "/register"; // client-service URL

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, client, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException.Conflict e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering client");
        }
    }
}