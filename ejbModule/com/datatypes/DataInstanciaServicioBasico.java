package com.datatypes;

import java.util.Date;

public class DataInstanciaServicioBasico {
	
	private String Estado = "Ok";
	private int InstanciaServicioId;
	private float InstanciaServicioCosto;
	private float InstanciaServicioDistancia;
	private Date InstanciaServicioFechaInicio;
	private Date InstanciaServicioFechaFin;
	private float InstanciaServicioTiempo;
	private DataUbicacion Ubicacion;
	private DataUbicacion UbicacionDestino;

	public DataInstanciaServicioBasico() {
	}
	
	
	public DataInstanciaServicioBasico(int InstanciaServicioId, float InstanciaServicioCosto, float InstanciaServicioDistancia, Date InstanciaServicioFechaInicio, Date InstanciaServicioFechaFin, float InstanciaServicioTiempo, DataUbicacion Ubicacion, DataUbicacion UbicacionDestino) {
		this.InstanciaServicioId 			= InstanciaServicioId;
		this.InstanciaServicioCosto 		= InstanciaServicioCosto;
		this.InstanciaServicioDistancia		= InstanciaServicioDistancia;
		this.InstanciaServicioFechaInicio	= InstanciaServicioFechaInicio;
		this.InstanciaServicioFechaFin		= InstanciaServicioFechaFin;
		this.InstanciaServicioTiempo		= InstanciaServicioTiempo;
		this.Ubicacion						= Ubicacion;
		this.UbicacionDestino				= UbicacionDestino;
	}

	public DataUbicacion getUbicacionDestino() {
		return UbicacionDestino;
	}

	public void setUbicacionDestino(DataUbicacion ubicacionDestino) {
		UbicacionDestino = ubicacionDestino;
	}

	
	public int getInstanciaServicioId() {
		return InstanciaServicioId;
	}

	public void setInstanciaServicioId(int instanciaServicioId) {
		InstanciaServicioId = instanciaServicioId;
	}

	public float getInstanciaServicioCosto() {
		return InstanciaServicioCosto;
	}

	public void setInstanciaServicioCosto(float instanciaServicioCosto) {
		InstanciaServicioCosto = instanciaServicioCosto;
	}

	public float getInstanciaServicioDistancia() {
		return InstanciaServicioDistancia;
	}

	public void setInstanciaServicioDistancia(float instanciaServicioDistancia) {
		InstanciaServicioDistancia = instanciaServicioDistancia;
	}

	public float getInstanciaServicioTiempo() {
		return InstanciaServicioTiempo;
	}

	public void setInstanciaServicioTiempo(float instanciaServicioTiempo) {
		InstanciaServicioTiempo = instanciaServicioTiempo;
	}


	public String getEstado() {
		return Estado;
	}


	public void setEstado(String estado) {
		Estado = estado;
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


	public DataUbicacion getUbicacion() {
		return Ubicacion;
	}


	public void setUbicacion(DataUbicacion ubicacion) {
		Ubicacion = ubicacion;
	}

	
}
