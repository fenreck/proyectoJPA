package com.example.JPA.demo.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity // ("")
@Table(name = "clientes") // nombre de la tabla
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	

//	 @Size (min = 4, max=12) // el numero de caracteres para el nombre
	 @NotEmpty
	String nombre;
	 @NotEmpty
	String apellido;
	 
	 @NotEmpty
	 @Email
	String email;
	 
	 @NotNull				
	@Column(name = "create_at") // el nombre del atributo en la tabla
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;
	
	private String foto;
				
	// va crear cliente seria la llave foranea en Factura, para la relación de ambas tablas, un cliente va tener muchas facturas (llave fomanea en mapped by)
	@OneToMany (mappedBy="cliente",fetch=FetchType.LAZY, cascade=CascadeType.ALL ) // persiste todas las facturas que tiene el cliente, cuando se elimna cliente va eliminar tambien sus facturas asociadas, en la base de datos
	private List <Factura> facturas; 
	
	
	public Cliente() {
		
		facturas = new ArrayList<Factura> ();
	}


	public List<Factura> getFacturas() {
		return facturas;
	}
	
	
	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
	public void addFactura (Factura Factura) { // agrega un elemento a factura
		facturas.add(Factura);
	}


	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
