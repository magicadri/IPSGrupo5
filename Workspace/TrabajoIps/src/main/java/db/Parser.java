package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.Actividad;
import logic.Instalacion;
import logic.Recibo;
import logic.Reserva;
import logic.Socio;

public class Parser {
	
	ArrayList<Socio> socios = new ArrayList<>();
	ArrayList<Instalacion> instalaciones = new ArrayList<>();
	ArrayList<Reserva> reservas = new ArrayList<>();
	ArrayList<Recibo> recibos = new ArrayList<>();
	ArrayList<Actividad> actividades = new ArrayList<>();
	public ArrayList<Socio> getSocios() {
		return socios;
	}

	public ArrayList<Instalacion> getInstalaciones() {
		return instalaciones;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public ArrayList<Recibo> getRecibos() {
		return recibos;
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
			instalaciones.add(new Instalacion(rs.getString("instalacionID")));
		}
		
		s = c.createStatement();
		rs = s.executeQuery("Select * From RESERVA");
		while(rs.next())
		{
			reservas.add(new Reserva(rs.getString("socioID"), rs.getString("instalacionID"),
					rs.getTimestamp("horaComienzo"), rs.getTimestamp("horaFinal"),
					rs.getTimestamp("horaEntrada"), rs.getTimestamp("horaSalida"), 
					rs.getString("modoPago"), rs.getBoolean("pagado"), rs.getInt("precio")));
		}
		
		// TODO cuota y entrada_cuota
		
		s = c.createStatement();
		rs = s.executeQuery("Select * From ACTIVIDAD");
		while(rs.next())
		{
			actividades.add(new Actividad(rs.getString("actividadID"), rs.getTimestamp("fechaComienzo"), rs.getTimestamp("fechaFinal")));
		}
		
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
	public boolean comprobarDisponibilidad(Date horaC, Date horaF) {
		boolean resultado = true;

		for (Reserva reserva : reservas) {
			if (reserva.getHoraComienzo().equals(horaC) && reserva.getHoraFinal().equals(horaF)) {
				resultado = false;
			}
		}
		return resultado;
	}
	
}
