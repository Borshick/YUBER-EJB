package com.ejb.beans;

import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataLogin;
import com.datatypes.DataProveedor;
import com.datatypes.DataProveedorBasico;
import com.datatypes.DataReseña;
import com.datatypes.DataUbicacion;
import com.ejb.beans.interfaces.ControladorProveedorLocal;
import com.ejb.beans.interfaces.ControladorProveedorRemote;
import com.entities.Cliente;
import com.entities.InstanciaServicio;
import com.entities.Proveedor;
import com.entities.Reseña;
import com.entities.Servicio;
import com.entities.Vertical;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Account;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.utils.ControlErrores;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONObject;

/**
 * Session Bean implementation class ControladorProveedor
 */
@Stateless
public class ControladorProveedor implements ControladorProveedorRemote, ControladorProveedorLocal {

	@PersistenceContext
	private EntityManager em;
	
	
	private ControlErrores Error = new ControlErrores();
	
	public ControladorProveedor() {
	}
	
	
	@Override
	public String AceptarServicio(int InstanciaServicioId, String Correo){
		Proveedor prov;
		try{
			prov = (Proveedor)em.find(Proveedor.class, Correo);
			em.flush();
			if(prov == null){
				return Error.P52;
			}
		}catch(Exception e){
			return Error.P52;
		}	
		if(!prov.isTrabajando()){
			return Error.P51;
		}
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
		//Asocio al provedor dicha InstanciaServicio
		if (is.getProveedor() == null){
			prov.addInstanciaServicio(is);
			em.persist(prov);
			em.flush();
			
			DataProveedorBasico dataProv = prov.getDataProveedorBasico();
			Cliente Cliente = is.getCliente();
			
			ControlSistema sistema = new ControlSistema();			
			JSONObject json = new JSONObject(dataProv);					
			sistema.EnviarPushNotification("Tu Yuber esta en camino", "Ya viene " + prov.getUsuarioNombre() ,json.toString(), Cliente.getUsuarioSesionActiva().getSesionDeviceID());
				
			return Error.Ok;
		}else{
			return Error.I50;			
		}
	}
	
	
	@Override
	public String AsociarServicio(String ProveedorCorreo, int ServicioId){
		//Busco el servicio
		Servicio Servicio;
		try{
			Servicio = (Servicio)em.find(Servicio.class, ServicioId);
			em.flush();
			if(Servicio == null){
				return Error.S2;
			}
		}catch(Exception e){
			return Error.S2;
		}
		//Busco al proveedor que lo solicito
		Proveedor prov;
		try{
			prov = (Proveedor)em.find(Proveedor.class, ProveedorCorreo);
			em.flush();
			if(prov == null){
				return Error.P52;
			}
		}catch(Exception e){
			return Error.P52;
		}					
		//Guardo el BD				
		prov.setServicio(Servicio);
		List<Proveedor> ListaProveedores = Servicio.getProveedores();
		ListaProveedores.add(prov);
		Servicio.setProveedores(ListaProveedores);				
		em.persist(prov);
		em.flush();
		return Error.Ok;
	}
	
	
	@Override
	public String FinalizarJornada(String ProveedorCorreo, int ServicioId){		
		//Busco al Proveedor
		Proveedor prov;
		try{
			prov = em.find(Proveedor.class, ProveedorCorreo);
			em.flush();
			if(prov == null){
				return Error.P52;
			}
		}catch(Exception e){
			return Error.P52;
		}	
		if(prov.isTrabajando()){
			if(ServicioId == 0)
			{
				prov.setTrabajando(false);
				em.persist(prov);
				em.flush();
			}
			else
			{
				if(prov.getServicio().getServicioId() == ServicioId){
					prov.setTrabajando(false);
					em.persist(prov);
					em.flush();
				}else{
					return Error.P50;
				}
			}
			
		}
		return Error.Ok;
	}
	
