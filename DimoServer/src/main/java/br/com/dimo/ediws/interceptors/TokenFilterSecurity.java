package br.com.dimo.ediws.interceptors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class TokenFilterSecurity extends GenericFilterBean {
		
	private static final Logger LOG = LoggerFactory.getLogger(TokenFilterSecurity.class);
    private static final String SECRET = "dimoExpressoPermissaoLogin";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private static final String LOGIN_USUARIO = "login";    
    

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;	
		
		String login = req.getHeader(LOGIN_USUARIO);	
		String authorization = req.getHeader(HEADER_STRING);
						
		if ( authorization == null || !authorization.startsWith(TOKEN_PREFIX)) {
			LOG.error("Token inexistente ou inválido!");
			throw new ServletException("Token inexistente ou inválido!");
		}
		
		String token =  authorization.substring(7); //extraindo só o token
		
		try {
			Jwts.parser().setSigningKey(login + SECRET).parseClaimsJws(token).getBody();			
		} catch(JwtException e) {
			LOG.error("Token inválido!");
			throw new ServletException("Token inválido!");
		}
		
		chain.doFilter(request, response);
	}
	
	
	
}
