package br.com.dimo.ediws.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(LayoutArquivoCampoId.class)
@Table(name="T_IMPORT_CAMPO_TABELA")
public class LayoutArquivoCampoTabela implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequisicaoExecutorContextoGenerator")
    @SequenceGenerator(name = "RequisicaoExecutorContextoGenerator", sequenceName = "T_IMPORT_CAMPO_TESTE_SEQUENCE_ID")
    @Column(name = "id")	
	private Long id;
	
	@Id
	@Column(name = "t_import_arquivo_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayoutArquivo;
	
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumns(value ={
			@JoinColumn(name = "t_import_arquivo_t_import_layout_id", referencedColumnName = "t_import_layout_id", insertable=false, updatable=false),
			@JoinColumn(name = "t_import_arquivo_id", referencedColumnName = "id", insertable=false, updatable=false) 
	})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonBackReference
	private LayoutArquivo layoutArquivo;
	
	
	@Id
	@Column(name = "t_import_arquivo_t_import_layout_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayout;		
		
	@Column(name = "nome_campo")
	@JsonInclude(value = Include.NON_NULL)
	private String nomeCampo;
	
	@Column(name = "nome_campo_tabela")
	@JsonInclude(value = Include.NON_NULL)
	private String nomeCampoTabela;
	
	@Column(name = "json_depara")
	@JsonInclude(value = Include.NON_NULL)
	private String jsonDepara;
	
	@Column(name = "caractere")
	@JsonInclude(value = Include.NON_NULL)
	private String caractere;
	
	@Column(name = "indexador")
	@JsonInclude(value = Include.NON_NULL)
	private String indexador;
	
	@Column(name = "posicao_inicial")
	@JsonInclude(value = Include.NON_NULL)
	private String posicaoInicial;
	
	@Column(name = "posicao_final")
	@JsonInclude(value = Include.NON_NULL)
	private String posicaoFinal;		
	
	@Column(name = "nome_tabela")
	@JsonInclude(value = Include.NON_NULL)
	private String nomeTabela;
	
		
	public LayoutArquivoCampoTabela() {
		
	}

	public LayoutArquivoCampoTabela(Long id, Long idLayoutArquivo, Long idLayout,
		    String nomeCampo, String nomeCampoTabela, String jsonDepara, String caractere,
			String indexador, String posicaoInicial, String posicaoFinal, String nomeTabela) {
		this.id = id;		
		this.idLayoutArquivo = idLayoutArquivo;		
		this.idLayout = idLayout;
		this.nomeCampo = nomeCampo;
		this.nomeCampoTabela = nomeCampoTabela;
		this.jsonDepara = jsonDepara;
		this.caractere = caractere;
		this.indexador = indexador;
		this.posicaoInicial = posicaoInicial;
		this.posicaoFinal = posicaoFinal;
		this.nomeTabela = nomeTabela;
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

	public LayoutArquivo getLayoutArquivo() {
		return layoutArquivo;
	}

	public void setLayoutArquivo(LayoutArquivo layoutArquivo) {
		this.layoutArquivo = layoutArquivo;
	}

	public Long getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(Long idLayout) {
		this.idLayout = idLayout;
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

	public String getCaractere() {
		return caractere;
	}

	public void setCaractere(String caractere) {
		this.caractere = caractere;
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

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
			
}
