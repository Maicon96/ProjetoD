package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroClienteLayoutDTO;
import br.com.dimo.ediws.entity.LayoutArquivoCliente;
import br.com.dimo.ediws.repository.LayoutArquivoClienteRepository;

@Service
public class LayoutArquivoClienteService {
	
	@Autowired
	private LayoutArquivoClienteRepository layoutArquivoClienteRepository;
			
	@Autowired
	private ClienteService clienteService;
	
	
	
	public List<CampoErroDTO> salvar(LayoutArquivoCliente layoutArquivoCliente) {			
						
		List<CampoErroDTO> erros = this.validaSalvar(layoutArquivoCliente);
		if (erros.isEmpty()) {
			this.layoutArquivoClienteRepository.save(layoutArquivoCliente);
		}	
		
		return erros;
	}
	
	public List<CampoErroDTO> salvarLayoutCliente(CadastroClienteLayoutDTO cadastroClienteLayoutDTO) {		
				
		LayoutArquivoCliente layoutArquivoCliente = new LayoutArquivoCliente(cadastroClienteLayoutDTO.getIdCliente(), 
				cadastroClienteLayoutDTO.getIdLayout(),	cadastroClienteLayoutDTO.getDataInicio(), 
				cadastroClienteLayoutDTO.getDataFim());
		
		List<CampoErroDTO> errors = this.clienteService.salvar(cadastroClienteLayoutDTO.getCliente());		
			
		if (errors.isEmpty()) {		
			layoutArquivoCliente.setIdCliente(cadastroClienteLayoutDTO.getCliente().getId());
			
			errors = this.validaSalvar(layoutArquivoCliente);
			if (errors.isEmpty()) {
				this.layoutArquivoClienteRepository.save(layoutArquivoCliente);
			}
		}
		
		return errors;
	}
	
	public List<CampoErroDTO> validaSalvar(LayoutArquivoCliente layoutArquivoCliente) {			
		
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		this.validaLayout(layoutArquivoCliente, erros);
		this.validaCliente(layoutArquivoCliente, erros);
		this.validaInicio(layoutArquivoCliente, erros);
		this.validaFim(layoutArquivoCliente, erros);

		return erros;		
	}
	

	public void validaLayout(LayoutArquivoCliente layoutArquivoCliente, List<CampoErroDTO> erros) {
		if (layoutArquivoCliente.getIdLayout() == null) {
			erros.add(new CampoErroDTO("idLayout", "Layout é campo obrigatório"));
//		} else {
//			
//			Long idLayout = this.layoutArquivoClienteRepository.existsByIdLayout(layoutArquivoCliente.getIdLayout());
//			
//			if (idLayout != null) {
//				erros.add(new CampoErroDTO("idLayout", "Login já cadastrado para essa empresa. Escolha outro!"));	
//			}
//			
		}
	}
	
	public void validaCliente(LayoutArquivoCliente layoutArquivoCliente, List<CampoErroDTO> erros) {
		if (layoutArquivoCliente.getIdCliente() == null) {
			erros.add(new CampoErroDTO("idCliente", "Cliente é campo obrigatório"));
		}
	}
	
	public void validaInicio(LayoutArquivoCliente layoutArquivoCliente, List<CampoErroDTO> erros) {
		if (layoutArquivoCliente.getDataInicio() == null) {
			erros.add(new CampoErroDTO("dataInicio", "Data Início é campo obrigatório"));
		}
	}	
	
	public void validaFim(LayoutArquivoCliente layoutArquivoCliente, List<CampoErroDTO> erros) {
		if (layoutArquivoCliente.getDataFim() != null) {						
			if (layoutArquivoCliente.getDataFim().compareTo(layoutArquivoCliente.getDataInicio()) < 0) {
				erros.add(new CampoErroDTO("dataFim", "Data Fim é menor que a data inicial"));
			}
		}
	}	
	
	
}
