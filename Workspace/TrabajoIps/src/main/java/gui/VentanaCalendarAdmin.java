package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import db.Database;
import db.Parser;
import logic.Instalacion;
import logic.Reserva;
import logic.Socio;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.beans.PropertyChangeEvent;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;
import javax.swing.JList;

public class VentanaCalendarAdmin extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JDateChooser dateChooser;
	private JTextField tf;
	private JTable table;
	private JLabel lblHora;
	private Parser parser;
	private JLabel lbHora;
	private JButton btnLlegada;
	private JButton btnSalida;
	private JComboBox comboBoxInstalacion;
	private DefaultComboBoxModel cmodel;
	private String socioID;
	private String SocioTxB;
	private VentanaCalendarAdmin ref = this;
	private JButton btnCancelarReserva;
	private JButton btnReservar;
	private JButton btnTodoElDia;
	private JLabel lblHora_1;
	private JLabel lblInstalacion;
	private JLabel lblSocio;
	private JLabel lblHoraEntrada;
	private JLabel lblHoraSalida;
	private JLabel lblReservaId;


	/**
	 * Create the dialog.
	 */
	public VentanaCalendarAdmin() {
		parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		setBounds(100, 100, 1146, 494);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getDateChooser());
		contentPanel.add(getTable());
		// Pone el dia actual en el dateChooser
		dateChooser.setDate((new Date()));
		contentPanel.add(getLblHora());
		contentPanel.add(getLbHora());
		contentPanel.add(getBtnLlegada());
		contentPanel.add(getBtnSalida());
		contentPanel.add(getComboBoxInstalacion());
		contentPanel.add(getBtnCancelarReserva());
		contentPanel.add(getBtnReservar());
		contentPanel.add(getBtnTodoElDia());
		contentPanel.add(getLblHora_1());
		contentPanel.add(getLblInstalacion());
		contentPanel.add(getLblSocio());
		contentPanel.add(getLblHoraEntrada());
		contentPanel.add(getLblHoraSalida());
		contentPanel.add(getLblReservaId());

	}

	// Quitar cuando se haga el de abajo.
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
				 public void propertyChange(PropertyChangeEvent evt) {
					 limpiarTabla();
					 try {
						if (getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())) != null)
								llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				 }
		});
		}
	
		dateChooser.setBounds(44, 30, 95, 20);
		return dateChooser;
	}

	
	// Cambiar por el metodo que reconozca lo seleccionado en la combobox y
	// actualice correspondientemente

	/*
	 * private JDateChooser getDateChooser() { if (dateChooser == null) {
	 * dateChooser = new JDateChooser();
	 * dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
	 * public void propertyChange(PropertyChangeEvent arg0) { limpiarTabla(); //
	 * Actualizar el horario para cada dia cambiado if (chbPiscina.isSelected())
	 * { llenarTabla(parser.getPiscina()); } if (chbTenis.isSelected()) {
	 * llenarTabla(parser.getTenis()); } if (chbFutbol.isSelected()) {
	 * llenarTabla(parser.getFutbol()); } } }); dateChooser.setBounds(44, 30,
	 * 95, 20); } return dateChooser; }
	 */

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setBounds(197, 37, 692, 384);

			DataTableModel dm = new DataTableModel(

					new Object[][] {
						{"00:00", null, null, null, null, null},
						{"01:00", null, null, null, null, null},
						{"02:00", null, null, null, null, null},
						{"03:00", null, null, null, null, null},
						{"04:00", null, null, null, null, null},
						{"05:00", null, null, null, null, null},
						{"06:00", null, null, null, null, null},
						{"07:00", null, null, null, null, null},
						{"08:00", null, null, null, null, null},
						{"09:00", null, null, null, null, null},
						{"10:00", null, null, null, null, null},
						{"11:00", null, null, null, null, null},
						{"12:00", null, null, null, null, null},
						{"13:00", null, null, null, null, null},
						{"14:00", null, null, null, null, null},
						{"15:00", null, null, null, null, null},
						{"16:00", null, null, null, null, null},
						{"17:00", null, null, null, null, null},
						{"18:00", null, null, null, null, null},
						{"19:00", null, null, null, null, null},
						{"20:00", null, null, null, null, null},
						{"21:00", null, null, null, null, null},
						{"22:00", null, null, null, null, null},
						{"23:00", null, null, null, null, null},
					},
					new String[] {
						"Horas", "Disponibilidad", "Llegada", "Salida", "Socio", "ReservaID" });

			table.setModel(dm);

			// Listener para tomar los valores de las filas de la tabla
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent ev) {
					// Pone en la descripcion el valor de la columna
					// fila/columna
					/* Añade la descripcion
					txPDescripcion.setText((String) table.getModel().getValueAt(table.getSelectedRow(), 0) + " "
							+ table.getModel().getValueAt(table.getSelectedRow(), 1));
							*/
				}

				// No editable NO FUNCIONA DE MOMENTO
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			});
		}
		return table;
	}

	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	public void limpiarTabla() {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.clearSelection();
			table.setValueAt("", i, 1);
			table.setValueAt("", i, 2);
			table.setValueAt("", i, 3);
			table.setValueAt("", i, 4);
			table.setValueAt("", i, 5);
		}
	}

	/**
	 * Limpia los valores de la tabla para una reserva en particular
	 */
	private void limpiarReserva(String inst) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 1).equals(inst))
				table.setValueAt("", i, 1);
		}
	}

	/**
	 * Saca el dia de un Date
	 * 
	 * @param date
	 * @return String con el dia
	 */
	private String sacarDia(Date date) {
		String[] var = date.toString().split(" ");
		return var[2];
	}

	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("Hora:");
			lblHora.setBounds(10, 74, 38, 20);
		}
		return lblHora;
	}

	private JLabel getLbHora() {
		if (lbHora == null) {
			lbHora = new JLabel("");
			lbHora.setBounds(54, 77, 46, 14);
			lbHora.setText(String.valueOf(LocalDateTime.now().getHour()) + ":"
					+ String.valueOf(LocalDateTime.now().getMinute()));
		}
		return lbHora;
	}

	private JButton getBtnLlegada() {
		if (btnLlegada == null) {
			btnLlegada = new JButton("Llegada");
			btnLlegada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					//SACAR DE LA TABLA EL SOCIO DONDE SE ESTA SELECCIONANDO
					String SocioID = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
					//Se saca la hora de la columna seleccionada
					String string = (String) table.getModel().getValueAt(table.getSelectedRow(),0);
					String[] Hora1 = string.split(":");
					String Hora = Hora1[0];
					int ReservaID = (int) table.getModel().getValueAt(table.getSelectedRow(), 5);
					//Set hora de llegada
					//if(((int)table.getModel().getValueAt(1,table.getSelectedColumn())) == LocalDateTime.now().getHour()){
					if(Hora.equals(String.valueOf(LocalDateTime.now().getHour())) && SocioID != "admin"){
						JOptionPane.showMessageDialog(null, "Llegada a las: "+ LocalDateTime.now().getHour());
						table.setValueAt(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute(), table.getSelectedRow(), 3);
						Calendar calendar = Calendar.getInstance();
						java.sql.Timestamp TimeStamp = new java.sql.Timestamp(calendar.getTime().getTime());
						
						try {
							Database.getInstance().getC().createStatement().execute("UPDATE RESERVA SET horaEntrada ='"+TimeStamp+"' where RESERVAID = '" + ReservaID+"'");
							} catch (SQLException i) {
								i.printStackTrace();
							}
						


						
					}
					else{

						JOptionPane.showMessageDialog(null, "Error. No es una hora correcta o no es un socio.");
					}

				}
			});
			btnLlegada.setBounds(919, 73, 89, 23);
		}
		return btnLlegada;
	}

	private JButton getBtnSalida() {
		if (btnSalida == null) {
			btnSalida = new JButton("Salida");
			btnSalida.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					//SACAR DE LA TABLA EL SOCIO DONDE SE ESTA SELECCIONANDO
					String SocioID = "'"+(String) table.getModel().getValueAt(table.getSelectedRow(), 2)+"'";  
					//Se saca la hora de la columna seleccionada
					String string = (String) table.getModel().getValueAt(table.getSelectedRow(),0);
					String[] Hora1 = string.split(":");
					String Hora = Hora1[0];
					int ReservaID = (int) table.getModel().getValueAt(table.getSelectedRow(), 5);

					//Set hora de salida
					//if(((int)table.getModel().getValueAt(1,table.getSelectedColumn())) == LocalDateTime.now().getHour()){
					if(Hora.equals(String.valueOf(LocalDateTime.now().getHour()))){
						JOptionPane.showMessageDialog(null, "Salida las: "+ LocalDateTime.now().getHour());
						table.setValueAt(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute(), table.getSelectedRow(), 4);
						Calendar calendar = Calendar.getInstance();
						java.sql.Timestamp TimeStamp = new java.sql.Timestamp(calendar.getTime().getTime());
						
						
						
						try {
							Database.getInstance().getC().createStatement().execute("UPDATE RESERVA SET horaSalida= '"+TimeStamp+"' where RESERVAID = '" + ReservaID+"'");
							} catch (SQLException e) {
								e.printStackTrace();
							}
						
					}
					else{
						JOptionPane.showMessageDialog(null, "Error. No es una hora correcta o no es un socio.");
					}
					
				
				}
			});
			btnSalida.setBounds(1018, 73, 89, 23);
		}
		return btnSalida;
	}

	private int getDia(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public JComboBox getComboBoxInstalacion() {
		String[] modelItems = new String[] { "Elija instalacion:", "Piscina", "Cancha fútbol", "Pista tenis" };
		cmodel = new DefaultComboBoxModel(modelItems);
		if (comboBoxInstalacion == null) {
			comboBoxInstalacion = new JComboBox();
			comboBoxInstalacion.setBounds(10, 105, 157, 20);
			comboBoxInstalacion.setModel(cmodel);
		}
		comboBoxInstalacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarTabla();
				try {
					if (getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())) != null)
						llenarTabla(
								getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			}
		});
		return comboBoxInstalacion;
	}

	public Instalacion getInstalacionFromNombre(String nombre) throws SQLException {
		Parser parser = new Parser();
		parser.fillArrays();
		for (Instalacion i : parser.getInstalaciones())
			if (i.getInstalacion_nombre().equals(nombre))
				return i;
		return null;
	}
	

	@SuppressWarnings("deprecation") public void llenarTabla(Instalacion ins) throws SQLException{
		
		for (Reserva reserva : parser.getReservas()) {

			Date a = getDateChooser().getDate();
			String nombre = reserva.getSocioID();
			String dia = sacarDia(a);
			if (String.valueOf(getDia(reserva.getHoraComienzo())).equals(dia)) {
				if (getInstalacionIDFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())) != -1)
					if (ins.getInstalacionID() == (getInstalacionIDFromNombre(
							String.valueOf(getComboBoxInstalacion().getSelectedItem())))) { // Piscina
						table.setValueAt(String.valueOf(getComboBoxInstalacion().getSelectedItem()),
								reserva.getHoraComienzo().getHours(), 1);
						table.setValueAt(nombre,reserva.getHoraComienzo().getHours(), 2);
						table.setValueAt(reserva.getReservaID(), reserva.getHoraComienzo().getHours(), 5);
					
					}
			}
		}
	}

	private int getInstalacionIDFromNombre(String nombre) {
		for (Instalacion i : parser.getInstalaciones())
			if (i.getInstalacion_nombre().equals(nombre))
				return i.getInstalacionID();
		return -1;
	}

	
	
	@SuppressWarnings("deprecation")
	private boolean cancelarReserva(int horaComienzo, int horaFinal) throws SQLException{
				
		Reserva reserva = new Reserva();
		Date date = dateChooser.getDate();
		date.setHours(horaComienzo);
		Date date2 = dateChooser.getDate();
		date2.setHours(horaComienzo+1);
		Date date3 = dateChooser.getDate();
		date3.setHours(horaFinal+1);
		
		boolean result;
		if(horaFinal != -1)
			result = reserva.cancelarReserva(getSocioID(), new Timestamp(date.getTime()), new Timestamp(date3.getTime()));
		else
			result = reserva.cancelarReserva(getSocioID(), new Timestamp(date.getTime()), new Timestamp(date2.getTime()));
		parser.removeArrays();
		parser.fillArrays();
		
		
		limpiarTabla();
		if(comboBoxInstalacion != null)
		llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
		else
			llenarTabla(getInstalacionFromNombre("Piscina"));
			comboBoxInstalacion.setSelectedIndex(1);
		return result;
	}
	
	private JButton getBtnCancelarReserva() {
		if (btnCancelarReserva == null) {
			btnCancelarReserva = new JButton("Cancelar reserva");
			btnCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int[] selecciones = table.getSelectedRows();
					boolean result = false;
					if(selecciones.length > 2){
						JOptionPane.showMessageDialog(ref, "No puedes seleccionar más de dos horas para realizar una reserva", "Error", JOptionPane.ERROR_MESSAGE);
					}else if(selecciones.length == 2){
						try {
							result = cancelarReserva(selecciones[0], selecciones[1]);
							limpiarTabla();
							if(comboBoxInstalacion != null)
								llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
								else{
									llenarTabla(getInstalacionFromNombre("Piscina"));
									comboBoxInstalacion.setSelectedIndex(1);
								}
						} catch (SQLException e1) {
							
						}
					}else if(selecciones.length == 1){
						try {
							result = cancelarReserva(selecciones[0], -1);
							limpiarTabla();
							if(comboBoxInstalacion != null)
								llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
								else
									llenarTabla(getInstalacionFromNombre("Piscina"));
									comboBoxInstalacion.setSelectedIndex(1);
						} catch (SQLException e1) {
							
						}
					}else{
						JOptionPane.showMessageDialog(ref, "Proceso de cancelacion erroneo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
					if(result){
						JOptionPane.showMessageDialog(ref, "Reserva cancelada con exito", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
					
					
				}
			});
			btnCancelarReserva.setBounds(914, 238, 193, 23);

		}
		return btnCancelarReserva;
	}
	
	
	
	public String getSocioID() {
		
		return (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
	}
	
	
	
	
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					//Coger metodo de pago
					Object[] options = { "Cuota", "Efectivo"};
					int[] selecciones = table.getSelectedRows();
					if(selecciones.length > 2){
						JOptionPane.showMessageDialog(ref, "No puedes seleccionar más de dos horas para realizar una reserva", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(selecciones.length == 2){
						try {
							hacerReserva(selecciones[0], selecciones[1], options[1]);
						} catch (SQLException e1) {
							
						}
					}else if(selecciones.length == 1){
						try {
							hacerReserva(selecciones[0], -1, options[1]);
						} catch (SQLException e1) {
							
						}
					}else{
						JOptionPane.showMessageDialog(ref, "Proceso de reserva cancelado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
			btnReservar.setBounds(914, 194, 193, 20);
		}
		return btnReservar;
	}
	
	
	
	
	
	private void hacerReserva(int horaComienzo, int horaComienzo2, Object modoPago) throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		

		Date date = dateChooser.getDate();
		date.setHours(horaComienzo);
		Date date2 = dateChooser.getDate();
		date2.setHours(horaComienzo+1);
		Date date3 = dateChooser.getDate();
		date3.setHours(horaComienzo2);

		// Crear y guardar la reserva
		Reserva reserva = new Reserva();
		if(horaComienzo2 != -1)
			reserva.hacerReserva("admin", getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem()), parser.getReservas().size()+1,
				new Timestamp(date.getTime()), new Timestamp(date3.getTime()), null, null, getModoPago(getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem())),
				false, getPrecioFromNombre((String) comboBoxInstalacion.getSelectedItem()));
		else
			reserva.hacerReserva("admin", getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem()), parser.getReservas().size()+1,
					new Timestamp(date.getTime()), new Timestamp(date2.getTime()), null, null, getModoPago(getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem())),
					false, getPrecioFromNombre((String) comboBoxInstalacion.getSelectedItem()));
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		
		// Rellenar la tabla		
		limpiarTabla();
		if(comboBoxInstalacion != null)
		llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
		else
			llenarTabla(getInstalacionFromNombre("Piscina"));
			comboBoxInstalacion.setSelectedIndex(1);
	}
	
	private void hacerReservaTodoElDia(int horaComienzo, int horaComienzo2, Object modoPago) throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		

		Date date = dateChooser.getDate();
		date.setHours(horaComienzo);
		Date date2 = dateChooser.getDate();
		date2.setHours(horaComienzo+1);
		Date date3 = dateChooser.getDate();
		date3.setHours(horaComienzo2);

		// Crear y guardar la reserva
		Reserva reserva = new Reserva();
		if(horaComienzo2 != -1)
			reserva.hacerReservaTodoElDia("admin", getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem()), parser.getReservas().size()+1,
				new Timestamp(date.getTime()), new Timestamp(date3.getTime()), null, null, getModoPago(getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem())),
				false, getPrecioFromNombre((String) comboBoxInstalacion.getSelectedItem()));
		else
			reserva.hacerReservaTodoElDia("admin", getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem()), parser.getReservas().size()+1,
					new Timestamp(date.getTime()), new Timestamp(date2.getTime()), null, null, getModoPago(getInstalacionIDFromNombre((String) comboBoxInstalacion.getSelectedItem())),
					false, getPrecioFromNombre((String) comboBoxInstalacion.getSelectedItem()));
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		
		// Rellenar la tabla		
		limpiarTabla();
		if(comboBoxInstalacion != null)
		llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
		else
			llenarTabla(getInstalacionFromNombre("Piscina"));
			comboBoxInstalacion.setSelectedIndex(1);
	}
	
	
	
	private int getPrecioFromNombre(String nombre) throws SQLException{ 
		int id = getInstalacionIDFromNombre(nombre);
		
		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();

		// Ver si es admin
		for (Reserva res : r) {
			if (res.getInstalacionID() == id)
				return res.getPrecio();
		}
		return -1;
	}
	
	
	private String getModoPago(int reservaID) throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();

		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();
		
		for(Reserva res : r){
			if(res.getReservaID() == reservaID){
				return res.getModoPago();
			}
		}
		
		return "";
	}
	private JButton getBtnTodoElDia() {
		if (btnTodoElDia == null) {
			btnTodoElDia = new JButton("Todo el dia");
			btnTodoElDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Comprobar que no haya reservas en el mismo dia, hay que cancelarlas antes
					
					//Fila 1º seleccionada
					table.getSelectionModel().setSelectionInterval(0, 1);
					int seleccion = table.getSelectedRow();
					for(int i=0; i<table.getRowCount(); i++){
						
						
						try {
							hacerReservaTodoElDia(seleccion, -1, "Efectivo");
						} catch (SQLException e) {
							e.printStackTrace();
						}
						seleccion++;
					}
					
					// Pasarlos a la clase actual
					ArrayList<Reserva> r = new ArrayList<Reserva>();
					r = parser.getReservas();
					/*
					for(Reserva res : r){
						if(res.getHoraComienzo().getDate() == dateChooser.getDate().getDate()){
							JOptionPane.showMessageDialog(null, "Cancele las reservas antes de reservar el dia entero.");
						}
						else{
						if(res.getHoraComienzo().getDate() == dateChooser.getDate().getDate() && res.getSocioID().equals("admin")){
							JOptionPane.showMessageDialog(null,
									"Se han reservado el dia entero con exito.");
						}
						
						else{
							JOptionPane.showMessageDialog(null,
									"No se ha podido reservar el dia entero, compruebe que no haya reservas existentes ");
						}
						
						
						
					
						}
					}
					*/
				}
			});
			btnTodoElDia.setBounds(919, 147, 193, 23);
		}
		return btnTodoElDia;
	}
	private JLabel getLblHora_1() {
		if (lblHora_1 == null) {
			lblHora_1 = new JLabel("Hora:");
			lblHora_1.setBounds(220, 22, 46, 14);
		}
		return lblHora_1;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalacion: ");
			lblInstalacion.setBounds(328, 22, 89, 14);
		}
		return lblInstalacion;
	}
	private JLabel getLblSocio() {
		if (lblSocio == null) {
			lblSocio = new JLabel("Socio:");
			lblSocio.setBounds(460, 22, 55, 14);
		}
		return lblSocio;
	}
	private JLabel getLblHoraEntrada() {
		if (lblHoraEntrada == null) {
			lblHoraEntrada = new JLabel("Hora entrada:");
			lblHoraEntrada.setBounds(557, 22, 79, 14);
		}
		return lblHoraEntrada;
	}
	private JLabel getLblHoraSalida() {
		if (lblHoraSalida == null) {
			lblHoraSalida = new JLabel("Hora salida:");
			lblHoraSalida.setBounds(678, 22, 79, 14);
		}
		return lblHoraSalida;
	}
	private JLabel getLblReservaId() {
		if (lblReservaId == null) {
			lblReservaId = new JLabel("Reserva ID: ");
			lblReservaId.setBounds(803, 22, 67, 14);
		}
		return lblReservaId;
	}
}
