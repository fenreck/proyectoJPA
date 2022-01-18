package com.example.JPA.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//import com.example.JPA.demo.model.DAO.IClienteDaoBAK;
import com.example.JPA.demo.model.DAO.IClienteDao;
import com.example.JPA.demo.model.DAO.IProductoDao;
import com.example.JPA.demo.model.DAO.iFacturaDao;
import com.example.JPA.demo.model.entity.Cliente;
import com.example.JPA.demo.model.entity.Factura;
import com.example.JPA.demo.model.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private iFacturaDao facturaDao;
	
	
	@Override
	@Transactional (readOnly=true) // va tomar el contenido del metodo lo convierte en una transaccion
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional  // va tomar el contenido del metodo lo convierte en una transaccion
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		clienteDao.save(cliente);
	}

	@Override
	@Transactional (readOnly=true)
	public Cliente findOne(long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null); // si lo encuentra va retornar el cliente de lo contrario retornara NULL
	}

	@Override
	@Transactional  // va tomar el contenido del metodo lo convierte en una transaccion
	public void delete(long id) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(id);
		
	}

	@Override	
	@Transactional (readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional (readOnly=true)
	public List<Producto> findProductoByNombre(String term) {
		// TODO Auto-generated method stub
	//	return productoDao.findByNombre(term); // se puede usar tambien este metodo
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");  // % para que sea como el like
	}

	@Override
	@Transactional 
	public void saveFactura(Factura factura) {
		// TODO Auto-generated method stub
		facturaDao.save(factura);
	}

	@Override
	@Transactional (readOnly=true)
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}


}
