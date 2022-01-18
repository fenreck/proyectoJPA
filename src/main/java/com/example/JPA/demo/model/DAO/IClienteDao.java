package com.example.JPA.demo.model.DAO;

import java.util.List;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.JPA.demo.model.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> { // <Entidad, Llave para encontrar la entidad>
	



}