	@Override
	public String CancelarViaje(int InstanciaServicioId){	
		System.out.println("+++ CacelarViaje_Proveedor +++");
		System.out.println("InstanciaServicioId: " + InstanciaServicioId);
		//Busco la InstanciaServicio
		InstanciaServicio is;
		try{
			is = (InstanciaServicio)em.find(InstanciaServicio.class, InstanciaServicioId);
			if(is == null){
				System.out.println(Error.I52);
				System.out.println("--- CacelarViaje_Proveedor ---");
				return Error.I52;
			}
		}catch(Exception e){
			System.out.println(Error.I52 + e.getMessage());
			System.out.println("--- CacelarViaje_Proveedor ---");
			return Error.I52;
		}
		em.flush();
		//Elimino la instancia en proveedor
		if (is.getProveedor() != null){
			System.out.println("Eliminando instancia en proveedor");
			Proveedor Proveedor = is.getProveedor();
			Proveedor.removeInstanciaServicio(is);		

			System.out.println("Eliminando instancia en cliente");
			Cliente cliente = is.getCliente();
			cliente.removeInstanciaServicio(is);
			
			ControlSistema sistema = new ControlSistema();			
			JSONObject json = new JSONObject(Proveedor.getDataProveedorBasico());
			
			System.out.println("Envio push -Servicio cacelado- a cliente");
			sistema.EnviarPushNotification("Servicio cancelado", "Proveedor: " + Proveedor.getUsuarioNombre() ,json.toString(), cliente.getUsuarioSesionActiva().getSesionDeviceID());

		}
		//elimino la InstanciaServicio
		em.remove(is);
		System.out.println("--- CacelarViaje_Proveedor ---");
		return Error.Ok;
	}
	
	@Override
	public String FinServicio(int InstanciaServicioId, float Distancia){
		System.out.println("+++ FinServicio +++");
		System.out.println("InstanciaServicioId: " + InstanciaServicioId);
		System.out.println("Distancia: " + Distancia);
		//Paso distancia a kilometros
		Distancia = Distancia/1000;
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
		if(InstanciaServicio.getInstanciaServicioFechaFin() != null){
			return Error.I53;
		}
		//Calculo el tiempo
		java.util.Date fechaFin = new Date();
		java.util.Date fechaInicio = InstanciaServicio.getInstanciaServicioFechaInicio();
		float Tiempo = ( fechaFin.getTime() - fechaInicio.getTime() )/1000; 
		float Costo = 0;
		float TarifaBase = InstanciaServicio.getServicio().getServicioTarifaBase();
		System.out.println("TarifaBase: " + TarifaBase);
		String TipoDeVertical = InstanciaServicio.getServicio().getVertical().getVerticalTipo();
		System.out.println("TipoDeVertical: " + TipoDeVertical);
		//Calculo la tarifa
		if (TipoDeVertical.equals("Transporte")){
			//La tarifa, estará determinada como: tarifa_base + distancia_recorrida * precio_por_km
			float PrecioPorKM = InstanciaServicio.getServicio().getServicioPrecioKM();
			Costo = TarifaBase + (Distancia*PrecioPorKM);			
		}else{
			//tarifa_base + tiempo * precio_por_hora
			float PrecioPorHora = InstanciaServicio.getServicio().getServicioPrecioHora();
			float PrecioPorSegundo = PrecioPorHora/60/60;			
			Costo = TarifaBase + (Tiempo*PrecioPorSegundo);
		}					
		System.out.println("Costo: " + Costo);
		//Seteo y guardo
		InstanciaServicio.setInstanciaServicioCosto(Costo);
		InstanciaServicio.setInstanciaServicioDistancia(Distancia);
		InstanciaServicio.setInstanciaServicioTiempo(Tiempo);
		InstanciaServicio.setInstanciaServicioFechaFin(fechaFin);
		//Guardo el BD
		em.persist(InstanciaServicio);
		em.flush();
		float porcentaje = 10;
		float GananciaViaje = Costo - (Costo * porcentaje/100);
		Proveedor prov = InstanciaServicio.getProveedor();
		prov.setGananciaTotal(prov.getGananciaTotal()+GananciaViaje);
		prov.setPorCobrar(prov.getPorCobrar()+GananciaViaje);
		em.persist(prov);
		em.flush();
		
		//Falta logica de paypal donde nos pagamos costo a nuestra cuenta
		//if(prov.getStripeKey() != null && prov.getTokenDePago()!= null)
			HacerPago(Costo, InstanciaServicio);
		
		ControlSistema sistema = new ControlSistema();			
		JSONObject json = new JSONObject(InstanciaServicio.getDataInstanciaServicioBasico());					
		sistema.EnviarPushNotification("Filanizo su viaje", "Costo: " + Costo ,json.toString(), InstanciaServicio.getCliente().getUsuarioSesionActiva().getSesionDeviceID());
		System.out.println("--- FinServicio ---");
		return Error.Ok;
	}
		
