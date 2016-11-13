package com.datatypes;

import java.io.Serializable;

public class DataAdministradorBasico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1840688006968269497L;
	private String Estado = "Ok";
	private String AdministradorCorreo;
	private String AdministradorContraseña;
	private String AdministradorNombre;
		
	public DataAdministradorBasico() {
	}
	
	public DataAdministradorBasico(String Estado, String AdministradorCorreo, String AdministradorNombre, String AdministradorContraseña){
		this.Estado					= Estado;
		this.AdministradorCorreo 	= AdministradorCorreo;
		this.AdministradorNombre 	= AdministradorNombre;
		this.AdministradorContraseña= AdministradorContraseña;
	}
	
	public String getAdministradorCorreo() {
		return AdministradorCorreo;
	}

	public void setAdministradorCorreo(String administradorCorreo) {
		AdministradorCorreo = administradorCorreo;
	}

	public String getAdministradorContraseña() {
		return AdministradorContraseña;
	}

	public void setAdministradorContraseña(String administradorContraseña) {
		AdministradorContraseña = administradorContraseña;
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
