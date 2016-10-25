package logic;

import java.sql.Timestamp;
import java.util.Date;

public class Reserva {

	private String socioID;
	private String instalacionID;
	private Timestamp horaComienzo;
	private Timestamp horaFinal;
	private Timestamp horaEntrada;
	private Timestamp horaSalida;
	private String modoPago;
	private int precio;
	private boolean pagado;

	public Reserva(String socioID, String instalacionID, Timestamp horaComienzo, Timestamp horaFinal,
			Timestamp horaEntrada, Timestamp horaSalida, String modoPago, boolean pagado, int precio) {
		this.pagado = pagado;
		this.socioID=socioID;
		this.instalacionID=instalacionID;
		this.horaComienzo=horaComienzo;
		this.horaFinal=horaFinal;
		this.horaEntrada=horaEntrada;
		this.horaSalida=horaSalida;
		this.modoPago=modoPago;
		this.precio=precio;

	}

	public String getSocioID() {
		return socioID;
	}

	public String getInstalacionID() {
		return instalacionID;
	}

	public Timestamp getHoraComienzo() {
		return horaComienzo;
	}

	public Timestamp getHoraFinal() {
		return horaFinal;
	}

	public Timestamp getHoraEntrada() {
		return horaEntrada;
	}

	public Timestamp getHoraSalida() {
		return horaSalida;
	}

	public String getModoPago() {
		return modoPago;
	}

	public int getPrecio() {
		return precio;
	}
	

}
