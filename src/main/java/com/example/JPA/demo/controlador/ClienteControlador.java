package com.example.JPA.demo.controlador;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.example.JPA.demo.model.entity.Cliente;
import com.example.JPA.demo.model.service.IClienteService;
import com.example.JPA.demo.model.service.IUploadFilesService;
import com.example.JPA.demo.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteControlador {

	@Autowired
	// @Qualifier ("ClienteDaoJPA") // para saber a que bean va seleccionar
	private IClienteService clienteService; // implementa IclienteService y dentro las interfaces DAO

	@Autowired
	private IUploadFilesService uploadFilesService; // implementa IclienteService y dentro las interfaces DAO

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = uploadFilesService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok() // manda el recurso a la respuesta
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 5); // ( numero de paginas , numero de elementos por pagina )

		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		// PageRender<Cliente> pageRender = new PageRender <> ("/listar",clientes);
		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		// model.addAttribute("clientes", clienteService.findAll() ); // sin paginacion
		return "listar"; // nombre de la vista

	}

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {

			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Infomración del cliente: " + cliente.getNombre());

		return "ver"; // nombre de la vista
	}

	@RequestMapping(value = "/form")
	public String crearCliente(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");

		return "form"; // nombre de la vista
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST) // va utilizar /fomr pero del metodo post para que nos
																	// entregue los datos ingresados al objeto Cliente
	public String guardaCliente(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		String uniqueFilesName = null;

		if (result.hasErrors()) { // si tiene errores va regresar al vista del formulario
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}

		if (!foto.isEmpty()) { // para editar foto
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) { // si existe foto

				uploadFilesService.delete(cliente.getFoto());

			}

			try {
				uniqueFilesName = uploadFilesService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// Path directorioUploads = Paths.get("C://temp//uploads"); // se van a guardar
		// las imagenes
		// String rootPath = directorioUploads.toFile().getAbsolutePath();

		flash.addFlashAttribute("info", "Has subido correctamente la foto " + uniqueFilesName);
//			log.info("/*** LOGGER ***/ rootAbsolutePath: " + rootAbsolutePathFile );
		cliente.setFoto(uniqueFilesName);

		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con exito!" : "Cliente creado con exito!";
		clienteService.save(cliente);
		status.setComplete(); // elimna el objeto cliente de la sesión
		flash.addFlashAttribute("success", mensajeFlash); // mensaje dentro del html /layoutMenus/layoutmenu.html
		return "redirect:listar";

	}

	@RequestMapping(value = "/formEditar/{id}")
	public String editaCliente(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) { // se pone pathvariable puesto que es una variable que va entrar a ese mapeo de
										// la página

		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);

			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del Cliente no existe en la Base de Datos"); // mensaje dentro
																										// del html
																										// /layoutMenus/layoutmenu.html
				return "redirect:/listar"; // nombre de la vista
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero"); // mensaje dentro del html
																						// /layoutMenus/layoutmenu.html
			return "redirect:/listar"; // nombre de la vista
		}

		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");

		return "form"; // nombre de la vista
	}

	@RequestMapping(value = "/elimina/{id}")
	public String eliminarCliente(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);

			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con exito!"); // mensaje dentro del html
																				// /layoutMenus/layoutmenu.html
			if ( uploadFilesService.delete(cliente.getFoto() )) {
			flash.addFlashAttribute("info", "foto " + cliente.getFoto() + " eliminada con exito");
			}

		}

		return "redirect:/listar"; // nombre de la vista
	}
	
	
	@RequestMapping(value = "/calendario", method = RequestMethod.GET)
	public String calendario (Model model) {

		// PageRender<Cliente> pageRender = new PageRender <> ("/listar",clientes);
	
		model.addAttribute("titulo", "Listado de Clientes");
		


		return "calendario"; // nombre de la vista

	}

}
