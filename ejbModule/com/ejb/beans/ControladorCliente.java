package com.ejb.beans;

import com.datatypes.DataCliente;
import com.datatypes.DataClienteBasico;
import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataInstanciaServicioBasico;
import com.datatypes.DataNotificacionNuevaSolicitud;
import com.datatypes.DataReseña;
import com.datatypes.DataUbicacion;
import com.ejb.beans.interfaces.ControlSistemaLocal;
import com.ejb.beans.interfaces.ControladorClienteLocal;
import com.ejb.beans.interfaces.ControladorClienteRemote;
import com.entities.Cliente;
import com.entities.InstanciaServicio;
import com.entities.Proveedor;
import com.entities.Reseña;
import com.entities.Servicio;
import com.entities.Vertical;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.utils.ControlErrores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Session Bean implementation class ControladorCliente
 */
@Stateless
public class ControladorCliente implements ControladorClienteRemote, ControladorClienteLocal {

	@PersistenceContext//(name= "ad_24afd021b5189d7")
	private EntityManager em;
	
	private ControlErrores Error = new ControlErrores();
	
	@EJB
	private ControlSistemaLocal sistema;

    public ControladorCliente() {
        // TODO Auto-generated constructor stub
    }

    @Override
	public String AsociarMecanismoDePago(String ClienteCorreo, String Token){	
    	System.out.println("+++AsociarMecanismoDePago_Cliente+++");
    	System.out.println("ClienteCorreo: " + ClienteCorreo);
    	System.out.println("Token: " + Token);
    	
    	Cliente cliente;
		try{
			cliente = (Cliente)em.find(Cliente.class, ClienteCorreo);
			if(cliente == null){
				System.out.println(Error.C52);
				System.out.println("---AsociarMecanismoDePago_Cliente---");
				return Error.C52;
			}
		}catch(Exception e){
			System.out.println(Error.C52 + e);
			System.out.println("---AsociarMecanismoDePago_Cliente---");
			return Error.C52;
		}
		em.flush();
		
		try{
			Stripe.apiKey ="sk_test_1cuTC5OBX6oCuibHLUTMg9TP";
			
			Map<String, Object> customerParams = new HashMap<String, Object>();
			customerParams.put("source", Token);
			customerParams.put("description", ClienteCorreo);

			Customer customer;	
			customer = Customer.create(customerParams);	
			
			cliente.setTokenDePago(customer.getId());			
			em.persist(cliente);
			em.flush();
			
			System.out.println("---AsociarMecanismoDePago_Cliente---");
			return Error.Ok;    	
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("---AsociarMecanismoDePago_Cliente---");
			return Error.C52;
		}
	}
	
	
	@Override
	public String CancelarPedido(int InstanciaServicioId){	
		System.out.println("+++ CacelarPedido +++");
		System.out.println("InstanciaServicioId: " + InstanciaServicioId);
		//Busco la InstanciaServicio
		InstanciaServicio is;
		try{
			is = (InstanciaServicio)em.find(InstanciaServicio.class, InstanciaServicioId);
			if(is == null){
				System.out.println(Error.I52);
				System.out.println("--- CacelarPedido ---");
				return Error.I52;
			}
		}catch(Exception e){
			System.out.println(Error.I52 + e.getMessage());
			System.out.println("--- CacelarPedido ---");
			return Error.I52;
		}
		em.flush();
		//Elimino la instancia en proveedor
		if (is.getProveedor() != null){
			System.out.println("Eliminando instancia en proveedor");
			Proveedor Proveedor = is.getProveedor();
			Proveedor.removeInstanciaServicio(is);	
			System.out.println("Eliminando instancia en cliente");
			Cliente Cliente = is.getCliente();
			Cliente.removeInstanciaServicio(is);
			
			ControlSistema sistema = new ControlSistema();			
			JSONObject json = new JSONObject(Cliente.getDataClienteBasico());
			
			System.out.println("Envio push -Solicitud cacelada- a proveedor");
			sistema.EnviarPushNotification("Solicitud cancelada", "Cliente: " + Cliente.getUsuarioNombre() ,json.toString(), Proveedor.getUsuarioSesionActiva().getSesionDeviceID());

		}
		//elimino la InstanciaServicio
		em.remove(is);
		System.out.println("--- CacelarPedido ---");
		return Error.Ok;
	}
		
	
/*	@Override
	public boolean Login(DataLogin Datos){	
		return true;
	}*/
	
	
	@Override
	public List<DataReseña> MisReseñasObtenidas(String ClienteCorreo){
		System.out.println("+++ MisReseñasObtenidas_Cliente +++");
		Cliente cliente;
		try{
			cliente = (Cliente)em.find(Cliente.class, ClienteCorreo);
			if(cliente == null){
				return null;
			}
		}catch(Exception e){
			return null;
		}
		em.flush();
		List<DataReseña> ListaReseña = new ArrayList<DataReseña>();
		List<InstanciaServicio> ListaInstanciaServicio = cliente.getInstanciasServicio();
		for(InstanciaServicio is : ListaInstanciaServicio){
			if(is.getReseñaCliente() != null){
				ListaReseña.add(is.getReseñaCliente().getDataReseña());
			}
		}
		System.out.println("--- MisReseñasObtenidas_Cliente ---");
		return ListaReseña;
	}
	
