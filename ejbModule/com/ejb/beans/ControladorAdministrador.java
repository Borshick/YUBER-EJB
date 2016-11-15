package com.ejb.beans;

import com.datatypes.DataAdministrador;
import com.datatypes.DataAdministradorBasico;
import com.datatypes.DataClienteBasico;
import com.datatypes.DataClienteCantServicios;
import com.datatypes.DataClienteCantServiciosBasico;
import com.datatypes.DataProveedor;
import com.datatypes.DataProveedorBasico;
import com.datatypes.DataProveedorGanancia;
import com.datatypes.DataVertical;
import com.datatypes.DataVerticalBasico;
import com.ejb.beans.interfaces.ControladorAdministradorLocal;
import com.ejb.beans.interfaces.ControladorAdministradorRemote;
import com.entities.Administrador;
import com.entities.Cliente;
import com.entities.InstanciaServicio;
import com.entities.Proveedor;
import com.entities.Vertical;
import com.utils.ControlErrores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class ControladorAdministrador
 */
@Stateless
public class ControladorAdministrador implements ControladorAdministradorRemote, ControladorAdministradorLocal {

	@PersistenceContext
	private EntityManager em;
	private ControlErrores Error = new ControlErrores();
	
	public ControladorAdministrador() {
	}

	@Override
	public void Login(String AdministradorEmail, String Password){					
	}
	
	@Override
	public List<DataClienteBasico> ObtenerClientesActivos(){
		//Se considera clientes activos aquellos que tienen
	/*	//instancia_servicio con menos de 30 dias 
		Date fechaHoy = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaHoy); 
		calendar.add(Calendar.MONTH, -1);
		Date fecha = calendar.getTime();
		 
		List<DataClienteBasico> ListaDataClientes = new ArrayList<DataClienteBasico>();
		Query query = em.createNamedQuery("ObtenerClientesActivos", Cliente.class);
		query.setParameter("Fecha", fecha);
		List<Cliente> ListaCliente = query.getResultList();
		for(Cliente Cliente : ListaCliente){
			ListaDataClientes.add(Cliente.getDataClienteBasico());
		}*/
		return null;
	}
	
	@Override
	public List<DataProveedorBasico> ObtenerProveedoresActivos(){
		//Se considera provedores activos aquellos que tienen
		//instancia_servicio con menos de 30 dias
		/*Date fechaHoy = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaHoy); 
		calendar.add(Calendar.MONTH, -1);  
		Date fecha = calendar.getTime();
		  
		List<DataProveedorBasico> ListaDataProveedores = new ArrayList<DataProveedorBasico>();
		Query query = em.createNamedQuery("ObtenerProveedoresActivos", Proveedor.class);
		query.setParameter("Fecha", fecha);
		List<Proveedor> ListaProveedor = query.getResultList();		
		for(Proveedor Proveedor : ListaProveedor){
			ListaDataProveedores.add(Proveedor.getDataProveedorBasico());
		}		*/
		return null;		
	}
	
