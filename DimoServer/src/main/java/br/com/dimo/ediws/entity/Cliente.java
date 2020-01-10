package br.com.dimo.ediws.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.dimo.ediws.util.Mascaras;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="T_CLIENTE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
		
	@NotNull
	@Column(name = "cpfcnpj")
	@JsonInclude(value = Include.NON_NULL)
	private String cpfcnpj;
	
	//@NotNull
	@JsonInclude(value = Include.NON_NULL)
	@Transient
	private String nome;
	
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "cliente")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonManagedReference
	private List<ClienteFormaRecebimentoEmail> formasRecebimentoEmails;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "cliente")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonManagedReference
	private List<ClienteFormaRecebimentoPasta> formasRecebimentoPastas;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "cliente")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(value = Include.NON_NULL)
	@JsonManagedReference
	private List<ClienteFormaRecebimentoFTP> formasRecebimentoFTPs;
		
	
			
	public Cliente() {
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}
	
	public String getCpfcnpjFormat() {				
		if (cpfcnpj.length() == 11) {
			cpfcnpj = Mascaras.mascaraCPF(cpfcnpj);
		} else if (cpfcnpj.length() == 15) {
			cpfcnpj = Mascaras.mascaraCNPJ(cpfcnpj);
		}
		
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<ClienteFormaRecebimentoEmail> getFormasRecebimentoEmails() {
		return formasRecebimentoEmails;
	}

	public void setFormasRecebimentoEmails(List<ClienteFormaRecebimentoEmail> formasRecebimentoEmails) {
		this.formasRecebimentoEmails = formasRecebimentoEmails;
	}

	public List<ClienteFormaRecebimentoPasta> getFormasRecebimentoPastas() {
		return formasRecebimentoPastas;
	}

	public void setFormasRecebimentoPastas(List<ClienteFormaRecebimentoPasta> formasRecebimentoPastas) {
		this.formasRecebimentoPastas = formasRecebimentoPastas;
	}

	public List<ClienteFormaRecebimentoFTP> getFormasRecebimentoFTPs() {
		return formasRecebimentoFTPs;
	}

	public void setFormasRecebimentoFTPs(List<ClienteFormaRecebimentoFTP> formasRecebimentoFTPs) {
		this.formasRecebimentoFTPs = formasRecebimentoFTPs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpfcnpj == null) ? 0 : cpfcnpj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cpfcnpj == null) {
			if (other.cpfcnpj != null)
				return false;
		} else if (!cpfcnpj.equals(other.cpfcnpj))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
	
	
}
