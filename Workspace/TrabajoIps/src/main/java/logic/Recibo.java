package logic;

public class Recibo {
	
	private String reciboID;
	private String socioID;
	private String reservaID;
	private String instalacionID;
	private int importe;
	
	
	public String getReciboID() {
		return reciboID;
	}


	public String getSocioID() {
		return socioID;
	}


	public String getReservaID() {
		return reservaID;
	}


	public String getInstalacionID() {
		return instalacionID;
	}
	
	public int getImporte()
	{
		return importe;
	}


	public Recibo(String reciboID, String socioID, String reservaID, String instalacionID, int importe)
	{
		this.reciboID=reciboID;
		this.socioID=socioID;
		this.reservaID=reservaID;
		this.instalacionID=instalacionID;
		this.importe=importe;
	}

}
