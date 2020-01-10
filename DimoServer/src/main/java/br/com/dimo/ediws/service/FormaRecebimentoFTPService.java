package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.entity.ClienteFormaRecebimentoFTP;
import br.com.dimo.ediws.entity.FormaRecebimentoFTP;
import br.com.dimo.ediws.repository.ClienteFormaRecebimentoFTPRepository;
import br.com.dimo.ediws.repository.FormaRecebimentoFTPRepository;

@Service
public class FormaRecebimentoFTPService {
	
	@Autowired
	FormaRecebimentoFTPRepository formaRecebimentoFTPRepository;
	
	@Autowired
	ClienteFormaRecebimentoFTPRepository clienteFormaRecebimentoFTPRepository;
	
	
	public List<CampoErroDTO> salvar(FormaRecebimentoFTP formaRecebimentoFTP) {			
						
		List<CampoErroDTO> erros = this.validaSalvar(formaRecebimentoFTP);
		if (erros.isEmpty()) {
			this.formaRecebimentoFTPRepository.save(formaRecebimentoFTP);
		}	
		
		if (formaRecebimentoFTP.getIdCliente() != null) {
			ClienteFormaRecebimentoFTP clienteFormaRecebimentoFTP = new ClienteFormaRecebimentoFTP(
					formaRecebimentoFTP.getIdCliente(), formaRecebimentoFTP.getId());
			
			this.clienteFormaRecebimentoFTPRepository.save(clienteFormaRecebimentoFTP);
		}
		
		return erros;
	}
	
	public List<CampoErroDTO> validaSalvar(FormaRecebimentoFTP formaRecebimentoFTP) {			
		
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		this.validaServidor(formaRecebimentoFTP, erros);
		this.validaUsuario(formaRecebimentoFTP, erros);
		this.validaSenha(formaRecebimentoFTP, erros);
		this.validaTLSSSL(formaRecebimentoFTP, erros);
		this.validaDiretorio(formaRecebimentoFTP, erros);

		return erros;
	}
	

	public void validaServidor(FormaRecebimentoFTP formaRecebimentoFTP, List<CampoErroDTO> erros) {
		if (formaRecebimentoFTP.getServidor() == null || formaRecebimentoFTP.getServidor() == "") {
			erros.add(new CampoErroDTO("servidor", "Servidor é campo obrigatório"));
		}
	}
	
	public void validaUsuario(FormaRecebimentoFTP formaRecebimentoFTP, List<CampoErroDTO> erros) {
		if (formaRecebimentoFTP.getUsuario() == "") {		
			erros.add(new CampoErroDTO("usuario", "Usuário é campo obrigatório"));
		}
	}
	
	public void validaSenha(FormaRecebimentoFTP formaRecebimentoFTP, List<CampoErroDTO> erros) {
		if (formaRecebimentoFTP.getSenha() == "") {		
			erros.add(new CampoErroDTO("senha", "Senha é campo obrigatório"));
		}
	}
	
	public void validaTLSSSL(FormaRecebimentoFTP formaRecebimentoFTP, List<CampoErroDTO> erros) {
		if (formaRecebimentoFTP.getTlsSsl() == "") {
			erros.add(new CampoErroDTO("tlsSsl", "TLS/SSL é campo obrigatório"));
		}
	}
	
	public void validaDiretorio(FormaRecebimentoFTP formaRecebimentoFTP, List<CampoErroDTO> erros) {
		if (formaRecebimentoFTP.getDiretorio() == "") {
			erros.add(new CampoErroDTO("diretorio", "Diretório é campo obrigatório"));
		}
	}
	
	
	public void deletar(List<FormaRecebimentoFTP> formaRecebimentoFTPs) {					
		for (FormaRecebimentoFTP formaRecebimentoFTP: formaRecebimentoFTPs) {
			
			ClienteFormaRecebimentoFTP clienteFormaRecebimentoFTP = this.clienteFormaRecebimentoFTPRepository
					.findByIdFormaRecebimentoFTP(formaRecebimentoFTP.getId());
			
			if (clienteFormaRecebimentoFTP != null) {		
				this.clienteFormaRecebimentoFTPRepository.delete(clienteFormaRecebimentoFTP);				
			}
			
			this.formaRecebimentoFTPRepository.delete(formaRecebimentoFTP);						
		}			
	}
		
	
}
