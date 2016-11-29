package com.datatypes;

public class DataCrearCliente {

	
	private String tipoVertical;
	private DataClienteBasico cliente;
	
	public DataCrearCliente(){};
	
	public DataCrearCliente(String tipoVertical, DataClienteBasico cliente) {
		super();
		this.tipoVertical = tipoVertical;
		this.cliente = cliente;
	}
	
	public String getTipoVertical() {
		return tipoVertical;
	}
	public void setTipoVertical(String tipoVertical) {
		this.tipoVertical = tipoVertical;
	}
	public DataClienteBasico getCliente() {
		return cliente;
	}
	public void setCliente(DataClienteBasico cliente) {
		this.cliente = cliente;
	}
	
}
