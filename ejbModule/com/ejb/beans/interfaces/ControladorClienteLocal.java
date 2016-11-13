package com.ejb.beans.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.datatypes.DataCliente;
import com.datatypes.DataClienteBasico;
import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataRese�a;
import com.datatypes.DataUbicacion;
import com.datatypes.DataLogin;

@Local
public interface ControladorClienteLocal {

	void AsociarMecanismoDePago(String ClienteCorreo, String MedioDePago);
	
	String CancelarPedido(int InstanciaServicioId);

//	boolean Login(DataLogin datos);

	List<DataRese�a> MisRese�asObtenidas(String ClienteCorreo);

	List<DataRese�a> MisRese�asObtenidas(String ClienteCorreo, int servicioId);

	List<DataCliente> ObtenerClientes();

	List<DataInstanciaServicio> ObtenerHistorial(String ClienteCorreo, int ServicioId);

	boolean OlvidePass(String ClienteCorreo);

	String PedirServicio(String ClienteCorreo, int ServicioId, DataUbicacion DataUbicacion);

	String PuntuarCliente(int Puntaje, String Comentario, int InstanciaServicioId);

	String RegistrarCliente(DataClienteBasico Cliente);
	
	String AgregarDestino(int instaniaServicioId, double latitud, double longitud);

}