package logic;

import java.util.Date;

public class Instalacion {
	
	private int instalacionID;
	private String instalacion_nombre;
	
	public Instalacion(int instalacionID, String instalacion_nombre) {
		this.instalacionID = instalacionID;
		this.instalacion_nombre=instalacion_nombre;
	}

	public String getInstalacion_nombre() {
		return instalacion_nombre;
	}

	public int getInstalacionID() {
		return instalacionID;
	}
	
	

}
