/**
 * 
 */
package com.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.datatypes.DataProveedor;
import com.datatypes.DataProveedorBasico;
import com.ejb.beans.interfaces.ControlSistemaLocal;
import com.ejb.beans.interfaces.UsuarioDAORemote;
import com.entities.Usuario;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

/**
 * @author Maxi
 *
 */
@RequestScoped
@Path("/yuberRS")
public class yuberRS {
	
	@EJB
	private UsuarioDAORemote usuario;
	
	@EJB
	private ControlSistemaLocal sistema;

	@GET
	@Produces("text/plain")
	@Path("/doit")
	public Response getMessage(){
		
		Stripe.apiKey ="sk_test_1cuTC5OBX6oCuibHLUTMg9TP";
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("amount", 100000);
		chargeParams.put("currency", "usd");
		chargeParams.put("source", "tok_19FTHKJ84SoyOekuUsMvZWuo");
		chargeParams.put("application_fee", 10000);
		chargeParams.put("destination", "cus_9YbYdUpNjN5gSf");

	
	/* Map<String, Object> chargeParams = new HashMap<String, Object>();
     chargeParams.put("amount", 500000);
     chargeParams.put("currency", "usd");
     chargeParams.put("source", "tok_19FTHKJ84SoyOekuUsMvZWuo");
     chargeParams.put("description", "Test Plaid charge");
*/
     try {
		Charge charge = Charge.create(chargeParams);

		/*System.out.println(charge.getBalanceTransaction());
		System.out.println(charge.getCurrency());
		System.out.println(charge.getCustomer());
		System.out.println(charge.getFailureCode());
		System.out.println(charge.getFailureMessage());
		System.out.println(charge.getInvoice());*/
	} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
			| APIException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		return Response.ok("Hola").build();
	}
	
	@GET
	@Produces("text/plain")
	@Path("/dopush/{dispo}")
	public Response dopush(@PathParam ("dispo") String dispo){
		
		//DataProveedorBasico prov = new DataProveedorBasico("prov1", "nombre1", "apellido1", "ciudad1", "lala", "kaka", 2, "0550", false);
		
		//JSONObject json = new JSONObject(prov);
		
		//sistema.EnviarPushNotification("Tu yuber esta en camino", "Ya viene " + prov.getUsuarioNombre() ,json.toString(), dispo);		
		
		return null;//Response.ok(json.toString()).build();
	}
	/*
	@GET
	@Produces("text/plain")
	@Path("crear")
	public Response crearUsuario(){
		Usuario u = new Usuario();
		u.setId(3);
		u.setNombre("maxi");
		u.setApellido("barnech");
		usuario.create(u);
		return Response.ok("todook").build();
	}
	
	@GET
	@Produces("application/json")
	@Path("obtenerUsuarios")
	public Response obtenerUsuarios(){
		List<Usuario> Lista = usuario.getAllUsuarios();
		return Response.ok(Lista).build();
	}*/
}
