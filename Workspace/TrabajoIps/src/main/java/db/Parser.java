package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import logic.Actividad;
import logic.Cuota;
import logic.Instalacion;
import logic.ObjetoCuota;
import logic.Recibo;
import logic.Reserva;
import logic.ReservaActividad;
import logic.Socio;
import logic.SocioActividad;

public class Parser {

	ArrayList<Socio> socios = new ArrayList<>();
	ArrayList<Instalacion> instalaciones = new ArrayList<>();
	ArrayList<Reserva> reservas = new ArrayList<>();
	ArrayList<ObjetoCuota> objetoscuota = new ArrayList<>();
	ArrayList<Actividad> actividades = new ArrayList<>();
	ArrayList<ReservaActividad> reservasactividad = new ArrayList<>();
	ArrayList<SocioActividad> sociosactividad = new ArrayList<>();

	public ArrayList<SocioActividad> getSociosactividad() {
		return sociosactividad;
	}

	ArrayList<Cuota> cuotas = new ArrayList<>();
	ArrayList<Recibo> recibos = new ArrayList<>();

	public ArrayList<Recibo> getRecibos() {
		return recibos;
	}

	public ArrayList<ReservaActividad> getReservasactividad() {
		return reservasactividad;
	}

	public ArrayList<Socio> getSocios() {
		return socios;
	}

