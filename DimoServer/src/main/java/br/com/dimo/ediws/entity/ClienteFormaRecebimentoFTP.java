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
@IdClass(ClienteFormaRecebimentoFTPId.class)
@Table(name="T_CLIENTE_HAS_T_IMPORT_FTP")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClienteFormaRecebimentoFTP implements Serializable {
	
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
	@Column(name = "T_IMPORT_FTP_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idFormaRecebimentoFTP;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "T_IMPORT_FTP_id", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	private FormaRecebimentoFTP formaRecebimentoFTP;
		
	
	public ClienteFormaRecebimentoFTP() {
		
	}
	

	public ClienteFormaRecebimentoFTP(Long idCliente, Long idFormaRecebimentoFTP) {
		this.idCliente = idCliente;
		this.idFormaRecebimentoFTP = idFormaRecebimentoFTP;
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

	public Long getIdFormaRecebimentoFTP() {
		return idFormaRecebimentoFTP;
	}

	public void setIdFormaRecebimentoFTP(Long idFormaRecebimentoFTP) {
		this.idFormaRecebimentoFTP = idFormaRecebimentoFTP;
	}

	public FormaRecebimentoFTP getFormaRecebimentoFTP() {
		return formaRecebimentoFTP;
	}

	public void setFormaRecebimentoFTP(FormaRecebimentoFTP formaRecebimentoFTP) {
		this.formaRecebimentoFTP = formaRecebimentoFTP;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
			
			
}
