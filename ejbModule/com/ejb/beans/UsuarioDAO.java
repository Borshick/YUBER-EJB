package com.ejb.beans;

import com.ejb.beans.interfaces.UsuarioDAOLocal;
import com.ejb.beans.interfaces.UsuarioDAORemote;
import com.entities.Usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class UsuarioDAO
 */
@Stateless
public class UsuarioDAO implements UsuarioDAORemote, UsuarioDAOLocal {

	@PersistenceContext
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public UsuarioDAO() {
    }

	@Override
	public Usuario create(Usuario u) {
		em.persist(u);
		return u;
	}

	@Override
	public Usuario update(Usuario u) {
		em.merge(u);
		return u;
	}

	@Override
	public void remove(int id) {
		em.remove(getUsuario(id));
		
	}

	@Override
	public Usuario getUsuario(int id) {
		return em.find(Usuario.class, id);
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		return em.createNamedQuery("Usuario.getAll", Usuario.class).getResultList();
	}

}
