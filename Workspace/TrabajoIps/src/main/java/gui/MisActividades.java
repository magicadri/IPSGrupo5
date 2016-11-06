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
	 * Launch the application.
	 */
	
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MisReservas frame = new MisReservas();
					frame.setVisible(true);

					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	
	
	*/ 
	
	
	
	
	/**
	 * Create the frame.
	 */
	public MisActividades(String socioID) {
		setSocioID(socioID);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		
	
		
		//RellenarTabla();
				
	}
	
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable(){
				/* OJO
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					Component comp = super.prepareRenderer(renderer, row, col);
					
					if((Integer)spinnerDesde.getValue() == 0)
						return comp;
					
					for(int i=0;i<=table.getRowCount(); i++)
						
					if ((Integer)table.getModel().getValueAt(i, 1) < LocalDate.now().getDayOfMonth()) {
						comp.setBackground(Color.red);
					} else 
						comp.setBackground(Color.GREEN);
					

					return comp;
				}
			
			*/
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

			/*// Listener para tomar los valores de las filas de la tabla
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent ev) {
					// Pone en la descripcion el valor de la columna
					// fila/columna
					txPDescripcion.setText((String) table.getModel().getValueAt(table.getSelectedRow(), 0) + " "
							+ table.getModel().getValueAt(table.getSelectedRow(), 1));
				}

			});*/
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
	
	
	/*
	@SuppressWarnings("deprecation")
	public void llenarTablaUsuario() {
		ColorCellRed ccr = new ColorCellRed();
		ColorCellGreen ccg = new ColorCellGreen();
		TableColumn tcol = null;
		int i = 0;
		for (Actividad actividad : parser.getActividades()) {
			int Desde = (Integer) spinnerDesde.getValue();
			int Hasta = (Integer) spinnerHasta.getValue();
			
			if(actividad.getHoraComienzo().getDate() >= Desde  && actividad.getHoraComienzo().getDate() <= Hasta){
						int duracion = actividad.getHoraFinal().getHours() - actividad.getHoraComienzo().getHours();
						
						if(duracion>=1)
							if(getSocioID().equals(actividad.getSocioID())  && (actividad.getInstalacionID() == 1)){
							
								table.setValueAt("Piscina",i,0);
								table.setValueAt(actividad.getHoraComienzo().getDate(),i,1);
								table.setValueAt(actividad.getHoraComienzo().getHours() +":"
										+actividad.getHoraComienzo().getMinutes()+actividad.getHoraComienzo().getMinutes(),i,2);
								
								//No funciona porque no muestra las reservas que ya han pasado
								if(actividad.getHoraComienzo().getDate()< Desde){
									tcol = table.getColumnModel().getColumn(1);
									tcol.setCellRenderer(ccr);

								}
							}
							
							else if(getSocioID().equals(actividad.getSocioID())  && (actividad.getInstalacionID()== 2)){
								table.setValueAt("Cancha de futbol",i,0);
								table.setValueAt(actividad.getHoraComienzo().getDate(),i,1);
								table.setValueAt(actividad.getHoraComienzo().getHours() +":"
										+actividad.getHoraComienzo().getMinutes()+actividad.getHoraComienzo().getMinutes(),i,2);
							}
						
						
							//actividad.getHoraComienzo().getHours()
							else if(getSocioID().equals(actividad.getSocioID())  && (actividad.getInstalacionID()== 3)){
								
								table.setValueAt("Pista de tenis",i,0);
								table.setValueAt(actividad.getHoraComienzo().getDate(),i,1);
								table.setValueAt(actividad.getHoraComienzo().getHours() +":"
										+actividad.getHoraComienzo().getMinutes()+actividad.getHoraComienzo().getMinutes(),i,2);
										}
						i++;
					}
		}
			}
			
			*/
	
	
	@SuppressWarnings("deprecation")
	public void llenarTablaUsuario() {
		ColorCellRed ccr = new ColorCellRed();
		ColorCellGreen ccg = new ColorCellGreen();
		TableColumn tcol = null;
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
