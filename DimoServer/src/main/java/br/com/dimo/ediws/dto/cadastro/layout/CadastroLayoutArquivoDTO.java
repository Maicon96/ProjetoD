package br.com.dimo.ediws.dto.cadastro.layout;

import java.util.ArrayList;
import java.util.List;

public class CadastroLayoutArquivoDTO {

	String identificadorLinha;	
	List<CadastroLayoutArquivoCamposDTO> cadastroLayoutArquivoDTOs = new  ArrayList<CadastroLayoutArquivoCamposDTO>();	
	
		
	public CadastroLayoutArquivoDTO() {
		
	}


	public String getIdentificadorLinha() {
		return identificadorLinha;
	}

	public void setIdentificadorLinha(String identificadorLinha) {
		this.identificadorLinha = identificadorLinha;
	}

	public List<CadastroLayoutArquivoCamposDTO> getCadastroLayoutArquivoDTOs() {
		return cadastroLayoutArquivoDTOs;
	}

	public void setCadastroLayoutArquivoDTOs(List<CadastroLayoutArquivoCamposDTO> cadastroLayoutArquivoDTOs) {
		this.cadastroLayoutArquivoDTOs = cadastroLayoutArquivoDTOs;
	}
	

}
