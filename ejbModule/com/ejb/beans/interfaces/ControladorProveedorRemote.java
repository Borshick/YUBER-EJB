package com.ejb.beans.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataProveedor;
import com.datatypes.DataProveedorBasico;
import com.datatypes.DataReseña;
import com.datatypes.DataLogin;

@Remote
public interface ControladorProveedorRemote {

	String PuntuarProveedor( int Puntaje, String Comentario, int InstanciaServicioId);

	String RegistrarProveedor(DataProveedorBasico Proveedor);

	//boolean Login(DataLogin datos);
	
	String AceptarServicio(int InstanciaServicioId, String Correo);

	String RechazarServicio(int InstanciaServicioId);

	String IniciarJornada(String ProveedorCorreo, int ServicioId);

	String FinalizarJornada(String ProveedorCorreo, int ServicioId);

	List<DataReseña> MisReseñasObtenidas(String ProveedorCorreo);

	String RetirarFondos(String ProveedorCorreo);

	List<DataInstanciaServicio> ObtenerHistorial( String ProveedorCorreo);

	void NotificarArribo(int InstanciaServicioId);

	String AsociarServicio(String ProveedorCorreo, int ServicioId);

	String IniciarServicio( int InstanciaServicioId);
	
	String FinServicio( int InstanciaServicioId, float Distancia);
	
	boolean OlvidePass( String ProveedorCorreo);
	
	boolean EstoyTrabajando( String ProveedorCorreo);

	void AsociarMecanismoDePago( String ProveedorCorreo, String MedioDePago);

	void Cobrar( String ProveedorCorreo);
	
	String ActualizarCoordenadas(String correo, double latitud, double longitud, boolean enViaje, int instanciaServicioId);
	
	List<DataProveedor> ObtenerProveedores();
}
