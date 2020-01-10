package br.com.dimo.ediwsboot.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class LayoutArquivoCampoId implements Serializable {
	
	private static final long serialVersionUID = 1L;
			
	private Long id;
	
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayoutArquivo;
	
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayout;
	
	
	public LayoutArquivoCampoId() {
		
	}

	public LayoutArquivoCampoId(Long id, Long idLayoutArquivo, Long idLayout) {
		super();
		this.id = id;
		this.idLayoutArquivo = idLayoutArquivo;
		this.idLayout = idLayout;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idLayout == null) ? 0 : idLayout.hashCode());
		result = prime * result + ((idLayoutArquivo == null) ? 0 : idLayoutArquivo.hashCode());
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
		LayoutArquivoCampoId other = (LayoutArquivoCampoId) obj;
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
		if (idLayoutArquivo == null) {
			if (other.idLayoutArquivo != null)
				return false;
		} else if (!idLayoutArquivo.equals(other.idLayoutArquivo))
			return false;
		return true;
	}
	
	
		
			
}
