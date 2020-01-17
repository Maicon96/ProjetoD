package br.com.dimo.ediws.dto.cadastro.layout;

public class LayoutDTO {
	
	Long id;	
	String descricao;
	Integer padrao;		
	Integer identificadorLinha;	
	Integer tipoDelimitador;	
	Integer posicaoInicial;	
	Integer posicaoFinal;		
	String delimitador;	
	String indexador;
	String cpfEmpresa;
	Boolean cabecalho;
	
	
	public LayoutDTO() {
				
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getPadrao() {
		return padrao;
	}

	public void setPadrao(Integer padrao) {
		this.padrao = padrao;
	}
	
	public Integer getIdentificadorLinha() {
		return identificadorLinha;
	}

	public void setIdentificadorLinha(Integer identificadorLinha) {
		this.identificadorLinha = identificadorLinha;
	}

	public Integer getTipoDelimitador() {
		return tipoDelimitador;
	}

	public void setTipoDelimitador(Integer tipoDelimitador) {
		this.tipoDelimitador = tipoDelimitador;
	}

	public Integer getPosicaoInicial() {
		return posicaoInicial;
	}

	public void setPosicaoInicial(Integer posicaoInicial) {
		this.posicaoInicial = posicaoInicial;
	}

	public Integer getPosicaoFinal() {
		return posicaoFinal;
	}

	public void setPosicaoFinal(Integer posicaoFinal) {
		this.posicaoFinal = posicaoFinal;
	}

	public String getDelimitador() {
		return delimitador;
	}

	public void setDelimitador(String delimitador) {
		this.delimitador = delimitador;
	}

	public String getIndexador() {
		return indexador;
	}

	public void setIndexador(String indexador) {
		this.indexador = indexador;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Boolean getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(Boolean cabecalho) {
		this.cabecalho = cabecalho;
	}
	
	
	
	

}
