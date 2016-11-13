package com.utils;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ejb.beans.UsuarioDAO;
import com.ejb.beans.interfaces.UsuarioDAOLocal;
import com.entities.Usuario;

@ViewScoped
@ManagedBean
public class UsuarioMB {

	@EJB
	private UsuarioDAO usuarioDAOLocal;
	
	private int id = 0;
	
	public UsuarioMB(){
		
	}
	/*
	public String test(){
		System.out.println(usuarioDAOLocal);
		Usuario u = new Usuario();
		u.setId(25);
		u.setNombre("Maxi");
		u.setApellido("Barnech");
		usuarioDAOLocal.create(u);
		return "OK";
	}
*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
