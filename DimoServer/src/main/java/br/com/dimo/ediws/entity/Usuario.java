package br.com.dimo.ediws.entity;

import java.io.Serializable;

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
@Table(name="usuarios")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	
	@NotNull
	@Column(name = "nome")
	@JsonInclude(value = Include.NON_NULL)
	private String nome;
		
	@NotNull
	@Column(name = "login")
	@JsonInclude(value = Include.NON_NULL)
	private String login;
		
	@NotNull
	@Column(name = "senha")
	@JsonInclude(value = Include.NON_NULL)
	private String senha;
	
	
	@NotNull
	@Column(name = "id_perfil")
	@JsonInclude(value = Include.NON_NULL)
	private Integer idPerfil;
		
		
	@Column(name = "avatar")
	@JsonInclude(value = Include.NON_NULL)
	private byte[] avatar;	
	
	
	public Usuario() {
		
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
		
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Integer getIdPerfil() {
		return idPerfil;
	}
	
	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
