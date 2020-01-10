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
@Table(name="T_IMPORT_EMAIL")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormaRecebimentoEmail implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonInclude(value = Include.NON_NULL)
	private Long id;
	
	@JsonInclude(value = Include.NON_NULL)
	@Transient		
	private Long idCliente;	
	
	@NotNull
	@Column(name = "pop3")
	@JsonInclude(value = Include.NON_NULL)
	private String pop3;
	
	@NotNull
	@Column(name = "usuario")
	@JsonInclude(value = Include.NON_NULL)
	private String usuario;
	
	@NotNull
	@Column(name = "senha")
	@JsonInclude(value = Include.NON_NULL)
	private String senha;
	
	@NotNull
	@Column(name = "porta")
	@JsonInclude(value = Include.NON_NULL)
	private String porta;
	
	@NotNull
	@Column(name = "tls_ssl")
	@JsonInclude(value = Include.NON_NULL)
	private String tlsSsl;
		
	
	public FormaRecebimentoEmail() {
		
	}
	
	public FormaRecebimentoEmail(Long id, @NotNull String pop3, @NotNull String usuario, @NotNull String senha,
			@NotNull String porta, @NotNull String tlsSsl) {
		super();
		this.id = id;
		this.pop3 = pop3;
		this.usuario = usuario;
		this.senha = senha;
		this.porta = porta;
		this.tlsSsl = tlsSsl;
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

	public String getPop3() {
		return pop3;
	}

	public void setPop3(String pop3) {
		this.pop3 = pop3;
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

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}	

	public String getTlsSsl() {
		return tlsSsl;
	}

	public void setTlsSsl(String tlsSsl) {
		this.tlsSsl = tlsSsl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pop3 == null) ? 0 : pop3.hashCode());
		result = prime * result + ((porta == null) ? 0 : porta.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((tlsSsl == null) ? 0 : tlsSsl.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormaRecebimentoEmail other = (FormaRecebimentoEmail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pop3 == null) {
			if (other.pop3 != null)
				return false;
		} else if (!pop3.equals(other.pop3))
			return false;
		if (porta == null) {
			if (other.porta != null)
				return false;
		} else if (!porta.equals(other.porta))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (tlsSsl == null) {
			if (other.tlsSsl != null)
				return false;
		} else if (!tlsSsl.equals(other.tlsSsl))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	
			
			
}
