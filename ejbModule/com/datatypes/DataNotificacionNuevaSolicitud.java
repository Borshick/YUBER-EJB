package com.datatypes;

import java.util.Date;

public class DataNotificacionNuevaSolicitud {
	
	private String Estado = "Ok";
	private int InstanciaServicioId;
	private DataUbicacion Ubicacion;
	private DataClienteBasico Cliente;

	public DataNotificacionNuevaSolicitud() {
	}

	public DataNotificacionNuevaSolicitud(int InstanciaServicioId, DataUbicacion Ubicacion, DataClienteBasico Cliente) {
		this.InstanciaServicioId 			= InstanciaServicioId;
		this.Ubicacion						= Ubicacion;
		this.setCliente(Cliente);
	}
	
	public int getInstanciaServicioId() {
		return InstanciaServicioId;
	}

	public void setInstanciaServicioId(int instanciaServicioId) {
		InstanciaServicioId = instanciaServicioId;
	}

	public DataClienteBasico getCliente() {
		return Cliente;
	}

	public void setCliente(DataClienteBasico cliente) {
		Cliente = cliente;
	}


	public DataUbicacion getUbicacion() {
		return Ubicacion;
	}

	public void setUbicacion(DataUbicacion ubicacion) {
		Ubicacion = ubicacion;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	

	
	
}
