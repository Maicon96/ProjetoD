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
import br.com.dimo.ediws.entity.Cliente;
import br.com.dimo.ediws.repository.ClienteRepository;
import br.com.dimo.ediws.service.ClienteService;


@RestController
@RequestMapping(value="/service/clientes")
public class ClienteResource extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClienteResource.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@PostMapping("/exists/cpf")
	public ResponseEntity<?> existsCpf(@RequestBody Cliente cliente) {
		ResponseDTO<Cliente> response = new ResponseDTO<Cliente>();
				
		try {						
			cliente = this.clienteRepository.findById(1);
			
			if (cliente != null) {
				response.success = false;
				response.msg = "Cliente não encontrado!";
				LOG.info("Exists - Cliente não encontrado!!");
			} else {
				response.registro = cliente;
				response.msg = "Sucesso ao buscar Nome do Cliente!";
				LOG.info("Exists - Sucesso ao buscar Nome do Cliente!");
			}			
			
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao buscar Nome do Cliente: " + e.getMessage();
			LOG.error("Exists - Falha ao buscar Nome do Cliente: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@PostMapping("/load")
	public ResponseEntity<?> loadCliente() {
		LoadResponseDTO<Cliente> dto = new LoadResponseDTO<Cliente>();

		try {
			List<Cliente> clientes = this.clienteRepository.findAll();

			if (clientes == null) {
				dto.success = false;
				dto.msg = "Nenhum cliente encontrado!";
				LOG.info("Load - Nenhum cliente encontrado!");
			} else {
				dto.registros = clientes;
				dto.msg = "Sucesso ao buscar cliente!";
				LOG.info("Load - Sucesso ao buscar cliente!");
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar clientes: " + e.getMessage();
			LOG.error("Load - Falha ao buscar clientes: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
				
	@PostMapping("/save")	
	public ResponseEntity<?> updateUpload(@RequestBody Cliente cliente) {		
		ResponseDTO<Cliente> response = new ResponseDTO<>();
									
		try {			
			List<CampoErroDTO> errors = this.clienteService.salvar(cliente);	
			if (!errors.isEmpty()) {
				response.success = false;
				response.errors = errors;				
			} else {
				response.registro = cliente;
			}
		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao salvar cliente", e);
			response.errors.add(new CampoErroDTO("id", "Falha ao salvar cliente"));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody List<Cliente> clientes) {		
		ResponseDTO<Cliente> response = new ResponseDTO<Cliente>();		
		
		try {
			this.clienteRepository.deleteAll(clientes);
			response.msg = "Sucesso ao deletar cliente!";					
			LOG.info("Delete - Sucesso ao deletar cliente!");
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao deletar cliente: " + e.getMessage();
			LOG.error("Delete - Falha ao deletar cliente: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	
}
