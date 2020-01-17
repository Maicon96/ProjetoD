package br.com.dimo.ediws.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	@Column(name = "identificador_linha")
	@JsonInclude(value = Include.NON_NULL)
	private Integer identificadorLinha;
	
	@Column(name = "tipo_delimitador")
	@JsonInclude(value = Include.NON_NULL)
	private Integer tipoDelimitador;
	
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
	
	@NotNull
	@Column(name = "cpf_empresa")
	@JsonInclude(value = Include.NON_NULL)
	private String cpfEmpresa;

	@Column(name = "cabecalho")
	@JsonInclude(value = Include.NON_NULL)
	private Boolean cabecalho;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "layout")	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)	
	@JsonManagedReference
	private List<LayoutArquivo> linhas;
	
	
	public Layout() {
		
	}
		
	public Layout(Long id, @NotNull String descricao, @NotNull Integer padrao, Integer identificadorLinha, 
			Integer tipoDelimitador, Integer posicaoInicial,
			Integer posicaoFinal, String delimitador, String indexador, 
			@NotNull String cpfEmpresa, Boolean cabecalho) {		
		this.id = id;
		this.descricao = descricao;
		this.padrao = padrao;
		this.identificadorLinha = identificadorLinha;
		this.tipoDelimitador = tipoDelimitador;
		this.posicaoInicial = posicaoInicial;
		this.posicaoFinal = posicaoFinal;
		this.delimitador = delimitador;
		this.indexador = indexador;
		this.cpfEmpresa = cpfEmpresa;
		this.cabecalho = cabecalho;
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
		
	public String getCpfEmpresa() {
		return cpfEmpresa;
	}

	public void setCpfEmpresa(String cpfEmpresa) {
		this.cpfEmpresa = cpfEmpresa;
	}

	public List<LayoutArquivo> getLinhas() {
		return linhas;		
//		return new ArrayList<LayoutArquivo>();
	}

	public void setLinhas(List<LayoutArquivo> linhas) {
		this.linhas = linhas;
	}

	public Boolean getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(Boolean cabecalho) {
		this.cabecalho = cabecalho;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cabecalho == null) ? 0 : cabecalho.hashCode());
		result = prime * result + ((delimitador == null) ? 0 : delimitador.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((identificadorLinha == null) ? 0 : identificadorLinha.hashCode());
		result = prime * result + ((indexador == null) ? 0 : indexador.hashCode());
		result = prime * result + ((linhas == null) ? 0 : linhas.hashCode());
		result = prime * result + ((cpfEmpresa == null) ? 0 : cpfEmpresa.hashCode());
		result = prime * result + ((padrao == null) ? 0 : padrao.hashCode());
		result = prime * result + ((posicaoFinal == null) ? 0 : posicaoFinal.hashCode());
		result = prime * result + ((posicaoInicial == null) ? 0 : posicaoInicial.hashCode());
		result = prime * result + ((tipoDelimitador == null) ? 0 : tipoDelimitador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Layout other = (Layout) obj;
		if (cabecalho == null) {
			if (other.cabecalho != null)
				return false;
		} else if (!cabecalho.equals(other.cabecalho))
			return false;
		if (delimitador == null) {
			if (other.delimitador != null)
				return false;
		} else if (!delimitador.equals(other.delimitador))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identificadorLinha == null) {
			if (other.identificadorLinha != null)
				return false;
		} else if (!identificadorLinha.equals(other.identificadorLinha))
			return false;
		if (indexador == null) {
			if (other.indexador != null)
				return false;
		} else if (!indexador.equals(other.indexador))
			return false;
		if (linhas == null) {
			if (other.linhas != null)
				return false;
		} else if (!linhas.equals(other.linhas))
			return false;
		if (cpfEmpresa == null) {
			if (other.cpfEmpresa != null)
				return false;
		} else if (!cpfEmpresa.equals(other.cpfEmpresa))
			return false;
		if (padrao == null) {
			if (other.padrao != null)
				return false;
		} else if (!padrao.equals(other.padrao))
			return false;
		if (posicaoFinal == null) {
			if (other.posicaoFinal != null)
				return false;
		} else if (!posicaoFinal.equals(other.posicaoFinal))
			return false;
		if (posicaoInicial == null) {
			if (other.posicaoInicial != null)
				return false;
		} else if (!posicaoInicial.equals(other.posicaoInicial))
			return false;
		if (tipoDelimitador == null) {
			if (other.tipoDelimitador != null)
				return false;
		} else if (!tipoDelimitador.equals(other.tipoDelimitador))
			return false;
		return true;
	}

	
	
}
