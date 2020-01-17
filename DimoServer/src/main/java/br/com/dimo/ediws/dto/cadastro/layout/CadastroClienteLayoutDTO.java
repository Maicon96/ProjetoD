package br.com.dimo.ediws.dto.cadastro.layout;

import java.time.LocalDate;

import br.com.dimo.ediws.entity.Cliente;

public class CadastroClienteLayoutDTO {
	
	private Long idCliente;
	private Cliente cliente;
	private Long idLayout;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
	public CadastroClienteLayoutDTO() {
				
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

	
}
