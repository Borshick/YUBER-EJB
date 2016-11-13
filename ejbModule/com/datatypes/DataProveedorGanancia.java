package com.datatypes;

import com.entities.Proveedor;

public class DataProveedorGanancia {

	private Proveedor prov;

	private float ganancia;
	
	
	public DataProveedorGanancia(Proveedor prov, float ganancia) {
		super();
		this.prov = prov;
		this.ganancia = ganancia;
	}
	
	public Proveedor getProv() {
		return prov;
	}
	public void setProv(Proveedor prov) {
		this.prov = prov;
	}
	public float getGanancia() {
		return ganancia;
	}
	public void setGanancia(float ganancia) {
		this.ganancia = ganancia;
	}
}
