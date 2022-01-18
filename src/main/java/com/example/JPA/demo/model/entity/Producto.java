package com.example.JPA.demo.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table (name="productos")
public class Producto implements Serializable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	private Date createAt;
	
	
	@PrePersist
	public void prePersist () {
		createAt  = new Date ();
	}
		
	
	private Double precio ;
	
	
	private  static final long serialVersionUID = 1L;
	
	@Id // va hacer el identificador del campo
	@GeneratedValue(strategy = GenerationType.IDENTITY) // va hacer autincrmental
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	public Date getCreateAt() {
		return createAt;
	}

	
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	

}
