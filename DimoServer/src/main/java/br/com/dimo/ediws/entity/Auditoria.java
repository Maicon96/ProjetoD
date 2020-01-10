package br.com.dimo.ediws.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name="auditoria")
public class Auditoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Column(name = "login_usuario")
	@JsonInclude(value = Include.NON_NULL)
	private String loginUsuario;	
		
	@NotNull
	@Column(name="operacao")
	@JsonInclude(value = Include.NON_NULL)
	private String operacao;
	
	@NotNull
	@Column(name="request")
	@JsonInclude(value = Include.NON_NULL)
	private String request;
	
	@NotNull
	@Column(name="response")
	@JsonInclude(value = Include.NON_NULL)
	private String response;
	
	@NotNull
	@Column(name="status")
	@JsonInclude(value = Include.NON_NULL)
	private Integer status;
	
	@NotNull	
	@Column(name="data_hora")
	@JsonInclude(value = Include.NON_NULL)
	private LocalDateTime dataHora;

	
	public Auditoria() {
		
	}
	
	
	public Auditoria(@NotNull String loginUsuario, @NotNull String operacao,
			@NotNull String request, @NotNull String response, @NotNull Integer status,
			@NotNull LocalDateTime dataHora) {
		this.loginUsuario = loginUsuario;
		this.operacao = operacao;
		this.request = request;
		this.response = response;
		this.status = status;
		this.dataHora = dataHora;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}	
	
	
	
}
