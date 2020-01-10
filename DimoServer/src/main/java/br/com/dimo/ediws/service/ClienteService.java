package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.entity.Cliente;
import br.com.dimo.ediws.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public List<CampoErroDTO> salvar(Cliente cliente) {			
						
		List<CampoErroDTO> erros = this.validaSalvar(cliente);
		if (erros.isEmpty()) {
			this.clienteRepository.save(cliente);
		}	
		
		return erros;		
	}
	
	public List<CampoErroDTO> validaSalvar(Cliente cliente) {			
		
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		this.validaCPFCNPJ(cliente, erros);

		return erros;		
	}
	

	public void validaCPFCNPJ(Cliente cliente, List<CampoErroDTO> erros) {
		if (cliente.getCpfcnpj() == null || cliente.getCpfcnpj() == "") {
			erros.add(new CampoErroDTO("cpfcnpj", "CPF/CNPJ é campo obrigatório"));
		}
	}
		
}
