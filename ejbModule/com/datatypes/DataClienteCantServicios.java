package com.datatypes;

import com.entities.Cliente;

public class DataClienteCantServicios {

	private Cliente cliente;
	private int cantServicios;
	
	
	DataClienteCantServicios(){		
	}
	
	public DataClienteCantServicios(Cliente cliente, int cantServicios) {
		super();
		this.cliente = cliente;
		this.cantServicios = cantServicios;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public int getCantServicios() {
		return cantServicios;
	}
	public void setCantServicios(int cantServicios) {
		this.cantServicios = cantServicios;
	}
	
	
	
}
