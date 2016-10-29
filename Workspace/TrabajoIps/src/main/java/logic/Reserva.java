package logic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import db.Database;
import db.Parser;

public class Reserva {
	
	private int reservaID;
	private int socioID;
	private int instalacionID;
	private Timestamp horaComienzo;
	private Timestamp horaFinal;
	private Timestamp horaEntrada;
	private Timestamp horaSalida;
	private String modoPago;
	private int precio;

	public Reserva(int reservaID, int socioID, int instalacionID, Timestamp horaComienzo, Timestamp horaFinal,
			Timestamp horaEntrada, Timestamp horaSalida, String modoPago, int precio) {
		this.reservaID = reservaID;
		this.socioID= socioID;
		this.instalacionID = instalacionID;
		this.horaComienzo = horaComienzo;
		this.horaFinal = horaFinal;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.modoPago = modoPago;
		this.precio = precio;

	}

	public int getReservaID(){
		return this.reservaID;
	}
	
	public int getSocioID() {
		return socioID;
	}

	public int getInstalacionID() {
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

	/**
	 * Hace una reserva y la guarda en la base de datos.
	 * historia->(Como socio quiero hacer una reserva de una instalación para mi uso particular)
	 * 
	 * @param reservaID, ID de la reserva que se quiere hacer
	 * 
	 * @author David
	 */
	public void hacerReserva(int reservaID, int socioID, int instalacionID, Timestamp horaComienzo, Timestamp horaFinal, Timestamp horaEntrada, Timestamp horaSalida, String modoPago, int precio){
		//Nueva reserva
		Reserva reserva = new Reserva(reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada, horaSalida, modoPago, precio);
		
		//Comprobar maximo de horas
		if(comprobarMaxHorasSeguidas(horaComienzo, horaFinal)){
			//Comprobar antelacion
			if(comprobarAntelacion(horaComienzo)){
				//Comprobar duracion
				if(comprobarMaxHorasSeguidas(horaComienzo, horaFinal)){
					//Comprobar reservas simultaneas
					if(!comprobarDisponibilidadPorSocio(socioID, horaComienzo, horaFinal)){
						//Comprobar disponibilidad
						if(comprobarDisponibilidadPorInstalacion(instalacionID, horaComienzo, horaFinal)){
							boolean aux = addReservaABase(reserva);
							if(aux){
								System.out.println("Reserva " + reservaID + " añadida a la base de datos.");
							}else{
								System.out.println("Reserva " + reservaID + " no ha podido ser añadida a la base de datos.");
							}
						}else{
							System.out.println("Reserva " + reservaID + " tiene un problema de colision de horarios.");
						}
					}else{
						System.out.println("El socio " + socioID + " tiene mas de una reserva simultanea.");
					}
				}else{
					System.out.println("Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas");
				}
			}else{
				System.out.println("No se puede hacer una reserva con menos de 1 hora, ni con mas de 15 dias, de antelacion.");
			}
		}else{
			System.out.println("Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas.");
		}
		
		
	}
	
	/**
	 * Comprueba que la fecha de reserva esté en un rango de al menos 1 dia hasta 15 como maximo
	 * 
	 * @return true si esta en el rango, false si no lo esta
	 * @author David
	 */
	@SuppressWarnings("deprecation")
	private boolean comprobarAntelacion(Timestamp horaComienzo){
		//fecha anterior a la actual
		if(!horaComienzo.before(Timestamp.valueOf(LocalDateTime.now()))){
			//mismo mes
			if(horaComienzo.getMonth() == LocalDateTime.now().getMonthValue()){
				//dias
				if(horaComienzo.getDay() - LocalDateTime.now().getDayOfMonth() >=1 && horaComienzo.getDay() - LocalDateTime.now().getDayOfMonth() <= 15){
					return true;
				}else{
					return false;
				}
			}else{
				//Mes siguiente
				if(horaComienzo.getMonth() - LocalDateTime.now().getMonthValue() <= 1){
					//dias
					if(horaComienzo.getDay()+30 - LocalDateTime.now().getDayOfMonth() >=1 && horaComienzo.getDay()+30 - LocalDateTime.now().getDayOfMonth() <= 15){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}else{
			return false;
		}	
	}
	
	/**
	 * Comprueba que las horas de una reserva no superen el maximo de 2
	 * 
	 * @return true si no lo superan, false si lo hacen
	 * @author David
	 */
	@SuppressWarnings("deprecation")
	private boolean comprobarMaxHorasSeguidas(Timestamp horaComienzo, Timestamp horaFinal){
		if(horaFinal.getHours() - horaComienzo.getHours() <= 2)
			return true;
		else
			return false;
	}
	
	/**
	 * Add a una reserva a la base de datos
	 * 
	 * @return true, si se ha podido añadir. False, si ha habido un error.
	 * @author David
	 */
	private boolean addReservaABase(Reserva reserva){
		try {
			Database.getInstance().getC().createStatement().execute("INSERT INTO Reserva (reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada, horaSalida, modoPago, precio) VALUES (" 
			+ reserva.getReservaID() + "," + reserva.getSocioID() + "," + reserva.getInstalacionID() + ",'" + reserva.getHoraComienzo() + "','" + reserva.getHoraFinal() + "','" + reserva.getHoraEntrada() + "','" + reserva.getHoraSalida() + "','" + reserva.getModoPago()
			+ "'," + reserva.getPrecio() + ");");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer addReservaABase con la reserva : " + reserva.getReservaID());
			return false;
		}
	}
	
	/**
	 * Comprueba si una instalacion esta ocupada en una franja horaria para un dia determinado
	 * 
	 * @return true si está disponible, false si no lo esta
	 * @author David
	 */
	private boolean comprobarDisponibilidadPorInstalacion(int instalacionID, Date horaComienzo, Date horaFinal) {
		Parser parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al comprobarDisponibilidadPorInstalacion con las reservas del socio: " + socioID);
		}
		return parser.comprobarDisponibilidadPorInstalacion(instalacionID, horaComienzo, horaFinal);
	}
	
	/**
	 * Comprueba si un socio tiene mas de una reserva simultanea
	 * 
	 * @return true si la tiene, false si no la tiene
	 * @author David
	 */
	private boolean comprobarDisponibilidadPorSocio(int socioID, Date horaComienzo, Date horaFinal) {
		Parser parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al comprobarDisponibilidadPorSocio con las reservas del socio: " + socioID);
		}
		return parser.comprobarDisponibilidadPorSocio(socioID, horaComienzo, horaFinal);
	}
}
