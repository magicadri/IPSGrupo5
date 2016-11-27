package logic;

public class SocioActividad {
	
	private String socioID;
	private int reservaID;
	private int actividadID;
	private boolean presentado;
	private String noSocioID;
	
	public SocioActividad(String socioID, int reservaID, int actividadID, boolean presentado, String noSocioID)
	{
		this.socioID=socioID;
		this.reservaID=reservaID;
		this.actividadID=actividadID;
		this.presentado=presentado;
		this.noSocioID = noSocioID;
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

	public String getNoSocioID(){
		return this.noSocioID;
	}
}
