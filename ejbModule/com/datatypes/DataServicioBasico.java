package com.datatypes;


public class DataServicioBasico {
	
	private String Estado = "Ok";
	private int ServicioId;
	private String ServicioNombre;
	private float ServicioPrecioHora;
	private float ServicioPrecioKM;
	private float ServicioTarifaBase;
	private int Borrado;
	
	public DataServicioBasico() {
	}

	public DataServicioBasico(int servicioId, String servicioNombre, float servicioPrecioHora, float servicioPrecioKM,
		float servicioTarifaBase, int borrado) {
		ServicioId = servicioId;
		ServicioNombre = servicioNombre;
		ServicioPrecioHora = servicioPrecioHora;
		ServicioPrecioKM = servicioPrecioKM;
		ServicioTarifaBase = servicioTarifaBase;
		setBorrado(borrado);
	}
	
	public int getServicioId() {
		return ServicioId;
	}

	public void setServicioId(int servicioId) {
		ServicioId = servicioId;
	}

	public String getServicioNombre() {
		return ServicioNombre;
	}

	public void setServicioNombre(String servicioNombre) {
		ServicioNombre = servicioNombre;
	}

	public float getServicioPrecioHora() {
		return ServicioPrecioHora;
	}

	public void setServicioPrecioHora(float servicioPrecioHora) {
		ServicioPrecioHora = servicioPrecioHora;
	}

	public float getServicioPrecioKM() {
		return ServicioPrecioKM;
	}

	public void setServicioPrecioKM(float servicioPrecioKM) {
		ServicioPrecioKM = servicioPrecioKM;
	}

	public float getServicioTarifaBase() {
		return ServicioTarifaBase;
	}

	public void setServicioTarifaBase(float servicioTarifaBase) {
		ServicioTarifaBase = servicioTarifaBase;
	}

	public int getBorrado() {
		return Borrado;
	}

	public void setBorrado(int borrado) {
		Borrado = borrado;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

}
