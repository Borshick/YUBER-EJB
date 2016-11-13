package com.ejb.beans.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.entities.Usuario;

@Remote
public interface UsuarioDAORemote {
	public Usuario create(Usuario u);
	public Usuario update(Usuario u);
	public void remove(int id);
	public Usuario getUsuario(int id);
	public List<Usuario> getAllUsuarios();
}
