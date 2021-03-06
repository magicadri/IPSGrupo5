package logic;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import db.Database;
import db.Parser;
import gui.VentanaResolverColisiones;

public class Reserva {

	Parser parser;
	private int reservaID;
	private String socioID;
	private int instalacionID;
	private Timestamp horaComienzo;
	private Timestamp horaFinal;
	private Timestamp horaEntrada;
	private Timestamp horaSalida;
	private String modoPago;
	private int precio;
	private boolean reciboGenerado;

	public Reserva(int reservaID, String socioID, int instalacionID, Timestamp horaComienzo, Timestamp horaFinal,
			Timestamp horaEntrada, Timestamp horaSalida, String modoPago, boolean reciboGenerado, int precio) {
		this.reservaID = reservaID;
		this.socioID = socioID;
		this.instalacionID = instalacionID;
		this.horaComienzo = horaComienzo;
		this.horaFinal = horaFinal;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.modoPago = modoPago;
		this.precio = precio;
		this.reciboGenerado = reciboGenerado;
		this.parser = new Parser();
	}

	public Reserva() {
		this.parser = new Parser();
	}

	public int getReservaID() {
		return this.reservaID;
	}

	public String getSocioID() {
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

	public boolean getReciboGenerado() {
		return reciboGenerado;
	}

	public int getPrecio() {
		return precio;
	}

	/**
	 * Borra la reserva del socio entre las horas marcadas
	 * 
	 * historia->(Como socio quiero cancelar la reserva de una instalaci�n para
	 * no tener que pagar por algo que no voy a utilizar)
	 * 
	 * @author David
	 */

	// Cancelar reservas por el centro para que queden libres para socios
	public boolean cancelarReserva(String socioID, Timestamp horaComienzo, Timestamp horaFinal) {
		boolean result = marcarReserva(socioID, horaComienzo, horaFinal);
		if (marcarReserva(socioID, horaComienzo, horaFinal)) {
			removeReservaDeBase(parser.borrarReserva(socioID, horaComienzo, horaFinal));
			return result;
		}
		return result;
	}

	/**
	 * Comprueba si la reserva se puede borrar
	 * 
	 * @return true si se puede, false si no se puede
	 * 
	 * @author David
	 */
	private boolean marcarReserva(String socioID, Timestamp horaComienzo, Timestamp horaFinal) {
		try {
			parser.removeArrays();
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer marcarReserva con la reserva del socio: " + socioID);
		}
		return parser.marcarReserva(socioID, horaComienzo, horaFinal);
	}

	/**
	 * Remove a una reserva de la base de datos
	 * 
	 * @return true, si se ha podido borrar. False, si ha habido un error.
	 * 
	 * @author David
	 */
	private void removeReservaDeBase(Reserva reserva) {
		try {
			Database.getInstance().getC().createStatement()
					.execute("DELETE FROM Reserva WHERE reservaID =" + reserva.getReservaID() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer removeReservaABase con la reserva : " + reserva.getReservaID());
		}
	}

	/**
	 * Hace una reserva y la guarda en la base de datos. historia->(Como socio
	 * quiero hacer una reserva de una instalaci�n para mi uso particular)
	 * 
	 * @param reservaID,
	 *            ID de la reserva que se quiere hacer
	 * 
	 * @author David
	 */
	public void hacerReserva(String socioID, int instalacionID, int reservaID, Timestamp horaComienzo,
			Timestamp horaFinal, Timestamp horaEntrada, Timestamp horaSalida, String modoPago, boolean reciboGenerado,
			int precio) {
		// Nueva reserva
		Reserva reserva = new Reserva(reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada,
				horaSalida, modoPago, reciboGenerado, precio);

		// Comprobar maximo de horas
		if (comprobarMaxHorasSeguidas(horaComienzo, horaFinal)) {
			// Comprobar antelacion
			if (comprobarAntelacion(horaComienzo)) {
				// Comprobar duracion
				if (comprobarMaxHorasSeguidas(horaComienzo, horaFinal)) {
					// Comprobar reservas simultaneas
					if (!comprobarDisponibilidadPorSocio(socioID, instalacionID, horaComienzo, horaFinal)) {
						// Comprobar disponibilidad
						if (comprobarDisponibilidadPorInstalacion(instalacionID, horaComienzo, horaFinal)) {
							boolean aux = addReservaABase(reserva);
							if (aux) {
								JOptionPane.showMessageDialog(null,
										"Reserva " + reservaID + " a�adida a la base de datos.");
							} else {
								JOptionPane.showMessageDialog(null,
										"Reserva " + reservaID + " no ha podido ser a�adida a la base de datos.");
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Reserva " + reservaID + " tiene un problema de colision de horarios.");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"El socio " + socioID + " tiene mas de una reserva simultanea.");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"No se puede hacer una reserva con menos de 1 hora, ni con mas de 15 dias, de antelacion.");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas.");
		}

	}

	public void hacerReservaTodoElDia(String socioID, int instalacionID, int reservaID, Timestamp horaComienzo,
			Timestamp horaFinal, Timestamp horaEntrada, Timestamp horaSalida, String modoPago, boolean reciboGenerado,
			int precio) {
		// Nueva reserva
		Reserva reserva = new Reserva(reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada,
				horaSalida, modoPago, reciboGenerado, precio);

		// Comprobar maximo de horas
		if (comprobarMaxHorasSeguidas(horaComienzo, horaFinal)) {
			// Comprobar antelacion
			if (comprobarAntelacion(horaComienzo)) {
				// Comprobar duracion
				if (comprobarMaxHorasSeguidas(horaComienzo, horaFinal)) {
					// Comprobar reservas simultaneas
					if (!comprobarDisponibilidadPorSocio(socioID, instalacionID, horaComienzo, horaFinal)) {
						// Comprobar disponibilidad
						if (comprobarDisponibilidadPorInstalacion(instalacionID, horaComienzo, horaFinal)) {
							boolean aux = addReservaABase(reserva);
							if (aux) {

							} else {

							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Reserva " + reservaID + " tiene un problema de colision de horarios.");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"El socio " + socioID + " tiene mas de una reserva simultanea.");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"No se puede hacer una reserva con menos de 1 hora, ni con mas de 15 dias, de antelacion.");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas.");
		}

	}

	public void hacerReservaAdmin(String socioID, int instalacionID, int reservaID, Timestamp horaComienzo,
			Timestamp horaFinal, Timestamp horaEntrada, Timestamp horaSalida, String modoPago, boolean reciboGenerado,
			int precio) {
		// Nueva reserva
		Reserva reserva = new Reserva(reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada,
				horaSalida, modoPago, reciboGenerado, precio);

		// Comprobar maximo de horas
		if (comprobarMaxHorasSeguidas(horaComienzo, horaFinal)) {
			// Comprobar duracion
			if (comprobarMaxHorasSeguidas(horaComienzo, horaFinal)) {
				// Comprobar reservas simultaneas
				if (comprobarDisponibilidadPorSocioAdmin(socioID, horaComienzo, horaFinal) != null) {
					boolean aux = addReservaABase(reserva);
					Reserva rem = comprobarDisponibilidadPorSocioAdmin(socioID, horaComienzo, horaFinal);
					removeReservaDeBase(rem);

					disculpaSocio(rem.getSocioID() + ", lo sentimos, su reserva " + rem.getReservaID()
							+ " ha sido cancelada.");
					if (aux) {
						JOptionPane.showMessageDialog(null, "Reserva " + reservaID + " a�adida a la base de datos.");
					} else {
						JOptionPane.showMessageDialog(null,
								"Reserva " + reservaID + " no ha podido ser a�adida a la base de datos.");
					}
				} else if (comprobarDisponibilidadPorInstalacionAdmin(instalacionID, horaComienzo, horaFinal) != null) {
					boolean aux = addReservaABase(reserva);
					Reserva rem = comprobarDisponibilidadPorInstalacionAdmin(instalacionID, horaComienzo, horaFinal);
					removeReservaDeBase(rem);
					disculpaSocio(rem.getSocioID() + ", lo sentimos, su reserva " + rem.getReservaID()
							+ " ha sido cancelada.");

					if (aux) {
						JOptionPane.showMessageDialog(null, "Reserva " + reservaID + " a�adida a la base de datos.");
					} else {
						JOptionPane.showMessageDialog(null,
								"Reserva " + reservaID + " no ha podido ser a�adida a la base de datos.");
					}
				}

				else {
					boolean aux = addReservaABase(reserva);
					if (aux) {
						JOptionPane.showMessageDialog(null, "Reserva " + reservaID + " a�adida a la base de datos.");
					} else {
						JOptionPane.showMessageDialog(null,
								"Reserva " + reservaID + " no ha podido ser a�adida a la base de datos.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Los horarios de la reserva " + reservaID + "no pueden durar mas de 2 horas.");
		}

	}

	private void disculpaSocio(String disculpanomas) {
		System.out.println(disculpanomas);
	}

	private int getDia(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Comprueba que la fecha de reserva est� en un rango de al menos 1 dia
	 * hasta 15 como maximo
	 * 
	 * @return true si esta en el rango, false si no lo esta
	 * 
	 * @author David
	 */
	@SuppressWarnings("deprecation")
	private boolean comprobarAntelacion(Timestamp horaComienzo) {
		// fecha anterior a la actual
		if (!horaComienzo.before(Timestamp.valueOf(LocalDateTime.now()))) {
			// mismo mes
			if (horaComienzo.getMonth() + 1 == LocalDateTime.now().getMonthValue()) {
				// dias
				if (getDia(horaComienzo) - LocalDateTime.now().getDayOfMonth() >= 0
						&& getDia(horaComienzo) - LocalDateTime.now().getDayOfMonth() <= 15) {
					return true;
				} else {
					return false;
				}
			} else {
				// Mes siguiente
				if (horaComienzo.getMonth() + 1 - LocalDateTime.now().getMonthValue() <= 1) {
					// dias
					if (getDia(horaComienzo) + 30 - LocalDateTime.now().getDayOfMonth() >= 1
							&& getDia(horaComienzo) + 30 - LocalDateTime.now().getDayOfMonth() <= 15) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * Comprueba que las horas de una reserva no superen el maximo de 2
	 * 
	 * @return true si no lo superan, false si lo hacen
	 * 
	 * @author David
	 */
	@SuppressWarnings("deprecation")
	public boolean comprobarMaxHorasSeguidas(Timestamp horaComienzo, Timestamp horaFinal) {
		if (horaFinal.getHours() - horaComienzo.getHours() <= 2)
			return true;
		else
			return false;
	}

	/**
	 * Add a una reserva a la base de datos
	 * 
	 * @return true, si se ha podido a�adir. False, si ha habido un error.
	 * 
	 * @author David
	 */
	private boolean addReservaABase(Reserva reserva) {
		try {
			if (reserva.getHoraEntrada() != null)
				Database.getInstance().getC().createStatement().execute(
						"INSERT INTO Reserva (reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada, horaSalida, modoPago, reciboGenerado, precio) VALUES ("
								+ reserva.getReservaID() + ",'" + reserva.getSocioID() + "',"
								+ reserva.getInstalacionID() + ",'" + reserva.getHoraComienzo() + "','"
								+ reserva.getHoraFinal() + "','" + reserva.getHoraEntrada() + "','"
								+ reserva.getHoraSalida() + "','" + reserva.getModoPago() + "',"
								+ reserva.getReciboGenerado() + "," + reserva.getPrecio() + ");");
			else
				Database.getInstance().getC().createStatement().execute(
						"INSERT INTO Reserva (reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada, horaSalida, modoPago, reciboGenerado, precio) VALUES ("
								+ reserva.getReservaID() + ",'" + reserva.getSocioID() + "',"
								+ reserva.getInstalacionID() + ",'" + reserva.getHoraComienzo() + "','"
								+ reserva.getHoraFinal() + "'," + reserva.getHoraEntrada() + ","
								+ reserva.getHoraSalida() + ",'" + reserva.getModoPago() + "',"
								+ reserva.getReciboGenerado() + "," + reserva.getPrecio() + ");");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer addReservaABase con la reserva : " + reserva.getReservaID());
			return false;
		}
	}

	/**
	 * Comprueba si una instalacion esta ocupada en una franja horaria para un
	 * dia determinado
	 * 
	 * @return true si est� disponible, false si no lo esta
	 * 
	 * @author David
	 */
	private boolean comprobarDisponibilidadPorInstalacion(int instalacionID, Date horaComienzo, Date horaFinal) {
		try {
			parser.removeArrays();
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al comprobarDisponibilidadPorInstalacion con las reservas del socio: " + socioID);
		}
		return parser.comprobarDisponibilidadPorInstalacion(instalacionID, horaComienzo, horaFinal);
	}

	private Reserva comprobarDisponibilidadPorInstalacionAdmin(int instalacionID, Date horaComienzo, Date horaFinal) {
		try {
			parser.removeArrays();
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al comprobarDisponibilidadPorInstalacion con las reservas del socio: " + socioID);
		}
		return parser.comprobarDisponibilidadPorInstalacionAdmin(instalacionID, horaComienzo, horaFinal);
	}

	/**
	 * Comprueba si un socio tiene mas de una reserva simultanea
	 * 
	 * @return true si la tiene, false si no la tiene
	 * 
	 * @author David
	 */
	private boolean comprobarDisponibilidadPorSocio(String socioID, int insID, Date horaComienzo, Date horaFinal) {
		try {
			parser.removeArrays();
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al comprobarDisponibilidadPorSocio con las reservas del socio: " + socioID);
		}
		return parser.comprobarDisponibilidadPorSocio(socioID, insID, horaComienzo, horaFinal);
	}

	private Reserva comprobarDisponibilidadPorSocioAdmin(String socioID, Date horaComienzo, Date horaFinal) {
		try {
			parser.removeArrays();
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al comprobarDisponibilidadPorSocio con las reservas del socio: " + socioID);
		}
		return parser.comprobarDisponibilidadPorSocioAdmin(socioID, horaComienzo, horaFinal);
	}

	public void reservaNueva(String socioID, int instalacionID, int reservaID, Timestamp horaComienzo,
			Timestamp horaFinal, Timestamp horaEntrada, Timestamp horaSalida, String modoPago, boolean reciboGenerado,
			int precio) throws HeadlessException, SQLException {
		// Nueva reserva
		Reserva reserva = new Reserva(reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada,
				horaSalida, modoPago, reciboGenerado, precio);
		// Comprobar reservas simultaneas
		if (comprobarColisiones(reserva).size()!=0) {
			VentanaResolverColisiones vrc = new VentanaResolverColisiones(null, comprobarColisiones(reserva), reserva);
			vrc.setVisible(true);
		} 
		else {
			boolean aux = addReservaABase(reserva);
			if (aux) {
				JOptionPane.showMessageDialog(null, "Reserva " + reservaID + " a�adida a la base de datos.");
			} else {
				JOptionPane.showMessageDialog(null,
						"Reserva " + reservaID + " no ha podido ser a�adida a la base de datos.");
			}
		}
	}
	
	public ArrayList<Reserva> comprobarColisiones(Reserva reserva) throws SQLException
	{
		parser.fillArrays();
		ArrayList<Reserva> aux = new ArrayList<>();
		for(Reserva each: parser.getReservas())
		{
			if (each.getHoraComienzo().before(reserva.getHoraFinal()) && reserva.getHoraComienzo().before(each.getHoraFinal()) && each.getInstalacionID() == reserva.getInstalacionID())
				aux.add(each);
		}
		return aux;
	}

}
