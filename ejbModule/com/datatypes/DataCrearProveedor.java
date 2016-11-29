package com.datatypes;

public class DataCrearProveedor {

	
	private String tipoVertical;
	private DataProveedorBasico proveedor;
	
	public DataCrearProveedor(){};
	
	public DataCrearProveedor(String tipoVertical, DataProveedorBasico proveedor) {
		super();
		this.tipoVertical = tipoVertical;
		this.proveedor = proveedor;
	}
	
	public String getTipoVertical() {
		return tipoVertical;
	}
	public void setTipoVertical(String tipoVertical) {
		this.tipoVertical = tipoVertical;
	}
	public DataProveedorBasico getProveedor() {
		return proveedor;
	}
	public void setProveedor(DataProveedorBasico proveedor) {
		this.proveedor = proveedor;
	}
	
}
