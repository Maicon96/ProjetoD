package br.com.dimo.ediws.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(LayoutArquivoDelimitacaoId.class)
@Table(name="T_IMPORT_DELIMITACAO")
public class LayoutArquivoDelimitacao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequisicaoExecutorContextoGenerator")
    @SequenceGenerator(name = "RequisicaoExecutorContextoGenerator", sequenceName = "T_IMPORT_DELIMITACAO_SEQUENCE_ID")
    @Column(name = "id")
	private Long id;
	
	@Id
	@Column(name = "t_import_arquivo_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayoutArquivo;
	
	@Id
	@Column(name = "t_import_arquivo_t_import_layout_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayout;
				
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
	
	
	public LayoutArquivoDelimitacao() {
		
	}

	public LayoutArquivoDelimitacao(Long idLayoutArquivo, Long idLayout, String caractere, String indexador,
			String posicaoInicial, String posicaoFinal) {
		this.idLayoutArquivo = idLayoutArquivo;
		this.idLayout = idLayout;
		this.caractere = caractere;
		this.indexador = indexador;
		this.posicaoInicial = posicaoInicial;
		this.posicaoFinal = posicaoFinal;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
			
			
}
