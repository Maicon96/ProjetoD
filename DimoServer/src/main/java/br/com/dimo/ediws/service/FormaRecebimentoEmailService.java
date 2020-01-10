package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.entity.ClienteFormaRecebimentoEmail;
import br.com.dimo.ediws.entity.FormaRecebimentoEmail;
import br.com.dimo.ediws.repository.ClienteFormaRecebimentoEmailRepository;
import br.com.dimo.ediws.repository.FormaRecebimentoEmailRepository;

@Service
public class FormaRecebimentoEmailService {
	
	@Autowired
	FormaRecebimentoEmailRepository formaRecebimentoEmailRepository;
	
	@Autowired
	ClienteFormaRecebimentoEmailRepository clienteFormaRecebimentoEmailRepository;
	
	
	public List<CampoErroDTO> salvar(FormaRecebimentoEmail formaRecebimentoEmail) {			
						
		List<CampoErroDTO> erros = this.validaSalvar(formaRecebimentoEmail);
		if (erros.isEmpty()) {
			this.formaRecebimentoEmailRepository.save(formaRecebimentoEmail);
		}	
		
		if (formaRecebimentoEmail.getIdCliente() != null) {
			ClienteFormaRecebimentoEmail clienteFormaRecebimentoEmail = new ClienteFormaRecebimentoEmail(
					formaRecebimentoEmail.getIdCliente(), formaRecebimentoEmail.getId());
			
			this.clienteFormaRecebimentoEmailRepository.save(clienteFormaRecebimentoEmail);
		}
		
		return erros;
	}
	
	public List<CampoErroDTO> validaSalvar(FormaRecebimentoEmail formaRecebimentoEmail) {			
		
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		this.validaPOP3(formaRecebimentoEmail, erros);
		this.validaUsuario(formaRecebimentoEmail, erros);
		this.validaSenha(formaRecebimentoEmail, erros);
		this.validaPorta(formaRecebimentoEmail, erros);
		this.validaTLSSSL(formaRecebimentoEmail, erros);

		return erros;
	}
	

	public void validaPOP3(FormaRecebimentoEmail formaRecebimentoEmail, List<CampoErroDTO> erros) {
		if (formaRecebimentoEmail.getPop3() == null || formaRecebimentoEmail.getPop3() == "") {
			erros.add(new CampoErroDTO("servidor", "Servidor é campo obrigatório"));
		}
	}
	
	public void validaUsuario(FormaRecebimentoEmail formaRecebimentoEmail, List<CampoErroDTO> erros) {
		if (formaRecebimentoEmail.getUsuario() == "") {		
			erros.add(new CampoErroDTO("usuario", "Usuário é campo obrigatório"));
		}
	}
	
	public void validaSenha(FormaRecebimentoEmail formaRecebimentoEmail, List<CampoErroDTO> erros) {
		if (formaRecebimentoEmail.getSenha() == "") {		
			erros.add(new CampoErroDTO("senha", "Senha é campo obrigatório"));
		}
	}
	
	public void validaPorta(FormaRecebimentoEmail formaRecebimentoEmail, List<CampoErroDTO> erros) {
		if (formaRecebimentoEmail.getPorta() == "") {		
			erros.add(new CampoErroDTO("porta", "Porta é campo obrigatório"));
		}
	}
	
	public void validaTLSSSL(FormaRecebimentoEmail formaRecebimentoEmail, List<CampoErroDTO> erros) {
		if (formaRecebimentoEmail.getTlsSsl() == "") {		
			erros.add(new CampoErroDTO("tlsSsl", "TLS/SSL é campo obrigatório"));
		}
	}	
	
	public void deletar(List<FormaRecebimentoEmail> formaRecebimentoEmails) {					
		for (FormaRecebimentoEmail formaRecebimentoEmail: formaRecebimentoEmails) {
			
			ClienteFormaRecebimentoEmail clienteFormaRecebimentoEmail = this.clienteFormaRecebimentoEmailRepository
					.findByIdFormaRecebimentoEmail(formaRecebimentoEmail.getId());
			
			if (clienteFormaRecebimentoEmail != null) {
				this.clienteFormaRecebimentoEmailRepository.delete(clienteFormaRecebimentoEmail);
			}
			
			this.formaRecebimentoEmailRepository.delete(formaRecebimentoEmail);
		}			
	}
	
}
