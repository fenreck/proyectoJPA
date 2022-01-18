package com.example.JPA.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.JPA.demo.model.service.IUploadFilesService;

@SpringBootApplication
public class ProyectoJpaApplication implements CommandLineRunner{
@Autowired
IUploadFilesService uploadFileService ;
	
	public static void main(String[] args) {
		SpringApplication.run(ProyectoJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		uploadFileService.deleteAll(); // elimina todo lo que esta en el directorio y tambien la carpeta Uploads
		uploadFileService.init(); // crea la carpeta Uploads
	}

}
