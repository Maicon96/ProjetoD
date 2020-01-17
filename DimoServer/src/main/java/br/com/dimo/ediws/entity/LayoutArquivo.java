package br.com.dimo.ediws.entity;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@IdClass(LayoutArquivoId.class)
@Table(name="T_IMPORT_ARQUIVO")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
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
	@JsonBackReference(value="linhas")
	private Layout layout;	
		
		
	@Column(name = "identificador_linha")
	@JsonInclude(value = Include.NON_NULL)
	private Integer identificadorLinha;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "layoutArquivo")	
	@JsonIgnore
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonManagedReference
	private List<LayoutArquivoCampoTabela> layoutArquivoCampoTabelas;
//	
	
	public LayoutArquivo() {
		
	}	

	public LayoutArquivo(Long id, Long idLayout, @NotNull Integer identificadorLinha) {		
		this.id = id;
		this.idLayout = idLayout;
		this.identificadorLinha = identificadorLinha;
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

	public Integer getIdentificadorLinha() {
		return identificadorLinha;
	}	
	
	public void setIdentificadorLinha(Integer identificadorLinha) {
		this.identificadorLinha = identificadorLinha;
	}	

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public List<LayoutArquivoCampoTabela> getLayoutArquivoCampoTabelas() {
		return layoutArquivoCampoTabelas;		
	}

	public void setLayoutArquivoCampoTabelas(List<LayoutArquivoCampoTabela> layoutArquivoCampoTabelas) {
		this.layoutArquivoCampoTabelas = layoutArquivoCampoTabelas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
			
}
