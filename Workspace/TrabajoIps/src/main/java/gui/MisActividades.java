package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import db.Parser;




import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import logic.Actividad;
import logic.Reserva;
import logic.SocioActividad;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;


public class MisActividades extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Parser parser;

	private JTable table;
	private JButton btnActualizar;
	String socioID;
	private JLabel lblNewLabel;
	private JLabel lblInstalacin;
	private JLabel lblNewLabel_1;


	


	
	
	
	
	
	/**
	 * Create the frame.
	 */
	public MisActividades(String socioID) {
		setSocioID(socioID);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 787, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTable());
		contentPane.add(getBtnActualizar());
		contentPane.add(getLblNewLabel());
		contentPane.add(getLblInstalacin());
		contentPane.add(getLblNewLabel_1());
		parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable(){
			};
			
			table.setBounds(177, 68, 378, 384);

			DataTableModel dm = new DataTableModel(
					new Object[][] { { null, null,null }, {  null, null,null }, {  null, null,null }, { null, null,null },
							{  null,null,null }, {  null,null,null }, {  null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, {  null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, { null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, {  null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, {  null, null,null }, {  null, null,null }, },
					new String[] { "Horas", "Instalación", "Día" });
			table.setModel(dm);

		}
		return table;
	}

		
	
	private JButton getBtnActualizar() {
		if (btnActualizar == null) {
			btnActualizar = new JButton("Actualizar");
			btnActualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					limpiarTabla();
					llenarTablaUsuario();
				}
			});
			btnActualizar.setBounds(28, 104, 89, 23);
		}
		return btnActualizar;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void llenarTablaUsuario() {
		int i = 0;
		for (Actividad actividad : parser.getActividades()) {
			for(SocioActividad sactividad: parser.getSociosactividad()){
			
			if(sactividad.getSocioID().equals(socioID)){
							
								table.setValueAt(actividad.getActividad_nombre(),i,0);
								table.setValueAt(actividad.getSemanas(),i,1);
			}
							}
			
						i++;
					}
	}
			//}
	
	 public String getSocioID() {
			return socioID;
		}

		public void setSocioID(String socioID) {
			this.socioID = socioID;
		}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Hora");
			lblNewLabel.setBounds(469, 52, 46, 14);
		}
		return lblNewLabel;
	}
	private JLabel getLblInstalacin() {
		if (lblInstalacin == null) {
			lblInstalacin = new JLabel("Instalaci\u00F3n");
			lblInstalacin.setBounds(201, 52, 66, 14);
		}
		return lblInstalacin;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Dia");
			lblNewLabel_1.setBounds(348, 52, 46, 14);
		}
		return lblNewLabel_1;
	}
	
	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	private void limpiarTabla() {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.clearSelection();
			table.setValueAt("", i, 0);
			table.setValueAt("", i, 1);
			table.setValueAt("", i, 2);

		}
	}
}
