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
import logic.Socio;

public class Parser {
	
	ArrayList<Socio> socios = new ArrayList<>();
	ArrayList<Instalacion> instalaciones = new ArrayList<>();
	ArrayList<Reserva> reservas = new ArrayList<>();
	ArrayList<ObjetoCuota> objetoscuota = new ArrayList<>();
	ArrayList<Actividad> actividades = new ArrayList<>();
	ArrayList<Cuota> cuotas = new ArrayList<>();
	ArrayList<Recibo> recibos = new ArrayList<>();
	
	public ArrayList<Recibo> getRecibos() {
		return recibos;
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
	
	public void fillArrays() throws SQLException
	{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from SOCIO");
		while(rs.next())
		{
			socios.add(new Socio(rs.getString("socioID")));
		}
		
		s = c.createStatement();
		rs = s.executeQuery("select * from INSTALACION");
		while(rs.next())
		{
			instalaciones.add(new Instalacion(rs.getInt("instalacionID"), rs.getString("instalacion_nombre")));
		}
		
		s = c.createStatement();
		rs = s.executeQuery("Select * From RESERVA");
		while(rs.next())
		{
			reservas.add(new Reserva(rs.getInt("reservaID"),rs.getString("socioID"), rs.getInt("instalacionID"),
					rs.getTimestamp("horaComienzo"), rs.getTimestamp("horaFinal"),
					rs.getTimestamp("horaEntrada"), rs.getTimestamp("horaSalida"), 
					rs.getString("modoPago"),rs.getBoolean("reciboGenerado"), rs.getInt("precio")));
		}
		
		s = c.createStatement();
		rs = s.executeQuery("Select * From OBJETOCUOTA");
		while(rs.next())
		{
			objetoscuota.add(new ObjetoCuota(rs.getInt("reciboID"),rs.getString("socioID"),rs.getBoolean("pagado"), rs.getInt("importe"),
					rs.getTimestamp("fecha")));
		
		}
		
		s = c.createStatement();
		rs = s.executeQuery("Select * From RECIBO");
		while(rs.next())
		{
			recibos.add(new Recibo(rs.getString("socioID"), rs.getInt("importe"),
					rs.getString("descripcion")));
		}
		
		s = c.createStatement();
		rs = s.executeQuery("Select * From CUOTA");
		while(rs.next())
		{
			cuotas.add(new Cuota(rs.getString("socioID"),rs.getInt("mes"), rs.getInt("importe")));
		}
		
//		s = c.createStatement();
//		rs = s.executeQuery("Select * From ACTIVIDAD");
//		while(rs.next())
//		{
//			actividades.add(new Actividad(rs.getString("actividadID"), rs.getTimestamp("fechaComienzo"), rs.getTimestamp("fechaFinal")));
//		}
//		
	}
	
	/**
	 * Limpia todos los arrays
	 */
	public void removeArrays(){
		if(socios != null)
			for(int i=socios.size()-1;i>=0; i--)
				socios.remove(i);
		if(instalaciones != null)
			for(int i=instalaciones.size()-1;i>=0;i--)
				instalaciones.remove(i);
		if(reservas != null)
			for(int i=reservas.size()-1;i>=0; i--)
				reservas.remove(i);
	}
	
	/**
	 * Comprueba si est� ocupada en una franja horaria
	 * 
	 * @param horaC,
	 *            hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return true si est� disponible, false si no
	 */
	@SuppressWarnings("deprecation")
	public boolean comprobarDisponibilidadPorInstalacion(int instalacionID, Date horaC, Date horaF) {
		boolean resultado = true;

		for (Reserva reserva : reservas) {
			if (reserva.getHoraComienzo().getHours()==horaC.getHours() && reserva.getHoraFinal().getHours()==horaF.getHours() && reserva.getInstalacionID()==instalacionID) {
				resultado = false;
			}
		}
		return resultado;
	}
	
	
	/**
	 * Comprueba si un socio tiene mas de una instalacion reservada simultaneamente
	 * 
	 * @param horaC,
	 *            hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return true si la tiene, false si no la tiene
	 */
	@SuppressWarnings("deprecation")
	public boolean comprobarDisponibilidadPorSocio(String socioID, Date horaC, Date horaF) {
		boolean resultado = false;

		for (Reserva reserva : reservas) {
			if (reserva.getHoraComienzo().getHours()==horaC.getHours() && reserva.getHoraFinal().getHours()==horaF.getHours() && reserva.getSocioID().equals(socioID)) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	/**
	 * Borra la reserva de un socio
	 * @param socioID
	 * @param horaC,
	 * 			  hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return true si se puede borrar, false si no se puede
	 */
	@SuppressWarnings("deprecation")
	public boolean marcarReserva(String socioID,  Date horaC, Date horaF) {
		boolean resultado = false;
		int aux = 0;
		for (Reserva reserva : reservas) {
			if (reserva.getHoraComienzo().getHours()==horaC.getHours() && reserva.getHoraFinal().getHours()==horaF.getHours() && reserva.getSocioID().equals(socioID)  &&
					reserva.getHoraComienzo().getMonth() == horaC.getMonth() && reserva.getHoraComienzo().getYear() == horaC.getYear()) {
				//Cancelado mas de una hora antes
				if(getDia(reserva.getHoraComienzo()) == LocalDateTime.now().getDayOfMonth())
					if( reserva.getHoraComienzo().getHours() - LocalDateTime.now().getHour() > 1){
						resultado = true;
						aux = 1;
					}else{
						aux = 1;
						resultado = false;
						System.out.println("No se puede cancelar una reserva menos de 1 hora antes.");
					}
				else{
					resultado = true;
					aux = 1;
				}
			}
		}
		if(aux == 0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado la reserva del socio: " + socioID);
		return resultado;
	}
	
	private int getDia(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Borra la reserva de un socio
	 * @param socioID
	 * @param horaC,
	 * 			  hora de comienzo
	 * @param horaF,
	 *            hora final
	 * @return la reserva si se pudo borrar, null si no se pudo
	 */
	@SuppressWarnings("deprecation")
	public Reserva borrarReserva(String socioID,  Date horaC, Date horaF) {
		Reserva resultado = null;

		for (Reserva reserva : reservas) {
			if (reserva.getHoraComienzo().getHours()==horaC.getHours() && reserva.getHoraFinal().getHours()==horaF.getHours() && reserva.getSocioID().equals(socioID) &&
					reserva.getHoraComienzo().getMonth() == horaC.getMonth() && reserva.getHoraComienzo().getYear() == horaC.getYear()) {
				resultado = reserva;
			}
		}
		reservas.remove(resultado);
		return resultado;
	}
}