	@Override
	public String CrearAdministrador(String Correo, String Contrasena, String Nombre){
		try{			
			Administrador Admin = new Administrador(Correo, Contrasena, Nombre, null);
			String Result = Persistir(Admin);
			if(!Result.equals(Error.Ok)){
				return Error.ErrorCompuesto(Error.A53, Correo);
			}
			return Result;
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.A53, Correo);
		}
	}
	
	@Override
	public String EliminarAdministrador(String AdministradorEmail){
		try{
			Administrador Admin = this.em.find(Administrador.class, AdministradorEmail);
			for(Vertical Vert : Admin.getVerticales())
			{				
				String Result = this.DenegarVertical("FullAccess", AdministradorEmail, Vert.getVerticalTipo());
				if(Result != Error.Ok)
					try{
						em.remove(Admin);
						em.flush();
						return Result;
					}catch(Exception e){
						return Error.G1;
					}					
			}
			try{
				em.remove(Admin);
				em.flush();
				return Error.Ok;
			}catch(Exception e){
				return Error.G1;
			}			
		}catch(Exception e){
			return Error.A52;
		}	
	}
	
	@Override
	public String ModificarAdministrador(String Correo, String Contrasena, String Nombre){
		Administrador Admin;
		try{			
				Admin = this.em.find(Administrador.class, Correo);
		}catch(Exception e){
			return Error.A52;
		}
		if (Admin != null)
		{
			Admin.setAdministradorContraseña(Contrasena);
			Admin.setAdministradorNombre(Nombre);		
			return Persistir(Admin);
		}
		else
		{
			return Error.A52;
		}
		
	}
	
	public List<DataAdministradorBasico> ObtenerAdministradores()
	{
		List<DataAdministradorBasico> ListaDataAdministradorBasico = new ArrayList<DataAdministradorBasico>();
		List<Administrador> ListaAdministrador = em.createQuery(
				"SELECT a FROM Administrador a", Administrador.class).getResultList();
		for (Administrador Administrador : ListaAdministrador){ 
			DataAdministradorBasico dc = Administrador.getDataAdministradorBasico();
			ListaDataAdministradorBasico.add(dc);
		}
		return ListaDataAdministradorBasico;
		
	}
	
	@Override
	public DataAdministrador ObtenerAdministrador(String AdministradorEmail){	
		DataAdministrador DAdmin;
			try{
				DAdmin = this.em.find(Administrador.class, AdministradorEmail).getDataAdministrador();
				if(DAdmin == null)
				{
					DAdmin = new DataAdministrador();
					DAdmin.setEstado(Error.A52);
				}
			}catch(Exception e){
				DAdmin = new DataAdministrador();
				DAdmin.setEstado(Error.A52);
			}
			return DAdmin;
	}	
	
	@Override
	public float ObtenerGananciaMensual(int mes){		  
		//obtengo el primer dia del mes
		Date fechaInicio = new Date(2016, mes, 1);
		//Calulo el ultimo dia del mes		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio); 
		calendar.add(Calendar.MONTH, + 1);  
		calendar.add(Calendar.DATE, - 1);  
		Date fechaFin = calendar.getTime();
		//Hago la consulta		
		Query query = em.createNamedQuery("ObtenerGananciaMensual", InstanciaServicio.class);
		query.setParameter("FechaInicio", fechaInicio);
		query.setParameter("FechaFin", fechaFin);		
		List<InstanciaServicio> ListaInstanciaServicio = query.getResultList();
		float ganancia = 0;
		for(InstanciaServicio is : ListaInstanciaServicio){
			ganancia += is.getInstanciaServicioCosto();
		}		
		return ganancia;
	}
	
	@Override
	public List<DataProveedorBasico> TopProveedoresPorPuntajes(int Limit, String Vertical){
		List<DataProveedorBasico> ListaDataProveedores = new ArrayList<DataProveedorBasico>();
		Query query = em.createNamedQuery("TopProveedoresPorPuntajes", Proveedor.class);
		query.setParameter("Vertical", Vertical);
		query.setMaxResults(Limit);	
		List<Proveedor> ListaProveedor = query.getResultList();
		for(Proveedor prov : ListaProveedor){			
			ListaDataProveedores.add(prov.getDataProveedorBasico());
		}		
		return ListaDataProveedores;
	}
	
	@Override
	public List<DataProveedorBasico> TopProveedoresPorGanancia(int Limit, String Vertical){
		List<DataProveedorBasico> ListaDataProveedores = new ArrayList<DataProveedorBasico>();
		Query query = em.createNamedQuery("TopProveedoresPorGanancia", Proveedor.class);
		query.setParameter("Vertical", Vertical);
		query.setMaxResults(Limit);	
		//List<DataProveedorGanancia> ListaDataProveedorGanancia = query.getResultList();
		List<Proveedor> ListaProveedor = query.getResultList();
		for(Proveedor prov : ListaProveedor){
			ListaDataProveedores.add(prov.getDataProveedorBasico());
		}		
		return ListaDataProveedores;
	}
	
	public List<DataClienteCantServiciosBasico> TopClientesPorCantServicios(int Limit, String Vertical){
		List<DataClienteCantServiciosBasico> ListaDataClientes = new ArrayList<DataClienteCantServiciosBasico>();
		Query query = em.createNamedQuery("TopClientesPorCantServicios",Cliente.class);
		query.setParameter("VerticalTipo", Vertical);
		query.setMaxResults(Limit);	
		List<Cliente> ListaClientes = query.getResultList();
		System.out.println("Tamano clientes por cant servicios: " + ListaClientes.size());
		for(Cliente user : ListaClientes){
			DataClienteCantServiciosBasico Dcb = new DataClienteCantServiciosBasico(user.getDataClienteBasico(),user.getInstanciasServicio().size());
			ListaDataClientes.add(Dcb);
		}		
		return ListaDataClientes;
	}

	@Override
	public List<DataClienteCantServiciosBasico> TopClientesPorPuntaje(int Limit, String Vertical){
		List<DataClienteCantServiciosBasico> ListaDataClientes = new ArrayList<DataClienteCantServiciosBasico>();
		Query query = em.createNamedQuery("TopClientesPorPuntaje", Cliente.class);
		query.setParameter("VerticalTipo", Vertical);
		query.setMaxResults(Limit);	
		List<Cliente> ListaClientes = query.getResultList();
		System.out.println("Tamano clientes por puntaje: " + ListaClientes.size());
		for(Cliente user : ListaClientes){
			DataClienteCantServiciosBasico Dcb = new DataClienteCantServiciosBasico(user.getDataClienteBasico(),user.getInstanciasServicio().size());
			ListaDataClientes.add(Dcb);
		}		
		return ListaDataClientes;		
	}	
	
	@Override
	public String CrearVertical(String verticalTipo, String verticalNombre){
		try{
			Vertical NuevaVertical = new Vertical(verticalTipo, verticalNombre, null, null, null);	
			em.persist(NuevaVertical);
			em.flush();
			return Error.Ok;
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.V50, verticalTipo);
		}
	}
	
	@Override
	public String AsignarVertical(String AdminCreadorId, String AdminId, String TipoVertical){
		boolean Habilitado = false;
		if(AdminCreadorId.equalsIgnoreCase("FullAccess"))
		{
			Habilitado = true;
		}
		else
		{
			DataAdministrador AdminCreador;
			try{
				AdminCreador = this.em.find(Administrador.class, AdminCreadorId).getDataAdministrador();
				if(AdminCreador == null)
					return Error.A52;
			}catch(Exception e){
				return Error.A52;
			}
			for (DataVerticalBasico DataVertical : AdminCreador.getVerticales())
			{
				if (DataVertical.getVerticalTipo().equalsIgnoreCase(TipoVertical))
				{
					Habilitado = true;
				}
			}
		}
		if (Habilitado)
		{
			Administrador Admin;
			try{
				 Admin = this.em.find(Administrador.class, AdminId);
				if(Admin == null)
					return Error.A52;
			}catch(Exception e){
				return Error.A52;
			}
			
			Vertical Vertical;
			try{
				Vertical = this.em.find(Vertical.class, TipoVertical);
				if(Vertical == null)
					return Error.V51;
			}catch(Exception e){
				return Error.V51;
			}
			
			List<Administrador> ListaAdmin = Vertical.getAdministradores();
			ListaAdmin.add(Admin);
			Vertical.setAdministradores(ListaAdmin);
			
			List<Vertical> ListaVerticales = Admin.getVerticales();
			ListaVerticales.add(Vertical);
			Admin.setVerticales(ListaVerticales);
			
			return Persistir(Admin);
		}
		else
		{
			return Error.A50;
		}
		
				
	}
	
	@Override
	public String DenegarVertical(String AdminCreadorId, String AdminId, String TipoVertical){
		boolean Habilitado = false;
		if(AdminCreadorId.equalsIgnoreCase("FullAccess"))
		{
			Habilitado = true;
		}
		else
		{
			DataAdministrador AdminCreador;
			try{
				AdminCreador = this.em.find(Administrador.class, AdminCreadorId).getDataAdministrador();
				if(AdminCreador == null)
					return Error.A52;
			}catch(Exception e){
				return Error.A52;
			}		
			for (DataVerticalBasico DataVertical : AdminCreador.getVerticales())
			{
				if (DataVertical.getVerticalTipo().equalsIgnoreCase(TipoVertical))
				{
					Habilitado = true;
				}
			}
		}
		if (Habilitado)
		{
			Administrador Admin;
			try{
				 Admin = this.em.find(Administrador.class, AdminId);
				if(Admin == null)
					return Error.A52;
			}catch(Exception e){
				return Error.A52;
			}
			
			Vertical Vertical;
			try{
				Vertical = this.em.find(Vertical.class, TipoVertical);
				if(Vertical == null)
					return Error.V51;
			}catch(Exception e){
				return Error.V51;
			}
			
			List<Administrador> ListaAdmin = Vertical.getAdministradores();
			if(ListaAdmin.remove(Admin))			
				Vertical.setAdministradores(ListaAdmin);
			else
				return Error.A54;
			
			List<Vertical> ListaVerticales = Admin.getVerticales();
			if(ListaVerticales.remove(Vertical))
				Admin.setVerticales(ListaVerticales);
			else
				return Error.V52;
			
			return Persistir(Admin);
		}
		else
		{
			return Error.A51;
		}
	}
	
	public List<DataVertical> ListarVerticales(){
		List<DataVertical> ListaDataverticales = new ArrayList<DataVertical>();
		List<Vertical> ListaVerticales = em.createQuery(
				"SELECT v FROM Vertical v", Vertical.class).getResultList();
		for (Vertical Vertical : ListaVerticales){ 
			DataVertical dv = Vertical.getDataVertical();
			ListaDataverticales.add(dv);
		}	
		return ListaDataverticales;	
	}
	
	private String Persistir(Object Objeto)
	{
		try{
			em.persist(Objeto);
			em.flush();
			return Error.Ok;
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.G1 , e.getMessage());
		}
	}
	
	
	
}
