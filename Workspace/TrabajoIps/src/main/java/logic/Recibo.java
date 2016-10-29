package logic;

import java.sql.Timestamp;

public class Recibo {
	
	private int reciboID;
    private String socioID;
    private int importe;
    private Timestamp fecha;
	
	public int getReciboID() {
		return reciboID;
	}


	public String getSocioID() {
		return socioID;
	}
	
	public int getImporte()
	{
		return importe;
	}


	public Timestamp getFecha() {
		return fecha;
	}


	public Recibo(int reciboID, String socioID, int importe, Timestamp fecha)
	{
		this.reciboID=reciboID;
		this.socioID=socioID;
		this.importe=importe;
		this.fecha=fecha;
	}

}
