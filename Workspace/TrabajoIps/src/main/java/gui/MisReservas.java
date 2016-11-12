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
import logic.Reserva;
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


public class MisReservas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblDesde;
	private JSpinner spinnerDesde;
	private JLabel lblHasta;
	private JSpinner spinnerHasta;
	private Parser parser;
	VentanaPrincipal VP = new VentanaPrincipal();

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
	public MisReservas(String socioID) {
		setSocioID(socioID);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 787, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblDesde());
		contentPane.add(getSpinnerDesde());
		contentPane.add(getLblHasta());
		contentPane.add(getSpinnerHasta());
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
	
	/*
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setBounds(175, 11, 375, 384);

			DataTableModel dm = new DataTableModel(
					new Object[][] { { null, null, null, null, null }, { null, null, null, null,null }, { null, null, null, null,null },
							{ null, null, null, null,null }, { null, null, null, null,null }, { null, null, null, null,null },
							{ null, null, null, null,null }, { null, null, null, null,null }, { null, null, null, null,null },
							{ null, null, null, null,null }, { null, null, null, null,null }, { null, null, null, null,null },
							{ null, null, null, null,null }, { null, null, null, null,null }, { null, null, null, null,null },
							{ null, null, null, null,null }, { null, null, null, null,null }, { null, null, null, null,null },
							{ null, null, null, null,null }, { null, null, null, null,null }, { null, null, null, null,null },
							{ null, null, null, null ,null}, { null, null, null, null ,null}, { null, null, null, null,null }, },
					new String[] { "ReservaID","InstalacionID", "SocioID", "Hora comienzo", "Hora salida" });
			
			
			VentanaPrincipal VP = new VentanaPrincipal();
			
			//Por cada iteracion del bucle sobre las reservas que hay, comprueba si hay alguna que coincida con el IDSocio
			//Que es un string compuesto por el input el cual es necesario para acceder a la ventana socio
			for(Reserva reserva : Parser.getReservas()){
			 if(reserva.getSocioID().equals(VP.IDSocio)){
				 Object[] row = new Object[4];
					//Para el tamaño del vector reservas (cantidad de reservas hechas) 
					//Se añade una fila por cada reserva hecha
					for(int i=0;i<Parser.getReservas().size(); i++){
						
						row[0] = Parser.getReservas().get(i).getReservaID();
						row[1] = Parser.getReservas().get(i).getInstalacionID();
						row[2] = Parser.getReservas().get(i).getSocioID();
						row[3] = Parser.getReservas().get(i).getHoraComienzo();
						row[4] = Parser.getReservas().get(i).getHoraFinal();
							dm.addRow(row);

					} 
			 }

			}
			
			
			
			
			table.setModel(dm);
			
			

			// Listener para tomar los valores de las filas de la tabla
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				// No editable NO FUNCIONA DE MOMENTO
				public boolean isCellEditable(int row, int column) {
					return false;
				}

				@Override
				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub

				}

			});
			
			
		
		}
		return table;
	}
	*/
	
	
	
	
/*
	public void addRows() {
		String header[] = new String[] { "reservaID", "socioID", "instalacionID", "horaComienzo", "horaFinal",
				"horaEntrada", "horaSalida", "modoPago", "precio" };

		tableModel.setColumnIdentifiers(header);
		for (int count = 1; count <= 30; count++) {
			tableModel.addRow(new Object[] { "data", "data", "data", "data", "data", "data" });
		}
		*/


		

	
	private JLabel getLblDesde() {
		if (lblDesde == null) {
			lblDesde = new JLabel("Desde: ");
			lblDesde.setBounds(26, 42, 46, 14);
		}
		return lblDesde;
	}
	private JSpinner getSpinnerDesde() {
		if (spinnerDesde == null) {
			spinnerDesde = new JSpinner();
			spinnerDesde.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerDesde.setBounds(26, 65, 36, 20);
		}
		return spinnerDesde;
	}
	private JLabel getLblHasta() {
		if (lblHasta == null) {
			lblHasta = new JLabel("Hasta");
			lblHasta.setBounds(26, 121, 46, 14);
		}
		return lblHasta;
	}
	private JSpinner getSpinnerHasta() {
		if (spinnerHasta == null) {
			spinnerHasta = new JSpinner();
			spinnerHasta.setModel(new SpinnerNumberModel(31, 0, 31, 1));
			spinnerHasta.setBounds(26, 146, 39, 20);
		}
		return spinnerHasta;
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
			btnActualizar.setBounds(34, 206, 89, 23);
		}
		return btnActualizar;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void llenarTablaUsuario() {
		ColorCellRed ccr = new ColorCellRed();
		ColorCellGreen ccg = new ColorCellGreen();
		TableColumn tcol = null;
		int i = 0;
		for (Reserva reserva : parser.getReservas()) {
			int Desde = (Integer) spinnerDesde.getValue();
			int Hasta = (Integer) spinnerHasta.getValue();
			
			if(reserva.getHoraComienzo().getDate() >= Desde  && reserva.getHoraComienzo().getDate() <= Hasta){
						int duracion = reserva.getHoraFinal().getHours() - reserva.getHoraComienzo().getHours();
						
						if(duracion>=1)
							if(getSocioID().equals(reserva.getSocioID())  && (reserva.getInstalacionID() == 1)){
							
								table.setValueAt("Piscina",i,0);
								table.setValueAt(reserva.getHoraComienzo().getDate(),i,1);
								table.setValueAt(reserva.getHoraComienzo().getHours() +":"
										+reserva.getHoraComienzo().getMinutes()+reserva.getHoraComienzo().getMinutes(),i,2);
								
								//No funciona porque no muestra las reservas que ya han pasado
								if(reserva.getHoraComienzo().getDate()< Desde){
									tcol = table.getColumnModel().getColumn(1);
									tcol.setCellRenderer(ccr);

								}
							}
							
							else if(getSocioID().equals(reserva.getSocioID())  && (reserva.getInstalacionID()== 2)){
								table.setValueAt("Cancha de futbol",i,0);
								table.setValueAt(reserva.getHoraComienzo().getDate(),i,1);
								table.setValueAt(reserva.getHoraComienzo().getHours() +":"
										+reserva.getHoraComienzo().getMinutes()+reserva.getHoraComienzo().getMinutes(),i,2);
							}
						
						
							//reserva.getHoraComienzo().getHours()
							else if(getSocioID().equals(reserva.getSocioID())  && (reserva.getInstalacionID()== 3)){
								
								table.setValueAt("Pista de tenis",i,0);
								table.setValueAt(reserva.getHoraComienzo().getDate(),i,1);
								table.setValueAt(reserva.getHoraComienzo().getHours() +":"
										+reserva.getHoraComienzo().getMinutes()+reserva.getHoraComienzo().getMinutes(),i,2);
										}
						i++;
					}
		}
			}
	
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
