package br.com.dimo.ediws.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(ClienteFormaRecebimentoEmailId.class)
@Table(name="T_CLIENTE_HAS_T_IMPORT_EMAIL")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClienteFormaRecebimentoEmail implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	
	@Id
	@Column(name = "T_CLIENTE_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idCliente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "T_CLIENTE_id", insertable=false, updatable=false)	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonBackReference
	private Cliente cliente;
	
		
	@Id
	@Column(name = "T_IMPORT_EMAIL_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idFormaRecebimentoEmail;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "T_IMPORT_EMAIL_id", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	private FormaRecebimentoEmail formaRecebimentoEmail;
		
	
	public ClienteFormaRecebimentoEmail() {
		
	}
	
	
	public ClienteFormaRecebimentoEmail(Long idCliente, Cliente cliente, Long idFormaRecebimentoEmail,
			FormaRecebimentoEmail formaRecebimentoEmail) {
		this.idCliente = idCliente;
		this.cliente = cliente;
		this.idFormaRecebimentoEmail = idFormaRecebimentoEmail;
		this.formaRecebimentoEmail = formaRecebimentoEmail;
	}
	
	
	public ClienteFormaRecebimentoEmail(Long idCliente, Long idFormaRecebimentoEmail) {		
		this.idCliente = idCliente;
		this.idFormaRecebimentoEmail = idFormaRecebimentoEmail;
	}


	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdFormaRecebimentoEmail() {
		return idFormaRecebimentoEmail;
	}

	public void setIdFormaRecebimentoEmail(Long idFormaRecebimentoEmail) {
		this.idFormaRecebimentoEmail = idFormaRecebimentoEmail;
	}	
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public FormaRecebimentoEmail getFormaRecebimentoEmail() {
		return formaRecebimentoEmail;
	}

	public void setFormaRecebimentoEmail(FormaRecebimentoEmail formaRecebimentoEmail) {
		this.formaRecebimentoEmail = formaRecebimentoEmail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
			
			
}
