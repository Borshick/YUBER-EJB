package com.ejb.beans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONObject;

import com.datatypes.DataLogin;
import com.datatypes.DataProveedor;
import com.ejb.beans.interfaces.ControlSistemaLocal;
import com.ejb.beans.interfaces.ControlSistemaRemote;
import com.entities.Administrador;
import com.entities.Cliente;
import com.entities.Proveedor;
import com.entities.Sesion;
import com.utils.ControlErrores;

import sun.net.www.http.HttpClient;


/**
 * Session Bean implementation class ControlSistema
 */
@Stateless
public class ControlSistema implements ControlSistemaRemote, ControlSistemaLocal {

	@PersistenceContext//(name= "ad_24afd021b5189d7")
	private EntityManager em;
	
	private ControlErrores Error = new ControlErrores();
	
    public ControlSistema() {
    }
    
    @Override
    public boolean Logout(String correo, String tipoUsuario){
    	
    	Sesion sesion = null;
    	if(tipoUsuario.equalsIgnoreCase("cliente"))
    	{
    		Cliente cli = em.find(Cliente.class, correo); 
    		if(cli != null)
    			sesion = cli.getUsuarioSesionActiva();
    	}
    	else
    	{
    		if(tipoUsuario.equalsIgnoreCase("proveedor"))
    		{
    			Proveedor prov = em.find(Proveedor.class, correo);  
    			if(prov != null)
    				sesion = prov.getUsuarioSesionActiva();
    		}
    		else
    		{
    			Administrador Admin = em.find(Administrador.class, correo);
    			if(Admin != null)
    				sesion = Admin.getAdministradorSesionActiva();
    		}
    	}
    	  
    	if(sesion != null)
		{
			sesion.setSesionHabilitada(false);
			em.persist(sesion);
			em.flush();
			return true;
		}
    	else
    	{
    		return false;
    	}
    	
    }
    
    @Override
    public boolean Login(DataLogin datos, String tipoUsuario){
    	
    	String correo = datos.getCorreo();
    	String password = datos.getPassword();
    	String deviceId = datos.getDeviceId();
    	
    	System.out.println("correo: " + correo);
    	System.out.println("password: " + password);
    	System.out.println("deviceId: " + deviceId);
    	
    	String passObtenida;
    	java.util.Date fecha = new Date();
    	Sesion usuarioSesion = null;
    	Cliente cli = null;
    	Proveedor prov = null;
    	Administrador admin = null;
    	
    	if(tipoUsuario.equalsIgnoreCase("cliente"))
    	{
    		cli = em.find(Cliente.class, correo);
    		if(cli == null)
    		{
    			return false;
    		}
    		passObtenida = cli.getUsuarioContraseña();
    		usuarioSesion = cli.getUsuarioSesionActiva(); 
    		if(!password.equals(passObtenida))
	    		return false;
			if(usuarioSesion != null && usuarioSesion.getSesionHabilitada())
			{
				usuarioSesion.setFecha(fecha);
				usuarioSesion.setSesionDeviceID(deviceId);
				cli.setUsuarioSesionActiva(usuarioSesion);
				em.persist(usuarioSesion);
				return true;
			}
			else
			{
				Sesion sesion = new Sesion(deviceId, correo, true, fecha);
				sesion.setUsuario(cli);
	        	cli.setUsuarioSesionActiva(sesion);
	        	sesion.setTipoUsuario("cliente");
				em.persist(sesion);
	        	return true;
			}   
    	}
    	else
    	{
    		if(tipoUsuario.equalsIgnoreCase("proveedor"))
    		{
    			prov = em.find(Proveedor.class, correo);
    			if(prov == null)
        		{
        			return false;
        		}
    			passObtenida = prov.getUsuarioContraseña();
    			usuarioSesion = prov.getUsuarioSesionActiva();
    			if(!password.equals(passObtenida))
		    		return false;
				if(usuarioSesion != null && usuarioSesion.getSesionHabilitada())
				{
					usuarioSesion.setFecha(fecha);
					usuarioSesion.setSesionDeviceID(deviceId);
					prov.setUsuarioSesionActiva(usuarioSesion);
					em.persist(usuarioSesion);
					return true;
				}
				else
				{
					Sesion sesion = new Sesion(deviceId, correo, true, fecha);
					sesion.setUsuario(cli);
					sesion.setTipoUsuario("proveedor");
					prov.setUsuarioSesionActiva(sesion);
    	        	em.persist(sesion);
    	        	return true;
				}   
    		}
    		else
    		{
    			if(tipoUsuario.equalsIgnoreCase("administrador"))
    			{
    				admin = em.find(Administrador.class, correo);
    				if(admin == null)
            		{
            			return false;
            		}
    				passObtenida = admin.getAdministradorContraseña();
    				usuarioSesion = admin.getAdministradorSesionActiva();
    				if(!password.equals(passObtenida))
    		    		return false;
    				if(usuarioSesion != null && usuarioSesion.getSesionHabilitada())
    				{
    					usuarioSesion.setFecha(fecha);
    					usuarioSesion.setSesionDeviceID(deviceId);
    					admin.setAdministradorSesionActiva(usuarioSesion);
    					em.persist(usuarioSesion);
    					return true;
    				}
    				else
    				{
    					Sesion sesion = new Sesion(deviceId, correo, true, fecha);
    					sesion.setTipoUsuario("administrador");
    					sesion.setAdministrador(admin);
    					admin.setAdministradorSesionActiva(sesion);
        	        	em.persist(sesion);
        	        	return true;
    				}    					
    			}
    			else
    				return false;
    		}
    	} 	
    }

    public String ValidarSesion(String token){
    	try{
		Sesion Sesion = em.createQuery(
				"SELECT s FROM Sesion s WHERE s.SesionHabilitada = 1 AND s.SesionDeviceID LIKE ?1", Sesion.class)
				.setParameter(1, token)
				.getResultList()
				.stream().findFirst().orElse(null);		
    	if(Sesion == null)    	
    		return Error.L1;
    	else
    		return Sesion.getCorreoUsuario();
    	}catch(Exception e){
    		return Error.L1;
    	}
    }

    public String EnviarPushNotification(String Titulo, String Texto, String JsonIN, String Dispositivo){
    	try{
    		
    		URL url = new URL("https://fcm.googleapis.com/fcm/send");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Content-Type", "application/json");
    		conn.setRequestProperty("Authorization", "key=AIzaSyCGcVBTINypGF8wvdkWTs-DiyROkKjjUqY");

    		String input = "{ \"notification\": "+ "{ \"title\": \"" + Titulo + "\",\"text\": \""+ Texto + "\"} ,"
    				+ "\"data\": "+JsonIN
    				+ "\"to\": \""+ Dispositivo +"\"}";
    		
    		
    		System.out.println(input);
    		
    		OutputStream os = conn.getOutputStream();
    		os.write(input.getBytes());
    		os.flush();

    		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    				(conn.getInputStream())));

    		String output;
    		System.out.println("PUSH SENDED: Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}
    		
    		conn.disconnect();   
    		return output;
    	}catch(Exception e){
    		return "ERROR: " + e.getMessage();
    	}  	
    	
    	
    }



}
