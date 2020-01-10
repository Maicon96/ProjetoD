package br.com.dimo.ediws.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ClienteFormaRecebimentoPastaId implements Serializable {
	
	private static final long serialVersionUID = 1L;
				
	@JsonInclude(value = Include.NON_NULL)
	private Long idCliente;
	
	@JsonInclude(value = Include.NON_NULL)
	private Long idFormaRecebimentoPasta;	
		
	
	public ClienteFormaRecebimentoPastaId() {
		
	}
	
	
	public ClienteFormaRecebimentoPastaId(Long idCliente, Long idFormaRecebimentoPasta) {
		super();
		this.idCliente = idCliente;
		this.idFormaRecebimentoPasta = idFormaRecebimentoPasta;
	}


	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdFormaRecebimentoPasta() {
		return idFormaRecebimentoPasta;
	}

	public void setIdFormaRecebimentoPasta(Long idFormaRecebimentoPasta) {
		this.idFormaRecebimentoPasta = idFormaRecebimentoPasta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((idFormaRecebimentoPasta == null) ? 0 : idFormaRecebimentoPasta.hashCode());
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
		ClienteFormaRecebimentoPastaId other = (ClienteFormaRecebimentoPastaId) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (idFormaRecebimentoPasta == null) {
			if (other.idFormaRecebimentoPasta != null)
				return false;
		} else if (!idFormaRecebimentoPasta.equals(other.idFormaRecebimentoPasta))
			return false;
		return true;
	}	
	

}
