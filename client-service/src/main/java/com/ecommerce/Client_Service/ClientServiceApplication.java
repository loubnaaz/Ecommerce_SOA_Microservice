package com.ecommerce.Client_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.Client_Service.model.JwtUtil;

@SpringBootApplication
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
		
		System.out.println("hello 8081 client service");
	}
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	  public JwtUtil jwtUtil() {
	        return new JwtUtil();
	    }
	
	
}
