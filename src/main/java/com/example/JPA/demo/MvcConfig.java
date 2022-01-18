package com.example.JPA.demo;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

/*	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString() ;
		log.info(" resourcePath: "+resourcePath);
																	// toUri() convierte a esquema de tipo file (nombre de archivo y exte)
		// registry.addResourceHandler("/uploads/**").addResourceLocations("file:/C:/temp/uploads/");
		
	registry.addResourceHandler("uploads/**").addResourceLocations(resourcePath);
	
	}

*/

}
