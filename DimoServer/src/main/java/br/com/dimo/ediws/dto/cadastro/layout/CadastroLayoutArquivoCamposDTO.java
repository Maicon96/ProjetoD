package br.com.dimo.ediws.dto.cadastro.layout;

public class CadastroLayoutArquivoCamposDTO {
	
	Long id;
	Long idLayoutArquivo;
	Long idLayout;
	String indexador;
	String posicaoInicial;
	String posicaoFinal;
	String nomeCampo;
	String nomeCampoTabela;
	String jsonDepara;
	String nomeTabela;
	
	public CadastroLayoutArquivoCamposDTO() {
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdLayoutArquivo() {
		return idLayoutArquivo;
	}

	public void setIdLayoutArquivo(Long idLayoutArquivo) {
		this.idLayoutArquivo = idLayoutArquivo;
	}

	public Long getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(Long idLayout) {
		this.idLayout = idLayout;
	}

	public String getIndexador() {
		return indexador;
	}

	public void setIndexador(String indexador) {
		this.indexador = indexador;
	}

	public String getPosicaoInicial() {
		return posicaoInicial;
	}

	public void setPosicaoInicial(String posicaoInicial) {
		this.posicaoInicial = posicaoInicial;
	}

	public String getPosicaoFinal() {
		return posicaoFinal;
	}

	public void setPosicaoFinal(String posicaoFinal) {
		this.posicaoFinal = posicaoFinal;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getNomeCampoTabela() {
		return nomeCampoTabela;
	}

	public void setNomeCampoTabela(String nomeCampoTabela) {
		this.nomeCampoTabela = nomeCampoTabela;
	}

	public String getJsonDepara() {
		return jsonDepara;
	}

	public void setJsonDepara(String jsonDepara) {
		this.jsonDepara = jsonDepara;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	

}
