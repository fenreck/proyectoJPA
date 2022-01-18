package com.example.JPA.demo.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.JPA.demo.model.entity.Cliente;
import com.example.JPA.demo.model.entity.Factura;
import com.example.JPA.demo.model.entity.Producto;
import com.example.JPA.demo.model.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes ("factura") // para que el objeto factura persista en la sesion
public class FacturaController {
	@Autowired
	private IClienteService clienteService;

	@GetMapping("/formFactura/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.put("titulo", "Crea factura");
		model.put("factura", factura);

		return "factura/formFactura.html";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargaProducto(@PathVariable String term) { // @ResponseBody suprime cargar una
																					// vista con thymeleaf, en lugar
																					// regresa json, dentro del body de
																					// la respuesta

		return clienteService.findProductoByNombre(term);

	}
	/*
	 * public String guardar (Factura factura) {
	 * 
	 * }
	 */

}
