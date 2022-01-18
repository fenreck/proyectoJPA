package com.example.JPA.demo.model.DAO;

import java.util.List;

import com.example.JPA.demo.model.entity.Cliente;

public interface IClienteDaoBAK {
	

	public List<Cliente> findAll(); // implementacion para obtener los datos de la tabla en la base de datos
	
	public void save (Cliente cliente) ; // implementación para guardar nuevo cliente en la base de datos
	
	public Cliente findOne(long id); // implementación para busqueda de un solo cliente y posteriomente poderlo editar
	
	public void delete (long id) ; // implementa interfaz para eliminar algún cliente de la BD

}
