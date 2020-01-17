package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.entity.ClienteFormaRecebimentoPasta;
import br.com.dimo.ediws.entity.FormaRecebimentoPasta;
import br.com.dimo.ediws.repository.ClienteFormaRecebimentoPastaRepository;
import br.com.dimo.ediws.repository.FormaRecebimentoPastaRepository;

@Service
public class FormaRecebimentoPastaService {
	
	@Autowired
	FormaRecebimentoPastaRepository formaRecebimentoPastaRepository;
	
	@Autowired
	ClienteFormaRecebimentoPastaRepository clienteFormaRecebimentoPastaRepository;
	
	
	
	public List<CampoErroDTO> salvar(FormaRecebimentoPasta formaRecebimentoPasta) {		
							
		List<CampoErroDTO> erros = this.validaSalvar(formaRecebimentoPasta);
		if (erros.isEmpty()) {
			this.formaRecebimentoPastaRepository.save(formaRecebimentoPasta);
			
			if (formaRecebimentoPasta.getIdCliente() != null) {
				ClienteFormaRecebimentoPasta clienteFormaRecebimentoPasta = new ClienteFormaRecebimentoPasta(
						formaRecebimentoPasta.getIdCliente(), formaRecebimentoPasta.getId());
				
				this.clienteFormaRecebimentoPastaRepository.save(clienteFormaRecebimentoPasta);
			}
		}		
		
		return erros;		
	}
	
	public List<CampoErroDTO> validaSalvar(FormaRecebimentoPasta formaRecebimentoPasta) {			
		
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		this.validaServidor(formaRecebimentoPasta, erros);
		this.validaUsuario(formaRecebimentoPasta, erros);
		this.validaSenha(formaRecebimentoPasta, erros);
		this.validaDiretorio(formaRecebimentoPasta, erros);

		return erros;
	}
	

	public void validaServidor(FormaRecebimentoPasta formaRecebimentoPasta, List<CampoErroDTO> erros) {
		if (formaRecebimentoPasta.getServidor() == null || formaRecebimentoPasta.getServidor() == "") {
			erros.add(new CampoErroDTO("servidor", "Servidor é campo obrigatório"));
		}
	}
	
	public void validaUsuario(FormaRecebimentoPasta formaRecebimentoPasta, List<CampoErroDTO> erros) {
		if (formaRecebimentoPasta.getUsuario() == "") {		
			erros.add(new CampoErroDTO("usuario", "Usuário é campo obrigatório"));
		}
	}
	
	public void validaSenha(FormaRecebimentoPasta formaRecebimentoPasta, List<CampoErroDTO> erros) {
		if (formaRecebimentoPasta.getSenha() == "") {		
			erros.add(new CampoErroDTO("senha", "Senha é campo obrigatório"));
		}
	}
	
	public void validaDiretorio(FormaRecebimentoPasta formaRecebimentoPasta, List<CampoErroDTO> erros) {
		if (formaRecebimentoPasta.getCaminho() == "") {		
			erros.add(new CampoErroDTO("caminho", "Diretório é campo obrigatório"));
		}
	}
	
	
	public void deletar(List<FormaRecebimentoPasta> formaRecebimentoPastas) {					
		for (FormaRecebimentoPasta formaRecebimentoPasta: formaRecebimentoPastas) {
			
			ClienteFormaRecebimentoPasta clienteFormaRecebimentoPasta = this.clienteFormaRecebimentoPastaRepository
					.findByIdFormaRecebimentoPasta(formaRecebimentoPasta.getId());
			
			if (clienteFormaRecebimentoPasta != null) {		
				this.clienteFormaRecebimentoPastaRepository.delete(clienteFormaRecebimentoPasta);				
			}
			
			this.formaRecebimentoPastaRepository.delete(formaRecebimentoPasta);						
		}			
	}
	
	
}
