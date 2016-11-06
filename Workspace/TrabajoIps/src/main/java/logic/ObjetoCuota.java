package logic;

import java.sql.Timestamp;

public class ObjetoCuota {
	
	private int reciboID;
    private String socioID;
    private int importe;
    private boolean pagado;
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


	public ObjetoCuota(int reciboID, String socioID,boolean pagado, int importe, Timestamp fecha)
	{
		this.reciboID=reciboID;
		this.socioID=socioID;
		this.importe=importe;
		this.pagado=pagado;
		this.fecha=fecha;
	}


	public boolean getPagado() {
		return pagado;
	}

}
