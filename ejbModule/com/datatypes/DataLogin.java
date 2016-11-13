package com.datatypes;

public class DataLogin {
	

	private String correo;
	private String password;
	private String deviceId;
	
	

	DataLogin(){		
	}
	
	public DataLogin(String correo, String password, String deviceId) {
		super();
		this.correo = correo;
		this.password = password;
		this.deviceId = deviceId;
	}
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
