package com.ejb.beans.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.datatypes.DataInstanciaServicio;
import com.datatypes.DataInstanciaServicioBasico;
import com.datatypes.DataServicio;
import com.datatypes.DataServicioBasico;

@Local
public interface ControladorServicioLocal {

	List<DataServicioBasico> ObtenerServicios(String TipoDeVertical);

	String CrearServicio(DataServicioBasico Servicio, String TipoVertical);

	String EliminarServicio(int ServicioId);

	String ModificarServicio(DataServicioBasico Servicio);

	DataServicio ObtenerServicio(int ServicioId);

	DataInstanciaServicioBasico ObtenerInstanciaServicio(int InstanciaServicioId);
}
