package com.utils;

public class ControlErrores {

	public String Ok = ToJson("EXITO" , "Ok");
	
	//Errores genericos
	public String G1 = ToJson("ERROR" ,"Se produjo un error, vuelva a intentar");
	
	//A0-A100 : Errores de Administradores
	public String A50 = ToJson("ERROR" ,"El administrador actual no tiene permisos suficientes para asignar dicha vertical");
	public String A51 = ToJson("ERROR" ,"El administrador actual no tiene permisos suficientes para denegar dicha vertical");
	public String A52 = ToJson("ERROR" ,"El administrador no existe en el sistema");
	public String A53 = ToJson("ERROR" ,"El administrador ya existe en el sistema");
	public String A54 = ToJson("ERROR" ,"No se ha podido desasociar el administrador");	
	//C0-100 : Errores de Clientes
	public String C51 = ToJson("ERROR" ,"El cliente ya existe en el sistema");
	public String C52 = ToJson("ERROR" ,"El cliente no existe en el sistema");
	public String C53 = ToJson("ERROR" ,"Error calculando el puntaje del cliente");
	public String C54 = ToJson("ERROR" ,"Error registrando cliente");
	
	//P0-P100 : Errores de Provedores
	public String P50 = ToJson("ERROR" ,"El proveedor no brinda el servicio dado");
	public String P51 = ToJson("ERROR" ,"El proveedor debe estar trabajando");
	public String P52 = ToJson("ERROR" ,"El proveedor no existe en el sistema");
	public String P53 = ToJson("ERROR" ,"Error calculando el puntaje del proveedor");
		
	//I0-I100 : Errores de Instancias de servicio
	public String I50 = ToJson("ERROR" ,"La instancia de servicio ya fue asignada a otro proveedor");
	public String I52 = ToJson("ERROR" ,"La servicio no existe en el sistema");
	public String I53 = ToJson("ERROR" ,"El servicio ya fue finalizado");
		
	//S0-S100 : Errores de Servicios
	public String S1 = ToJson("ERROR" ,"No existe dicha vertical");
	public String S2 = ToJson("ERROR" ,"No existe dicho servicio");
	
	//V0-V100 : Errores de Verticales
	public String V50 = ToJson("ERROR" ,"La vertical ya existe en el sistema");
	public String V51 = ToJson("ERROR" ,"La vertical no existe en el sistema");
	public String V52 = ToJson("ERROR" ,"No se ha podido desasociar la vertical");
	
	//L0-L100 : Errores de Sesiones
	public String L1 = ToJson("ERROR" ,"La sesion no existe en el sistema");
	public String L2 = ToJson("ERROR" ,"La vertical no existe en el sistema");
		
	public ControlErrores() {
		
	}
	
	public String ToJson(String Clave, String Valor){
		String JSONClave = "\"" + Clave + "\"";
		String JSONValor = "\"" + Valor + "\"";
		String JSONFinal = "{" + JSONClave + ":" + JSONValor + "}";
		return JSONFinal;	
	}
	
	public String GetJsonValor(String Json){
		String[] JsonList = Json.split(":");
		Json = JsonList[1];
		Json = Json.replaceAll("\"", "");	
		Json = Json.replaceAll("}", "");	
		System.out.println("GETJsonValor: " +Json);
		return Json;	
	}
	
	public String ErrorCompuesto(String Error, String componente){
		String NuevoJsonValor = GetJsonValor(Error) + " : " + componente;	
		String Resultado = ToJson("ERROR", NuevoJsonValor);
		System.out.println("Error compuesto: " +Resultado);
		return Resultado;	
	}
	
}
