package br.com.dimo.ediws.resource.security;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dimo.ediws.dto.ResponseAutenticacaoDTO;
import br.com.dimo.ediws.entity.Usuario;
import br.com.dimo.ediws.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "/login")
public class LoginController extends ResponseEntityExceptionHandler {

	private static final long EXPIRATIONTIME = 240 * 60 * 1000;
	private static final String SECRET = "dimoExpressoPermissaoLogin";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/exists/usuario")
	public ResponseEntity<?> buscarUsuario(@RequestBody Usuario usuario) {
		ResponseAutenticacaoDTO<Usuario> dto = new ResponseAutenticacaoDTO<Usuario>();

		try {
			Usuario usu = this.usuarioRepository.findByLogin(usuario.getLogin());

			if (usu != null) {
				dto.registro = usu;
				dto.msg = "Sucesso ao buscar Usuário!";
			} else {
				dto.success = false;
				dto.msg = "Usuário não encontrado!";
			}

		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao autenticar Usuário: " + e.getMessage();
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/autenticar/usuario")
	public ResponseEntity<?> autenticar(@RequestBody Usuario usuario) {
		ResponseAutenticacaoDTO<Usuario> dto = new ResponseAutenticacaoDTO<Usuario>();

		try {
			Usuario usu = this.usuarioRepository.findUsuarioSenha(usuario.getLogin(), usuario.getSenha());

			if (usu != null) {
				usu.setSenha(null);
				dto.registro = usu;
				dto.msg = "Sucesso ao autenticar Usuário!";
				dto.token = this.geraToken(usu);
			} else {
				dto.success = false;
				dto.msg = "Usuário ou Senha inválido!";

			}

		} catch (Exception e) {
			dto.success = false;
			dto.msg = "Falha ao autenticar Usuário: " + e.getMessage();
		}

		return dto.success ? new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.ACCEPTED)
				: new ResponseEntity<Object>(dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public String geraToken(Usuario usuario) throws ServletException {

		String token = "";

		try {
			token = Jwts.builder().setSubject(usuario.getNome())
					.signWith(SignatureAlgorithm.HS512, usuario.getLogin() + SECRET)
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();

		} catch (Exception e) {
			throw new ServletException("Falha ao gerar token!");
		}

		return token;
	}

}
