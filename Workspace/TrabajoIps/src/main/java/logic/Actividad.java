package logic;

import java.sql.Timestamp;

public class Actividad {
	
	private Timestamp fechaComienzo;
	private Timestamp fechaFinal;
	private String actividadID;
	
	public Actividad(String actividadID, Timestamp fechaComienzo, Timestamp fechaFinal)
	{
		this.actividadID=actividadID;
		this.fechaComienzo=fechaComienzo;
		this.fechaFinal=fechaFinal;
	}

	public Timestamp getFechaComienzo() {
		return fechaComienzo;
	}

	public Timestamp getFechaFinal() {
		return fechaFinal;
	}

	public String getActividadID() {
		return actividadID;
	}

}
