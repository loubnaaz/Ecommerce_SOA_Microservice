package com.ecommerce_microservices.Produit_Service.logic;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce_microservices.Produit_Service.dataaccess.ProduitRepository;
import com.ecommerce_microservices.Produit_Service.model.Produit;
import com.ecommerce_microservices.Produit_Service.model.ProduitType;
import com.ecommerce_microservices.Produit_Service.model.productDTO;

@Service
public class ProduitService {
	@Autowired
    private ProduitRepository produitRepository;
	
	private static final String IMAGE_DIR = System.getProperty("user.dir") + "/public/images/";
    private static final String IMAGE_BASE_URL = "http://localhost:8082/images/";

    public List<Produit> getAllProduits() {
    	List<Produit> result =  produitRepository.findAll();
    	System.out.println("========= size "+result.size());
    	return result;
    }
    
    public Optional<Produit> getProduitById(int produitId) {
        return produitRepository.findById(produitId);
    }

    public Produit saveProduit(productDTO productDTO) throws IOException {
        MultipartFile file = productDTO.getImageFile();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = IMAGE_DIR + fileName;

        System.out.println("Saving file to: " + filePath);

        File directory = new File(IMAGE_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            System.out.println("Directory created: " + created);
        }

        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);

        // Verify if the file was saved
        if (destinationFile.exists()) {
            System.out.println("File saved successfully!");
        } else {
            System.out.println("File was not saved!");
        }

        String imageUrl = IMAGE_BASE_URL + fileName;

        Produit produit = new Produit();
        produit.setType(ProduitType.valueOf(productDTO.getType().toUpperCase()));
        produit.setTitre(productDTO.getTitre());
        produit.setReference(productDTO.getReference());
        produit.setDescription(productDTO.getDescription());
        produit.setPrix(productDTO.getPrix());
        produit.setQuantity(productDTO.getQuantity());
        produit.setImagepath(imageUrl);

        return produitRepository.save(produit);
    }

	
    public Produit updateProduit(int produitId, productDTO updateDTO) throws IOException {
        Optional<Produit> existingProduitOpt = produitRepository.findById(produitId);
        if (existingProduitOpt.isPresent()) {
            Produit produit = existingProduitOpt.get();

            // Update text fields
       //  a revoir   //produit.setType(updateDTO.getType());
            produit.setType(ProduitType.valueOf(updateDTO.getType().toUpperCase()));
            produit.setReference(updateDTO.getReference());
            produit.setDescription(updateDTO.getDescription());
            produit.setPrix(updateDTO.getPrix());
            produit.setQuantity(updateDTO.getQuantity());
            produit.setTitre(updateDTO.getTitre());
            
            // Handle image file update
            MultipartFile file = updateDTO.getImageFile();
            if (file != null && !file.isEmpty()) {
                // Delete the old file if it exists
                if (produit.getImagepath() != null) {
                    String oldFilePath = produit.getImagepath().replace(IMAGE_BASE_URL, IMAGE_DIR);
                    File oldFile = new File(oldFilePath);
                    if (oldFile.exists()) {
                        oldFile.delete();
                        System.out.println("Old file deleted: " + oldFilePath);
                    }
                }

                // Save new file
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String filePath = IMAGE_DIR + fileName;
                File directory = new File(IMAGE_DIR);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                file.transferTo(new File(filePath));

                // Update image path in database
                produit.setImagepath(IMAGE_BASE_URL + fileName);
            }

            return produitRepository.save(produit);
        }
        return null;
    }

    
    public void deleteProduit(int produitId) {
    	produitRepository.deleteById(produitId);
    }
}