package br.com.dimo.ediwsboot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(LayoutArquivoDelimitacaoId.class)
@Table(name="T_IMPORT_NOMETABELA")
public class LayoutArquivoTabela implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequisicaoExecutorContextoGenerator")
    @SequenceGenerator(name = "RequisicaoExecutorContextoGenerator", sequenceName = "T_IMPORT_NOMETABELA_SEQUENCE_ID")
    @Column(name = "id")
	private Long id;
	
	@Id
	@Column(name = "t_import_arquivo_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayoutArquivo;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "t_import_arquivo_id", insertable=false, updatable=false)	
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@JsonInclude(value = Include.NON_NULL)
//	@JsonBackReference
//	private LayoutArquivo layoutArquivo;
	
	@Id
	@Column(name = "t_import_arquivo_t_import_layout_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayout;
		
	@NotNull
	@Column(name = "nome_tabela")
	@JsonInclude(value = Include.NON_NULL)
	private String nomeTabela;
	
		
	public LayoutArquivoTabela() {
		
	}
	

	public LayoutArquivoTabela(Long idLayoutArquivo, Long idLayout, @NotNull String nomeTabela) {
		this.idLayoutArquivo = idLayoutArquivo;
		this.idLayout = idLayout;
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

//	public LayoutArquivo getLayoutArquivo() {
//		return layoutArquivo;
//	}
//
//	public void setLayoutArquivo(LayoutArquivo layoutArquivo) {
//		this.layoutArquivo = layoutArquivo;
//	}

	public Long getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(Long idLayout) {
		this.idLayout = idLayout;
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
