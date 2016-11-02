package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

public class VentanaCalendar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JDateChooser dateChooser;
	private JTextField tf;
	private JTable table;
	private Parser parser;
	private JTextPane txPDescripcion;
	private JLabel lblDescripcion;
	private JLabel lblHora;
	private JLabel lbHora;
	private JComboBox comboBoxInstalacion;
	private DefaultComboBoxModel cmodel;
	private String socioID = "adri";
	private JButton btnMisReservas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaCalendar dialog = new VentanaCalendar();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSocioID() {
		return socioID;
	}

	public void setSocioID(String socioID) {
		this.socioID = socioID;
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCalendar() {
		setBounds(100, 100, 887, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getDateChooser());
		contentPanel.add(getTable());
		// Pone el dia actual en el dateChooser
		dateChooser.setDate((new Date()));
		parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPanel.add(getTxPDescripcion());
		contentPanel.add(getLblDescripcion());
		contentPanel.add(getLblHora());
		contentPanel.add(getLbHora());
		contentPanel.add(getComboBoxInstalacion());
		contentPanel.add(getBtnMisReservas());
	}

	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					limpiarTabla();
					try {
						if(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem()))!=null)
								llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			dateChooser.setBounds(44, 30, 95, 20);
		}
		return dateChooser;
	}
	
	private Instalacion getInstalacionFromNombre(String nombre) throws SQLException
	{
		Parser parser = new Parser();
		parser.fillArrays();
		for(Instalacion i : parser.getInstalaciones())
			if(i.getInstalacion_nombre().equals(nombre))
				return i;
		return null;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setBounds(175, 11, 248, 384);

			DataTableModel dm = new DataTableModel(
					new Object[][] { { "00:00", null }, { "01:00", null }, { "02:00", null }, { "03:00", null },
							{ "04:00", null }, { "05:00", null }, { "06:00", null }, { "07:00", null },
							{ "08:00", null }, { "09:00", null }, { "10:00", null }, { "11:00", null },
							{ "12:00", null }, { "13:00", null }, { "14:00", null }, { "15:00", null },
							{ "16:00", null }, { "17:00", null }, { "18:00", null }, { "19:00", null },
							{ "20:00", null }, { "21:00", null }, { "22:00", null }, { "23:00", null }, },
					new String[] { "Horas", "Disponibilidad" });
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

			});
		}
		return table;
	}

	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	private void limpiarTabla() {
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

	private int getInstalacionIDFromNombre(String nombre) {
		for (Instalacion i : parser.getInstalaciones())
			if (i.getInstalacion_nombre().equals(nombre))
				return i.getInstalacionID();
		return -1;
	}

	/**
	 * Coge las reservas de una instalacion y rellena la tabla
	 * 
	 * @param ins,
	 *            Instalacion
	 */
	@SuppressWarnings("deprecation")
	private void llenarTabla(Instalacion ins) {
		TableColumn tcol;
		ColorCellRed ccr = new ColorCellRed();
		ColorCellGreen ccg = new ColorCellGreen();

		for (Reserva reserva : parser.getReservas()) {

			Date a = getDateChooser().getDate();
			String dia = sacarDia(a);
			if (String.valueOf(getDia(reserva.getHoraComienzo())).equals(dia)) {
				if (getInstalacionIDFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())) != -1)
					if (ins.getInstalacionID() == (getInstalacionIDFromNombre(
							String.valueOf(getComboBoxInstalacion().getSelectedItem())))) { // Piscina
						table.setValueAt(String.valueOf(getComboBoxInstalacion().getSelectedItem()),
								reserva.getHoraComienzo().getHours(), 1);
						tcol = table.getColumnModel().getColumn(1);
						if (reserva.getSocioID().equals(socioID))
							tcol.setCellRenderer(ccr);
						else
							tcol.setCellRenderer(ccg);
					}
			}
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
			txPDescripcion.setBounds(453, 71, 338, 84);
		}
		return txPDescripcion;
	}

	private JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel("Descripcion:");
			lblDescripcion.setBounds(453, 30, 130, 30);
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

	private int getDia(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	private JComboBox getComboBoxInstalacion() {
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
					if(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem()))!=null)
							llenarTabla(getInstalacionFromNombre(String.valueOf(getComboBoxInstalacion().getSelectedItem())));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		return comboBoxInstalacion;
	}
	private JButton getBtnMisReservas() {
		if (btnMisReservas == null) {
			btnMisReservas = new JButton("Mis reservas");
			btnMisReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MisReservas ms = new MisReservas();
					ms.setVisible(true);
				}
			});
			btnMisReservas.setBounds(30, 159, 109, 23);
		}
		return btnMisReservas;
	}
}
