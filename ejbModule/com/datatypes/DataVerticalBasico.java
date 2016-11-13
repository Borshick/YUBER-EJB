package com.datatypes;

import javax.ws.rs.QueryParam;

public class DataVerticalBasico {
	
	private String Estado = "Ok";
	
	private String VerticalTipo;
	private String VerticalNombre;
	
	public DataVerticalBasico() {
	}
	
	public DataVerticalBasico(String verticalTipo, String verticalNombre) {
		super();
		VerticalTipo = verticalTipo;
		VerticalNombre = verticalNombre;
	}
	
	public String getVerticalTipo() {
		return VerticalTipo;
	}
	
	public void setVerticalTipo(String verticalTipo) {
		VerticalTipo = verticalTipo;
	}
	
	public String getVerticalNombre() {
		return VerticalNombre;
	}
	
	public void setVerticalNombre(String verticalNombre) {
		VerticalNombre = verticalNombre;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}
	

}
