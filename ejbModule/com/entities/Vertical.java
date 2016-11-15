package com.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.datatypes.DataAdministradorBasico;
import com.datatypes.DataServicioBasico;
import com.datatypes.DataUsuarioBasico;
import com.datatypes.DataVertical;
import com.datatypes.DataVerticalBasico;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Vertical implements Serializable {
	


	private static final long serialVersionUID = 1L;
	@Id
	private String VerticalTipo;
	private String VerticalNombre;
	@OneToMany(mappedBy="Vertical", cascade=CascadeType.PERSIST)
	private List<Servicio> Servicios;
	@ManyToMany(cascade=CascadeType.PERSIST)
	private List<Administrador> Administradores;
	@OneToMany(mappedBy="Vertical", cascade=CascadeType.PERSIST)
	private List<Usuario> Usuarios;

	public Vertical() {
	}
	
	public Vertical(String verticalTipo, String verticalNombre, List<Servicio> servicios,
			List<Administrador> administradores, List<Usuario> usuarios) {
		super();
		VerticalTipo = verticalTipo;
		VerticalNombre = verticalNombre;
		Servicios = servicios;
		Administradores = administradores;
		Usuarios = usuarios;
	}
	
	public DataVertical getDataVertical(){		
		List<DataServicioBasico> ListaServicios = new ArrayList<DataServicioBasico>();
		for(Servicio Servicio : this.Servicios)
		{
			DataServicioBasico DataServicio = Servicio.getDataServicioBasico();
			ListaServicios.add(DataServicio);
		}		
		List<DataAdministradorBasico> ListaAdministradores = new ArrayList<DataAdministradorBasico>();
		for(Administrador Administrador : this.Administradores)
		{
			DataAdministradorBasico DataAdministrador = Administrador.getDataAdministradorBasico();
			ListaAdministradores.add(DataAdministrador);
		}	
		List<DataUsuarioBasico> ListaUsuarios = new ArrayList<DataUsuarioBasico>();
		for(Usuario Usuario : this.Usuarios)
		{
			DataUsuarioBasico DataUsuario = Usuario.getDataUsuarioBasico();
			ListaUsuarios.add(DataUsuario);
		}	
		return new DataVertical(
								this.VerticalTipo, 
								this.VerticalNombre, 
								ListaServicios, 
								ListaAdministradores,
								ListaUsuarios								
							   );
	}
	
	public DataVerticalBasico getDataVerticalBasico(){		
		return new DataVerticalBasico(
								this.VerticalTipo, 
								this.VerticalNombre
							   );
	}

	public String getVerticalTipo() {
		return this.VerticalTipo;
	}

	public void setVerticalTipo(String VerticalTipo) {
		this.VerticalTipo = VerticalTipo;
	}

	public String getVerticalNombre() {
		return this.VerticalNombre;
	}

	public void setVerticalNombre(String VerticalNombre) {
		this.VerticalNombre = VerticalNombre;
	}

	public List<Servicio> getServicios() {
		return this.Servicios;
	}

	public void setServicios(List<Servicio> Servicios) {
		this.Servicios = Servicios;
	}

	public Servicio addServicio(Servicio servicio) {
		getServicios().add(servicio);
		servicio.setVertical(this);
		return servicio;
	}

	public Servicio removeServicio(Servicio servicio) {
		getServicios().remove(servicio);
		servicio.setVertical(null);
		return servicio;
	}

	public List<Administrador> getAdministradores() {
		return this.Administradores;
	}

	public void setAdministradores(List<Administrador> Administradores) {
		this.Administradores = Administradores;
	}

}