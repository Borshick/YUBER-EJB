package com.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.datatypes.DataAdministrador;
import com.datatypes.DataAdministradorBasico;
import com.datatypes.DataLogin;
import com.datatypes.DataVerticalBasico;
import com.ejb.beans.interfaces.ControlSistemaLocal;
import com.ejb.beans.interfaces.ControladorAdministradorLocal;
import com.ejb.beans.interfaces.ControladorAdministradorRemote;
import com.entities.Administrador;
import com.utils.ControlErrores;

@RequestScoped
@Path("/Admin")
public class ControladorRestAdministrador {

	
	@EJB
	private ControladorAdministradorLocal controlador;
	
	@EJB
	private ControlSistemaLocal sistema;

	@GET
	@Path("/ObtenerClientesActivos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerClientesActivos(){
		ResponseBuilder respuesta = Response.ok(controlador.ObtenerClientesActivos());
		return respuesta.build();
	}

	@GET
	@Path("/ObtenerProveedoresActivos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerProveedoresActivos(){
		ResponseBuilder respuesta = Response.ok(controlador.ObtenerProveedoresActivos());
		return respuesta.build();
	}

	@POST
	@Path("/CrearAdministrador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response CrearAdministrador(@QueryParam("correo") String Correo,@QueryParam("contrasena")String Contrasena,@QueryParam("nombre") String Nombre){
		ResponseBuilder respuesta = Response.ok(controlador.CrearAdministrador(Correo,Contrasena,Nombre));
		return respuesta.build();
	}

	@GET
	@Path("/EliminarAdministrador/{adminEmail}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response EliminarAdministrador(@PathParam ("adminEmail") String AdministradorEmail){
		ResponseBuilder respuesta = Response.ok(controlador.EliminarAdministrador(AdministradorEmail));
		return respuesta.build();
	}
	
	@POST
	@Path("/ModificarAdministrador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ModificarAdministrador(@QueryParam("correo") String Correo,@QueryParam("contrasena")String Contrasena,@QueryParam("nombre") String Nombre){
		ResponseBuilder respuesta = Response.ok(controlador.ModificarAdministrador(Correo,Contrasena,Nombre));
		return respuesta.build();
	}
	
	@GET
	@Path("/ObtenerAdministrador/{adminMail}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerAdministrador(@PathParam ("adminMail") String AdministradorEmail){
		ResponseBuilder respuesta = Response.ok(controlador.ObtenerAdministrador(AdministradorEmail),MediaType.APPLICATION_JSON);
		return respuesta.build();
	}

	@GET
	@Path("/ObtenerGananciaMensual/{mes}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerGananciaMensual(@PathParam ("mes") int mes){
		ResponseBuilder respuesta = Response.ok(controlador.ObtenerGananciaMensual(mes));
		return respuesta.build();
	}

	@GET
	@Path("/TopProveedoresPorPuntajes/{limit},{tipoVertical}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response TopProveedoresPorPuntajes(@PathParam ("limit") int Limit, @PathParam ("tipoVertical") String tipoVertical){
		ResponseBuilder respuesta = Response.ok(controlador.TopProveedoresPorPuntajes(Limit, tipoVertical));
		return respuesta.build();
	}

	@GET
	@Path("/TopProveedoresPorGanancia/{limit},{tipoVertical}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response TopProveedoresPorGanancia(@PathParam ("limit") int Limit, @PathParam ("tipoVertical") String tipoVertical){
		ResponseBuilder respuesta = Response.ok(controlador.TopProveedoresPorGanancia(Limit, tipoVertical));
		return respuesta.build();
	}

	@GET
	@Path("/TopClientesPorPuntaje/{limit},{tipoVertical}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response TopClientesPorPuntaje(@PathParam ("limit") int Limit, @PathParam ("tipoVertical") String tipoVertical){
		ResponseBuilder respuesta = Response.ok(controlador.TopClientesPorPuntaje(Limit, tipoVertical));
		return respuesta.build();
	}
	
	@GET
	@Path("/TopClientesPorCantServicios/{limit},{tipoVertical}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response TopClientesPorCantServicios(@PathParam ("limit") int Limit, @PathParam ("tipoVertical") String tipoVertical){
		ResponseBuilder respuesta = Response.ok(controlador.TopClientesPorCantServicios(Limit, tipoVertical));
		return respuesta.build();
	}
	

	@POST
	@Path("/CrearVertical")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response CrearVertical(@QueryParam("tipoVertical") String tipoVertical, @QueryParam("nombreVertical") String nombreVertical){
		String result = controlador.CrearVertical(tipoVertical,nombreVertical);
		ResponseBuilder respuesta =  Response.ok(result);
		return respuesta.build();
	}

	@GET
	@Path("/AsignarVertical/{adminCreadorId},{adminId},{tipoVertical}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response AsignarVertical(@PathParam ("adminCreadorId") String AdminCreadorId, @PathParam ("adminId") String AdminId, @PathParam ("tipoVertical") String TipoVertical){
		ResponseBuilder respuesta = Response.ok(controlador.AsignarVertical(AdminCreadorId, AdminId, TipoVertical));
		return respuesta.build();
	}

	@GET
	@Path("/DenegarVertical/{adminCreadorId},{adminId},{tipoVertical}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response DenegarVertical(@PathParam ("adminCreadorId") String AdminCreadorId, @PathParam ("adminId") String AdminId, @PathParam ("tipoVertical") String TipoVertical){
		ResponseBuilder respuesta = Response.ok(controlador.DenegarVertical(AdminCreadorId, AdminId, TipoVertical));
		return respuesta.build();
	}
	
	@GET
	@Path("/ListarVerticales")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ListarVerticales(){
		ResponseBuilder respuesta = Response.ok(controlador.ListarVerticales());
		return respuesta.build();
	}
	
	@POST
	@Path("/Login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Login(DataLogin datos){
		ResponseBuilder respuesta = Response.ok(sistema.Login(datos,"administrador"));
		return respuesta.build();
	}	
	
	@POST
	@Path("/Logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Logout(DataLogin datos){
		return ArmarResponse(sistema.Logout(datos.getCorreo(),"administrador"));
	}
	
	@GET
	@Path("/ObtenerAdministradores")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerAdministradores(){
		ResponseBuilder respuesta = Response.ok(controlador.ObtenerAdministradores());
		return respuesta.build();	
	}
	/*
	@GET
	@Path("/EnviarPush/{titulo},{mensaje},{dispositivo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Logout(@PathParam ("titulo") String titulo, @PathParam ("mensaje") String mensaje, @PathParam ("dispositivo") String dispositivo ){
		return ArmarResponse(sistema.EnviarPushNotification(titulo, mensaje, dispositivo));
	}
	*/
	
	private Response ArmarResponse(Object entrada){
		ResponseBuilder respuesta =Response.ok(entrada);
		/*respuesta.header("Access-Control-Allow-Origin", "*");
		respuesta.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		respuesta.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");		
		respuesta.header("Access-Control-Allow-Credentials", "true");
		respuesta.header("Access-Control-Max-Age", "1209600");*/
		return respuesta.build();
	}
}
