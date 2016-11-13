package com.datatypes;

import java.util.List;

public class DataServicio {
	
	private String Estado = "Ok";
	private int ServicioId;
	private String ServicioNombre;
	private float ServicioPrecioHora;
	private float ServicioPrecioKM;
	private float ServicioTarifaBase;
	private int Borrado;
	private List<DataInstanciaServicioBasico> InstanciasServicio;
	private DataVerticalBasico Vertical;
	private List<DataProveedorBasico> Proveedores;
	
	public DataServicio() {
	}

	public DataServicio(int servicioId, String servicioNombre, float servicioPrecioHora, float servicioPrecioKM,
		float servicioTarifaBase,int Borrado, List<DataInstanciaServicioBasico> instanciasServicio, DataVerticalBasico vertical,
		List<DataProveedorBasico> proveedores) {
		ServicioId = servicioId;
		ServicioNombre = servicioNombre;
		ServicioPrecioHora = servicioPrecioHora;
		ServicioPrecioKM = servicioPrecioKM;
		ServicioTarifaBase = servicioTarifaBase;
		this.Borrado = Borrado;
		InstanciasServicio = instanciasServicio;
		Vertical = vertical;
		Proveedores = proveedores;
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

	public List<DataInstanciaServicioBasico> getInstanciasServicio() {
		return InstanciasServicio;
	}

	public void setInstanciasServicio(List<DataInstanciaServicioBasico> instanciasServicio) {
		InstanciasServicio = instanciasServicio;
	}

	public DataVerticalBasico getVertical() {
		return Vertical;
	}

	public void setVertical(DataVerticalBasico vertical) {
		Vertical = vertical;
	}

	public List<DataProveedorBasico> getProveedores() {
		return Proveedores;
	}

	public void setProveedores(List<DataProveedorBasico> proveedores) {
		Proveedores = proveedores;
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
