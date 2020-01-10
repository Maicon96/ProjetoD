package br.com.dimo.ediwsboot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="T_IMPORT_PASTA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormaRecebimentoPasta implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonInclude(value = Include.NON_NULL)
	private Long id;
	
		
	@JsonInclude(value = Include.NON_NULL)
	@Transient
	private Long idCliente;
	
	@NotNull
	@Column(name = "servidor")
	@JsonInclude(value = Include.NON_NULL)
	private String servidor;
		
	@Column(name = "usuario")
	@JsonInclude(value = Include.NON_NULL)
	private String usuario;
		
	@Column(name = "senha")
	@JsonInclude(value = Include.NON_NULL)
	private String senha;
	
	@NotNull
	@Column(name = "caminho")
	@JsonInclude(value = Include.NON_NULL)
	private String caminho;
		
		
	public FormaRecebimentoPasta() {
		
	}	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
		
			
}
