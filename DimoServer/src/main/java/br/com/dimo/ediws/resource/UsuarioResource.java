package br.com.dimo.ediws.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.dto.LoadResponseDTO;
import br.com.dimo.ediws.dto.ResponseDTO;
import br.com.dimo.ediws.entity.Usuario;
import br.com.dimo.ediws.repository.UsuarioRepository;
import br.com.dimo.ediws.service.UsuarioService;


@RestController
@RequestMapping(value="/service/usuarios")
public class UsuarioResource extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(UsuarioResource.class);
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	
	@PostMapping("/load")
	public ResponseEntity<?> loadUsuarios() {
		LoadResponseDTO<Usuario> dto = new LoadResponseDTO<Usuario>();

		try {
			List<Usuario> usuarios = this.usuarioRepository.findAll();

			if (usuarios == null) {
				dto.success = false;
				dto.msg = "Nenhum Usuário encontrado!";
				LOG.info("Load - Nenhum Usuário encontrado!");
			} else {
				dto.registros = usuarios;
				dto.msg = "Sucesso ao buscar Usuários!";
				LOG.info("Load - Sucesso ao buscar Usuários!");				
			}
		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao buscar Usuários: " + e.getMessage();
			LOG.error("Load - Falha ao buscar Usuários: " + e.getMessage());
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/exists/{id}")
	public ResponseEntity<?> existsUsuario(@PathVariable(value="id") long id) {
		ResponseDTO<Usuario> response = new ResponseDTO<Usuario>();
				
		try {
			Usuario usuario = this.usuarioRepository.findById(id);

			if (usuario == null) {
				response.success = false;
				response.msg = "Usuário não cadastrado!";
				LOG.info("Exists - Usuário não cadastrado!");
			} else {
				response.registro = usuario;
				response.msg = "Sucesso ao buscar Usuário!";
				LOG.info("Exists - Sucesso ao buscar Usuário!");
			}
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao buscar Usuário: " + e.getMessage();
			LOG.error("Exists - Falha ao buscar Usuário: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Usuario usuario) {			
		ResponseDTO<Usuario> response = new ResponseDTO<Usuario>();		
		
		try {						
			List<CampoErroDTO> erros = this.usuarioService.salvar(usuario);			
			
			if (erros.isEmpty()) {		
				response.registro = usuario;
				response.msg = "Sucesso ao cadastrar Usuário!";
				LOG.info("Save - Sucesso ao cadastrar Usuário!");
			} else {
				response.success = false;				
				response.msg = "Erro ao cadastrar Usuário!";				
				response.errors.addAll(erros);
				LOG.info("Save - Erro ao cadastrar Usuário!");
			}			
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao salvar Usuário: " + e.getMessage();
			LOG.error("Save - Falha ao salvar Usuário: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody Usuario usuario) {			
		ResponseDTO<Usuario> response = new ResponseDTO<Usuario>();		
		
		try {
			List<CampoErroDTO> erros = this.usuarioService.salvar(usuario);			
			
			if (erros.isEmpty()) {		
				response.registro = usuario;
				response.msg = "Sucesso ao atualizar Usuário!";
				LOG.info("Update - Sucesso ao atualizar Usuário!");
			} else {
				response.success = false;				
				response.msg = "Erro ao atualizar Usuário!";				
				response.errors.addAll(erros);
				LOG.info("Update - Erro ao atualizar Usuário!");
			}			
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao atualizar Usuário: " + e.getMessage();
			LOG.error("Update - Falha ao atualizar Usuário: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody List<Usuario> usuarios) {		
		ResponseDTO<Usuario> response = new ResponseDTO<Usuario>();		
		
		try {					
			this.usuarioRepository.deleteAll(usuarios);				
			response.msg = "Sucesso ao deletar Usuários!";					
			LOG.info("Delete - Sucesso ao deletar Usuários!");
		} catch (Exception e) {
			response.success = false;
			response.msg = "Falha ao deletar Usuários: " + e.getMessage();
			LOG.error("Delete - Falha ao deletar Usuários: " + e.getMessage());
		}

		return response.success ? new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
}
