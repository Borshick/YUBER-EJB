package com.ejb.beans.interfaces;

import javax.ejb.Local;

import com.datatypes.DataLogin;

@Local
public interface ControlSistemaLocal {

	boolean Login(DataLogin datos, String tipoUsuario);

	boolean Logout(String correo, String tipoUsuario);

	String ValidarSesion(String token);
	
	String EnviarPushNotification(String Titulo, String Texto, String JsonIN, String Dispositivo);
	
	String HashPassword(String passwordToHash);
}
