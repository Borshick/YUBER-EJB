package com.datatypes;

public class DataReseņa {
	
	private String Estado = "Ok";
	private int ReseņaId;
	private DataInstanciaServicioBasico InstanciaServicio;
	private String ReseņaComentario;
	private int ReseņaPuntaje;

	public DataReseņa() {
	}
	
	public DataReseņa(int reseņaId, DataInstanciaServicioBasico instanciaServicio, String reseņaComentario, int reseņaPuntaje) {
		ReseņaId = reseņaId;
		InstanciaServicio = instanciaServicio;
		ReseņaComentario = reseņaComentario;
		ReseņaPuntaje = reseņaPuntaje;
	}

	public int getReseņaId() {
		return ReseņaId;
	}

	public void setReseņaId(int reseņaId) {
		ReseņaId = reseņaId;
	}

	public DataInstanciaServicioBasico getInstanciaServicio() {
		return InstanciaServicio;
	}

	public void setInstanciaServicio(DataInstanciaServicioBasico instanciaServicio) {
		InstanciaServicio = instanciaServicio;
	}

	public String getReseņaComentario() {
		return ReseņaComentario;
	}

	public void setReseņaComentario(String reseņaComentario) {
		ReseņaComentario = reseņaComentario;
	}

	public int getReseņaPuntaje() {
		return ReseņaPuntaje;
	}

	public void setReseņaPuntaje(int reseņaPuntaje) {
		ReseņaPuntaje = reseņaPuntaje;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

}
