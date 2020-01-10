package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.entity.Auditoria;
import br.com.dimo.ediws.repository.AuditoriaRepository;

@Service
public class AuditoriaService {
	
	@Autowired
	AuditoriaRepository auditoriaRepository;
	
	
	public List<CampoErroDTO> salvar(Auditoria auditoria) {			
		
		List<CampoErroDTO> erros = this.validaSalvar(auditoria);

		if (erros.isEmpty()) {
			this.auditoriaRepository.save(auditoria);			
		}	
		
		return erros;
		
	}
	
	public List<CampoErroDTO> validaSalvar(Auditoria adesao) {			

		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

//		this.validaEmpresa(adesao, erros);		
//		this.validaServico(adesao, erros);
//		this.validaCliente(adesao, erros);
//		this.validaDataInicio(adesao, erros);
//		this.validaValor(adesao, erros);
//		this.validaTpoValor(adesao, erros);
//		this.validaDiaCobranca(adesao, erros);
//		this.validaDataFim(adesao, erros);

		return erros;		
	}
	
//	public void validaEmpresa(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getIdEmpresa() == null) {		
//			erros.add(new CampoErroDTO("idEmpresa", "Empresa é campo obrigatório"));
//		} else {					
////			if (this.existsByNome(cliente.getNome())) {
////				erros.add(new CampoErroDTO("nome", "Nome Já cadastrado para outro cliente"));
////			}			
//		}
//	}
//	
//	public void validaServico(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getIdServico() == null) {		
//			erros.add(new CampoErroDTO("idServico", "Serviço é campo obrigatório"));
//		} else {					
////			if (this.existsByNome(cliente.getNome())) {
////				erros.add(new CampoErroDTO("nome", "Nome Já cadastrado para outro cliente"));
////			}			
//		}
//	}
//	
//	public void validaCliente(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getIdCliente() == null) {		
//			erros.add(new CampoErroDTO("idCliente", "Cliente é campo obrigatório"));
//		} else {					
////			if (this.existsByNome(cliente.getNome())) {
////				erros.add(new CampoErroDTO("nome", "Nome Já cadastrado para outro cliente"));
////			}			
//		}
//	}
//	
//	public void validaDataInicio(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getDataInicio() == null) {		
//			erros.add(new CampoErroDTO("dataInicio", "Data Início é campo obrigatório"));
//		}
//	}
//	
//	public void validaValor(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getValor() == null) {		
//			erros.add(new CampoErroDTO("valor", "Valor é campo obrigatório"));
//		}
//	}
//	
//	public void validaTpoValor(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getTipoValor() == null) {		
//			erros.add(new CampoErroDTO("tipoValor", "Tipo do Valor é campo obrigatório"));
//		}
//	}
//	
//	public void validaDiaCobranca(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getDataCobranca() == null) {		
//			erros.add(new CampoErroDTO("dataCobranca", "Dia de cobrança é campo obrigatório"));
//		}
//	}
//	
//	public void validaDataFim(Auditoria adesao, List<CampoErroDTO> erros) {
//		if (adesao.getDataFim() == null) {		
//			erros.add(new CampoErroDTO("dataFim", "Data Fim é campo obrigatório"));
//		}
//	}
	
}