	public List<DataReseña> MisReseñasObtenidas(String ClienteCorreo, int ServicioId){
		System.out.println("+++MisReseñasObtenidas_ConServicioId+++");
		System.out.println("ServicioId: " + ServicioId);
		System.out.println("ClienteCorreo: " + ClienteCorreo);
		
		Cliente cliente;
		try{
			cliente = (Cliente)em.find(Cliente.class, ClienteCorreo);
			if(cliente == null){
				return null;
			}
		}catch(Exception e){
			return null;
		}
		em.flush();
		List<DataReseña> ListaReseña = new ArrayList<DataReseña>();
		List<InstanciaServicio> ListaInstanciaServicio = cliente.getInstanciasServicio();
		for(InstanciaServicio is : ListaInstanciaServicio){
			if(is.getServicio().getServicioId() == ServicioId && is.getReseñaCliente() != null){
				ListaReseña.add(is.getReseñaCliente().getDataReseña());
			}
		}
		System.out.println("Cantidad: " + ListaReseña.size());
		System.out.println("---MisReseñasObtenidas_ConServicioId---");		
		return ListaReseña;
	}
	
	@Override
	public List<DataCliente> ObtenerClientes(){	
		List<DataCliente> ListaDataCliente = new ArrayList<DataCliente>();
		List<Cliente> ListaCliente = em.createQuery(
				"SELECT u FROM Cliente u", Cliente.class).getResultList();
		for (Cliente Cliente : ListaCliente){ 
			DataCliente dc = Cliente.getDataCliente();
			ListaDataCliente.add(dc);
		}
		return ListaDataCliente;
	}
	
	
	@Override
	public List<DataInstanciaServicio> ObtenerHistorial(String ClienteCorreo, int ServicioId){		
		System.out.println("+++ ObtenerHistorial_Cliente +++");
		System.out.println("ClienteCorreo: "+ ClienteCorreo);
		
		Cliente cliente;
		try{
			cliente = (Cliente)em.find(Cliente.class, ClienteCorreo);
			if(cliente == null){
				return null;
			}
		}catch(Exception e){
			return null;
		}
		em.flush();
		List<DataInstanciaServicio> ListaDataInstanciaServicio = new ArrayList<DataInstanciaServicio>();		
		List<InstanciaServicio> ListaInstanciaServicio = cliente.getInstanciasServicio();
		System.out.println("Lista de instancias:");
		for (InstanciaServicio InstanciaServicio : ListaInstanciaServicio){ 
			DataInstanciaServicio DataInstanciaServicio = InstanciaServicio.getDataInstanciaServicio();
			ListaDataInstanciaServicio.add(DataInstanciaServicio);
			System.out.println("Instancia: "+DataInstanciaServicio.getInstanciaServicioId());
		}
		System.out.println("--- ObtenerHistorial_Cliente ---");
		return ListaDataInstanciaServicio;
	}
	
	
	@Override
	public boolean OlvidePass(String ClienteCorreo){	
		//************************ESTA INCOMPETO************************/
		//Retorna true si lo manda false en otro caso		
	    String para = ClienteCorreo;
	    String de = "martinperez_15@hotmail.com";
	    String host = "localhost";
	    Properties propiedades = System.getProperties();
	    propiedades.setProperty("mail.smtp.host", host);
	    Session sesion = Session.getDefaultInstance(propiedades);
		try{
			MimeMessage mensaje = new MimeMessage(sesion);
			mensaje.setFrom(new InternetAddress(de));
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
			mensaje.setSubject("Recuperación de la contraseña");
			Random rnd = new Random();
			int n = 100000 - 999999 + 1;
			int pass = rnd.nextInt() % n;
			String msgInicio = "<h1>YUBER</h1><br><p>La contraseña nueva para su cuenta es: '";
			String msgFin = "'. <br> ¡Gracias por utilizar el servicio!</p>";
			mensaje.setContent(msgInicio + pass + msgFin,"text/html");
			Transport.send(mensaje);
			//Cambio en la BD la pass
			Proveedor prov = (Proveedor)em.find(Proveedor.class, ClienteCorreo);
			em.flush();
			prov.setUsuarioContraseña(String.valueOf(pass));
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}
	
	
	@Override
	public String PedirServicio(String ClienteCorreo, int ServicioId, DataUbicacion DataUbicacion){
		System.out.println("+++ PedirServicio +++");
		System.out.println("ClienteCorreo: " + ClienteCorreo);
		System.out.println("ServicioId: " + ServicioId);
		System.out.println("DataUbicacionLatitud: " + DataUbicacion.getLatitud());
		System.out.println("DataUbicacionLongitud: " + DataUbicacion.getLongitud());
		
		//Busco el servicio
		Servicio Servicio = (Servicio)em.find(Servicio.class, ServicioId);
		em.flush();
		//Busco al cliente que lo solicito
		Cliente Cliente = (Cliente)em.find(Cliente.class, ClienteCorreo);
		em.flush();
		List<Proveedor> Proveedores = CalcularProveedoresCercanos(DataUbicacion, ServicioId);
		if(Proveedores.size() > 0)
		{
			//Creo la instancia servicio y le asocio dicho servicio
			InstanciaServicio is = new InstanciaServicio();
			is.setInstanciaServicioCosto(0);
			is.setInstanciaServicioDistancia(0);
			is.setInstanciaServicioFechaInicio(null);
			is.setInstanciaServicioFechaFin(null);
			is.setInstanciaServicioTiempo(0);
			is.setReseñaCliente(null);
			is.setReseñaProveedor(null);
			is.setProveedor(null);
			is.setCliente(Cliente);
			is.setServicio(Servicio);
			is.setLatitud(DataUbicacion.getLatitud());
			is.setLongitud(DataUbicacion.getLongitud());
			//Guardo el BD
			Cliente.addInstanciaServicio(is);
			em.persist(is);
			em.flush();
			
	
			DataNotificacionNuevaSolicitud DataNotificacion = new DataNotificacionNuevaSolicitud(is.getInstanciaServicioId(), DataUbicacion, Cliente.getDataClienteBasico());
			ControlSistema sistema = new ControlSistema();			
			JSONObject json = new JSONObject(DataNotificacion);	
			
			//System.out.println(DataNotificacion.toString());
			
			for(Proveedor prov : Proveedores)
			{
				if(prov.isTrabajando())
					sistema.EnviarPushNotification("Nueva solicitud", "Cliente: " + Cliente.getUsuarioNombre() ,json.toString(), prov.getUsuarioSesionActiva().getSesionDeviceID());
			}
			
			JSONObject idResult = new JSONObject();
			try{		
				idResult.put("id", is.getInstanciaServicioId());

				System.out.println("idResult.toString():" + idResult.toString());
				System.out.println("--- PedirServicio ---");
				return idResult.toString();
			}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
				System.out.println("--- PedirServicio ---");
				return "Error";	
			}	
		}
		else
		{
			System.out.println(Error.P54);
			System.out.println("--- PedirServicio ---");
			return Error.P54;	
		}
	}
	
	
	public String PuntuarCliente(int Puntaje, String Comentario, int InstanciaServicioId){	
		System.out.println("+++ PuntuarCliente +++");
		System.out.println("Puntaje: " + Puntaje);
		System.out.println("Comentario: " + Comentario);
		System.out.println("InstanciaServicioId: " + InstanciaServicioId);
		//Busco la InstanciaServicio 		
		InstanciaServicio InstanciaServicio;
		try{
			InstanciaServicio = (InstanciaServicio)em.find(InstanciaServicio.class, InstanciaServicioId);
			if(InstanciaServicio == null)
			{
				System.out.println(Error.I52);
				System.out.println("--- PuntuarCliente ---");
				return Error.I52;
			}
		}catch(Exception e){
			System.out.println(Error.I52 + e.getMessage());
			System.out.println("--- PuntuarCliente ---");
			return Error.I52;
		}
		em.flush();
		//Creo reseña para el cliente para dicha InstanciaServicio
		Reseña Reseña = new Reseña();
		Reseña.setInstanciaServicio(InstanciaServicio);
		Reseña.setReseñaComentario(Comentario);
		Reseña.setReseñaPuntaje(Puntaje);		
		InstanciaServicio.setReseñaCliente(Reseña);
		//Guardo el BD
		em.persist(Reseña);	
		em.flush();
		String resultado = RecalcularPromedio(InstanciaServicio, Puntaje);
		System.out.println("resultado: " + resultado);
		System.out.println("--- PuntuarCliente ---");
		return resultado;
	}
	
	
	private String RecalcularPromedio(InstanciaServicio ActualIs, float puntajeNuevo){
		try{
			System.out.println("+++RecalcularPromedio_Cliente+++ ");
			System.out.println("ActualIs: " + ActualIs.getInstanciaServicioId());
			System.out.println("puntajeNuevo: " + puntajeNuevo);
			
			Cliente Cliente = ActualIs.getCliente();
			System.out.println("Recalculando promedio del puntje del cliente "+ Cliente.getUsuarioNombre());
			List<InstanciaServicio> ListaInstancias = Cliente.getInstanciasServicio();
			if(ListaInstancias == null)
				System.out.println("ListaInstanciasVacia");
			
			float suma = puntajeNuevo;
			int cantidad = 1;
			System.out.println("Lista: ");			
			for(InstanciaServicio is : ListaInstancias){
				if(is.getInstanciaServicioId() != ActualIs.getInstanciaServicioId())
				{
					System.out.println("is.getInstanciaServicioId(): "+is.getInstanciaServicioId());
					Reseña reseña =is.getReseñaCliente();
					if(reseña != null)
					{
						System.out.println("reseña.getReseñaId(): " +reseña.getReseñaId());
						float puntaje = (float)reseña.getReseñaPuntaje();
						System.out.println("puntaje: "+ puntaje);
						suma += puntaje;
						cantidad++;
					}					
				}
			}
			System.out.println("suma: "+ suma);
			System.out.println("cantidad: "+ cantidad);
			float promedio = suma/cantidad;
			System.out.println("promedio: "+ promedio);			
			Cliente.setUsuarioPromedioPuntaje(promedio);
			em.persist(Cliente);
			em.flush();

			System.out.println("--- RecalcularPromedio_Cliente ---");
			return Error.Ok;
		}catch(Exception e){
			System.out.println("--- RecalcularPromedio_Cliente ---");
			System.out.println(Error.C53);
			return Error.ErrorCompuesto(Error.C53, e.getMessage());
		}
	}
	
	
	@Override
	public String RegistrarCliente(DataClienteBasico Cliente, String TipoVertical){	
		try{
			System.out.println("+++ RegistrarCliente +++");
			System.out.println("Cliente : " + Cliente.getUsuarioCorreo());
			System.out.println("TipoVertical : " + TipoVertical);
			
			Cliente existente = em.find(Cliente.class, Cliente.getUsuarioCorreo());
			if(existente != null)
				return Error.C51 +": " +Cliente.getUsuarioCorreo();
			Vertical vertical;
			try{
				vertical = em.find(Vertical.class, TipoVertical);
			}catch(Exception e){
				return Error.ErrorCompuesto(Error.S1 ,TipoVertical);
			}
			if(vertical == null){
				return Error.ErrorCompuesto(Error.S1 ,TipoVertical);
			}	
			System.out.println(Cliente.getUsuarioContraseña());
			System.out.println(sistema.HashPassword(Cliente.getUsuarioContraseña()));
			Cliente user = new Cliente();
			user.setUsuarioNombre(Cliente.getUsuarioNombre());
			user.setUsuarioApellido(Cliente.getUsuarioApellido());
			user.setUsuarioCiudad(Cliente.getUsuarioCiudad());
			user.setUsuarioContraseña(sistema.HashPassword(Cliente.getUsuarioContraseña()));
			user.setUsuarioCorreo(Cliente.getUsuarioCorreo());
			user.setUsuarioDireccion(Cliente.getUsuarioDireccion());		
			user.setUsuarioPromedioPuntaje(0);
			user.setUsuarioTelefono(Cliente.getUsuarioTelefono());
			user.setVertical(vertical);
			em.persist(user);
			System.out.println("--- RegistrarCliente ---");
			return Error.Ok;
		}catch(Exception e){
			return Error.C54 + e.getMessage();
		}
	}


