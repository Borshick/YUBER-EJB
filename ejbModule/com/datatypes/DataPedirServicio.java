package com.datatypes;

public class DataPedirServicio {
	
	private String correo;

	private int servicioId;
	private DataUbicacion ubicacion;

	public DataPedirServicio(){
		
	};
	
	public DataPedirServicio(String correo, int servicioId, DataUbicacion ubicacion) {
		super();
		this.correo = correo;
		this.servicioId = servicioId;
		this.ubicacion = ubicacion;
	}

	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getServicioId() {
		return servicioId;
	}
	public void setServicioId(int servicioId) {
		this.servicioId = servicioId;
	}
	public DataUbicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(DataUbicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
}
