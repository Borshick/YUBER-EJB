package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import com.datatypes.DataInstanciaServicioBasico;
import com.datatypes.DataProveedorBasico;
import com.datatypes.DataServicio;
import com.datatypes.DataServicioBasico;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(query = "SELECT s FROM Servicio s WHERE s.Borrado = 0 AND s.Vertical.VerticalTipo = :TipoVertical", name = "BuscarServiciosActivosDeVertical")
public class Servicio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ServicioId;
	private String ServicioNombre;
	private float ServicioPrecioHora;
	private float ServicioPrecioKM;
	private float ServicioTarifaBase;
	private int Borrado;
	@OneToMany(mappedBy="Servicio", cascade=CascadeType.PERSIST)
	private List<InstanciaServicio> InstanciasServicio;
	@ManyToOne
	private Vertical Vertical;
	@OneToMany(mappedBy="Servicio", cascade=CascadeType.PERSIST)
	private List<Proveedor> Proveedores;

	public Servicio() {
	}
	
	public Servicio(int servicioId, String servicioNombre, float servicioPrecioHora, float servicioPrecioKM,
		float servicioTarifaBase, List<InstanciaServicio> instanciasServicio, com.entities.Vertical vertical,
		List<Proveedor> proveedores) {
		super();
		//ServicioId = servicioId;
		ServicioNombre = servicioNombre;
		ServicioPrecioHora = servicioPrecioHora;
		ServicioPrecioKM = servicioPrecioKM;
		ServicioTarifaBase = servicioTarifaBase;
		InstanciasServicio = instanciasServicio;
		Vertical = vertical;
		Proveedores = proveedores;
		Borrado = 0;
	}
	
	public DataServicio getDataServicio(){
		List<DataInstanciaServicioBasico> ListaInstanciasServicio = new ArrayList<DataInstanciaServicioBasico>();
		for(InstanciaServicio Instancia : this.InstanciasServicio)
		{
			DataInstanciaServicioBasico DataInstanciaServicio = Instancia.getDataInstanciaServicioBasico();
			ListaInstanciasServicio.add(DataInstanciaServicio);
		}
		List<DataProveedorBasico> ListaProveedores = new ArrayList<DataProveedorBasico>();
		for(Proveedor Proveedor : this.Proveedores)
		{
			DataProveedorBasico DataProveedor = Proveedor.getDataProveedorBasico();
			ListaProveedores.add(DataProveedor);
		}		
		return new DataServicio(
								this.getServicioId(),
								this.getServicioNombre(),
								this.getServicioPrecioHora(),
								this.getServicioPrecioKM(),
								this.getServicioTarifaBase(),
								this.getBorrado(),
								ListaInstanciasServicio,
								this.Vertical.getDataVerticalBasico(),
								ListaProveedores
							   );
	}
	
	public DataServicioBasico getDataServicioBasico(){	
		return new DataServicioBasico(
								this.getServicioId(),
								this.getServicioNombre(),
								this.getServicioPrecioHora(),
								this.getServicioPrecioKM(),
								this.getServicioTarifaBase(),
								this.getBorrado()
							   );
	}
	public int getServicioId() {
		return this.ServicioId;
	}

	public void setServicioId(int ServicioId) {
		this.ServicioId = ServicioId;
	}

	public String getServicioNombre() {
		return this.ServicioNombre;
	}

	public void setServicioNombre(String ServicioNombre) {
		this.ServicioNombre = ServicioNombre;
	}

	public float getServicioPrecioHora() {
		return this.ServicioPrecioHora;
	}

	public void setServicioPrecioHora(float ServicioPrecioHora) {
		this.ServicioPrecioHora = ServicioPrecioHora;
	}

	public float getServicioPrecioKM() {
		return this.ServicioPrecioKM;
	}

	public void setServicioPrecioKM(float ServicioPrecioKM) {
		this.ServicioPrecioKM = ServicioPrecioKM;
	}

	public float getServicioTarifaBase() {
		return this.ServicioTarifaBase;
	}

	public void setServicioTarifaBase(float ServicioTarifaBase) {
		this.ServicioTarifaBase = ServicioTarifaBase;
	}

	public List<InstanciaServicio> getInstanciasServicio() {
		return this.InstanciasServicio;
	}

	public void setInstanciasServicio(List<InstanciaServicio> InstanciasServicio) {
		this.InstanciasServicio = InstanciasServicio;
	}

	public InstanciaServicio addInstanciaservicio(InstanciaServicio InstanciaServicio) {
		getInstanciasServicio().add(InstanciaServicio);
		InstanciaServicio.setServicio(this);
		return InstanciaServicio;
	}

	public InstanciaServicio removeInstanciaservicio(InstanciaServicio InstanciaServicio) {
		getInstanciasServicio().remove(InstanciaServicio);
		InstanciaServicio.setServicio(null);
		return InstanciaServicio;
	}

	public Vertical getVertical() {
		return this.Vertical;
	}

	public void setVertical(Vertical Vertical) {
		this.Vertical = Vertical;
	}

	public List<Proveedor> getProveedores() {
		return this.Proveedores;
	}

	public void setProveedores(List<Proveedor> Proveedores) {
		this.Proveedores = Proveedores;
	}

	public Proveedor addProveedor(Proveedor Proveedor) {
		getProveedores().add(Proveedor);
		Proveedor.setServicio(this);
		return Proveedor;
	}

	public Proveedor removeUsuario(Proveedor Proveedor) {
		getProveedores().remove(Proveedor);
		Proveedor.setServicio(null);
		return Proveedor;
	}

	public int getBorrado() {
		return Borrado;
	}

	public void setBorrado(int borrado) {
		Borrado = borrado;
	}

}