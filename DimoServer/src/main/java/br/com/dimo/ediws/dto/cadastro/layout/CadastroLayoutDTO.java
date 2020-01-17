package br.com.dimo.ediws.dto.cadastro.layout;

import java.util.ArrayList;
import java.util.List;

public class CadastroLayoutDTO {
	
//	public Layout layout;	
	public LayoutDTO layout;
	public List<CadastroLayoutArquivoDTO> cadastroLayoutArquivoDTOs = new  ArrayList<CadastroLayoutArquivoDTO>();	

	
	public CadastroLayoutDTO() {
				
	}

	
	public LayoutDTO getLayout() {
		return layout;
	}

	public void setLayout(LayoutDTO layout) {
		this.layout = layout;
	}

	public List<CadastroLayoutArquivoDTO> getCadastroLayoutArquivoDTOs() {
		return cadastroLayoutArquivoDTOs;
	}

	public void setCadastroLayoutArquivoDTOs(List<CadastroLayoutArquivoDTO> cadastroLayoutArquivoDTOs) {
		this.cadastroLayoutArquivoDTOs = cadastroLayoutArquivoDTOs;
	}
	

	
	
}
