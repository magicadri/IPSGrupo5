package logic;

import java.sql.SQLException;
import java.sql.Timestamp;

import db.Database;
import db.Parser;

public class ReservaActividad {
	
	private int actividadID;
	private int reservaID;
	Parser parser;
	
	public ReservaActividad() {	
		this.parser = new Parser();
		}


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
	
	
	public boolean cancelarReservaActividad(int actividadID, int reservaID) {
		boolean result = marcarReserva(actividadID, reservaID);
		if (marcarReserva(actividadID, reservaID)) {
			removeReservaActividadDeBase(parser.borrarReservaActividad(actividadID, reservaID));
			for(SocioActividad SActividad : parser.getSociosactividad()){
				try {
					Database.getInstance().getC().createStatement()
					.execute("DELETE FROM SocioActividad WHERE ActividadID =" + actividadID + ";");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		return result;
	}
	

	
	
	private boolean marcarReserva(int actividadID, int reservaID) {
		try {
			parser.removeArrays();
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer marcarReserva con la reserva: " + reservaID);
		}
		return parser.marcarReservaActividad(actividadID, reservaID);
	}
	
	
	private void removeReservaActividadDeBase(ReservaActividad reserva) {
		try {
			Database.getInstance().getC().createStatement()
					.execute("DELETE FROM ReservaActividad WHERE reservaID =" + reserva.getReservaID() + ";");
			Database.getInstance().getC().createStatement()
			.execute("DELETE FROM Actividad WHERE ActividadID =" + reserva.getActividadID() + ";");
			
			//Borrar socios
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer removeReservaABase con la reserva: " + reserva.getReservaID());
		}
	}
	
	

}
