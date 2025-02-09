package com.ecommerce.Client_Service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Client_Service.logic.ClientService;
import com.ecommerce.Client_Service.model.Client;
import com.ecommerce.Client_Service.model.JwtUtil;
import com.ecommerce.Client_Service.model.LoginRequest;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	
	 @GetMapping
	public List<Client> getClients(){
		
		return clientService.getAllClients();
	}
	 

	  
	    @GetMapping("/{id}")
	    public Optional<Client> getClientById(@PathVariable int id) {
	        return clientService.getClientById(id);
	    }
	  
	    @GetMapping("/checkUser/{login}/{password}")
	    public ResponseEntity<Client> checkUser(@PathVariable String login, @PathVariable String password) {
	    	System.out.println("********* isUserExist *********");
	    	System.out.println("login "+login);
	        Client c = clientService.isUserExist(login, password);
	        if(c != null ) {
	        	System.out.println("client trouvé !");
	        	System.out.println(c.toString());
	        }
	        else {
	        	System.out.println("client non trouvé");
	        }
	        return ResponseEntity.ok(c);
	    }

	   /* @PostMapping
	    public Client createClient(@RequestBody Client client) {
	        return clientService.saveClient(client);
	    }*/

	    @PutMapping("/{id}")
	    public Client updateClient(@PathVariable int id, @RequestBody Client client) {
	        return clientService.updateClient(id, client);
	    }
	    
	    @DeleteMapping("/{id}")
	    public String deleteClient(@PathVariable int id) {
	        clientService.deleteClient(id);
	        return "Client deleted!";
	    }
	    @Autowired
	    private JwtUtil jwtUtil;

	    // ✅ Register a new user
	    @PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody Client client) {
	        boolean isRegistered = clientService.register(client);
	        if (!isRegistered) {
	            return ResponseEntity.status(409).body("Email already exists");
	        }
	        return ResponseEntity.ok("Registration successful");
	    }

	    // ✅ Login & Generate JWT
	    @PostMapping("/authenticate")
	    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
	        String token = clientService.authenticate(loginRequest);
	        
	        if (token == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
	        
	        return ResponseEntity.ok(token);
	    }

	    // ✅ Register New User
	  /*  @PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody Client client) {
	        boolean isRegistered = clientService.register(client);
	        if (!isRegistered) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
	        }
	        return ResponseEntity.ok("Registration successful");
	    }

	    // ✅ Login & Generate JWT
	    private final RestTemplate restTemplate = new RestTemplate(); // Permet d'envoyer des requêtes HTTP

	    @PostMapping("/login")
	    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
	        try {
	            String url = "http://localhost:8081/clients/authenticate"; // URL de ton microservice Client

	            // Création de l'objet contenant l'email et le password
	            loginRequest loginRequest = new loginRequest(email, password); 

	            // Envoi de la requête POST pour authentifier l'utilisateur
	            ResponseEntity<Client> response = restTemplate.postForEntity(url, loginRequest, Client.class);

	            if (response.getStatusCode() == HttpStatus.OK) {
	                session.setAttribute("client", response.getBody()); // Stocke les infos du client en session
	                return "redirect:/checkout"; // Redirige vers la page checkout après connexion réussie
	            }
	        } catch (Exception e) {
	            //model.addAttribute("error", "Identifiants invalides");
	            return "login"; // Retourne sur la page de login avec un message d'erreur
	        }

	        return "redirect:/login";
	    }
	    */
	  
}
