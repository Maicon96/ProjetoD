package br.com.dimo.ediws.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class LayoutArquivoClienteId implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@JsonInclude(value = Include.NON_NULL)
	private Long idCliente;
	
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayout;
	
	
	public LayoutArquivoClienteId() {
		
	}
	

	public LayoutArquivoClienteId(Long idCliente, Long idLayout) {
		this.idCliente = idCliente;
		this.idLayout = idLayout;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((idLayout == null) ? 0 : idLayout.hashCode());
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
		LayoutArquivoClienteId other = (LayoutArquivoClienteId) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (idLayout == null) {
			if (other.idLayout != null)
				return false;
		} else if (!idLayout.equals(other.idLayout))
			return false;
		return true;
	}
	
			
}
