package br.com.dimo.ediws.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import br.com.dimo.ediws.entity.ClienteFormaRecebimentoPasta;
import br.com.dimo.ediws.entity.FormaRecebimentoPasta;
import br.com.dimo.ediws.repository.ClienteFormaRecebimentoPastaRepository;
import br.com.dimo.ediws.repository.FormaRecebimentoPastaRepository;
import br.com.dimo.ediws.service.FormaRecebimentoPastaService;


@RestController
@RequestMapping(value="/service/forma/recebimento/pasta")
public class FormaRecebimentoPastaResource extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(FormaRecebimentoPastaResource.class);
	
	@Autowired
	private FormaRecebimentoPastaService formaRecebimentoPastaService;
	
	@Autowired
	private FormaRecebimentoPastaRepository formaRecebimentoPastaRepository;
	
	@Autowired
	private ClienteFormaRecebimentoPastaRepository clienteFormaRecebimentoPastaRepository;
	
	
	@PostMapping("/load")
	public ResponseEntity<?> loadFormaRecebimentoPasta() {
		LoadResponseDTO<FormaRecebimentoPasta> dto = new LoadResponseDTO<FormaRecebimentoPasta>();

		try {
			List<FormaRecebimentoPasta> formaRecebimentoPastas = this.formaRecebimentoPastaRepository.findAllNotCliente();

			if (formaRecebimentoPastas == null) {
				dto.success = false;
				dto.msg = "Forma de Recebimento Pasta não encontrada!";
				LOG.info("Load - Nenhuma Forma de Recebimento Pasta encontrada!");
			} else {
				dto.registros = formaRecebimentoPastas;
				dto.msg = "Sucesso ao buscar Forma de Recebimento - Pasta!";
				LOG.info("Load - Sucesso ao buscar Forma de Recebimento - Pasta!");
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Forma de Recebimento - Pasta: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Forma de Recebimento - Pasta: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/load/filter/cliente")
	public ResponseEntity<?> loadFormaRecebimentoPastaFilter(@RequestBody Cliente cliente) {
		LoadResponseDTO<FormaRecebimentoPasta> dto = new LoadResponseDTO<FormaRecebimentoPasta>();
		List<FormaRecebimentoPasta> formasPastas = new ArrayList<FormaRecebimentoPasta>();
				
		try {			
			List<ClienteFormaRecebimentoPasta> pastas = this.clienteFormaRecebimentoPastaRepository.findByIdCliente(cliente.getId());
			
			for(ClienteFormaRecebimentoPasta pasta : pastas) {
				formasPastas.add(pasta.getFormaRecebimentoPasta());
			}			
			
			if (formasPastas.size() == 0) {
				dto.msg = "Nenhuma Forma de Recebimento - Pasta encontrada!";
				LOG.info("Load - Nenhuma Forma de Recebimento - Pasta encontrada!");
			} else {
				dto.registros = formasPastas;
				dto.msg = "Sucesso ao buscar Forma de Recebimento - Pasta!";
				LOG.info("Load - Sucesso ao buscar Forma de Recebimento - Pasta!");
			}
			
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Forma de Recebimento - Pasta: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Forma de Recebimento - Pasta: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/exists")
	public ResponseEntity<?> existsUsuario(@RequestBody FormaRecebimentoPasta formaRecebimentoPasta) {
		ResponseDTO<FormaRecebimentoPasta> response = new ResponseDTO<FormaRecebimentoPasta>();
				
		try {			
			Optional<FormaRecebimentoPasta> formaRecebimentoPastaOp = this.formaRecebimentoPastaRepository.findById(formaRecebimentoPasta.getId());
			
			if (!formaRecebimentoPastaOp.isPresent()) {
				response.success = false;
				response.msg = "Forma de Recebimento não cadastrada!";
				LOG.info("Exists - Forma de Recebimento não cadastrado!");
			} else {
				response.registro = formaRecebimentoPastaOp.get();
				response.msg = "Sucesso ao buscar Forma de Recebimento!";
				LOG.info("Exists - Sucesso ao buscar Forma de Recebimento!");
			}
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao buscar Forma de Recebimento: " + e.getMessage();
			LOG.error("Exists - Falha ao buscar Forma de Recebimento: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
				
	@PostMapping("/save")	
	public ResponseEntity<?> updateUpload(@RequestBody FormaRecebimentoPasta formaRecebimentoPasta) {		
		ResponseDTO<FormaRecebimentoPasta> response = new ResponseDTO<>();
									
		try {			
			List<CampoErroDTO> errors = this.formaRecebimentoPastaService.salvar(formaRecebimentoPasta);	
			if (!errors.isEmpty()) {
				response.success = false;
				response.msg = "Erro ao salvar Forma de Recebimento - Pasta";
				response.errors = errors;
			} else {
				response.registro = formaRecebimentoPasta;
			}
		} catch (Exception e) {
			response.success = false;
			LOG.error("Falha ao salvar Forma de Recebimento - Pasta", e);
			response.errors.add(new CampoErroDTO("id", "Falha ao salvar Forma de Recebimento - Pasta"));
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody List<FormaRecebimentoPasta> formaRecebimentoPasta) {		
		ResponseDTO<FormaRecebimentoPasta> response = new ResponseDTO<FormaRecebimentoPasta>();		
		
		try {			
			this.formaRecebimentoPastaService.deletar(formaRecebimentoPasta);
			response.msg = "Sucesso ao deletar Forma de Recebimento - Pasta!";					
			LOG.info("Delete - Sucesso ao deletar Forma de Recebimento - Pasta!");
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao deletar Forma de Recebimento - Pasta: " + e.getMessage();
			LOG.error("Delete - Falha ao deletar Forma de Recebimento - Pasta: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	
}
