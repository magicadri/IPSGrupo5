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
	private JTextPane txPDescripcion;
	private JLabel lblDescripcion;
	private JLabel lblHora;
	private Parser parser;
	private JLabel lbHora;
	private JButton btnLlegada;
	private JButton btnSalida;
	private JComboBox comboBoxInstalacion;
	private DefaultComboBoxModel cmodel;
	private String socioID;
	private String SocioTxB;
	private JButton btnCancelarReserva;
	private VentanaCalendarAdmin ref = this;


	/**
	 * Create the dialog.
	 */
	public VentanaCalendarAdmin() {
		parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(100, 100, 887, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getDateChooser());
		contentPanel.add(getTable());
		// Pone el dia actual en el dateChooser
		dateChooser.setDate((new Date()));
		contentPanel.add(getTxPDescripcion());
		contentPanel.add(getLblDescripcion());
		contentPanel.add(getLblHora());
		contentPanel.add(getLbHora());
		contentPanel.add(getBtnLlegada());
		contentPanel.add(getBtnSalida());
		contentPanel.add(getComboBoxInstalacion());
		contentPanel.add(getBtnCancelarReserva());

	}

	// Quitar cuando se haga el de abajo.
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
		}
		dateChooser.setBounds(44, 30, 95, 20);
		return dateChooser;
	}

	// TODO
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
			table.setBounds(176, 37, 453, 384);

			DataTableModel dm = new DataTableModel(

					new Object[][] { { "00:00", null, null, null, null }, { "01:00", null, null, null, null }, { "02:00", null, null, null, null }, { "03:00", null, null, null, null },
							{ "04:00", null, null, null, null }, { "05:00", null, null, null, null }, { "06:00", null, null, null, null }, { "07:00", null, null, null, null },
							{ "08:00", null, null, null, null }, { "09:00", null, null, null, null }, { "10:00", null, null, null, null }, { "11:00", null, null, null, null },
							{ "12:00", null, null, null, null }, { "13:00", null, null, null, null }, { "14:00", null, null, null, null }, { "15:00", null, null, null, null },
							{ "16:00", null, null, null, null }, { "17:00", null, null, null, null }, { "18:00", null, null, null, null }, { "19:00", null, null, null, null },
							{ "20:00", null, null, null, null }, { "21:00", null, null, null, null}, { "22:00", null, null, null, null }, { "23:00", null, null, null, null }, },
					new String[] { "Horas", "Disponibilidad", "Llegada", "Salida","Socio" });

			table.setModel(dm);

			// Listener para tomar los valores de las filas de la tabla
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent ev) {
					// Pone en la descripcion el valor de la columna
					// fila/columna
					txPDescripcion.setText((String) table.getModel().getValueAt(table.getSelectedRow(), 0) + " "
							+ table.getModel().getValueAt(table.getSelectedRow(), 1));
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

	private JTextPane getTxPDescripcion() {
		if (txPDescripcion == null) {
			txPDescripcion = new JTextPane();
			txPDescripcion.setBounds(643, 107, 207, 47);
		}
		return txPDescripcion;
	}

	private JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel("Descripcion:");
			lblDescripcion.setBounds(643, 66, 130, 30);
		}
		return lblDescripcion;
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
					//Set hora de llegada
					//if(((int)table.getModel().getValueAt(1,table.getSelectedColumn())) == LocalDateTime.now().getHour()){
					if(Hora.equals(String.valueOf(LocalDateTime.now().getHour()))){
						JOptionPane.showMessageDialog(null, "Llegada a las: "+ LocalDateTime.now().getHour());
						table.setValueAt(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute(), table.getSelectedRow(), 3);

						
						


						//DB
						/* ERROR COMILLAS SIMPLES 
						Calendar calendar = Calendar.getInstance();
						java.sql.Timestamp TimeStamp = new java.sql.Timestamp(calendar.getTime().getTime());
						
						Properties connectionProps = new Properties();
						connectionProps.put("user", "SA");
						String query  = "UPDATE RESERVA SET horaSalida =(?) WHERE IDSOCIO = (?)";
						Connection conn = null;
						try {
							conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", connectionProps);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						
							PreparedStatement ps;
							try {
								ps = conn.prepareStatement(query);
								ps.setTimestamp(1, TimeStamp);
								ps.setString(2, SocioID);
								ps.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							*/
					}
					else{

						JOptionPane.showMessageDialog(null, "Error. No es una hora correcta.");
					}

				}
			});
			btnLlegada.setBounds(644, 180, 89, 23);
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
					//Set hora de salida
					//if(((int)table.getModel().getValueAt(1,table.getSelectedColumn())) == LocalDateTime.now().getHour()){
					if(Hora.equals(String.valueOf(LocalDateTime.now().getHour()))){
						JOptionPane.showMessageDialog(null, "Salida las: "+ LocalDateTime.now().getHour());
						table.setValueAt(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute(), table.getSelectedRow(), 4);

						
//DB
						/* ERROR COMILLAS SIMPLES 
						Calendar calendar = Calendar.getInstance();
						java.sql.Timestamp TimeStamp = new java.sql.Timestamp(calendar.getTime().getTime());
						
						Properties connectionProps = new Properties();
						connectionProps.put("user", "SA");
						String query  = "UPDATE RESERVA SET horaEntrada =(?) WHERE IDSOCIO = (?)";
						Connection conn = null;
						try {
							conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", connectionProps);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						
							PreparedStatement ps;
							try {
								ps = conn.prepareStatement(query);
								ps.setTimestamp(1, TimeStamp);
								ps.setString(2, SocioID);
								ps.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							*/
						
					}
					else{
						JOptionPane.showMessageDialog(null, "Error. No es una hora correcta.");
					}
					
				
				}
			});
			btnSalida.setBounds(772, 180, 89, 23);
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
					// TODO Auto-generated catch block
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
	

	@SuppressWarnings("deprecation") public void llenarTabla(Instalacion ins) {
		TableColumn tcol;
		ColorCellRed ccr = new ColorCellRed();
		ColorCellGreen ccg = new ColorCellGreen();

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
						tcol = table.getColumnModel().getColumn(1);
						if (reserva.getSocioID().equals(socioID))

							tcol.setCellRenderer(ccr);

						else
							tcol.setCellRenderer(ccg);
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
	private JButton getBtnCancelarReserva() {
		if (btnCancelarReserva == null) {
			btnCancelarReserva = new JButton("Cancelar reserva");
			btnCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaCancelarReservaAdmin vcr = new VentanaCancelarReservaAdmin(ref, "admin",
							new Timestamp(dateChooser.getDate().getTime()));
					vcr.setVisible(true);
				}
			});
			btnCancelarReserva.setBounds(644, 230, 217, 23);
		}
		return btnCancelarReserva;
	}
	
	
	
}
