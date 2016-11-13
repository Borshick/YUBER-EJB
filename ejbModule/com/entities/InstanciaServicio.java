package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import com.datatypes.*;

@Entity
@NamedQueries({
	@NamedQuery(query = "SELECT i FROM InstanciaServicio i WHERE i.Cliente.UsuarioCorreo = :ClienteCorreo AND i.Servicio.ServicioId = :ServicioId", name = "ObtenerHistorialCliente"),
	@NamedQuery(query = "SELECT i FROM InstanciaServicio i WHERE i.Proveedor.UsuarioCorreo = :ProveedorCorreo", name = "ObtenerHistorialProveedor"),
	@NamedQuery(query = "SELECT i FROM InstanciaServicio i WHERE i.InstanciaServicioFechaInicio >= :FechaInicio AND i.InstanciaServicioFechaInicio <= :FechaFin", name = "ObtenerGananciaMensual")
})
public class InstanciaServicio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int InstanciaServicioId;
	private float InstanciaServicioCosto;
	private float InstanciaServicioDistancia;
	private Date InstanciaServicioFechaInicio;
	private Date InstanciaServicioFechaFin;
	private float InstanciaServicioTiempo;
	private double Latitud;
	private double Longitud;
	private double LatitudDestino;
	private double LongitudDestino;
	@OneToOne(cascade=CascadeType.PERSIST)
	private Reseña ReseñaCliente;
	@OneToOne(cascade=CascadeType.PERSIST)
	private Reseña ReseñaProveedor;	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Servicio Servicio;
	@ManyToOne
	private Cliente Cliente;
	@ManyToOne
	private Proveedor Proveedor;
	
	public InstanciaServicio() {
	}
	
	public DataInstanciaServicio getDataInstanciaServicio() {
		DataUbicacion DataUbicacion = new DataUbicacion(getLongitud(),getLatitud());
		DataUbicacion DataUbicacionDestino = new DataUbicacion(getLongitudDestino(),getLatitudDestino());
		DataInstanciaServicio result = new DataInstanciaServicio(	this.InstanciaServicioId,
											this.InstanciaServicioCosto,
											this.InstanciaServicioDistancia,
											this.InstanciaServicioFechaInicio,
											this.InstanciaServicioFechaFin,
											this.InstanciaServicioTiempo,
											DataUbicacion,
											DataUbicacionDestino,
											null,
											null,
											null,
											null,
											null);
		
		if(this.ReseñaCliente != null)
				result.setReseñaCliente(this.ReseñaCliente.getDataReseñaBasico());
		if(this.ReseñaProveedor != null)
			result.setReseñaProveedor(this.ReseñaProveedor.getDataReseñaBasico());
		if(this.Servicio != null)
			result.setServicio(this.Servicio.getDataServicioBasico());
		if(this.Cliente != null)
			result.setCliente(this.Cliente.getDataClienteBasico());
		if(this.Proveedor != null)
			result.setProveedor(this.Proveedor.getDataProveedorBasico());						
										
	
		return result;
	}
	
	public DataInstanciaServicioBasico getDataInstanciaServicioBasico() {
		DataUbicacion DataUbicacion = new DataUbicacion(getLongitud(),getLatitud());
		DataUbicacion DataUbicacionDestino = new DataUbicacion(getLongitudDestino(),getLatitudDestino());
		return new DataInstanciaServicioBasico(	this.getInstanciaServicioId(),
											this.getInstanciaServicioCosto(),
											this.getInstanciaServicioDistancia(),
											this.InstanciaServicioFechaInicio,
											this.InstanciaServicioFechaFin,
											this.getInstanciaServicioTiempo(),
											DataUbicacion,
											DataUbicacionDestino											
										);
	}

	
	
	public double getLatitudDestino() {
		return LatitudDestino;
	}

	public void setLatitudDestino(double latitudDestino) {
		LatitudDestino = latitudDestino;
	}

	public double getLongitudDestino() {
		return LongitudDestino;
	}

	public void setLongitudDestino(double longitudDestino) {
		LongitudDestino = longitudDestino;
	}

	public int getInstanciaServicioId() {
		return this.InstanciaServicioId;
	}

	public void setInstanciaServicioId(int InstanciaServicioId) {
		this.InstanciaServicioId = InstanciaServicioId;
	}

	public float getInstanciaServicioCosto() {
		return this.InstanciaServicioCosto;
	}

	public void setInstanciaServicioCosto(float InstanciaServicioCosto) {
		this.InstanciaServicioCosto = InstanciaServicioCosto;
	}

	public float getInstanciaServicioDistancia() {
		return this.InstanciaServicioDistancia;
	}

	public void setInstanciaServicioDistancia(float InstanciaServicioDistancia) {
		this.InstanciaServicioDistancia = InstanciaServicioDistancia;
	}

	public float getInstanciaServicioTiempo() {
		return this.InstanciaServicioTiempo;
	}

	public void setInstanciaServicioTiempo(float InstanciaServicioTiempo) {
		this.InstanciaServicioTiempo = InstanciaServicioTiempo;
	}

	public Servicio getServicio() {
		return this.Servicio;
	}

	public void setServicio(Servicio servicio) {
		this.Servicio = servicio;
	}

	public Reseña getReseñaCliente() {
		return ReseñaCliente;
	}

	public void setReseñaCliente(Reseña reseñaCliente) {
		ReseñaCliente = reseñaCliente;
	}

	public Reseña getReseñaProveedor() {
		return ReseñaProveedor;
	}

	public void setReseñaProveedor(Reseña reseñaProveedor) {
		ReseñaProveedor = reseñaProveedor;
	}

	public Cliente getCliente() {
		return Cliente;
	}

	public void setCliente(Cliente cliente) {
		Cliente = cliente;
	}

	public Proveedor getProveedor() {
		return Proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		Proveedor = proveedor;
	}

	public Date getInstanciaServicioFechaInicio() {
		return InstanciaServicioFechaInicio;
	}

	public void setInstanciaServicioFechaInicio(Date instanciaServicioFechaInicio) {
		InstanciaServicioFechaInicio = instanciaServicioFechaInicio;
	}

	public Date getInstanciaServicioFechaFin() {
		return InstanciaServicioFechaFin;
	}

	public void setInstanciaServicioFechaFin(Date instanciaServicioFechaFin) {
		InstanciaServicioFechaFin = instanciaServicioFechaFin;
	}

	public void setLatitud(double latitud) {
		Latitud = latitud;
	}

	public void setLongitud(double longitud) {
		Longitud = longitud;
	}

	public double getLatitud() {
		return Latitud;
	}

	public double getLongitud() {
		return Longitud;
	}


	
}