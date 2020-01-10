package br.com.dimo.ediws.entity;

import java.io.Serializable;

public class LayoutArquivoId implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private Long id;
	
	private Long idLayout;	
	
	
	public LayoutArquivoId() {
		
	}

	public LayoutArquivoId(Long id, Long idLayout) {
		this.id = id;
		this.idLayout = idLayout;
	}
	
	public LayoutArquivoId(Long idLayout) {
		this.idLayout = idLayout;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LayoutArquivoId other = (LayoutArquivoId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idLayout == null) {
			if (other.idLayout != null)
				return false;
		} else if (!idLayout.equals(other.idLayout))
			return false;
		return true;
	}
	
			
}