	public ArrayList<Instalacion> getInstalaciones() {
		return instalaciones;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public ArrayList<ObjetoCuota> getObjetosCuota() {
		return objetoscuota;
	}

	public ArrayList<Actividad> getActividades() {
		return actividades;
	}

	Connection c = Database.getInstance().getC();

	public void fillArrays() throws SQLException {
		reservas.clear();
		socios.clear();
		instalaciones.clear();
		recibos.clear();
		actividades.clear();
		objetoscuota.clear();
		cuotas.clear();
		sociosactividad.clear();
		reservasactividad.clear();
		
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from SOCIO");
		while (rs.next()) {
			socios.add(new Socio(rs.getString("socioID")));
		}

		s = c.createStatement();
		rs = s.executeQuery("select * from INSTALACION");
		while (rs.next()) {
			instalaciones.add(new Instalacion(rs.getInt("instalacionID"), rs.getString("instalacion_nombre")));
		}

		s = c.createStatement();
		rs = s.executeQuery("Select * From RESERVA");
		while (rs.next()) {
			reservas.add(new Reserva(rs.getInt("reservaID"), rs.getString("socioID"), rs.getInt("instalacionID"),
					rs.getTimestamp("horaComienzo"), rs.getTimestamp("horaFinal"), rs.getTimestamp("horaEntrada"),
					rs.getTimestamp("horaSalida"), rs.getString("modoPago"), rs.getBoolean("reciboGenerado"),
					rs.getInt("precio")));
		}

		s = c.createStatement();
		rs = s.executeQuery("Select * From OBJETOCUOTA");
		while (rs.next()) {
			objetoscuota.add(new ObjetoCuota(rs.getInt("reciboID"), rs.getString("socioID"), rs.getBoolean("pagado"),
					rs.getInt("importe"), rs.getTimestamp("fecha")));

		}

		s = c.createStatement();
		rs = s.executeQuery("Select * From RECIBO");
		while (rs.next()) {
			recibos.add(new Recibo(rs.getString("socioID"), rs.getInt("importe"), rs.getString("descripcion")));
		}

		s = c.createStatement();
		rs = s.executeQuery("Select * From CUOTA");
		while (rs.next()) {
			cuotas.add(new Cuota(rs.getString("socioID"), rs.getInt("mes"), rs.getInt("importe")));
		}

		s = c.createStatement();
		rs = s.executeQuery("Select * From SOCIOACTIVIDAD");
		while (rs.next()) {
			sociosactividad.add(new SocioActividad(rs.getString("socioID"), rs.getInt("reservaID"),
					rs.getInt("actividadID"), rs.getBoolean("presentado")));
		}

		s = c.createStatement();
		rs = s.executeQuery("Select * From RESERVAACTIVIDAD");
		while (rs.next()) {
			reservasactividad.add(new ReservaActividad(rs.getInt("actividadID"), rs.getInt("reservaID")));
		}

		s = c.createStatement();
		rs = s.executeQuery("Select * From ACTIVIDAD");
		while(rs.next())
		{
			actividades.add(new Actividad(rs.getInt("actividadID"),rs.getInt("instalacionID"), rs.getString("actividad_nombre"), rs.getInt("semanas")));
		}

	}

	/**
	 * Limpia todos los arrays
	 */
	public void removeArrays() {
		if (socios != null)
			for (int i = socios.size() - 1; i >= 0; i--)
				socios.remove(i);
		if (instalaciones != null)
			for (int i = instalaciones.size() - 1; i >= 0; i--)
				instalaciones.remove(i);
		if (reservas != null)
			for (int i = reservas.size() - 1; i >= 0; i--)
				reservas.remove(i);
		if (objetoscuota != null)
			for (int i = objetoscuota.size() - 1; i >= 0; i--)
				objetoscuota.remove(i);
		if (recibos != null)
			for (int i = recibos.size() - 1; i >= 0; i--)
				recibos.remove(i);
		if (cuotas != null)
			for (int i = cuotas.size() - 1; i >= 0; i--)
				cuotas.remove(i);
		if (sociosactividad != null)
			for (int i = sociosactividad.size() - 1; i >= 0; i--)
				sociosactividad.remove(i);
		if (reservasactividad != null)
			for (int i = reservasactividad.size() - 1; i >= 0; i--)
				reservasactividad.remove(i);
		if (actividades != null)
			for (int i = actividades.size() - 1; i >= 0; i--)
				actividades.remove(i);
		
	}

	/**
	 * Comprueba si está ocupada en una franja horaria
	 * 
	 * @param horaC,
	 *            hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return true si está disponible, false si no
	 */
	@SuppressWarnings("deprecation")
	public boolean comprobarDisponibilidadPorInstalacion(int instalacionID, Date horaC, Date horaF) {
		boolean resultado = true;

		for (Reserva reserva : reservas) {
			if (getDia(reserva.getHoraComienzo()) == getDia(horaC)) {
				if (reserva.getHoraComienzo().getHours() == horaC.getHours() && reserva.getHoraFinal().getHours() == horaF.getHours()
						&& reserva.getInstalacionID() == instalacionID) {
					resultado = false;
				}
			}
		}
		return resultado;
	}

	public Reserva comprobarDisponibilidadPorInstalacionAdmin(int instalacionID, Date horaC, Date horaF) {
		boolean resultado = true;
		Reserva ret = null;
		for (Reserva reserva : reservas) {
			//if (getDia(reserva.getHoraComienzo()) == getDia(horaC) && getHora(reserva.getHoraComienzo())== getHora(horaC) && getHora(reserva.getHoraFinal())==getHora(horaF) && reserva.getInstalacionID()==instalacionID) {
			if( horaC.before(reserva.getHoraFinal()) && reserva.getHoraComienzo().before(horaF) )
			{
				ret=reserva; 
				break;
			}
		}
		return ret;
	}

	/**
	 * Comprueba si un socio tiene mas de una instalacion reservada
	 * simultaneamente
	 * 
	 * @param horaC,
	 *            hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return true si la tiene, false si no la tiene
	 */
	@SuppressWarnings("deprecation")
	public boolean comprobarDisponibilidadPorSocio(String socioID, int insID, Date horaC, Date horaF) {
		boolean resultado = false;

		for (Reserva reserva : reservas) {
			if (getDia(reserva.getHoraComienzo()) == getDia(horaC)) {
				if (insID == reserva.getInstalacionID()) {
					if (reserva.getHoraComienzo().getHours() == horaC.getHours() && reserva.getHoraFinal().getHours() == horaF.getHours()
							&& reserva.getSocioID().equals(socioID)) {
						resultado = true;
					}
				}
			}
		}
		return resultado;
	}

	public Reserva comprobarDisponibilidadPorSocioAdmin(String socioID, Date horaC, Date horaF) {
		boolean resultado = false;
		Reserva ret = null;
		for (Reserva reserva : reservas) {
			//if (getDia(reserva.getHoraComienzo())==getDia(horaC) && getHora(reserva.getHoraComienzo())==getHora(horaC) && getHora(reserva.getHoraFinal())==getHora(horaF) && reserva.getSocioID().equals(socioID)) {
			if( horaC.before(reserva.getHoraFinal()) && reserva.getHoraComienzo().before(horaF) )	
			{
				reserva=ret; 
				break;
			}
		}
		return ret;
	}

	/**
	 * Borra la reserva de un socio
	 * 
	 * @param socioID
	 * @param horaC,
	 *            hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return true si se puede borrar, false si no se puede
	 */
	@SuppressWarnings("deprecation")
	public boolean marcarReserva(String socioID, Date horaC, Date horaF) {
		boolean resultado = false;
		int aux = 0;
		for (Reserva reserva : reservas) {
			if (reserva.getHoraComienzo().getHours() == horaC.getHours()
					&& reserva.getHoraFinal().getHours() == horaF.getHours() && reserva.getSocioID().equals(socioID)
					&& reserva.getHoraComienzo().getMonth() == horaC.getMonth()
					&& reserva.getHoraComienzo().getYear() == horaC.getYear()) {
				// Cancelado mas de una hora antes
				if (getDia(reserva.getHoraComienzo()) == LocalDateTime.now().getDayOfMonth())
					if (reserva.getHoraComienzo().getHours() - LocalDateTime.now().getHour() > 1) {
						resultado = true;
						aux = 1;
					} else {
						aux = 1;
						resultado = false;
						System.out.println("No se puede cancelar una reserva menos de 1 hora antes.");
					}
				else {
					resultado = true;
					aux = 1;
				}
			}
		}
		if (aux == 0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado la reserva del socio: " + socioID);
		return resultado;
	}
	
	private int getHora(Timestamp t)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.HOUR);
	}
	
