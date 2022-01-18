package com.example.JPA.demo.model.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service // registra en el bean, se puede inyectar en el controlador con autowired
public class UploadFileService implements IUploadFilesService {
	
	private final Logger log = LoggerFactory.getLogger ( getClass() );
	private final static String UPLOADS_FOLDER = "uploads";
	

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;
	
			recurso = new UrlResource(pathFoto.toUri());
			if(!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
			}
			
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {

		String uniqueFilesName = UUID.randomUUID().toString() +"_"+ file.getOriginalFilename() ; // PARA DEJAR nombre unico en la carpeta y no se repita
		Path rootPath =  getPath( uniqueFilesName ) ; // ruta concatenada con el nombre del archivo
	//	Path rootAbsolutePathFile =  rootPath.toAbsolutePath();
		
		System.out.println("/******/ rootPath: "+ rootPath);

		//	byte [] bytes = foto.getBytes();
		//	Path rutaCompleta = Paths.get(rootPath+"//"+ foto.getOriginalFilename() );
		//	Files.write(rutaCompleta , bytes);
			
			Files.copy(file.getInputStream(), rootPath );
			

			log.info("/*** LOGGER ***/ rootPath: " + rootPath );
		

		
		return uniqueFilesName;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile() ;
		
		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
		
				return true;
			}
			
		}
		
		return false;
	}

		public Path getPath(String filename) {
			
			return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
		}

		@Override
		public void deleteAll() { // para eliminar carpeta Uploads
			FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile()) ;
			
		}

		@Override
		public void init() throws IOException { // crea directorio uploads en la app
		 Files.createDirectory(Paths.get(UPLOADS_FOLDER) ) ;
			
		}
}
