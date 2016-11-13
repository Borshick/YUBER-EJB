package com.datatypes;

public class DataReseña {
	
	private String Estado = "Ok";
	private int ReseñaId;
	private DataInstanciaServicioBasico InstanciaServicio;
	private String ReseñaComentario;
	private int ReseñaPuntaje;

	public DataReseña() {
	}
	
	public DataReseña(int reseñaId, DataInstanciaServicioBasico instanciaServicio, String reseñaComentario, int reseñaPuntaje) {
		ReseñaId = reseñaId;
		InstanciaServicio = instanciaServicio;
		ReseñaComentario = reseñaComentario;
		ReseñaPuntaje = reseñaPuntaje;
	}

	public int getReseñaId() {
		return ReseñaId;
	}

	public void setReseñaId(int reseñaId) {
		ReseñaId = reseñaId;
	}

	public DataInstanciaServicioBasico getInstanciaServicio() {
		return InstanciaServicio;
	}

	public void setInstanciaServicio(DataInstanciaServicioBasico instanciaServicio) {
		InstanciaServicio = instanciaServicio;
	}

	public String getReseñaComentario() {
		return ReseñaComentario;
	}

	public void setReseñaComentario(String reseñaComentario) {
		ReseñaComentario = reseñaComentario;
	}

	public int getReseñaPuntaje() {
		return ReseñaPuntaje;
	}

	public void setReseñaPuntaje(int reseñaPuntaje) {
		ReseñaPuntaje = reseñaPuntaje;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

}
