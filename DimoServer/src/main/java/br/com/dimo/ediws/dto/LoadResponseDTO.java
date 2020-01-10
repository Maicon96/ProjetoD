package br.com.dimo.ediws.dto;

import java.util.ArrayList;
import java.util.List;


public class LoadResponseDTO<T> {
	
	public boolean success = true;
	public List<T> registros = null;	
	public String msg;
	public Boolean showMsg = false;
	public List<CampoErroDTO> errors = new ArrayList<CampoErroDTO>();

}