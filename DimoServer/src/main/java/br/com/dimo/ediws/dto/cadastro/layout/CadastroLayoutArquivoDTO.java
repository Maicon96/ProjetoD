package br.com.dimo.ediws.dto.cadastro.layout;

import java.util.ArrayList;
import java.util.List;

public class CadastroLayoutArquivoDTO {

	Long id;	
	Integer identificadorLinha;	
	List<CadastroLayoutArquivoCamposDTO> cadastroLayoutArquivoDTOs = new  ArrayList<CadastroLayoutArquivoCamposDTO>();	
	
		
	public CadastroLayoutArquivoDTO() {
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdentificadorLinha() {
		return identificadorLinha;
	}

	public void setIdentificadorLinha(Integer identificadorLinha) {
		this.identificadorLinha = identificadorLinha;
	}

	public List<CadastroLayoutArquivoCamposDTO> getCadastroLayoutArquivoDTOs() {
		return cadastroLayoutArquivoDTOs;
	}

	public void setCadastroLayoutArquivoDTOs(List<CadastroLayoutArquivoCamposDTO> cadastroLayoutArquivoDTOs) {
		this.cadastroLayoutArquivoDTOs = cadastroLayoutArquivoDTOs;
	}
	

}
