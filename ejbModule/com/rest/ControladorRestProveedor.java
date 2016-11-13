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

import com.datatypes.DataLogin;
import com.datatypes.DataProveedor;
import com.datatypes.DataProveedorBasico;
import com.ejb.beans.ControlSistema;
import com.ejb.beans.interfaces.ControlSistemaLocal;
import com.ejb.beans.interfaces.ControladorProveedorLocal;
import com.ejb.beans.interfaces.ControladorProveedorRemote;
import com.google.gson.Gson;
import com.utils.ControlErrores;

@RequestScoped
@Path("/Proveedor")
public class ControladorRestProveedor {

	private ControlErrores Errores = new ControlErrores();
	
	@EJB
	private ControladorProveedorLocal controlador;
	
	@EJB
	private ControlSistemaLocal sistema;
	
	@GET
	@Path("/PuntuarProveedor/{puntaje},{comentario},{instanciaServicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response PuntuarProveedor(@PathParam ("puntaje") int Puntaje, @PathParam ("comentario") String Comentario, @PathParam ("instanciaServicioId") int InstanciaServicioId){
		ResponseBuilder respuesta =Response.ok(controlador.PuntuarProveedor(Puntaje, Comentario, InstanciaServicioId));
		respuesta.header("Access-Control-Allow-Origin", "*");
		return  respuesta.build();
	}
	
	@GET
	@Path("/ActualizarCoordenadas/{correo},{latitud},{longitud},{enViaje},{instanciaServicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ActualizarCoordenadas(@PathParam ("correo") String correo, @PathParam ("latitud") double latitud, @PathParam ("longitud") double longitud, @PathParam ("enViaje") boolean enViaje, @PathParam ("instanciaServicioId") int instanciaServicioId){
		ResponseBuilder respuesta =Response.ok(controlador.ActualizarCoordenadas(correo, latitud, longitud, enViaje, instanciaServicioId));
		respuesta.header("Access-Control-Allow-Origin", "*");
		return  respuesta.build();
	}
	
	@POST
	@Path("/RegistrarProveedor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response RegistrarProveedor(DataProveedorBasico Proveedor){
		ResponseBuilder respuesta =Response.ok(controlador.RegistrarProveedor(Proveedor));
		return respuesta.build();		
	}

	/*@GET
	@Path("/RegistrarProveedorTest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response RegistrarProveedorTest(){
		
		DataProveedorBasico dpb = new DataProveedorBasico("prov1@gmail.com", "prov1", "prov1apellido", "monteviceo", "prov1", "lala123", 0, "0525626956", false);
	
		ResponseBuilder respuesta =Response.ok(dpb);

		return respuesta.build();		
	}*/
	
	
	@POST
	@Path("/Login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Login(DataLogin datos){
		ResponseBuilder respuesta =Response.ok(sistema.Login(datos,"proveedor"));
		/*
		Gson provJSON = new Gson();
		provJSON.toJson(datos);
		
		sistema.EnviarPushNotification("Nuevo usuario", "Bienvenido" + provJSON, datos.getDeviceId());
		*/
		return ArmarResponse(sistema.Login(datos,"proveedor"));
	}	
	
	@POST
	@Path("/Logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Logout(DataLogin datos){
		return ArmarResponse(sistema.Logout(datos.getCorreo(),"proveedor"));
	}
	
	@GET
	@Path("/AceptarServicio/{instanciaServicioId},{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response AceptarServicio(@PathParam ("instanciaServicioId") int InstanciaServicioId, @PathParam ("correo") String Correo){
		return ArmarResponse(controlador.AceptarServicio(InstanciaServicioId, Correo));
	}
	
	@GET
	@Path("/RechazarServicio/{instanciaServicioId}")
	public Response RechazarServicio(@PathParam ("instanciaServicioId") int InstanciaServicioId){
		return ArmarResponse(controlador.RechazarServicio(InstanciaServicioId));
	}

