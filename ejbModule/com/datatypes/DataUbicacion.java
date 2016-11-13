package com.datatypes;

public class DataUbicacion {
	
	private String Estado = "Ok";
	private double Longitud;
	private double Latitud;
	
	public DataUbicacion(){
	}
	
	public DataUbicacion(double Longitud, double Latitud){
		this.Longitud = Longitud;
		this.Latitud = Latitud;
	}
	
	public double getLongitud() {
		return Longitud;
	}
	
	public void setLongitud(double longitud) {
		Longitud = longitud;
	}
	
	public double getLatitud() {
		return Latitud;
	}
	
	public void setLatitud(double latitud) {
		Latitud = latitud;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	
	
}
