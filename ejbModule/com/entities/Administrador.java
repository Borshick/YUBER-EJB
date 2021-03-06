package com.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.datatypes.DataAdministrador;
import com.datatypes.DataAdministradorBasico;
import com.datatypes.DataVerticalBasico;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Administrador implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private String AdministradorCorreo;
	private String AdministradorContraseņa;
	private String AdministradorNombre;
	@ManyToMany(mappedBy="Administradores", cascade=CascadeType.PERSIST)
	private List<Vertical> Verticales = new ArrayList<Vertical>();
	@OneToOne(cascade=CascadeType.PERSIST)
	private Sesion AdministradorSesionActiva;

	public Administrador() {
	}
	
	public Administrador(String administradorCorreo, String administradorContraseņa, String administradorNombre,
			List<Vertical> verticales) {
		super();
		AdministradorCorreo = administradorCorreo;
		AdministradorContraseņa = administradorContraseņa;
		AdministradorNombre = administradorNombre;
		Verticales = verticales;
	}
	
	public DataAdministrador getDataAdministrador(){
		List<DataVerticalBasico> ListaVerticales = new ArrayList<DataVerticalBasico>();
		for(Vertical Instancia : this.Verticales)
		{
			DataVerticalBasico DataVertical = Instancia.getDataVerticalBasico();
			ListaVerticales.add(DataVertical);
		}		
		return new DataAdministrador(	this.getAdministradorCorreo(),
										this.getAdministradorNombre(),
										this.getAdministradorContraseņa(),										
										ListaVerticales
				    				);
		
	}
	
	public DataAdministradorBasico getDataAdministradorBasico(){		
		return new DataAdministradorBasico(	
										"OK",
										this.getAdministradorCorreo(),
										this.getAdministradorNombre(),
										this.getAdministradorContraseņa()
				    				);
		
	}
	
	public Sesion getAdministradorSesionActiva() {
		return AdministradorSesionActiva;
	}

	public void setAdministradorSesionActiva(Sesion administradorSesionActiva) {
		AdministradorSesionActiva = administradorSesionActiva;
	}
	
	public String getAdministradorCorreo() {
		return this.AdministradorCorreo;
	}

	public void setAdministradorCorreo(String AdministradorCorreo) {
		this.AdministradorCorreo = AdministradorCorreo;
	}

	public String getAdministradorContraseņa() {
		return this.AdministradorContraseņa;
	}

	public void setAdministradorContraseņa(String AdministradorContraseņa) {
		this.AdministradorContraseņa = AdministradorContraseņa;
	}

	public String getAdministradorNombre() {
		return this.AdministradorNombre;
	}

	public void setAdministradorNombre(String AdministradorNombre) {
		this.AdministradorNombre = AdministradorNombre;
	}

	public List<Vertical> getVerticales() {
		return this.Verticales;
	}

	public void setVerticales(List<Vertical> Verticales) {
		this.Verticales = Verticales;
	}

}