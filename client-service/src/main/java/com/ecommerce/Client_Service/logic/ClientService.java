package com.ecommerce.Client_Service.logic;

import java.util.List;
import java.util.Optional;

import com.ecommerce.Client_Service.dataaccess.ClientRepository;
import com.ecommerce.Client_Service.model.Client;
import com.ecommerce.Client_Service.model.LoginRequest;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service 
public class ClientService {

	 private final RestTemplate restTemplate;
	    private final String productServiceUrl = "http://localhost:8082/produits";  // URL of the Product service
	    
	
	    public ClientService(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }
	    
	   /* public List<Produit> getAllProduitsFromProductService() {
	        // Making a GET request to the Product service to fetch all products
	        ResponseEntity<List<Produit>> response = restTemplate.exchange(
	                productServiceUrl, 
	                HttpMethod.GET, 
	                null, 
	                new ParameterizedTypeReference<List<Produit>>() {}
	        );
	        return response.getBody();  // Returns the list of products
	    }*/
	    
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client>getAllClients(){
		
		return clientRepository.findAll();
	}
	
	public Client isUserExist(String login, String password) {
		Optional<Client> client = clientRepository.findByEmailAndPassword(login, password);
        if(client.isPresent()) return client.get();
        else return null;
    }
	
	public Optional<Client> getClientById(int clientId) {
        return clientRepository.findById(clientId);
    }
	
	
	  @Transactional 
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
	
	
    public Client updateClient(int clientId, Client updatedClient) {
        Optional<Client> existingClient = clientRepository.findById(clientId);
        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            client.setNom(updatedClient.getNom());
            client.setPrenom(updatedClient.getPrenom());
            client.setDescription(updatedClient.getDescription());
            client.setTelephone(updatedClient.getTelephone());
            client.setVille(updatedClient.getVille());
            return clientRepository.save(client);
        }
        return null;
    }
    
    public void deleteClient(int clientId) {
        clientRepository.deleteById(clientId);
    }
    public boolean register(Client client) {
        Optional<Client> existingClient = clientRepository.findByEmail(client.getEmail());
        if (existingClient.isPresent()) {
            return false;
        }
        clientRepository.save(client);
        return true;
    }

    public String authenticate(LoginRequest loginRequest) {
        Optional<Client> client = clientRepository.findByEmail(loginRequest.getEmail());
        
        if (client.isPresent() && client.get().getPassword().equals(loginRequest.getPassword())) {
            // TODO: Generate JWT token instead of returning email
            return "TOKEN_12345"; // Dummy token for testing
        }
        
        return null;
    }

}
