package com.entities;

import com.datatypes.DataInstanciaServicioBasico;
import com.datatypes.DataProveedor;
import com.datatypes.DataProveedorBasico;
import com.datatypes.DataServicioBasico;
import com.entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@NamedQueries({
	@NamedQuery(query = "SELECT p FROM Proveedor p JOIN p.Servicio s JOIN s.Vertical v WHERE v.VerticalTipo = :Vertical ORDER BY p.UsuarioPromedioPuntaje DESC", name = "TopProveedoresPorPuntajes"),
	//@NamedQuery(query = "SELECT p , SUM(i.InstanciaServicioCosto) "
	//		+ "FROM Proveedor p JOIN p.InstanciasServicio i "
	//		+ "ORDER BY SUM(i.InstanciaServicioCosto) DESC", name = "TopProveedoresPorGanancia"),
	@NamedQuery(query = "SELECT p FROM Proveedor p JOIN p.Servicio s JOIN s.Vertical v WHERE v.VerticalTipo = :Vertical ORDER BY p.GananciaTotal DESC", name = "TopProveedoresPorGanancia"),	
	@NamedQuery(query = "SELECT p FROM Proveedor p", name = "ObtenerListaProveedoresEnCiudad"),//Falta el where segun ciudad
	
	
	//@NamedQuery(query = "SELECT p FROM Proveedor p INNER JOIN InstanciaServicio i ON (p.UsuarioCorreo = i.Proveedor.UsuarioCorreo) WHERE i.InstanciaServicioFechaFin >= :Fecha", name = "ObtenerProveedoresActivos")
})
public class Proveedor extends Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Servicio Servicio;
	@OneToMany(mappedBy="Proveedor", cascade=CascadeType.PERSIST)
	private List<InstanciaServicio> InstanciasServicio;
	private boolean Trabajando;
	private double Latitud;
	private double Longitud;
	private float GananciaTotal;
	private float PorCobrar;

	
	public Proveedor() {
		super();
	}
   
	public Proveedor (DataProveedor Proveedor){
		Usuario user = new Proveedor();	
		user.setUsuarioNombre(Proveedor.getUsuarioNombre());
		user.setUsuarioApellido(Proveedor.getUsuarioApellido());
		user.setUsuarioCiudad(Proveedor.getUsuarioCiudad());
		user.setUsuarioContraseña(Proveedor.getUsuarioContraseña());
		user.setUsuarioCorreo(Proveedor.getUsuarioCorreo());
		user.setUsuarioDireccion(Proveedor.getUsuarioDireccion());		
		user.setUsuarioPromedioPuntaje(0);
		user.setUsuarioTelefono(Proveedor.getUsuarioTelefono());
		this.Trabajando	= Proveedor.isTrabajando();
	}
	
	public DataProveedor getDataProveedor(){		
		List<DataInstanciaServicioBasico> ListaInstancias = new ArrayList<DataInstanciaServicioBasico>();
		for(InstanciaServicio Instancia : this.getInstanciasServicio())
		{
			DataInstanciaServicioBasico DataInstanciaServicio = Instancia.getDataInstanciaServicioBasico();
			ListaInstancias.add(DataInstanciaServicio);
		}	
		DataServicioBasico DataServicio;
		if (this.Servicio == null)
			DataServicio = null;
		else
			DataServicio = this.Servicio.getDataServicioBasico();
		return new DataProveedor(
								this.getUsuarioCorreo(), 
								this.getUsuarioNombre(), 
								this.getUsuarioApellido(), 
								this.getUsuarioCiudad(), 
								this.getUsuarioContraseña(), 
								this.getUsuarioDireccion(), 
								this.getUsuarioPromedioPuntaje(), 
								this.getUsuarioTelefono(), 
								this.isTrabajando(),
								this.GananciaTotal,
								this.PorCobrar,
								ListaInstancias,
								DataServicio
							   );
	}
	
	public DataProveedorBasico getDataProveedorBasico(){		
		return new DataProveedorBasico(
								this.getUsuarioCorreo(), 
								this.getUsuarioNombre(), 
								this.getUsuarioApellido(), 
								this.getUsuarioCiudad(), 
								this.getUsuarioContraseña(), 
								this.getUsuarioDireccion(), 
								this.getUsuarioPromedioPuntaje(), 
								this.getUsuarioTelefono(),
								this.isTrabajando(),
								this.GananciaTotal,
								this.PorCobrar
							   );
	}
	
	public Servicio getServicio() {
		return this.Servicio;
	}

	
	
	public float getGananciaTotal() {
		return GananciaTotal;
	}

	public void setGananciaTotal(float gananciaTotal) {
		GananciaTotal = gananciaTotal;
	}

	public float getPorCobrar() {
		return PorCobrar;
	}

	public void setPorCobrar(float porCobrar) {
		PorCobrar = porCobrar;
	}

	public void setServicio(Servicio Servicio) {
		this.Servicio = Servicio;
	}
	
	public List<InstanciaServicio> getInstanciasServicio() {
		return this.InstanciasServicio;
	}

	public void setInstanciasServicio(List<InstanciaServicio> instanciaServicios) {
		this.InstanciasServicio = instanciaServicios;
	}

	public InstanciaServicio addInstanciaServicio(InstanciaServicio instanciaServicio) {		
		System.out.println("Agregando instancia "+instanciaServicio.getInstanciaServicioId()+" al proveedor "+ this.getUsuarioNombre());
		getInstanciasServicio().add(instanciaServicio);
		instanciaServicio.setProveedor(this);		
		System.out.println("Lista de instancias de servicio del proveedor: ");
		for(InstanciaServicio is : getInstanciasServicio())
		{
			System.out.println(is.getInstanciaServicioId());
		}		
		return instanciaServicio;
	}

	public InstanciaServicio removeInstanciaServicio(InstanciaServicio instanciaServicio) {
		getInstanciasServicio().remove(instanciaServicio);
		instanciaServicio.setProveedor(null);
		return instanciaServicio;
	}

	public boolean isTrabajando() {
		return Trabajando;
	}

	public void setTrabajando(boolean trabajando) {
		Trabajando = trabajando;
	}
	public double getLatitud() {
		return Latitud;
	}

	public void setLatitud(double latitud) {
		Latitud = latitud;
	}

	public double getLongitud() {
		return Longitud;
	}

	public void setLongitud(double longitud) {
		Longitud = longitud;
	}

}
