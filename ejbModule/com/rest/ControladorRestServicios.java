package com.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.datatypes.DataCrearServicio;
import com.datatypes.DataProveedorBasico;
import com.datatypes.DataServicio;
import com.datatypes.DataServicioBasico;
import com.datatypes.DataVerticalBasico;
import com.ejb.beans.interfaces.ControladorServicioLocal;
import com.ejb.beans.interfaces.ControladorServicioRemote;
import com.utils.ControlErrores;

@RequestScoped
@Path("/Servicios")
public class ControladorRestServicios {

	private ControlErrores Errores = new ControlErrores();
	
	@EJB
	private ControladorServicioLocal controlador;
	
	@GET
	@Path("/ObtenerServicios/{tipoDeVertical}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerServicios(@PathParam ("tipoDeVertical") String TipoDeVertical){
		return Response.ok(controlador.ObtenerServicios(TipoDeVertical)).build(); 
	}

	@POST
	@Path("/CrearServicio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response CrearServicio(DataCrearServicio Servicio){
		return Response.ok(controlador.CrearServicio(Servicio.getServicio(), Servicio.getTipoVertical())).build(); 
	}
	
	@POST
	@Path("/Demo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Demo(){
		DataCrearServicio dcs = new DataCrearServicio();
		dcs.setTipoVertical("TipoVertical1");
	/*	DataProveedorBasico dp = new DataProveedorBasico(pepe@gmail.com, pepe, perez, montevideo, pepe123, lala, 0, 56565656, 0);
		DataProveedorBasico dp2 = new DataProveedorBasico(pepe2@gmail.com, pepe2, perez, montevideo, pepe123, lala, 0, 56565656, 0);
		List<DataProveedorBasico> listadp = new ArrayList<DataProveedorBasico>();
		listadp.add(dp);
		listadp.add(dp2)*/
		DataServicioBasico ds = new DataServicioBasico(5, "servicio5", 55, 55, 20, 0);
		dcs.setServicio(ds);
		return Response.ok(dcs).build(); 
		}
	
	@POST
	@Path("/DemoPost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Demo(DataVerticalBasico dv){		
		return Response.ok(dv).build(); 
		}
	
	@GET
	@Path("/EliminarServicio/{servicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response EliminarServicio(@PathParam ("servicioId") int ServicioId){
		return Response.ok(controlador.EliminarServicio(ServicioId)).build(); 
	}

	@POST
	@Path("/ModificarServicio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ModificarServicio(DataServicioBasico Servicio){
		return Response.ok(controlador.ModificarServicio(Servicio)).build(); 
	}
	
	@GET
	@Path("/ObtenerServicio/{servicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerServicio(@PathParam ("servicioId") int ServicioId){
		return Response.ok(controlador.ObtenerServicio(ServicioId)).build(); 
	}

}
