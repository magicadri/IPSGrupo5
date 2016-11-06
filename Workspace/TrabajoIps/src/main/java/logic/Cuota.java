package logic;

import java.sql.Timestamp;

public class Cuota {
	
	private String socioID;
	private int precio;
	private int mes;
	
	/**
	 * @param socioID
	 * @param precio
	 */
	public Cuota(String socioID,int mes, int precio)
	{
		this.socioID=socioID;
		this.mes=mes;
		this.precio=precio;
	}

	public int getMes() {
		return mes;
	}

	public String getSocioID() {
		return socioID;
	}

	public int getPrecio() {
		return precio;
	}

}