	@GET
	@Path("/IniciarJornada/{proveedorCorreo},{servicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response IniciarJornada(@PathParam ("proveedorCorreo") String ProveedorCorreo, @PathParam ("servicioId") int ServicioId){
		return ArmarResponse(controlador.IniciarJornada(ProveedorCorreo, ServicioId));
	}
	@GET
	@Path("/FinalizarJornada/{proveedorCorreo},{servicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response FinalizarJornada(@PathParam ("proveedorCorreo") String ProveedorCorreo, @PathParam ("servicioId") int ServicioId){
		return ArmarResponse(controlador.FinalizarJornada(ProveedorCorreo, ServicioId));
	}
	@GET
	@Path("/MisReseñasObtenidas/{proveedorCorreo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response MisReseñasObtenidas(@PathParam ("proveedorCorreo") String ProveedorCorreo){
		return ArmarResponse(controlador.MisReseñasObtenidas(ProveedorCorreo));
	}

	@GET
	@Path("/RetirarFondos/{proveedorCorreo}")
	public void RetirarFondos(@PathParam ("proveedorCorreo") String ProveedorCorreo){
		controlador.RetirarFondos(ProveedorCorreo);
	}

	@GET
	@Path("/ObtenerHistorial/{proveedorCorreo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerHistorial(@PathParam ("proveedorCorreo") String ProveedorCorreo){
		return ArmarResponse(controlador.ObtenerHistorial(ProveedorCorreo));
	}

	@GET
	@Path("/NotificarArribo/{instanciaServicioId}")
	public void NotificarArribo(@PathParam ("instanciaServicioId") int InstanciaServicioId){
		controlador.NotificarArribo(InstanciaServicioId);
	}

	@GET
	@Path("/AsociarServicio/{proveedorCorreo},{servicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response AsociarServicio(@PathParam ("proveedorCorreo") String ProveedorCorreo, @PathParam ("servicioId") int ServicioId){
		return ArmarResponse(controlador.AsociarServicio(ProveedorCorreo, ServicioId));
	}

	@GET
	@Path("/IniciarServicio/{instanciaServicioId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response IniciarServicio(@PathParam ("instanciaServicioId") int InstanciaServicioId){
		return ArmarResponse(controlador.IniciarServicio(InstanciaServicioId));
	}
	
	@GET
	@Path("/FinServicio/{instanciaServicioId},{distancia}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response FinServicio(@PathParam ("instanciaServicioId") int InstanciaServicioId, @PathParam ("distancia") float Distancia){
		return ArmarResponse(controlador.FinServicio(InstanciaServicioId, Distancia));
	}

	@GET
	@Path("/OlvidePass/{proveedorCorreo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response OlvidePass(@PathParam ("proveedorCorreo") String ProveedorCorreo){
		return ArmarResponse(controlador.OlvidePass(ProveedorCorreo));
	}

	@GET
	@Path("/AsociarMecanismoDePago/{proveedorCorreo},{medioDePago}")
	@Produces(MediaType.APPLICATION_JSON)
	public void AsociarMecanismoDePago(@PathParam ("proveedorCorreo") String ProveedorCorreo,@PathParam ("medioDePago") String MedioDePago){
		controlador.AsociarMecanismoDePago(ProveedorCorreo, MedioDePago);
	}

	@GET
	@Path("/Cobrar/{proveedorCorreo}")
	@Produces(MediaType.APPLICATION_JSON)
	public void Cobrar(@PathParam ("proveedorCorreo") String ProveedorCorreo){
		controlador.Cobrar(ProveedorCorreo);
	}
		
	@GET
	@Path("/ObtenerProveedores")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ObtenerProveedores(){
		ResponseBuilder respuesta = Response.ok(controlador.ObtenerProveedores());
		return respuesta.build();	
	}
	
	@GET
	@Path("/ValidarSesion/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ValidarSesion(@PathParam ("token") String token){
		return ArmarResponse(sistema.ValidarSesion(token));
	}
	
	@GET
	@Path("/EstoyTrabajando/{proveedorCorreo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response EstoyTrabajando(@PathParam ("proveedorCorreo") String ProveedorCorreo){
		ResponseBuilder respuesta =Response.ok(controlador.EstoyTrabajando(ProveedorCorreo));
		return respuesta.build();		
	}
	
	private Response ArmarResponse(Object entrada){
		ResponseBuilder respuesta =Response.ok(entrada);
		//respuesta.header("Access-Control-Allow-Origin", "*");
		return respuesta.build();
	}
	
	
	
}
