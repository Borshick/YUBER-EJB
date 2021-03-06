package com.datatypes;

public class DataReseņaBasico {
	
	private String Estado = "Ok";
	private int ReseņaId;
	private String ReseņaComentario;
	private int ReseņaPuntaje;

	public DataReseņaBasico() {
	}
	
	public DataReseņaBasico(int reseņaId, String reseņaComentario, int reseņaPuntaje) {
		ReseņaId = reseņaId;
		ReseņaComentario = reseņaComentario;
		ReseņaPuntaje = reseņaPuntaje;
	}

	public int getReseņaId() {
		return ReseņaId;
	}

	public void setReseņaId(int reseņaId) {
		ReseņaId = reseņaId;
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
