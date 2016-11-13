package com.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataAdministrador{
	
	/**
	 * 
	 */
	private String Estado = "Ok";
	private String AdministradorCorreo;
	private String AdministradorContraseña;
	private String AdministradorNombre;
	private List<DataVerticalBasico> Verticales = new ArrayList<DataVerticalBasico>();
		
	public DataAdministrador() {
	}
	
	public DataAdministrador(String AdministradorCorreo, String AdministradorNombre, String AdministradorContraseña, List<DataVerticalBasico> DataVertical){
		this.AdministradorCorreo 	= AdministradorCorreo;
		this.AdministradorNombre 	= AdministradorNombre;
		this.AdministradorContraseña= AdministradorContraseña;
		this.Verticales = DataVertical;
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

	public List<DataVerticalBasico> getVerticales() {
		return Verticales;
	}

	public void setVerticales(List<DataVerticalBasico> Verticales) {
		this.Verticales = Verticales;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

}