	private int getHora(Date d)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		return cal.get(Calendar.HOUR);
	}

	private int getDia(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	private int getDia(Date d)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Borra la reserva de un socio
	 * 
	 * @param socioID
	 * @param horaC,
	 *            hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return la reserva si se pudo borrar, null si no se pudo
	 */
	@SuppressWarnings("deprecation")
	public Reserva borrarReserva(String socioID, Date horaC, Date horaF) {
		Reserva resultado = null;

		for (Reserva reserva : reservas) {
			if (reserva.getHoraComienzo().getHours() == horaC.getHours()
					&& getDia(reserva.getHoraComienzo()) == getDia(horaC)
					&& reserva.getHoraFinal().getHours() == horaF.getHours() && reserva.getSocioID().equals(socioID)
					&& reserva.getHoraComienzo().getMonth() == horaC.getMonth()
					&& reserva.getHoraComienzo().getYear() == horaC.getYear()) {
				resultado = reserva;
			}
		}
		reservas.remove(resultado);
		return resultado;
	}

	public void actualizaRegistro(String elementAt, int actividadId) {
		SocioActividad sa = null;
		for (SocioActividad s : sociosactividad) {
			// Poner if() de 5 min antes si no lo hace el monitor [preguntar]
			if(s.getSocioID().equals(elementAt) && s.getActividadID() == actividadId)
			try {
				Statement s1 = c.createStatement();
				s1.executeUpdate("UPDATE socioActividad SET presentado = true where socioId = '" + elementAt
						+ "' and actividadId = " + actividadId);
				JOptionPane.showMessageDialog(null, "cliente "+ elementAt + " actualizado");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
