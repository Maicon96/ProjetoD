package br.com.dimo.ediws.dto;

import java.util.ArrayList;
import java.util.List;


public class ResponseDTO<T> {
	
	public boolean success = true;
	public T registro = null;	
	public String msg;
	public Boolean showMsg = true;
	public List<CampoErroDTO> errors = new ArrayList<CampoErroDTO>();

}