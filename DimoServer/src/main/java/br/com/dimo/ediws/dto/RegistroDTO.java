package br.com.dimo.ediws.dto;

public class RegistroDTO<T> {
	
	public T registro;

	public RegistroDTO() {
		
	}

	public RegistroDTO(T registro) {
		this.registro = registro;
	}

}
