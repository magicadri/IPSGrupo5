package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import db.Database;
import db.Parser;
import logic.Actividad;
import logic.Reserva;
import logic.ReservaActividad;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class VentanaActividadesSocio extends JFrame {

	private JPanel contentPane;
	private JButton btnOk;
	private JButton btnCancelar;
	private Parser parser;
	int ReservaID;
	private JTable table;
	private JButton btnActualizar;
	private JLabel lblActividad;
	private JLabel lblSemanas;
	private JButton btnApuntarme;
	String socioID;


	/**
	 * Create the frame.
	 * @param socioID 
	 */
	public VentanaActividadesSocio(String socioID) {
		socioID = socioID;
		parser = new Parser();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 656, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnOk());
		contentPane.add(getBtnCancelar());
		contentPane.add(getTable());
		contentPane.add(getBtnActualizar());
		contentPane.add(getLblActividad());
		contentPane.add(getLblSemanas());
		contentPane.add(getBtnApuntarme());
		llenarTabla();

	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			btnOk.setBounds(422, 387, 89, 23);
		}
		return btnOk;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelar.setBounds(528, 387, 89, 23);
		}
		return btnCancelar;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable() {

			};
			table.setToolTipText("");

			table.setBounds(181, 36, 258, 342);

			DataTableModel dm = new DataTableModel(new Object[][] {{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null}, }, new String[] { "Lugar","Semanas" });
			table.setModel(dm);
		}
		return table;
	}

	public void llenarTabla() {
		parser.removeArrays();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int i = 0;
		for (Actividad actividad : parser.getActividades()) {
			
			table.setValueAt(actividad.getActividad_nombre(), i, 0);
			table.setValueAt(actividad.getSemanas(), i, 1);

			i++;

		}
	}

	
	private boolean cancelarReservaActividad(int actividadID, int reservaID){
		ReservaActividad reservaActividad = new ReservaActividad();
		
		boolean result;
		
			result = reservaActividad.cancelarReservaActividad(actividadID, reservaID);
		parser.removeArrays();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		limpiarTabla();
		llenarTabla();
		
		return result;
	}
	
	
	private void limpiarTabla() {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.clearSelection();
			table.setValueAt("", i, 0);
			table.setValueAt("", i, 1);

		}
	}
	private JButton getBtnActualizar() {
		if (btnActualizar == null) {
			btnActualizar = new JButton("Actualizar");
			btnActualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limpiarTabla();
					llenarTabla();
				}
			});
			btnActualizar.setBounds(22, 81, 110, 23);
		}
		return btnActualizar;
	}
	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad:");
			lblActividad.setBounds(209, 11, 60, 14);
		}
		return lblActividad;
	}
	private JLabel getLblSemanas() {
		if (lblSemanas == null) {
			lblSemanas = new JLabel("Semanas:");
			lblSemanas.setBounds(335, 11, 65, 14);
		}
		return lblSemanas;
	}
	private JButton getBtnApuntarme() {
		if (btnApuntarme == null) {
			btnApuntarme = new JButton("Apuntarme");
			btnApuntarme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Reserva actividad como socio DB 
					boolean aux = false;
					String nombre = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
					for(Actividad actividad : parser.getActividades()){
						for(ReservaActividad ractividad : parser.getReservasactividad()){
							if(nombre.equals(actividad.getActividad_nombre())){
					try {
						aux = Database.getInstance().getC().createStatement().execute(
								"INSERT INTO SocioActividad (SocioID, ActividadID, ReservaID, Presentado, NoSocioID) VALUES ('"
										+ socioID + "'," + ractividad.getActividadID() + ","
										+ ractividad.getReservaID() + ","
										+ null + "," + null + ");");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
							}
					}
					}
					if(aux){
						JOptionPane.showMessageDialog(null, "Has sido añadido con exito a la actividad");
					}else{
					JOptionPane.showMessageDialog(null, "Ya te has apuntado a esa actividad.");
					}
				}
			});
			btnApuntarme.setBounds(476, 52, 89, 23);
		}
		return btnApuntarme;
	}
}
