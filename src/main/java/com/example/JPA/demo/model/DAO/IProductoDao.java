package com.example.JPA.demo.model.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.JPA.demo.model.entity.Producto;

public interface IProductoDao extends CrudRepository <Producto,Long> { // <Entidad, Llave para encontrar la entidad>
	
	@Query("select p from Producto p where p.nombre like %?1%") // select a nivel spring, no de tabla la p es una alias ( %?1% ) se refiere al parametro term
	public List<Producto> findByNombre (String term);

	public List<Producto> findByNombreLikeIgnoreCase (String term);
}
