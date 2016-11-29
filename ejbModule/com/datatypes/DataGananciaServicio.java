package com.datatypes;

public class DataGananciaServicio {

	private String Servicio;
	private float Ganancia;
	
	DataGananciaServicio(){
		
	}
	
	public DataGananciaServicio(String servicio, float ganancia) {
		super();
		Servicio = servicio;
		Ganancia = ganancia;
	}
	
	public String getServicio() {
		return Servicio;
	}
	public void setServicio(String servicio) {
		Servicio = servicio;
	}
	public float getGanancia() {
		return Ganancia;
	}
	public void setGanancia(float ganancia) {
		Ganancia = ganancia;
	}
	
	
}
