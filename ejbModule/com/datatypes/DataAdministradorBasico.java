package com.datatypes;

import java.io.Serializable;

public class DataAdministradorBasico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1840688006968269497L;
	private String Estado = "Ok";
	private String AdministradorCorreo;
	private String AdministradorContrase�a;
	private String AdministradorNombre;
		
	public DataAdministradorBasico() {
	}
	
	public DataAdministradorBasico(String Estado, String AdministradorCorreo, String AdministradorNombre, String AdministradorContrase�a){
		this.Estado					= Estado;
		this.AdministradorCorreo 	= AdministradorCorreo;
		this.AdministradorNombre 	= AdministradorNombre;
		this.AdministradorContrase�a= AdministradorContrase�a;
	}
	
	public String getAdministradorCorreo() {
		return AdministradorCorreo;
	}

	public void setAdministradorCorreo(String administradorCorreo) {
		AdministradorCorreo = administradorCorreo;
	}

	public String getAdministradorContrase�a() {
		return AdministradorContrase�a;
	}

	public void setAdministradorContrase�a(String administradorContrase�a) {
		AdministradorContrase�a = administradorContrase�a;
	}

	public String getAdministradorNombre() {
		return AdministradorNombre;
	}

	public void setAdministradorNombre(String administradorNombre) {
		AdministradorNombre = administradorNombre;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}
}
