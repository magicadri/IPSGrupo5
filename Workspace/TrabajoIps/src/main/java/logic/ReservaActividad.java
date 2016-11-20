package logic;

public class ReservaActividad {
	
	private int actividadID;
	private int reservaID;


	public ReservaActividad(int actividadID, int reservaID)
	{
		this.actividadID=actividadID;
		this.reservaID=reservaID;
	}


	public int getActividadID() {
		return actividadID;
	}


	public int getReservaID() {
		return reservaID;
	}

}
