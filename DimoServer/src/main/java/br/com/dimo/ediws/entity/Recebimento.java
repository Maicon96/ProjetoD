package br.com.dimo.ediws.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="T_IMPORT_RECEBIMENTO")
public class Recebimento implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@Column(name = "id")
	@JsonInclude(value = Include.NON_NULL)
	private Integer id;
	
	
	@NotNull
	@Column(name = "t_import_layout_cliente_t_cliente_id")
	@JsonInclude(value = Include.NON_NULL)
	private Integer idCliente;
	
	@NotNull
	@Column(name = "t_import_layout_cliente_t_import_layout_id")
	@JsonInclude(value = Include.NON_NULL)
	private Integer idLayout;
		
	public Recebimento() {
		
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(Integer idLayout) {
		this.idLayout = idLayout;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
			
}
