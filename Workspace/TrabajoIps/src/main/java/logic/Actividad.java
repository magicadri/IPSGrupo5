package logic;

import java.sql.Timestamp;

public class Actividad {
	

	private int actividadID;
	private int instalacionID;
	private int semanas;
	private String actividad_nombre;
	
	public Actividad(int actividadID,int instalacionID, String actividad_nombre, int semanas)
	{
		this.actividadID=actividadID;
		this.semanas=semanas;
		this.instalacionID=instalacionID;
		this.actividad_nombre=actividad_nombre;
	}

	public int getInstalacionID() {
		return instalacionID;
	}

	public int getActividadID() {
		return actividadID;
	}

	public int getSemanas() {
		return semanas;
	}

	public String getActividad_nombre() {
		return actividad_nombre;
	}


}
