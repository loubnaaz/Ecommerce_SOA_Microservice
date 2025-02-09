package com.ecommerce.Client_Service.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.Client_Service.model.Client;

public interface ClientRepository extends JpaRepository <Client, Integer>{

	 Optional<Client> findByEmail(String email);
	 Optional<Client> findByEmailAndPassword(String login, String password);
}
