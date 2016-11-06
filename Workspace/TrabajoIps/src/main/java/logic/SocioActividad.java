package logic;

public class SocioActividad {
	
	private String socioID;
	private int reservaID;
	private int actividadID;
	private boolean presentado;
	
	public SocioActividad(String socioID, int reservaID, int actividadID, boolean presentado)
	{
		this.socioID=socioID;
		this.reservaID=reservaID;
		this.actividadID=actividadID;
		this.presentado=presentado;
	}

	public String getSocioID() {
		return socioID;
	}

	public int getReservaID() {
		return reservaID;
	}

	public int getActividadID() {
		return actividadID;
	}

	public boolean getPresentado() {
		return presentado;
	}

}
