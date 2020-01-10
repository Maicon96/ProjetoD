package br.com.dimo.ediws.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ClienteFormaRecebimentoFTPId implements Serializable {
	
	private static final long serialVersionUID = 1L;
				
	@JsonInclude(value = Include.NON_NULL)
	private Long idCliente;
	
	@JsonInclude(value = Include.NON_NULL)
	private Long idFormaRecebimentoFTP;	
		
	
	public ClienteFormaRecebimentoFTPId() {
		
	}
	

	public ClienteFormaRecebimentoFTPId(Long idCliente, Long idFormaRecebimentoFTP) {
		this.idCliente = idCliente;
		this.idFormaRecebimentoFTP = idFormaRecebimentoFTP;
	}
	

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdFormaRecebimentoFTP() {
		return idFormaRecebimentoFTP;
	}

	public void setIdFormaRecebimentoFTP(Long idFormaRecebimentoFTP) {
		this.idFormaRecebimentoFTP = idFormaRecebimentoFTP;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((idFormaRecebimentoFTP == null) ? 0 : idFormaRecebimentoFTP.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteFormaRecebimentoFTPId other = (ClienteFormaRecebimentoFTPId) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (idFormaRecebimentoFTP == null) {
			if (other.idFormaRecebimentoFTP != null)
				return false;
		} else if (!idFormaRecebimentoFTP.equals(other.idFormaRecebimentoFTP))
			return false;
		return true;
	}
	

	
}
