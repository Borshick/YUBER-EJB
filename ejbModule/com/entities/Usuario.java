package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import com.datatypes.DataUsuario;
import com.datatypes.DataUsuarioBasico;


@Entity
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private String UsuarioCorreo;
	private String UsuarioNombre;
	private String UsuarioApellido;
	private String UsuarioCiudad;
	private String UsuarioContraseņa;
	private String UsuarioDireccion;	
	private float UsuarioPromedioPuntaje;
	private String UsuarioTelefono;
	private String TokenDePago;
	@OneToOne(cascade=CascadeType.PERSIST)
	private Sesion UsuarioSesionActiva;
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Vertical Vertical;
	

	public Usuario() {
	}

	public DataUsuario getDataUsuario(){
		return new DataUsuario(	this.UsuarioCorreo, 
								this.UsuarioNombre, 
								this.UsuarioApellido, 
								this.UsuarioCiudad, 
								this.UsuarioContraseņa, 
								this.UsuarioDireccion, 
								this.UsuarioPromedioPuntaje, 
								this.UsuarioTelefono,
								this.Vertical.getDataVerticalBasico()
							   );
	}

	public DataUsuarioBasico getDataUsuarioBasico(){
		return new DataUsuarioBasico(	this.UsuarioCorreo, 
								this.UsuarioNombre, 
								this.UsuarioApellido, 
								this.UsuarioCiudad, 
								this.UsuarioContraseņa, 
								this.UsuarioDireccion, 
								this.UsuarioPromedioPuntaje, 
								this.UsuarioTelefono
							   );
	}	
	
	public Vertical getVertical() {
		return Vertical;
	}

	public void setVertical(Vertical vertical) {
		Vertical = vertical;
	}

	String getUsuarioCorreo() {
		return this.UsuarioCorreo;
	}
	
	public void setUsuarioCorreo(String UsuarioCorreo) {
		this.UsuarioCorreo = UsuarioCorreo;
	}	
	
	public String getTokenDePago() {
		return TokenDePago;
	}

	public void setTokenDePago(String tokenDePago) {
		TokenDePago = tokenDePago;
	}

	public String getUsuarioApellido() {
		return this.UsuarioApellido;
	}

	public void setUsuarioApellido(String UsuarioApellido) {
		this.UsuarioApellido = UsuarioApellido;
	}

	public String getUsuarioCiudad() {
		return this.UsuarioCiudad;
	}

	public void setUsuarioCiudad(String UsuarioCiudad) {
		this.UsuarioCiudad = UsuarioCiudad;
	}

	public String getUsuarioContraseņa() {
		return this.UsuarioContraseņa;
	}

	public void setUsuarioContraseņa(String UsuarioContraseņa) {
		this.UsuarioContraseņa = UsuarioContraseņa;
	}

	public String getUsuarioDireccion() {
		return this.UsuarioDireccion;
	}

	public void setUsuarioDireccion(String UsuarioDireccion) {
		this.UsuarioDireccion = UsuarioDireccion;
	}

	public String getUsuarioNombre() {
		return this.UsuarioNombre;
	}

	public void setUsuarioNombre(String UsuarioNombre) {
		this.UsuarioNombre = UsuarioNombre;
	}

	public float getUsuarioPromedioPuntaje() {
		return this.UsuarioPromedioPuntaje;
	}

	public void setUsuarioPromedioPuntaje(float UsuarioPromedioPuntaje) {
		this.UsuarioPromedioPuntaje = UsuarioPromedioPuntaje;
	}

	public String getUsuarioTelefono() {
		return this.UsuarioTelefono;
	}

	public void setUsuarioTelefono(String UsuarioTelefono) {
		this.UsuarioTelefono = UsuarioTelefono;
	}
	
	public Sesion getUsuarioSesionActiva() {
		return UsuarioSesionActiva;
	}

	public void setUsuarioSesionActiva(Sesion usuarioSesionActiva) {
		UsuarioSesionActiva = usuarioSesionActiva;
	}

}