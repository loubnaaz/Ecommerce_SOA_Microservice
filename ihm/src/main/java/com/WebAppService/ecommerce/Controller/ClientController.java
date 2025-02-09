package com.WebAppService.ecommerce.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.WebAppService.ecommerce.Model.LoginRequest;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final String CLIENT_SERVICE_URL = "http://localhost:8081/clients"; 
    private final RestTemplate restTemplate = new RestTemplate();

    /*@PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
    	System.out.println("************** login ****************");
        String url = CLIENT_SERVICE_URL + "/authenticate";
        LoginRequest loginRequest = new LoginRequest(email, password);

        ResponseEntity<String> response = restTemplate.postForEntity(url, loginRequest, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String token = response.getBody();
            System.out.println("✅ Token received: " + token);  // Debugging
            session.setAttribute("token", token);
            return "redirect:/checkout";
        }

        System.out.println("❌ Invalid credentials - Redirecting to login"); // Debugging
        //model.addAttribute("error", "Invalid credentials");
        return "login";

   /* 
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

    */
    
    
    
    
}
