package com.ejb.beans;

import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataInstanciaServicioBasico;
import com.datatypes.DataServicio;
import com.datatypes.DataServicioBasico;
import com.ejb.beans.interfaces.ControladorServicioLocal;
import com.ejb.beans.interfaces.ControladorServicioRemote;
import com.entities.InstanciaServicio;
import com.entities.Servicio;
import com.entities.Vertical;
import com.utils.ControlErrores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class ControladorServicio
 */
@Stateless
public class ControladorServicio implements ControladorServicioRemote, ControladorServicioLocal {

	@PersistenceContext
	private EntityManager em;
	
	private ControlErrores Error = new ControlErrores();
	
	public ControladorServicio() {
	}
	
	@Override
	public List<DataServicioBasico> ObtenerServicios(String TipoDeVertical){ 
		System.out.println("+++ Obtener Servicios +++");
		System.out.println("TipoDeVertical: " + TipoDeVertical);
		List<DataServicioBasico> ListaDataServicios = new ArrayList<DataServicioBasico>();		
		//Verifico que exista la vertical
		Vertical vertical; 
		try{
			vertical = em.find(Vertical.class, TipoDeVertical);
		}catch(Exception e){
			return ListaDataServicios;
		}
		if(vertical == null){
			return ListaDataServicios;
		}
		//Existe, traigo las que esta activas
		List<Servicio> ListaServicios= vertical.getServicios();
		System.out.println("Lista: ");
		for(Servicio Servicio : ListaServicios){
			if(Servicio.getBorrado() == 0)
			{
				System.out.println(Servicio.getServicioNombre());
				ListaDataServicios.add(Servicio.getDataServicioBasico());
			}
			
		}		
		System.out.println("--- Obtener Servicios ---");
		return ListaDataServicios;
	}
	
	@Override
	public String CrearServicio(DataServicioBasico Servicio, String TipoVertical){
		Vertical vertical;
		try{
			vertical = em.find(Vertical.class, TipoVertical);
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.S1 ,TipoVertical);
		}
		if(vertical == null){
			return Error.ErrorCompuesto(Error.S1 ,TipoVertical);
		}	
		
		Servicio NuevoServicio = new Servicio(Servicio.getServicioId(), Servicio.getServicioNombre(), Servicio.getServicioPrecioHora(), Servicio.getServicioPrecioKM(), Servicio.getServicioTarifaBase(), null, vertical , null);
		NuevoServicio = vertical.addServicio(NuevoServicio);
		
		try{
			//em.persist(vertical);
			//em.flush();
			em.persist(NuevoServicio);
			em.flush();
			return Error.Ok;
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.G1 , e.getMessage());
		}
	}
	
	@Override
	public String EliminarServicio(int ServicioId){
		Servicio servicio;
		try{
			servicio = this.em.find(Servicio.class, ServicioId);
		}catch(Exception e){
			return Error.S2;
		}	
		if(servicio == null){
			return Error.S2;
		}
		//No se lo saca de la vertical porque es un borrado logico
		servicio.setBorrado(1);		
		return Persistir(servicio);
	}
	
	@Override
	public String ModificarServicio(DataServicioBasico Servicio){
		Servicio NuevoServicio;
		try{
			NuevoServicio = this.em.find(Servicio.class, Servicio.getServicioId());
		}catch(Exception e){
			return Error.S2;
		}
		if(NuevoServicio == null){
			return Error.S2;
		}
		NuevoServicio.setServicioNombre(Servicio.getServicioNombre());
		NuevoServicio.setServicioPrecioHora(Servicio.getServicioPrecioHora());
		NuevoServicio.setServicioPrecioKM(Servicio.getServicioPrecioKM());
		NuevoServicio.setServicioTarifaBase(Servicio.getServicioTarifaBase());		
		return Persistir(NuevoServicio);				
	}
	
	@Override
	public DataServicio ObtenerServicio(int ServicioId){
		DataServicio ds;
		try{
			ds = this.em.find(Servicio.class, ServicioId).getDataServicio();
		}catch(Exception e){	
			ds = new DataServicio();
			ds.setEstado(Error.GetJsonValor(Error.S2));
			return ds;
		}	
		if (ds == null){
			ds = new DataServicio();
			ds.setEstado(Error.GetJsonValor(Error.S2));
			return ds;
		}
		ds.setEstado(Error.GetJsonValor(Error.Ok));
		return ds;		
	}
	
	private String Persistir(Object Objeto)
	{
		try{
			em.flush();
			em.persist(Objeto);
			em.flush();
			return Error.Ok;
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.G1 , e.getMessage());
		}
	}
	
	public DataInstanciaServicioBasico ObtenerInstanciaServicio(int InstanciaServicioId){
		System.out.println("+++ ObtenerInstanciaServicio +++");
		System.out.println("InstanciaServicioId: " + InstanciaServicioId);	
		DataInstanciaServicioBasico ds;
		try{
			ds = this.em.find(InstanciaServicio.class, InstanciaServicioId).getDataInstanciaServicioBasico();
		}catch(Exception e){	
			ds = new DataInstanciaServicioBasico();
			ds.setEstado(Error.GetJsonValor(Error.S2));
			System.out.println("Error: " + Error.S2);
			System.out.println("--- ObtenerInstanciaServicio ---");
			return ds;
		}	
		if (ds == null){
			ds = new DataInstanciaServicioBasico();
			ds.setEstado(Error.GetJsonValor(Error.S2));
			System.out.println("Error: " + Error.S2);
			System.out.println("--- ObtenerInstanciaServicio ---");
			return ds;
		}
		ds.setEstado(Error.GetJsonValor(Error.Ok));
		System.out.println("DataInstanciaServicioId =" + ds.getInstanciaServicioId());
		System.out.println("DataInstanciaServicioCosto =" + ds.getInstanciaServicioCosto());
		System.out.println("--- ObtenerInstanciaServicio ---");
		return ds;		
	}
}
