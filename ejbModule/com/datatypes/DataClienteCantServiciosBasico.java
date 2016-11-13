package com.datatypes;

import com.entities.Cliente;

public class DataClienteCantServiciosBasico {

	private DataClienteBasico cliente;
	private int cantServicios;
	
	
	DataClienteCantServiciosBasico(){		
	}
	
	public DataClienteCantServiciosBasico(DataClienteBasico cliente, int cantServicios) {
		super();
		this.cliente = cliente;
		this.cantServicios = cantServicios;
	}
	public DataClienteBasico getCliente() {
		return cliente;
	}
	public void setCliente(DataClienteBasico cliente) {
		this.cliente = cliente;
	}
	public int getCantServicios() {
		return cantServicios;
	}
	public void setCantServicios(int cantServicios) {
		this.cantServicios = cantServicios;
	}
	
	
	
}
