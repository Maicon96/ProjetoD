package br.com.dimo.ediws.dto;

public class CampoErroDTO { //NOPMD

	public String id;
	public String msg;
	public Boolean onlyShowField;
	
	public CampoErroDTO() {

	}
	
	public CampoErroDTO(String id, String msg) {
		this.id = id;
		this.msg = msg;
		this.onlyShowField = false;
	}
	
	public CampoErroDTO(String id, String msg, Boolean onlyShowField) {
		this.id = id;
		this.msg = msg;
		this.onlyShowField = onlyShowField;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((onlyShowField == null) ? 0 : onlyShowField.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampoErroDTO other = (CampoErroDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (onlyShowField == null) {
			if (other.onlyShowField != null)
				return false;
		} else if (!onlyShowField.equals(other.onlyShowField))
			return false;
		return true;
	}
	
}