	private String HacerPago(float costo, InstanciaServicio is){
		try {
		System.out.println("+++ HacerPago +++");
		Cliente cliente = is.getCliente();
		System.out.println("costo: " + costo);
		System.out.println("cliente: " + cliente.getUsuarioNombre());
		
		Stripe.apiKey ="sk_test_1cuTC5OBX6oCuibHLUTMg9TP";
	
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		int aDebitar = (int)costo*100;
	    chargeParams.put("amount", aDebitar);
	    chargeParams.put("currency", "usd");    
	    chargeParams.put("customer", cliente.getTokenDePago());
	    chargeParams.put("description", "Pago de yuber del dia: "+ is.getInstanciaServicioFechaFin());    
	   
		Charge charge = Charge.create(chargeParams);
		
	    
	    System.out.println("--- HacerPago ---");
	    return Error.Ok;
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println("--- HacerPago ---");
			return e.getMessage();
		}
	}
	
	public String IniciarJornada(String ProveedorCorreo, int ServicioId){		
		//Busco al Proveedor
		Proveedor prov;
		try{
			prov = em.find(Proveedor.class, ProveedorCorreo);
			em.flush();
			if(prov == null){
				return Error.P52;
			}
		}catch(Exception e){
			return Error.P52;
		}	
		if(!prov.isTrabajando()){
			if(ServicioId == 0)
			{
				prov.setTrabajando(true);
				em.persist(prov);
				em.flush();
			}
			else
			{
				if(prov.getServicio().getServicioId() == ServicioId){
					prov.setTrabajando(true);
					em.persist(prov);
					em.flush();
				}else{
					return Error.P50;
				}
			}
			
		}
		return Error.Ok;
	}
	
	
	@Override
	public String IniciarServicio(int InstanciaServicioId){
		System.out.println("+++ Iniciar Servicio +++");
		System.out.println("InstanciaServicioId: " + InstanciaServicioId);
		java.util.Date fecha = new Date();
		//Busco la InstanciaServicio 
		InstanciaServicio is;
		try{
			is = (InstanciaServicio)em.find(InstanciaServicio.class, InstanciaServicioId);
			if(is == null){
				System.out.println(Error.I52);
				System.out.println("--- Iniciar Servicio ---");
				return Error.I52;
			}
		}catch(Exception e){
			System.out.println(Error.I52 + e.getMessage());
			System.out.println("--- Iniciar Servicio ---");
			return Error.I52;
		}
		em.flush();
		//Asocio la fecha a la InstanciaServicio
		is.setInstanciaServicioFechaInicio(fecha);
		em.persist(is);
		
		ControlSistema sistema = new ControlSistema();
		JSONObject json = new JSONObject(is.getDataInstanciaServicioBasico());	
		sistema.EnviarPushNotification("Empieza el viaje", "Viaje comenzado" ,json.toString(), is.getCliente().getUsuarioSesionActiva().getSesionDeviceID());
		
		System.out.println("--- Iniciar Servicio ---");
		return Error.Ok;
	}
	

/*	@Override
	public boolean Login(DataLogin datos){	
		
		return sistema.Login(datos);
	}*/
		
	
	public List<DataReseña> MisReseñasObtenidas(String ProveedorCorreo){	
		System.out.println("+++ MisReseñasObtenidas_Proveedor +++");
		System.out.println("ProveedorCorreo: "+ ProveedorCorreo);
		Proveedor prov;
		try{
			prov = (Proveedor)em.find(Proveedor.class, ProveedorCorreo);
			if(prov == null){
				return null;
			}
		}catch(Exception e){
			return null;
		}
		em.flush();
		List<DataReseña> ListaReseña = new ArrayList<DataReseña>();
		List<InstanciaServicio> ListaInstanciaServicio = prov.getInstanciasServicio();
		for(InstanciaServicio is : ListaInstanciaServicio){
			if(is.getReseñaProveedor() != null){
				ListaReseña.add(is.getReseñaProveedor().getDataReseña());
				System.out.println("DataInstanciaServicio: ");
				if(is.getInstanciaServicioFechaInicio() != null)
					System.out.println("FechaInicio: " + is.getInstanciaServicioFechaInicio().toString());
				if(is.getInstanciaServicioFechaFin() != null)
					System.out.println("FechaFin: " + is.getInstanciaServicioFechaFin().toString());
			}
		}
		System.out.println("--- MisReseñasObtenidas_Proveedor ---");
		return ListaReseña;
	}
	
	
	public List<DataInstanciaServicio> ObtenerHistorial(String ProveedorCorreo){		
		System.out.println("+++ ObtenerHistorial_Proveedor +++");
		System.out.println("ProveedorCorreo: "+ ProveedorCorreo);
		List<DataInstanciaServicio> ListaDataInstanciaServicio = new ArrayList<DataInstanciaServicio>();		
		Query query = em.createNamedQuery("ObtenerHistorialProveedor", InstanciaServicio.class).setParameter("ProveedorCorreo", ProveedorCorreo);
		List<InstanciaServicio> ListaInstanciaServicio = query.getResultList();
		for (InstanciaServicio InstanciaServicio : ListaInstanciaServicio){ 
			DataInstanciaServicio DataInstanciaServicio = InstanciaServicio.getDataInstanciaServicio();
			ListaDataInstanciaServicio.add(DataInstanciaServicio);
			System.out.println("DataInstanciaServicio: ");
			System.out.println("FechaInicio: " + DataInstanciaServicio.getInstanciaServicioFechaInicio().toString());
			System.out.println("FechaFin: " + DataInstanciaServicio.getInstanciaServicioFechaFin().toString());
		}
		System.out.println("--- ObtenerHistorial_Proveedor ---");
		return ListaDataInstanciaServicio;
	}
	
	
	public List<DataProveedor> ObtenerProveedores(){	
		List<DataProveedor> ListaDataProveedor = new ArrayList<DataProveedor>();
		List<Proveedor> ListaProveedor = em.createQuery(
				"SELECT u FROM Proveedor u", Proveedor.class).getResultList();
		for (Proveedor Proveedor : ListaProveedor){ 
			DataProveedor dc = Proveedor.getDataProveedor();
			ListaDataProveedor.add(dc);
		}
		return ListaDataProveedor;
	}
	
	
	@Override
	public boolean OlvidePass(String ClienteCorreo){
		//************************ESTA INCOMPETO************************//
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
	
	
	public boolean EstoyTrabajando( String ProveedorCorreo){
		//Busco al Proveedor
		Proveedor prov;
		try{
			prov = em.find(Proveedor.class, ProveedorCorreo);
			em.flush();
			if(prov == null){
				return false;
			}
		}catch(Exception e){
			return false;
		}	
		if(prov.isTrabajando())
			return true;
		else
			return false;
	}
	
	public String PuntuarProveedor(int Puntaje, String Comentario, int InstanciaServicioId){	
		System.out.println("+++PuntuarProveedor+++ ");
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
		InstanciaServicio.setReseñaProveedor(Reseña);
		//Guardo el BD
		em.persist(Reseña);
		em.flush();
		String Error = RecalcularPromedio(InstanciaServicio, Puntaje);		
		System.out.println("/---Puntuar Proveedor---/");
		return Error;
	}
	
	
	private String RecalcularPromedio(InstanciaServicio ActualIs, float puntajeNuevo)	{
		try{			
			System.out.println("+++RecalcularPromedio_Proveedor+++ ");
			Proveedor Proveedor = ActualIs.getProveedor();
			System.out.println("Recalculando promedio del puntje del proveedor "+ Proveedor.getUsuarioNombre());
			List<InstanciaServicio> ListaInstancias = Proveedor.getInstanciasServicio();
			if(ListaInstancias == null)
				System.out.println("ListaInstanciasVacia");			
			float suma = puntajeNuevo;
			int cantidad = 1;
			System.out.println("Lista: ");			
			for(InstanciaServicio is : ListaInstancias){
				if(is.getInstanciaServicioId() != ActualIs.getInstanciaServicioId())
				{
					System.out.println("is.getInstanciaServicioId(): "+is.getInstanciaServicioId());
					Reseña reseña =is.getReseñaProveedor();
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
			Proveedor.setUsuarioPromedioPuntaje(promedio);
			em.persist(Proveedor);	
			System.out.println("---RecalcularPromedio_Proveedor---");
			System.out.println(Error.Ok);
			return Error.Ok;
		}catch(Exception e){
			System.out.println("---RecalcularPromedio_Proveedor---");
			System.out.println(Error.P53);
			return Error.P53;
		}
	}
	
	
	public String RechazarServicio(int InstanciaServicioId){
		//Creo que no tiene que hacer nada. Lo unico que hace esto es el cancel en el boton 
		//del celular ignorando el servicio que se le habia propuesto
		return Error.Ok;
	}
	
	public String RegistrarProveedor(DataProveedorBasico Proveedor, String TipoVertical){
		System.out.println("+++ Registrar Proveedor +++");
		System.out.println("Proveedor: " + Proveedor.getUsuarioCorreo());
		System.out.println("Vertical: " + TipoVertical);
		
		Proveedor existente = em.find(Proveedor.class, Proveedor.getUsuarioCorreo());
		if(existente != null)
		{
			System.out.println(Error.C51 + ": " +Proveedor.getUsuarioCorreo());
			System.out.println("--- Registrar Proveedor ---");
			return Error.C51 + ": " +Proveedor.getUsuarioCorreo();
		}
		Vertical vertical;
		try{
			vertical = em.find(Vertical.class, TipoVertical);
		}catch(Exception e){
			return Error.ErrorCompuesto(Error.S1 ,TipoVertical);
		}
		if(vertical == null){
			return Error.ErrorCompuesto(Error.S1 ,TipoVertical);
		}	
		
		Proveedor user = new Proveedor();
		user.setUsuarioNombre(Proveedor.getUsuarioNombre());
		user.setUsuarioApellido(Proveedor.getUsuarioApellido());
		user.setUsuarioCiudad(Proveedor.getUsuarioCiudad());
		user.setUsuarioContraseña(Proveedor.getUsuarioContraseña());
		user.setUsuarioCorreo(Proveedor.getUsuarioCorreo());
		user.setUsuarioDireccion(Proveedor.getUsuarioDireccion());		
		user.setUsuarioPromedioPuntaje(0);
		user.setUsuarioTelefono(Proveedor.getUsuarioTelefono());
		user.setGananciaTotal(0);
		user.setPorCobrar(0);
		user.setVehiculoMarca(Proveedor.getVehiculoMarca());
		user.setVehiculoModelo(Proveedor.getVehiculoModelo());
		user.setVertical(vertical);
		em.persist(user);
		em.flush();
		System.out.println("--- Registrar Proveedor ---");
		return Error.Ok;
	}
	
	public String ActualizarCoordenadas(String correo, double latitud, double longitud, boolean enViaje, int instanciaServicioId)
	{
		System.out.println("+++ActualizarCoordenadas+++");
		System.out.println("Se han recalculado las coordenadas: ");
		System.out.println("correo: "+correo);
		System.out.println("latitud: "+latitud);
		System.out.println("longitud: "+longitud);
		System.out.println("enViaje: "+enViaje);
		System.out.println("instanciaServicioId: "+instanciaServicioId);
		
		Proveedor proveedor = em.find(Proveedor.class, correo);
		proveedor.setLatitud(latitud);
		proveedor.setLongitud(longitud);
		em.persist(proveedor);
		
		if(enViaje)
		{
			//Busco la InstanciaServicio 		
			InstanciaServicio InstanciaServicio;
			try{
				InstanciaServicio = em.find(InstanciaServicio.class, instanciaServicioId);
				if(InstanciaServicio == null)
				{
					System.out.println(Error.I52);
					System.out.println("---ActualizarCoordenadas---");
					return Error.I52;
				}
			}catch(Exception e){
				System.out.println(Error.I52 + e);
				System.out.println("---ActualizarCoordenadas---");
				return Error.I52;
			}
			em.flush();
			DataUbicacion ubicacion = new DataUbicacion(longitud,latitud);
			ControlSistema sistema = new ControlSistema();			
			JSONObject json = new JSONObject(ubicacion);	
			
			String Id = InstanciaServicio.getCliente().getUsuarioSesionActiva().getSesionDeviceID();
			System.out.println("Id push: "+Id);
			sistema.EnviarPushNotification("Ubicacion proveedor", "Proveedor",json.toString(), Id);
			System.out.println("Push enviada");
		}
		System.out.println(Error.Ok);
		System.out.println("---ActualizarCoordenadas---");
		return Error.Ok;		
	}
		
	
	public String RetirarFondos(String ProveedorCorreo){	
		System.out.println("+++ Retirar fondos +++");
		System.out.println("ProveedorCorreo: "+ ProveedorCorreo);
		Proveedor prov;
		try{
			prov = (Proveedor)em.find(Proveedor.class, ProveedorCorreo);
			em.flush();
			if(prov == null){
				return Error.P52;
			}
		}catch(Exception e){
			return Error.P52;
		}	
		float monto = prov.getPorCobrar();
		
		Stripe.apiKey = prov.getStripeKey();
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		int aDebitar = (int)monto*100;
	    chargeParams.put("amount", aDebitar);
	    chargeParams.put("currency", "usd");	    
	    chargeParams.put("customer", prov.getTokenDePago());
	    chargeParams.put("description", "Test Plaid charge");
	    
	    try {
			Charge charge = Charge.create(chargeParams);			
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			e.printStackTrace();
			System.out.println("Errror pagando: " + e.getMessage());
			System.out.println("--- Retirar fondos ---");
		}
	    System.out.println("Se han pagado: " + monto);
		
		prov.setPorCobrar(0);	
		
		System.out.println("--- Retirar fondos ---");
		return Error.Ok;
	}
	
	
	public void NotificarArribo(int InstanciaServicioId){		
	}
	
	public String AsociarMecanismoDePago(String ProveedorCorreo, String Token, String StripeKey){	
		
		Proveedor prov;
		try{
			prov = (Proveedor)em.find(Proveedor.class, ProveedorCorreo);
			em.flush();
			if(prov == null){
				return Error.P52;
			}
		}catch(Exception e){
			return Error.P52;
		}		
		try{
		
		Stripe.apiKey = StripeKey;
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("source", Token);
		customerParams.put("description", ProveedorCorreo);

		Customer customer;	
		customer = Customer.create(customerParams);	
		
		prov.setTokenDePago(customer.getId());	
		prov.setStripeKey(StripeKey);
		
		em.persist(prov);
		return Error.Ok;
		}catch(Exception e){
			return e.getMessage();
		}	
	}
	
	@Override
	public void Cobrar(String ProveedorCorreo){
		//No es lo mismo que RetirarFrondos??
	}
		
}

