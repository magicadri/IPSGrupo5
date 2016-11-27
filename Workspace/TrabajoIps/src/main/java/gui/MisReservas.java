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
import logic.Reserva;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;


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
	private JDateChooser dcDesde;
	private JDateChooser dcHasta;
	private JButton btnOk;

	
	
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
		contentPane.add(getDcDesde());
		contentPane.add(getDcHasta());
		contentPane.add(getBtnOk());
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
				
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					Component comp = super.prepareRenderer(renderer, row, col);
					if(dcDesde.getDate() == null || dcHasta.getDate() == null)
						return comp;
					
					String value = String.valueOf(getModel().getValueAt(row, col));
					try {
						if (itsOver(value,row,col) == 0) {
							comp.setBackground(Color.green);
						} else if (itsOver(value,row,col) == 1) {
							comp.setBackground(Color.red);
						} else
							comp.setBackground(Color.WHITE);
					} catch (SQLException e) {
						System.err.println("[...ERROR AL PINTAR VALORES DE LA TABLA...]");
					}

					return comp;
				} 
				
				
			};
			table.setToolTipText("Las reservas se mostrar\u00E1n ordenadas en funci\u00F3n de la fecha cuando se hayan realizado.");
			
			
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
					if(dcDesde.getDate()== null || dcHasta.getDate() == null){
						JOptionPane.showMessageDialog(null, "Por favor seleccione una fecha.");
					}
					else{
						if((dcDesde.getDate().getDate()>dcHasta.getDate().getDate() && dcDesde.getDate().getMonth()+1 > dcHasta.getDate().getMonth()+1 ) || 
								dcDesde.getDate().getMonth()+1 > dcHasta.getDate().getMonth()+1){
							JOptionPane.showMessageDialog(null, "Por favor seleccione una fecha correcta.");
						}
						else{
							limpiarTabla();
							llenarTablaUsuario();
						}
			
					}
				}
			});
			btnActualizar.setBounds(26, 206, 120, 23);
		}
		return btnActualizar;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void llenarTablaUsuario() {
	

		int i = 0;
		for (Reserva reserva : parser.getReservas()) {
			Date Desde = dcDesde.getDate();
			Date Hasta = dcHasta.getDate();
			
						int duracion = reserva.getHoraFinal().getHours() - reserva.getHoraComienzo().getHours();
						
						if(duracion>=1)
							if((getSocioID().equals(reserva.getSocioID())  && (reserva.getInstalacionID() == 1) && 
									reserva.getHoraComienzo().getMonth()+1 >= dcDesde.getDate().getMonth()+1 &&
									reserva.getHoraComienzo().getMonth()+1 <= dcHasta.getDate().getMonth()+1 &&
									reserva.getHoraComienzo().getDate() >= dcDesde.getDate().getDate() &&
									reserva.getHoraComienzo().getDate() <= dcHasta.getDate().getDate()) ||
											(getSocioID().equals(reserva.getSocioID())  && (reserva.getInstalacionID() == 1) && 
											reserva.getHoraComienzo().getMonth()+1 >= dcDesde.getDate().getMonth()+1 &&
											reserva.getHoraComienzo().getMonth()+1 <= dcHasta.getDate().getMonth()+1 &&
											reserva.getHoraComienzo().getDate() >= dcDesde.getDate().getDate() &&
											reserva.getHoraComienzo().getDate() <= dcHasta.getDate().getDate() &&
									(dcDesde.getDate().getMonth()+1 <= dcHasta.getDate().getMonth()+1 &&
											dcDesde.getDate().getDate() >= dcHasta.getDate().getDate()))){
								
							
								table.setValueAt("Piscina",i,0);
								table.setValueAt(reserva.getHoraComienzo().getDate() + " Mes:"
										+(reserva.getHoraComienzo().getMonth()+1),i,1);
								table.setValueAt(reserva.getHoraComienzo().getHours() +":"
										+"00"+
										"-"+reserva.getHoraFinal().getHours() +":"
										+"00",i,2)
								;
								i++;
								
								
								//Comprueba si ya ha pasado el mes
								if(reserva.getHoraComienzo().getMonth()+1< (Calendar.getInstance().get(Calendar.MONTH)+1)){ //Localtime
									//Comprueba si ya ha pasado el dia
									if(reserva.getHoraComienzo().getDate() < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
									//Entonces lo pinta de rojo porque ya ha pasado
									
									}
									else{
										
										
									}
								}
								else{
									
								}
							}
							
						if(getSocioID().equals(reserva.getSocioID())  && (reserva.getInstalacionID() == 1) && 
									reserva.getHoraComienzo().getMonth()+1 >= dcDesde.getDate().getMonth()+1 &&
									reserva.getHoraComienzo().getMonth()+1 <= dcHasta.getDate().getMonth()+1 &&
									reserva.getHoraComienzo().getDate() >= dcDesde.getDate().getDate() &&
									reserva.getHoraComienzo().getDate() <= dcHasta.getDate().getDate()){
								
								table.setValueAt("Cancha de futbol",i,0);
								table.setValueAt(reserva.getHoraComienzo().getDate() + " Mes:"
										+(reserva.getHoraComienzo().getMonth()+1),i,1);
								table.setValueAt(reserva.getHoraComienzo().getHours() +":"
										+"00"+
										"-"+reserva.getHoraFinal().getHours() +":"
										+"00",i,2);
								i++;
								
								//Comprueba si ya ha pasado el mes
								if(reserva.getHoraComienzo().getMonth()+1< (Calendar.getInstance().get(Calendar.MONTH)+1)){ //Localtime
									//Comprueba si ya ha pasado el dia
									if(reserva.getHoraComienzo().getDate() < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
									
									}
									else{
										
									}
								}
								else{
									
								}
							}
						
						
						if(getSocioID().equals(reserva.getSocioID())  && (reserva.getInstalacionID() == 1) && 
									reserva.getHoraComienzo().getMonth()+1 >= dcDesde.getDate().getMonth()+1 &&
									reserva.getHoraComienzo().getMonth()+1 <= dcHasta.getDate().getMonth()+1 &&
									reserva.getHoraComienzo().getDate() >= dcDesde.getDate().getDate() &&
									reserva.getHoraComienzo().getDate() <= dcHasta.getDate().getDate()){
								
								
								table.setValueAt("Pista de tenis",i,0);
								table.setValueAt(reserva.getHoraComienzo().getDate() + " Mes:"
										+(reserva.getHoraComienzo().getMonth()+1),i,1);
								table.setValueAt(reserva.getHoraComienzo().getHours() +":"
										+"00"+
										"-"+reserva.getHoraFinal().getHours() +":"
										+"00",i,2);
								i++;
								
								
								//Comprueba si ya ha pasado el mes
								if(reserva.getHoraComienzo().getMonth()+1< (Calendar.getInstance().get(Calendar.MONTH)+1)){ //Localtime
									//Comprueba si ya ha pasado el dia
									if(reserva.getHoraComienzo().getDate() < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
									//Entonces lo pinta de rojo porque ya ha pasado
									
									}
									else{
										//Sino lo pinta de verde
										
									}
								}
								else{
									//Sino lo pinta de blanco
									
								}
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
//	0 verde 1 rojo
	private int itsOver(String value, int row, int col) throws SQLException {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		
		
		
		if(value == null || value.contains(":") || value.equals(""))
			return 2;
		
		
		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();
		
		// Ver si ha pasado la fecha
		for(Reserva res : r){
			//Si el nombre del input es igual al socio
			if(res.getSocioID().equals(socioID)){
				//Comprueba si ha pasado el mes
				if(res.getHoraComienzo().getMonth()+1 < (cal.get(Calendar.MONTH)+1)){ 
					//Comprueba si ya ha pasado el dia
					if(res.getHoraComienzo().getDate() < cal.get(Calendar.DAY_OF_MONTH)){
					//Entonces lo pinta de rojo porque ya ha pasado
						return 1;
					}
					else{
						//Sino lo pinta de verde
						return 0;
					}
				}
			}				
		}
		return 2;
	}
	private JDateChooser getDcDesde() {
		if (dcDesde == null) {
			dcDesde = new JDateChooser();
			dcDesde.setBounds(72, 67, 95, 20);
			
		}
		return dcDesde;
	}
	private JDateChooser getDcHasta() {
		if (dcHasta == null) {
			dcHasta = new JDateChooser();
			dcHasta.setBounds(75, 146, 95, 20);
		}
		return dcHasta;
	}
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnOk.setBounds(641, 443, 89, 23);
		}
		return btnOk;
	}
}
