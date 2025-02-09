package com.ecommerce_microservices.Produit_Service.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_microservices.Produit_Service.model.Produit;

public interface ProduitRepository extends JpaRepository <Produit, Integer>{

}
