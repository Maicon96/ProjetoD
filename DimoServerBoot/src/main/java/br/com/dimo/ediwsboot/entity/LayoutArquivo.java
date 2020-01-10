package br.com.dimo.ediwsboot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@IdClass(LayoutArquivoId.class)
@Table(name="T_IMPORT_ARQUIVO")
public class LayoutArquivo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequisicaoExecutorContextoGenerator")
    @SequenceGenerator(name = "RequisicaoExecutorContextoGenerator", sequenceName = "T_IMPORT_ARQUIVO_SEQUENCE_ID")
    @Column(name = "id")	
	private Long id;
	
	@Id
	@Column(name = "t_import_layout_id")
	private Long idLayout;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "t_import_layout_id", insertable=false, updatable=false)	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonBackReference
	private Layout layout;
	
	@Column(name = "identificador_linha")
	@JsonInclude(value = Include.NON_NULL)
	private String identificadorLinha;
	
	
//	
//	@OneToMany(fetch=FetchType.LAZY, mappedBy = "layoutArquivo")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@JsonInclude(value = Include.NON_NULL)
//	@JsonManagedReference
//	private List<LayoutArquivoCampo> campos;
//	
//	@OneToMany(fetch=FetchType.LAZY, mappedBy = "layoutArquivo")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@JsonInclude(value = Include.NON_NULL)
//	@JsonManagedReference
//	private List<LayoutArquivoDelimitacao> delimitacoes;
//	
//	@OneToMany(fetch=FetchType.LAZY, mappedBy = "layoutArquivo")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@JsonInclude(value = Include.NON_NULL)
//	@JsonManagedReference
//	private List<LayoutArquivoTabela> tabelas;	
//	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "layoutArquivo")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonManagedReference
	private List<LayoutArquivoCampoTabela> camposTabelas;
	
	
	public LayoutArquivo() {
		
	}
	

	public LayoutArquivo(Long idLayout, @NotNull String identificadorLinha) {		
		this.idLayout = idLayout;
		this.identificadorLinha = identificadorLinha;
	}
	
	
	public String getIdentificadorLinha() {
		return identificadorLinha;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(Long idLayout) {
		this.idLayout = idLayout;
	}

	public void setIdentificadorLinha(String identificadorLinha) {
		this.identificadorLinha = identificadorLinha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Layout getLayout() {
		return layout;
	}


	public void setLayout(Layout layout) {
		this.layout = layout;
	}


//	public List<LayoutArquivoCampo> getCampos() {
//		return campos;
//	}
//
//
//	public void setCampos(List<LayoutArquivoCampo> campos) {
//		this.campos = campos;
//	}
//
//
//	public List<LayoutArquivoDelimitacao> getDelimitacoes() {
//		return delimitacoes;
//	}
//
//
//	public void setDelimitacoes(List<LayoutArquivoDelimitacao> delimitacoes) {
//		this.delimitacoes = delimitacoes;
//	}
//
//
//	public List<LayoutArquivoTabela> getTabelas() {
//		return tabelas;
//	}
//
//
//	public void setTabelas(List<LayoutArquivoTabela> tabelas) {
//		this.tabelas = tabelas;
//	}
	
	public List<LayoutArquivoCampoTabela> getCamposTabelas() {
		return camposTabelas;
	}


	public void setCamposTabelas(List<LayoutArquivoCampoTabela> camposTabelas) {
		this.camposTabelas = camposTabelas;
	}

	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLayout == null) ? 0 : idLayout.hashCode());
		result = prime * result + ((identificadorLinha == null) ? 0 : identificadorLinha.hashCode());
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
		LayoutArquivo other = (LayoutArquivo) obj;
		if (idLayout == null) {
			if (other.idLayout != null)
				return false;
		} else if (!idLayout.equals(other.idLayout))
			return false;
		if (identificadorLinha == null) {
			if (other.identificadorLinha != null)
				return false;
		} else if (!identificadorLinha.equals(other.identificadorLinha))
			return false;
		return true;
	}
	
	

	
			
}
