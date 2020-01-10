package br.com.dimo.ediws.interceptors;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import br.com.dimo.ediws.entity.Auditoria;
import br.com.dimo.ediws.repository.AuditoriaRepository;

@Component
@Order(2)
public class AuditoriaRequestInterceptor extends GenericFilterBean {
	
	@Autowired
	private AuditoriaRepository auditoriaRepository;

	private static final Logger LOG = LoggerFactory.getLogger(AuditoriaRequestInterceptor.class);	
	private static final String LOGIN_USUARIO = "login";
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {		
		String usuario = "";
		String operacao = "";
		String requestBody = "";
        String responseBody = "";
		Integer status = 0;
		
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);		
		
        HttpServletRequest req = (HttpServletRequest) servletRequest;
		
		try {
            chain.doFilter(requestWrapper, responseWrapper);
//            
            usuario = req.getHeader(LOGIN_USUARIO); if (usuario == null) usuario = "0";
    		operacao = ((HttpServletRequest) req).getRequestURL().toString();		
    		requestBody = new String(requestWrapper.getContentAsByteArray());
            responseBody = new String(responseWrapper.getContentAsByteArray());
    		status = responseWrapper.getStatusCode();
    		
    		responseWrapper.copyBodyToResponse();
    		
            Auditoria auditoria = new Auditoria(usuario, operacao, 
            		requestBody, responseBody, status, LocalDateTime.now());
    		
    		//this.auditoriaRepository.save(auditoria);
    		
		} catch(Exception e) {			
			LOG.error("Falha ao gravar LOG de Auditoria! Exception: " + e.getMessage());
			throw new IOException("Falha ao gravar LOG de Auditoria! Exception: " + e.getMessage());
		}
	}
}
