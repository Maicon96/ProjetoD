package br.com.dimo.ediws.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(LayoutArquivoClienteId.class)
@Table(name="T_IMPORT_LAYOUT_CLIENTE")
public class LayoutArquivoCliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@Column(name = "t_cliente_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idCliente;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "t_cliente_id", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	private Cliente cliente;

	
	
	@Id
	@Column(name = "t_import_layout_id")
	@JsonInclude(value = Include.NON_NULL)
	private Long idLayout;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "t_import_layout_id", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	private Layout layout;
		
				
	@NotNull
	@Column(name = "data_inicio")
	@JsonInclude(value = Include.NON_NULL)
//	@JsonDeserialize(using = LocalDateDeserializer.class)
//	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dataInicio;
		
	
	
	@Column(name = "data_fim")
	@JsonInclude(value = Include.NON_NULL)
//	@JsonDeserialize(using = LocalDateDeserializer.class)
//	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dataFim;	
	
	
	
	public LayoutArquivoCliente() {
		
	}
	
	

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(Long idLayout) {
		this.idLayout = idLayout;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	
	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
			
}
