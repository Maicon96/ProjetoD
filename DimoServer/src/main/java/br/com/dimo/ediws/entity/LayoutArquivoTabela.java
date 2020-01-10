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
import javax.validation.constraints.NotNull;

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
