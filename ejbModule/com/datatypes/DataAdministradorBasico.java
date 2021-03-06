package com.datatypes;

import java.io.Serializable;

public class DataAdministradorBasico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1840688006968269497L;
	private String Estado = "Ok";
	private String AdministradorCorreo;
	private String AdministradorContraseņa;
	private String AdministradorNombre;
		
	public DataAdministradorBasico() {
	}
	
	public DataAdministradorBasico(String Estado, String AdministradorCorreo, String AdministradorNombre, String AdministradorContraseņa){
		this.Estado					= Estado;
		this.AdministradorCorreo 	= AdministradorCorreo;
		this.AdministradorNombre 	= AdministradorNombre;
		this.AdministradorContraseņa= AdministradorContraseņa;
	}
	
	public String getAdministradorCorreo() {
		return AdministradorCorreo;
	}

	public void setAdministradorCorreo(String administradorCorreo) {
		AdministradorCorreo = administradorCorreo;
	}

	public String getAdministradorContraseņa() {
		return AdministradorContraseņa;
	}

	public void setAdministradorContraseņa(String administradorContraseņa) {
		AdministradorContraseņa = administradorContraseņa;
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
