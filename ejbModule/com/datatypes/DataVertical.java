package com.datatypes;

import java.util.List;

public class DataVertical {
	
	private String Estado = "Ok";
	private String VerticalTipo;
	private String VerticalNombre;
	private List<DataServicioBasico> Servicios;
	private List<DataAdministradorBasico> Administradores;
	private List<DataUsuarioBasico> Usuarios;
	
	public DataVertical() {
	}
	
	public DataVertical(String verticalTipo, String verticalNombre, List<DataServicioBasico> servicios,
		List<DataAdministradorBasico> administradores, List<DataUsuarioBasico> usuarios) {
		super();
		VerticalTipo 	= verticalTipo;
		VerticalNombre 	= verticalNombre;
		Servicios 		= servicios;
		Administradores = administradores;
		Usuarios		= usuarios;
	}
	
	
	
	public List<DataUsuarioBasico> getUsuarios() {
		return Usuarios;
	}

	public void setUsuarios(List<DataUsuarioBasico> usuarios) {
		Usuarios = usuarios;
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
	
	public List<DataServicioBasico> getServicios() {
		return Servicios;
	}
	
	public void setServicios(List<DataServicioBasico> servicios) {
		Servicios = servicios;
	}

	public List<DataAdministradorBasico> getAdministradores() {
		return Administradores;
	}

	public void setAdministradores(List<DataAdministradorBasico> administradores) {
		Administradores = administradores;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

}
