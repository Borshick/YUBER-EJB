package com.ejb.beans.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.datatypes.DataAdministrador;
import com.datatypes.DataAdministradorBasico;
import com.datatypes.DataClienteBasico;
import com.datatypes.DataClienteCantServiciosBasico;
import com.datatypes.DataProveedorBasico;
import com.datatypes.DataVertical;
import com.datatypes.DataVerticalBasico;
import com.entities.Administrador;

@Local
public interface ControladorAdministradorLocal {

	void Login(String AdministradorEmail, String Password);

	List<DataClienteBasico> ObtenerClientesActivos();

	List<DataProveedorBasico> ObtenerProveedoresActivos();

	String CrearAdministrador(String Correo, String Contrasena, String Nombre);
	
	String EliminarAdministrador( String AdministradorEmail);

	String ModificarAdministrador(String Correo, String Contrasena, String Nombre);

	DataAdministrador ObtenerAdministrador( String AdministradorEmail);

	float ObtenerGananciaMensual(int mes);

	List<DataProveedorBasico> TopProveedoresPorPuntajes( int Limit, String Vertical);

	List<DataProveedorBasico> TopProveedoresPorGanancia(int Limit, String Vertical);

	List<DataClienteCantServiciosBasico> TopClientesPorPuntaje(int Limit, String Vertical);
	
	List<DataClienteCantServiciosBasico> TopClientesPorCantServicios(int Limit, String Vertical);
		
	String CrearVertical(String verticalTipo, String verticalNombre);

	String AsignarVertical(String AdminCreadorId, String AdminId, String TipoVertical);

	String DenegarVertical(String AdminCreadorId, String AdminId, String TipoVertical);
	
	List<DataAdministradorBasico> ObtenerAdministradores();
	
	List<DataVertical> ListarVerticales();
}
