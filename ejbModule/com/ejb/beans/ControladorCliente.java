package com.ejb.beans;

import com.datatypes.DataCliente;
import com.datatypes.DataClienteBasico;
import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataInstanciaServicioBasico;
import com.datatypes.DataNotificacionNuevaSolicitud;
import com.datatypes.DataReseña;
import com.datatypes.DataUbicacion;
import com.ejb.beans.interfaces.ControladorClienteLocal;
import com.ejb.beans.interfaces.ControladorClienteRemote;
import com.entities.Cliente;
import com.entities.InstanciaServicio;
import com.entities.Proveedor;
import com.entities.Reseña;
import com.entities.Servicio;
import com.utils.ControlErrores;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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

    public ControladorCliente() {
        // TODO Auto-generated constructor stub
    }

    @Override
	public void AsociarMecanismoDePago(String ClienteCorreo, String MedioDePago){		
	//Este parece no estar bien definido, hay que ver bien como se maneja el asociar paypal
	}
	
	
	@Override
	public String CancelarPedido(int InstanciaServicioId){	
			//Busco la InstanciaServicio
			InstanciaServicio is;
			try{
				is = (InstanciaServicio)em.find(InstanciaServicio.class, InstanciaServicioId);
				if(is == null){
					return Error.I52;
				}
			}catch(Exception e){
				return Error.I52;
			}
			em.flush();
			//Elimino la instancia en proveedor
			if (is.getProveedor() != null){
				Proveedor Proveedor = is.getProveedor();
				List<InstanciaServicio> lista = Proveedor.getInstanciasServicio();
				lista.remove(is);
				Proveedor.setInstanciasServicio(lista);	
				
				Cliente Cliente = is.getCliente();				
				ControlSistema sistema = new ControlSistema();			
				JSONObject json = new JSONObject(Cliente.getDataClienteBasico());
				
				sistema.EnviarPushNotification("Solicitud cancelada", "Cliente: " + Cliente.getUsuarioNombre() ,json.toString(), Proveedor.getUsuarioSesionActiva().getSesionDeviceID());

			}
			//elimino la InstanciaServicio
			em.remove(is);
			return Error.Ok;
	}
		
	
/*	@Override
	public boolean Login(DataLogin Datos){	
		return true;
	}*/
	
	
	@Override
	public List<DataReseña> MisReseñasObtenidas(String ClienteCorreo){
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
			if(is.getReseñaProveedor() != null){
				ListaReseña.add(is.getReseñaProveedor().getDataReseña());
			}
		}
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
			if(is.getServicio().getServicioId() == ServicioId && is.getReseñaProveedor() != null){
				ListaReseña.add(is.getReseñaProveedor().getDataReseña());
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
		List<DataInstanciaServicio> ListaDataInstanciaServicio = new ArrayList<DataInstanciaServicio>();		
		Query query = em.createNamedQuery("ObtenerHistorialCliente", InstanciaServicio.class).
				setParameter("ClienteCorreo", ClienteCorreo).setParameter("ServicioId", ServicioId);
		List<InstanciaServicio> ListaInstanciaServicio = query.getResultList();
		for (InstanciaServicio InstanciaServicio : ListaInstanciaServicio){ 
			DataInstanciaServicio DataInstanciaServicio = InstanciaServicio.getDataInstanciaServicio();
			ListaDataInstanciaServicio.add(DataInstanciaServicio);
		}
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
		//Busco el servicio
		Servicio Servicio = (Servicio)em.find(Servicio.class, ServicioId);
		em.flush();
		//Busco al cliente que lo solicito
		Cliente Cliente = (Cliente)em.find(Cliente.class, ClienteCorreo);
		em.flush();
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
		List<Proveedor> Proveedores = CalcularProveedoresCercanos(DataUbicacion, ServicioId);

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
		return idResult.toString();
		
		}catch(Exception e){
			return "Error";	}	
	}
	
	
	public String PuntuarCliente(int Puntaje, String Comentario, int InstanciaServicioId){	
		//Busco la InstanciaServicio 		
		InstanciaServicio InstanciaServicio;
		try{
			InstanciaServicio = (InstanciaServicio)em.find(InstanciaServicio.class, InstanciaServicioId);
			if(InstanciaServicio == null)
			{
				return Error.I52;
			}
		}catch(Exception e){
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
		return RecalcularPromedio(InstanciaServicio, Puntaje);
	}
	
	
	private String RecalcularPromedio(InstanciaServicio ActualIs, float puntajeNuevo){
		try{
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
			return Error.Ok;
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.C53, e.getMessage());
		}
	}
	
	
	@Override
	public String RegistrarCliente(DataClienteBasico Cliente){	
		try{
			Cliente existente = em.find(Cliente.class, Cliente.getUsuarioCorreo());
			if(existente != null)
				return Error.C51 +": " +Cliente.getUsuarioCorreo();
			Cliente user = new Cliente();
			user.setUsuarioNombre(Cliente.getUsuarioNombre());
			user.setUsuarioApellido(Cliente.getUsuarioApellido());
			user.setUsuarioCiudad(Cliente.getUsuarioCiudad());
			user.setUsuarioContraseña(Cliente.getUsuarioContraseña());
			user.setUsuarioCorreo(Cliente.getUsuarioCorreo());
			user.setUsuarioDireccion(Cliente.getUsuarioDireccion());		
			user.setUsuarioPromedioPuntaje(0);
			user.setUsuarioTelefono(Cliente.getUsuarioTelefono());
			em.persist(user);
			
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
			if(p.getServicio().getServicioId() == ServicioId)
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
		System.out.println("---Calcular proveedores cercanos---");		
		return ListaFinal;
	}

}
