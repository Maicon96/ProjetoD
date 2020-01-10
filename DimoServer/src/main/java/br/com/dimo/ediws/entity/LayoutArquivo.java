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
		
	@Column(name = "identificador_linha")
	@JsonInclude(value = Include.NON_NULL)
	private String identificadorLinha;
	
	
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

	
			
}
