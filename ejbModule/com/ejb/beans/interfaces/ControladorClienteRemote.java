package com.ejb.beans.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.datatypes.DataCliente;
import com.datatypes.DataClienteBasico;
import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataReseña;
import com.datatypes.DataUbicacion;
import com.datatypes.DataLogin;

@Remote
public interface ControladorClienteRemote {

	String AsociarMecanismoDePago(String ClienteCorreo, String MedioDePago);
	
	String CancelarPedido(int InstanciaServicioId);

//	boolean Login(DataLogin datos);

	List<DataReseña> MisReseñasObtenidas(String ClienteCorreo);
	
	List<DataReseña> MisReseñasObtenidas(String ClienteCorreo, int servicioId);

	List<DataCliente> ObtenerClientes();

	List<DataInstanciaServicio> ObtenerHistorial(String ClienteCorreo, int ServicioId);

	boolean OlvidePass(String ClienteCorreo);

	String PedirServicio(String ClienteCorreo, int ServicioId, DataUbicacion DataUbicacion);

	String PuntuarCliente(int Puntaje, String Comentario, int InstanciaServicioId);

	String RegistrarCliente(DataClienteBasico Cliente);
	
	String AgregarDestino(int instaniaServicioId, double latitud, double longitud);

}
