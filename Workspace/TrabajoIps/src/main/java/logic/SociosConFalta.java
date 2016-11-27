package logic;

public class SociosConFalta {

	private String socioID;
	private int actividadID;

	public SociosConFalta(String socioID, int actividadID) {
		this.socioID = socioID;
		this.actividadID = actividadID;
	}

	public String getSocioID() {
		return socioID;
	}
	
	public int getActividadID(){
		return actividadID;
	}
}
