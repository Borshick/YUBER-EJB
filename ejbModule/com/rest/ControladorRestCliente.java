package com.rest;

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
import javax.ws.rs.core.Response.ResponseBuilder;

import com.datatypes.DataClienteBasico;
import com.datatypes.DataCrearCliente;
import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataLogin;
import com.datatypes.DataPedirServicio;
import com.datatypes.DataUbicacion;
import com.ejb.beans.interfaces.ControlSistemaLocal;
import com.ejb.beans.interfaces.ControladorClienteLocal;
//import com.utils.ControlErrores;

@RequestScoped
@Path("/Cliente")
public class ControladorRestCliente {

	//private ControlErrores Errores = new ControlErrores();
	
	@EJB
	private ControladorClienteLocal controlador;
	
	@EJB
	private ControlSistemaLocal sistema;
	
	@GET
	@Path("/PuntuarCliente/{puntaje},{comentario},{instanciaServicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response PuntuarCliente(@PathParam ("puntaje") int Puntaje, @PathParam ("comentario") String Comentario, @PathParam ("instanciaServicioId") int InstanciaServicioId){
		String resultado = controlador.PuntuarCliente(Puntaje, Comentario, InstanciaServicioId);
		return ArmarResponse(resultado);
	}
	
	@POST
	@Path("/RegistrarCliente")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response RegistrarCliente(DataCrearCliente Cliente){
		System.out.println("Registrando cliente..." + Cliente.getCliente().getUsuarioCorreo());		 
		String resultado = controlador.RegistrarCliente(Cliente.getCliente(), Cliente.getTipoVertical());
		return ArmarResponse(resultado);  
	}
	
	/*@POST
	@Path("/RegistrarClientePlano")
	public Response RegistrarClientePlano(DataClienteBasico Cliente){
		String resultado = controlador.RegistrarCliente(Cliente);
		ResponseBuilder respuesta = Response.ok(resultado);
		respuesta.header("Access-Control-Allow-Origin", "*");
		return respuesta.build();
	}
	*/

	/*
	@POST
	@Path("/RegistrarClienteTest")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response RegistrarClienteTest(){
		DataClienteBasico Cliente = new DataClienteBasico("maxi@hotmail.com", "maxi", "barnech", "montevideo", "123", "lsls123", 0, "050505050");
		String resultado = controlador.RegistrarCliente(Cliente);
		return Response.ok(resultado).build();
	}*/
	
	
	@GET
	@Path("/ObtenerClientes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerClientes(){		
		return Response.ok(controlador.ObtenerClientes()).build();
	}
	
	@GET
	@Path("/ObtenerHistorial/{clienteCorreo},{servicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerHistorial(@PathParam ("clienteCorreo") String ClienteCorreo, @PathParam ("servicioId") int ServicioId){
		List<DataInstanciaServicio> resultado = controlador.ObtenerHistorial(ClienteCorreo, ServicioId);
		return ArmarResponse(resultado);
	}
	
	@GET
	@Path("/MisReseñasObtenidas/{clienteCorreo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response MisReseñasObtenidas(@PathParam ("clienteCorreo") String ClienteCorreo){
		return ArmarResponse(controlador.MisReseñasObtenidas(ClienteCorreo));
		
	}
	
	@GET
	@Path("/MisReseñasObtenidas/{clienteCorreo},{servicio}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response MisReseñasObtenidas(@PathParam ("clienteCorreo") String ClienteCorreo,@PathParam ("servicio") int servicioId){
		return ArmarResponse(controlador.MisReseñasObtenidas(ClienteCorreo, servicioId));
	}
	
	@GET
	@Path("/CancelarPedido/{instanciaServicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response CancelarPedido(@PathParam ("instanciaServicioId") int InstanciaServicioId){
		return ArmarResponse(controlador.CancelarPedido(InstanciaServicioId));
	}
	
	@GET
	@Path("/OlvidePass/{clienteCorreo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response OlvidePass(@PathParam ("clienteCorreo") String ClienteCorreo){
		return ArmarResponse(controlador.OlvidePass(ClienteCorreo));
		
	}	
	
	@GET
	@Path("/AsociarMecanismoDePago/{clienteCorreo},{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response AsociarMecanismoDePago(@PathParam ("clienteCorreo") String ClienteCorreo, @PathParam ("token") String Token){
		return ArmarResponse(controlador.AsociarMecanismoDePago(ClienteCorreo, Token));
	}
	
	@POST
	@Path("/PedirServicio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response PedirServicio(DataPedirServicio datos){
		System.out.println("getCorreo : " +datos.getCorreo());
		System.out.println("getServicioId : " +datos.getServicioId());
		System.out.println("getLatitud : " +datos.getUbicacion().getLatitud());
		System.out.println("getLongitud : " +datos.getUbicacion().getLongitud());
		return ArmarResponse(controlador.PedirServicio(datos.getCorreo(), datos.getServicioId(), datos.getUbicacion()));
	}
	
	/*@GET
	@Path("/ObtenerDataPedirServicio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerDataPedido(){
		
		DataPedirServicio dps = new DataPedirServicio();
		dps.setCorreo("maxi@hotmail.com");
		dps.setServicioId(2);
		DataUbicacion du = new DataUbicacion();
		du.setEstado("Ok");
		du.setLatitud("1515.15.256");
		du.setLongitud("8989.265.2567");
		dps.setUbicacion(du);
		return Response.ok(dps).build();
	}	*/
	
	@POST
	@Path("/Login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Login(DataLogin datos){
		System.out.println("Haciendo login..." + datos.getCorreo());
		

		/*Gson clienteJSON = new Gson();
		clienteJSON.toJsonTree(datos);
		
		sistema.EnviarPushNotification("Nuevo usuario", "Bienvenido" + clienteJSON, datos.getDeviceId());
		
		*/
		
		
		return ArmarResponse(sistema.Login(datos,"cliente"));
	}	
	
	@POST
	@Path("/Logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Logout(DataLogin datos){
		return ArmarResponse(sistema.Logout(datos.getCorreo(),"cliente"));
	}
	
	@GET
	@Path("/ValidarSesion/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ValidarSesion(@PathParam ("token") String token){
		return ArmarResponse(sistema.ValidarSesion(token));
	}
	
	@GET
	@Path("/AgregarDestino/{instanciaServicioId},{latitud},{longitud}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response AgregarDestino(@PathParam ("instanciaServicioId") int instaniaServicioId, @PathParam ("latitud") double latitud, @PathParam ("longitud") double longitud){
		String resultado = controlador.AgregarDestino(instaniaServicioId, latitud, longitud);
		return ArmarResponse(resultado);		
	}

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
