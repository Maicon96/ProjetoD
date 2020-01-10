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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(LayoutArquivoCampoId.class)
@Table(name="T_IMPORT_CAMPO")
public class LayoutArquivoCampo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequisicaoExecutorContextoGenerator")
    @SequenceGenerator(name = "RequisicaoExecutorContextoGenerator", sequenceName = "T_IMPORT_CAMPO_SEQUENCE_ID")
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
		
		
	@NotNull
	@Column(name = "nome_campo")
	@JsonInclude(value = Include.NON_NULL)
	private String nomeCampo;
	
	@NotNull
	@Column(name = "nome_campo_tabela")
	@JsonInclude(value = Include.NON_NULL)
	private String nomeCampoTabela;
	
	@Column(name = "json_depara")
	@JsonInclude(value = Include.NON_NULL)
	private String jsonDepara;
	
	
	public LayoutArquivoCampo() {
		
	}
	

	public LayoutArquivoCampo(Long idLayoutArquivo, Long idLayout, @NotNull String nomeCampo,
			@NotNull String nomeCampoTabela, String jsonDepara) {
		this.idLayoutArquivo = idLayoutArquivo;
		this.idLayout = idLayout;
		this.nomeCampo = nomeCampo;
		this.nomeCampoTabela = nomeCampoTabela;
		this.jsonDepara = jsonDepara;
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
	
	public LayoutArquivo getLayoutArquivo() {
		return layoutArquivo;
	}

	public void setLayoutArquivo(LayoutArquivo layoutArquivo) {
		this.layoutArquivo = layoutArquivo;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
			
}
