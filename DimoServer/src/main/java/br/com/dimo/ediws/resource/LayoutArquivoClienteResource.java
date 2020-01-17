package br.com.dimo.ediws.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.dto.LoadResponseDTO;
import br.com.dimo.ediws.dto.ResponseDTO;
import br.com.dimo.ediws.dto.cadastro.layout.CadastroClienteLayoutDTO;
import br.com.dimo.ediws.entity.LayoutArquivoCliente;
import br.com.dimo.ediws.repository.LayoutArquivoClienteRepository;
import br.com.dimo.ediws.service.ClienteService;
import br.com.dimo.ediws.service.LayoutArquivoClienteService;


@RestController
@RequestMapping(value="/service/layout/arquivo/clientes")
public class LayoutArquivoClienteResource extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(LayoutArquivoClienteResource.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private LayoutArquivoClienteService layoutArquivoClienteService;
	
	@Autowired
	private LayoutArquivoClienteRepository layoutArquivoClienteRepository;
	
	
	@PostMapping("/load")
	public ResponseEntity<?> loadLayoutArquivoCliente() {
		LoadResponseDTO<LayoutArquivoCliente> dto = new LoadResponseDTO<LayoutArquivoCliente>();

		try {
			List<LayoutArquivoCliente> layoutArquivoClientes = this.layoutArquivoClienteRepository
					.findAllByOrderByIdClienteAsc();

			if (layoutArquivoClientes == null) {
				dto.success = false;
				dto.msg = "Nenhum Layout Arquivo Cliente encontrado!";
				LOG.info("Load - Nenhum Layout Arquivo Cliente encontrado!");
			} else {
				dto.registros = layoutArquivoClientes;
				dto.msg = "Sucesso ao buscar Layout Arquivo Cliente!";
				LOG.info("Load - Sucesso ao buscar Layout Arquivo Cliente!");
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Layout Arquivo Clientes: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Layout Arquivo Clientes: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
				
	@PostMapping("/save")	
	public ResponseEntity<?> save(@RequestBody CadastroClienteLayoutDTO cadastroClienteLayoutDTO) {		
		ResponseDTO<CadastroClienteLayoutDTO> response = new ResponseDTO<>();
									
		try {		
			List<CampoErroDTO> errors = this.layoutArquivoClienteService.salvarLayoutCliente(cadastroClienteLayoutDTO);	
			if (!errors.isEmpty()) {
				response.success = false;
				response.errors = errors;
			} else {
				response.registro = cadastroClienteLayoutDTO;
			}
		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao salvar Layout Arquivo Cliente", e);
			response.errors.add(new CampoErroDTO("id", "Falha ao salvar Layout Arquivo Cliente"));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}	
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody List<LayoutArquivoCliente> layoutArquivoClientes) {		
		ResponseDTO<LayoutArquivoCliente> response = new ResponseDTO<LayoutArquivoCliente>();		
		
		try {
			this.layoutArquivoClienteRepository.deleteAll(layoutArquivoClientes);
			response.msg = "Sucesso ao deletar Layout Arquivo Cliente!";					
			LOG.info("Delete - Sucesso ao deletar Layout Arquivo Cliente!");
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao deletar Layout Arquivo Cliente: " + e.getMessage();
			LOG.error("Delete - Falha ao deletar Layout Arquivo Cliente: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	
}
