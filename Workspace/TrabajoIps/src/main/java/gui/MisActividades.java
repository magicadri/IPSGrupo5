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
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import logic.Actividad;
import logic.Reserva;
import logic.ReservaActividad;
import logic.SocioActividad;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	private JLabel lblInstalacin;
	private JLabel lblNewLabel_1;
	private JButton btnCancelar;


	


	
	
	
	
	
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
		contentPane.add(getLblInstalacin());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getBtnCancelar());
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
					new Object[][] { { null, null }, {  null, null}, {  null, null}, { null, null},
							{  null,null}, {  null,null}, {  null, null}, {  null, null},
							{  null, null }, {  null, null}, {  null, null}, {  null, null},
							{  null, null }, {  null, null}, { null, null}, {  null, null},
							{  null, null}, {  null, null}, {  null, null}, {  null, null},
							{  null, null }, {  null, null}, {  null, null}, {  null, null}, },
					new String[] { "Horas", "Instalación"});
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
			lblNewLabel_1.setBounds(453, 52, 46, 14);
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

		}
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					/*
					int actividadID= 0;
					int ReservaID= 0;
					int[] selecciones = table.getSelectedRows();
					//Nombre de la actividad seleccionada
					String Nombre = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
					
					for(Actividad actividad: parser.getActividades()){
					for(ReservaActividad reserva : parser.getReservasactividad()){
						if(Nombre.equals(actividad.getActividad_nombre()) && actividad.getActividadID() == reserva.getActividadID()){
							actividadID = actividad.getActividadID();
							ReservaID = reserva.getReservaID();
						}
					}
				}
					

					boolean result = false;
					if(selecciones.length > 2){
						JOptionPane.showMessageDialog(null, "No puedes seleccionar más de dos horas para realizar una reserva", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(selecciones.length == 1){
						result = cancelarReservaActividad(actividadID, ReservaID);
					}else{
						JOptionPane.showMessageDialog(null, "Proceso de cancelacion erroneo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
					if(result){
						JOptionPane.showMessageDialog(null, "Reserva cancelada con exito", "Información", JOptionPane.INFORMATION_MESSAGE);
					}*/
				}
			});
			btnCancelar.setBounds(600, 78, 89, 23);
		}
		return btnCancelar;
	}
	
	
	
}
