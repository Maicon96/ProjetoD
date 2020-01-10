package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.entity.LayoutArquivo;
import br.com.dimo.ediws.repository.LayoutArquivoRepository;

@Service
public class LayoutArquivoService {
	
	@Autowired
	LayoutArquivoRepository layoutArquivoRepository;
	
	
	
	public List<CampoErroDTO> salvar(LayoutArquivo layoutArquivo) {			
		
		List<CampoErroDTO> erros = this.validaSalvar(layoutArquivo);

		if (erros.isEmpty()) {
			this.layoutArquivoRepository.save(layoutArquivo);
		}	
		
		return erros;
		
	}	
	
	
	public List<CampoErroDTO> validaSalvar(LayoutArquivo layoutArquivo) {			

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		return erros;
	}
	
	
}
	