package com.example.JPA.demo.model.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;


import com.example.JPA.demo.model.entity.Cliente;

@Repository // ("ClienteDaoJPA")
public class ClienteDAOImplBAK implements IClienteDaoBAK {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cliente").getResultList(); // from ( se pone la clase )
	}
	
	@Override

	public Cliente findOne(long id) { // metodo para buscar un cliente 
		// TODO Auto-generated method stub
		return em.find(Cliente.class, id);
	}
	

	@Override

	public void save(Cliente cliente) {
		if (cliente.getId() != null && cliente.getId() > 0) { // si existe cliente entonces va hacer merge que es actualizar en la base de datos
			em.merge(cliente)	;	
		}
		else {												  // si no existe cliente (es nula) va crear el persist, creará un nuevo cliente (como insert en la base)
			em.persist(cliente); 
		}

	}


	@Override

	public void delete(long id) { // metodo para eliminar un cliente de la base obtenido del objeto cliente
		// TODO Auto-generated method stub
		Cliente cliente = findOne(id) ;
		em.remove(cliente);

	}

}
