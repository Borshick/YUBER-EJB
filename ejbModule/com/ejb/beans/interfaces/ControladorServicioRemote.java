package com.ejb.beans.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.datatypes.DataServicio;
import com.datatypes.DataServicioBasico;

@Remote
public interface ControladorServicioRemote {

	List<DataServicioBasico> ObtenerServicios(String TipoDeVertical);

	String CrearServicio(DataServicioBasico Servicio, String TipoVertical);

	String EliminarServicio(int ServicioId);

	String ModificarServicio(DataServicioBasico Servicio);

	DataServicio ObtenerServicio(int ServicioId);

}
