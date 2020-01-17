package br.com.dimo.ediws.dto;

import java.util.ArrayList;
import java.util.List;

public class TabelaDTO {	
	private String nomeTabela;
	private List<TabelaCampoDTO> campos = new ArrayList<TabelaCampoDTO>();
	
	
	public String getNomeTabela() {
		return nomeTabela.toLowerCase();
	}
	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	public List<TabelaCampoDTO> getCampos() {
		return campos;
	}
	public void setCampos(List<TabelaCampoDTO> campos) {
		this.campos = campos;
	}	
	
	
	

}