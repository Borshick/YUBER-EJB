package com.datatypes;

public class DataCrearServicio {

	
	private String tipoVertical;
	private DataServicioBasico servicio;
	
	public DataCrearServicio(){};
	
	public DataCrearServicio(String tipoVertical, DataServicioBasico servicio) {
		super();
		this.tipoVertical = tipoVertical;
		this.servicio = servicio;
	}
	
	public String getTipoVertical() {
		return tipoVertical;
	}
	public void setTipoVertical(String tipoVertical) {
		this.tipoVertical = tipoVertical;
	}
	public DataServicioBasico getServicio() {
		return servicio;
	}
	public void setServicio(DataServicioBasico servicio) {
		this.servicio = servicio;
	}
	
}
