package com.ecommerce_microservices.Produit_Service.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class RestTemplateConfig implements WebMvcConfigurer{
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/images/**")
         .addResourceLocations("file:public/images/");
    }

}
