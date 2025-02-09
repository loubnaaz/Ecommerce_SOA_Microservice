package com.ecommerce_microservices.Produit_Service.api;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce_microservices.Produit_Service.logic.ProduitService;
import com.ecommerce_microservices.Produit_Service.model.Produit;
import com.ecommerce_microservices.Produit_Service.model.productDTO;



@RestController
@RequestMapping("/produits")
public class ProduiController {
	
	// private static final String UPLOAD_DIR = "src/main/resources/static/images/";
	 
    @Autowired
    private ProduitService produitService;

    @GetMapping
    public List<Produit> getProduits() {
        return produitService.getAllProduits();
    }
    
    
    @GetMapping("/{produitId}")
    public ResponseEntity<Produit> getProduitById(@PathVariable int produitId) {
        return produitService.getProduitById(produitId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(
    		 @RequestParam("type") String type,
    		 @RequestParam("titre") String titre,
            @RequestParam("reference") int reference,
            @RequestParam("description") String description,
            @RequestParam("prix") int prix,
            @RequestParam("quantity") int quantity,
            @RequestParam("file") MultipartFile file) {

        System.out.println("Received file: " + file.getOriginalFilename());
        System.out.println("File size: " + file.getSize());

        try {
            productDTO productDTO = new productDTO();
            productDTO.setType(type);
            productDTO.setTitre(titre);
            productDTO.setReference(reference);
            productDTO.setDescription(description);
            productDTO.setQuantity(quantity);
            productDTO.setPrix(prix);
            productDTO.setImageFile(file);

            Produit savedProduct = produitService.saveProduit(productDTO);
            return ResponseEntity.ok("Produit créé avec succès ! Image URL: " + savedProduct.getImagepath());
        } catch (IOException e) {
            e.printStackTrace(); // Print detailed error in logs
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    @PutMapping("/{produitId}")
    public ResponseEntity<?> updateProduit(
            @PathVariable int produitId,
            @RequestParam("type") String type,
            @RequestParam("titre") String titre,
            @RequestParam("reference") int reference,
            @RequestParam("description") String description,
            @RequestParam("quantity") int quantity,
            @RequestParam("prix") int prix,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            productDTO updateDTO = new productDTO();
            updateDTO.setType(type);
            updateDTO.setTitre(titre);
            updateDTO.setReference(reference);
            updateDTO.setDescription(description);
            updateDTO.setPrix(prix);
            updateDTO.setQuantity(quantity);
            updateDTO.setImageFile(file); // May be null

            Produit updatedProduct = produitService.updateProduit(produitId, updateDTO);

            if (updatedProduct != null) {
                return ResponseEntity.ok("Produit mis à jour avec succès !");
            } else {
                return ResponseEntity.status(404).body("Produit non trouvé.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour du produit.");
        }
    }

    
    @DeleteMapping("/{produitId}")
    public String deleteProduit(@PathVariable int produitId) {
    	produitService.deleteProduit(produitId);
        return "produit deleted!";
    }
    
    

}
