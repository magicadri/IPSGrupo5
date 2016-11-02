package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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


public class MisReservas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblDesde;
	private JSpinner spinnerComienzo;
	private JLabel lblHasta;
	private JSpinner spinnerHasta;
	private Parser Parser = new Parser();
	//List<Reserva> Reservas = new ArrayList<>(Parser.getReservas());
	private String socioID = "adri";
	
	

	private JTable table;


	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MisReservas frame = new MisReservas();
					Parser Parser = new Parser();
					frame.setVisible(true);
					System.out.println("dsdasdadasda");

					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MisReservas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblDesde());
		contentPane.add(getSpinnerComienzo());
		contentPane.add(getLblHasta());
		contentPane.add(getSpinnerHasta());
		contentPane.add(getTable());
		
		
		
	
		
		//RellenarTabla();
				
	}
	
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
	private JSpinner getSpinnerComienzo() {
		if (spinnerComienzo == null) {
			spinnerComienzo = new JSpinner();
			spinnerComienzo.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerComienzo.setBounds(26, 65, 36, 20);
		}
		return spinnerComienzo;
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
			spinnerHasta.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerHasta.setBounds(26, 146, 39, 20);
		}
		return spinnerHasta;
	}
	
	//OJO
	
	public void comprobarSpinner(){
		Parser.getReservas();
		
	}
	
	/*
	public void RellenarTabla(){
		
		ArrayList<Reserva> Lista = Parser.getReservas();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] row = new Object[8];
		for(int i=0;i<Reservas.size(); i++){
			row[0] = Reservas.get(i).getInstalacionID();
		}
		
			model.addRow(row);
		
	}
	*/
	
}
