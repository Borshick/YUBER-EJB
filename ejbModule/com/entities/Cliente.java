package com.entities;

import com.datatypes.DataCliente;
import com.datatypes.DataClienteBasico;
import com.datatypes.DataInstanciaServicioBasico;
import com.datatypes.DataVerticalBasico;
import com.entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@NamedQueries({
	@NamedQuery(query = "SELECT DISTINCT c FROM Cliente c WHERE c.Vertical = :VerticalTipo ORDER BY c.UsuarioPromedioPuntaje DESC", name = "TopClientesPorPuntaje"),
	@NamedQuery(query = "SELECT DISTINCT c FROM Cliente c JOIN c.InstanciasServicio i WHERE c.Vertical = :VerticalTipo ORDER BY COUNT(i.InstanciaServicioId) DESC", name = "TopClientesPorCantServicios"),
	
	//@NamedQuery(query = "SELECT c FROM Cliente c INNER JOIN InstanciaServicio i ON (c.UsuarioCorreo = i.Cliente.UsuarioCorreo) WHERE i.InstanciaServicioFechaFin >= :Fecha", name = "ObtenerClientesActivos")
})
public class Cliente extends Usuario implements Serializable {
	
	@OneToMany(mappedBy="Cliente", cascade=CascadeType.PERSIST)
	private List<InstanciaServicio> InstanciasServicio;	
	private static final long serialVersionUID = 1L;
	public Cliente() {
		super();
	}
		
    public DataCliente getDataCliente(){		
		List<DataInstanciaServicioBasico> ListaInstancias = new ArrayList<DataInstanciaServicioBasico>();
		for(InstanciaServicio Instancia : InstanciasServicio)
		{
			DataInstanciaServicioBasico DataInstanciaServicio = Instancia.getDataInstanciaServicioBasico();
			ListaInstancias.add(DataInstanciaServicio);
		}		
		DataVerticalBasico DataVertical;
		if (this.getVertical() == null)
			DataVertical = null;
		else
			DataVertical = this.getVertical().getDataVerticalBasico();
		return new DataCliente(	this.getUsuarioCorreo(), 
								this.getUsuarioNombre(), 
								this.getUsuarioApellido(), 
								this.getUsuarioCiudad(), 
								this.getUsuarioContraseña(), 
								this.getUsuarioDireccion(), 
								this.getUsuarioPromedioPuntaje(), 
								this.getUsuarioTelefono(), 
								ListaInstancias,
								DataVertical
							   );
	}
    
    public DataClienteBasico getDataClienteBasico(){		
		return new DataClienteBasico(	this.getUsuarioCorreo(), 
								this.getUsuarioNombre(), 
								this.getUsuarioApellido(), 
								this.getUsuarioCiudad(), 
								this.getUsuarioContraseña(), 
								this.getUsuarioDireccion(), 
								this.getUsuarioPromedioPuntaje(), 
								this.getUsuarioTelefono()
							   );
	}
    
    public InstanciaServicio addInstanciaServicio(InstanciaServicio instanciaServicio) {
		getInstanciasServicio().add(instanciaServicio);
		instanciaServicio.setCliente(this);
		return instanciaServicio;
	}

	public InstanciaServicio removeInstanciaServicio(InstanciaServicio instanciaServicio) {
		getInstanciasServicio().remove(instanciaServicio);
		instanciaServicio.setCliente(null);
		return instanciaServicio;
	}
    
	public List<InstanciaServicio> getInstanciasServicio() {
		return this.InstanciasServicio;
	}

	public void setInstanciasServicio(List<InstanciaServicio> instanciaServicios) {
		this.InstanciasServicio = instanciaServicios;
	}
   
}
