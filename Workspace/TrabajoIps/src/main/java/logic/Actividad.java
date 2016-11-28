package logic;

public class Actividad {
	
	private int actividadID;
	private int instalacionID;
	private int semanas;
	private String actividad_nombre;
	private int max_plazas;
	
	public Actividad(int actividadID,int instalacionID, String actividad_nombre, int semanas, int max_plazas)
	{
		this.actividadID=actividadID;
		this.semanas=semanas;
		this.instalacionID=instalacionID;
		this.actividad_nombre=actividad_nombre;
		this.max_plazas=max_plazas;
	}

	public int getMax_plazas() {
		return max_plazas;
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