	public String AgregarDestino(int instaniaServicioId, double latitud, double longitud){
		System.out.println("+++Agregar Destino+++");
		DataUbicacion destino = new DataUbicacion(longitud, latitud);
		//Busco la InstanciaServicio 		
		InstanciaServicio InstanciaServicio;
		try{
			InstanciaServicio = (InstanciaServicio)em.find(InstanciaServicio.class, instaniaServicioId);
			if(InstanciaServicio == null)
			{
				System.out.println(Error.I52);
				return Error.I52;
			}
		}catch(Exception e){
			System.out.println(Error.I52);
			return Error.I52;
		}
		em.flush();
		
		InstanciaServicio.setLatitudDestino(destino.getLatitud());
		InstanciaServicio.setLongitudDestino(destino.getLongitud());
		em.persist(InstanciaServicio);
		em.flush();
		
		System.out.println("Destino elegido: " + destino.getLatitud() +":"+destino.getLongitud());
		
		Cliente Cliente = InstanciaServicio.getCliente();		
		Proveedor Proveedor = InstanciaServicio.getProveedor();	
		ControlSistema sistema = new ControlSistema();			
		JSONObject json = new JSONObject(destino);		
		sistema.EnviarPushNotification("Destino elegido", "Cliente: " + Cliente.getUsuarioNombre() ,json.toString(), Proveedor.getUsuarioSesionActiva().getSesionDeviceID());

		return Error.Ok;
	}
	
	
	private List<Proveedor> CalcularProveedoresCercanos(DataUbicacion ubicacion, int ServicioId){
		System.out.println("+++Calcular proveedores cercanos+++");
		System.out.println("Longitud: " + ubicacion.getLongitud());
		System.out.println("Latitud: " + ubicacion.getLatitud());
		System.out.println("ServicioId: " + ServicioId);
		
		double dist = 0.25;		
		Query query = em.createNamedQuery("ObtenerListaProveedoresEnCiudad", Proveedor.class);
		List<Proveedor> ListaProveedor = query.getResultList();
		List<Proveedor> ListaFinal = new ArrayList<Proveedor>();
		System.out.println("Lista de proveedores cercanos: ");		
		for(Proveedor p : ListaProveedor)
		{
			if(p.getServicio() != null)
			{
				if(p.getServicio().getServicioId() == ServicioId && p.isTrabajando())
				{
					double cateto1 = p.getLatitud() - ubicacion.getLatitud();
					double cateto2 = p.getLongitud() - ubicacion.getLongitud();
					double distancia = Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
					if(distancia < dist)
					{					
						ListaFinal.add(p);
						System.out.println("Proveedor solicitado: " + p.getUsuarioNombre());
					}				
				}
			}
			else
				System.out.println("El proveedor " + p.getUsuarioNombre() + " no tiene servicio asociado");
		}		
		System.out.println("---Calcular proveedores cercanos---");		
		return ListaFinal;
	}

}
