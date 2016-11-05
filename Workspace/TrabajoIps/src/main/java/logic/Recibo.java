package logic;

public class Recibo {
	
	private String socioID;
	private int importe;
	private String descripcion;
	public Recibo(String socioID, int importe, String descripcion)
	{
		this.socioID=socioID;
		this.importe=importe;
		this.descripcion=descripcion;
	}
	public String getSocioID() {
		return socioID;
	}
	public int getImporte() {
		return importe;
	}
	public String getDescripcion() {
		return descripcion;
	}

}
