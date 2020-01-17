package br.com.dimo.ediwsboot.dto;

import java.util.ArrayList;
import java.util.List;

public class DeparaDTO {

	List<DeparaCampoDTO> campos = new ArrayList<DeparaCampoDTO>();

	
	public DeparaDTO(){
		
	}
	
	public List<DeparaCampoDTO> getCampos() {
		return campos;
	}

	public void setCampos(List<DeparaCampoDTO> campos) {
		this.campos = campos;
	}
	
	
}
