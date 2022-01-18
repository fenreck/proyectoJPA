package com.example.JPA.demo.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.JPA.demo.model.entity.Cliente;
import com.example.JPA.demo.model.entity.Factura;
import com.example.JPA.demo.model.entity.Producto;

public interface IClienteService {
	
	 public List<Cliente> findAll(); // implementacion para obtener los datos de la tabla en la base de datos
	
	public Page<Cliente> findAll(Pageable pageable); // implementacion para obtener los datos de la tabla en la base de datos
	
	public void save (Cliente cliente) ; // implementación para guardar nuevo cliente en la base de datos
	
	public Cliente findOne(long id); // implementación para busqueda de un solo cliente y posteriomente poderlo editar
	
	public void delete (long id) ; // implementa interfaz para eliminar algún cliente de la BD
	
	public List<Producto> findProductoByNombre (String term);
	
	public void saveFactura(Factura factura);
	
	public Producto findProductoById(Long id);

}
