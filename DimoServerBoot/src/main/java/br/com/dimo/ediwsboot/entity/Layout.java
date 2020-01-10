package br.com.dimo.ediwsboot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="T_IMPORT_LAYOUT")
public class Layout implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
		
	@NotNull
	@Column(name = "descricao")
	@JsonInclude(value = Include.NON_NULL)
	private String descricao;	
	
	@NotNull
	@Column(name = "padrao")
	@JsonInclude(value = Include.NON_NULL)
	private Integer padrao; // 1 - Sim   0 - Não	
	
	@Column(name = "linha")
	@JsonInclude(value = Include.NON_NULL)
	private Integer linha;
	
	@Column(name = "posicao_inicial")
	@JsonInclude(value = Include.NON_NULL)
	private Integer posicaoInicial;
	
	@Column(name = "posicao_final")
	@JsonInclude(value = Include.NON_NULL)
	private Integer posicaoFinal;	
	
	@Column(name = "delimitador")
	@JsonInclude(value = Include.NON_NULL)
	private String delimitador;
	
	@Column(name = "indexador")
	@JsonInclude(value = Include.NON_NULL)
	private String indexador;
	
	
	public Layout() {
		
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

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

			
}
