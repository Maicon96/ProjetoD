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
@IdClass(ClienteFormaRecebimentoPastaId.class)
@Table(name="T_CLIENTE_HAS_T_IMPORT_PASTA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClienteFormaRecebimentoPasta implements Serializable {
	
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
	@Column(name = "T_IMPORT_PASTA_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idFormaRecebimentoPasta;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "T_IMPORT_PASTA_id", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	private FormaRecebimentoPasta formaRecebimentoPasta;
		
	
	public ClienteFormaRecebimentoPasta() {
		
	}
	

	public ClienteFormaRecebimentoPasta(Long idCliente, Long idFormaRecebimentoPasta) {		
		this.idCliente = idCliente;
		this.idFormaRecebimentoPasta = idFormaRecebimentoPasta;
	}


	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getIdFormaRecebimentoPasta() {
		return idFormaRecebimentoPasta;
	}

	public void setIdFormaRecebimentoPasta(Long idFormaRecebimentoPasta) {
		this.idFormaRecebimentoPasta = idFormaRecebimentoPasta;
	}

	public FormaRecebimentoPasta getFormaRecebimentoPasta() {
		return formaRecebimentoPasta;
	}

	public void setFormaRecebimentoPasta(FormaRecebimentoPasta formaRecebimentoPasta) {
		this.formaRecebimentoPasta = formaRecebimentoPasta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
			
			
}
