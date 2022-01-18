package com.example.JPA.demo.model.DAO;

import org.springframework.data.repository.CrudRepository;

import com.example.JPA.demo.model.entity.Factura;

public interface iFacturaDao extends CrudRepository <Factura, Long> {

}
