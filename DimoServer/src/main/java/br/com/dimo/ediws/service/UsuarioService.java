package br.com.dimo.ediws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimo.ediws.dto.CampoErroDTO;
import br.com.dimo.ediws.entity.Usuario;
import br.com.dimo.ediws.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<CampoErroDTO> salvar(Usuario usuario) {			
		
		if (usuario.getId() != null && usuario.getSenha() == null) {			
			usuario.setSenha(this.usuarioRepository.findSenha(usuario.getId()));			
		}
		
		List<CampoErroDTO> erros = this.validaSalvar(usuario);
		if (erros.isEmpty()) {
			this.usuarioRepository.save(usuario);			
		}	
		
		return erros;		
	}
	
	public List<CampoErroDTO> validaSalvar(Usuario usuario) {			
		
		List<CampoErroDTO> erros = new ArrayList<CampoErroDTO>();

		this.validaNome(usuario, erros);
		this.validaLogin(usuario, erros);
		this.validaSenha(usuario, erros);
		this.validaPerfil(usuario, erros);
//		this.validaUnique(usuario, erros);

		return erros;		
	}
	

	public void validaNome(Usuario usuario, List<CampoErroDTO> erros) {
		if (usuario.getNome() == null || usuario.getNome() == "") {
			erros.add(new CampoErroDTO("nome", "Nome é campo obrigatório"));
		}
	}
	
	public void validaLogin(Usuario usuario, List<CampoErroDTO> erros) {
		if (usuario.getLogin() == "") {		
			erros.add(new CampoErroDTO("login", "Login é campo obrigatório"));
		}
	}
	
	public void validaSenha(Usuario usuario, List<CampoErroDTO> erros) {
		if (usuario.getSenha() == null || usuario.getSenha() == "") {		
			erros.add(new CampoErroDTO("senha", "Senha é campo obrigatório"));
		} 
	}
	
	public void validaPerfil(Usuario usuario, List<CampoErroDTO> erros) {
		if (usuario.getIdPerfil() == null) {		
			erros.add(new CampoErroDTO("idPerfil", "Perfil é campo obrigatório"));
		}
	}
	
//	public void validaUnique(Usuario usuario, List<CampoErroDTO> erros) {
//		if (usuario.getIdEmpresa() != null && usuario.getLogin() != null) {
//			
//			if (usuario.getId() != null) {
//				Usuario usuarioAux = this.usuarioRepository.existsEmpresaUsuario(usuario.getId(), 
//						usuario.getIdEmpresa(), usuario.getLogin());
//				
//				if (usuarioAux != null) {
//					erros.add(new CampoErroDTO("login", "Login já cadastrado para essa empresa. Escolha outro!"));	
//				}	
//			} else {
//				Usuario usuarioAux = this.usuarioRepository.existsEmpresaUsuario(usuario.getIdEmpresa(), 
//						usuario.getLogin());
//				
//				if (usuarioAux != null) {
//					erros.add(new CampoErroDTO("login", "Login já cadastrado para essa empresa. Escolha outro!"));	
//				}
//			}				
//		}
//	}
	
	
}
