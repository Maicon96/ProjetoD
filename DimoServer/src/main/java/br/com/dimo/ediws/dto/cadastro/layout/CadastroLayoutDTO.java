package br.com.dimo.ediws.dto.cadastro.layout;

import java.util.ArrayList;
import java.util.List;

import br.com.dimo.ediws.entity.Layout;

public class CadastroLayoutDTO {
	
	public Layout layout;	
	public List<CadastroLayoutArquivoDTO> cadastroLayoutArquivoDTOs = new  ArrayList<CadastroLayoutArquivoDTO>();	

	
	public CadastroLayoutDTO() {
				
	}

	
	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public List<CadastroLayoutArquivoDTO> getCadastroLayoutArquivoDTOs() {
		return cadastroLayoutArquivoDTOs;
	}

	public void setCadastroLayoutArquivoDTOs(List<CadastroLayoutArquivoDTO> cadastroLayoutArquivoDTOs) {
		this.cadastroLayoutArquivoDTOs = cadastroLayoutArquivoDTOs;
	}
	

	
	
}
