package br.com.dimo.ediws.dto;

public class ResponseAutenticacaoDTO<T> {
	
	public boolean success = true;
	public T registro = null;	
	public String msg;
	public String token;

}