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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class VentanaActividades extends JFrame {

	private JPanel contentPane;
	private JTextField tfSocioID;
	private JLabel lblSocioid;
	private JLabel lblActividadid;
	private JTextField tfActividadID;
	private JButton btnOk;
	private JButton btnCancelar;
	private Parser parser;
	int ReservaID;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaActividades frame = new VentanaActividades();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaActividades() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 656, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTfSocioID());
		contentPane.add(getLblSocioid());
		contentPane.add(getLblActividadid());
		contentPane.add(getTfActividadID());
		contentPane.add(getBtnOk());
		contentPane.add(getBtnCancelar());
		contentPane.add(getTable());
		llenarTabla();

	}
	private JTextField getTfSocioID() {
		if (tfSocioID == null) {
			tfSocioID = new JTextField();
			tfSocioID.setBounds(514, 35, 86, 20);
			tfSocioID.setColumns(10);
		}
		return tfSocioID;
	}
	private JLabel getLblSocioid() {
		if (lblSocioid == null) {
			lblSocioid = new JLabel("SocioID:");
			lblSocioid.setBounds(458, 38, 46, 14);
		}
		return lblSocioid;
	}
	private JLabel getLblActividadid() {
		if (lblActividadid == null) {
			lblActividadid = new JLabel("ActividadID:");
			lblActividadid.setBounds(439, 71, 65, 14);
		}
		return lblActividadid;
	}
	private JTextField getTfActividadID() {
		if (tfActividadID == null) {
			tfActividadID = new JTextField();
			tfActividadID.setBounds(514, 68, 86, 20);
			tfActividadID.setColumns(10);
		}
		return tfActividadID;
	}
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String SocioID = tfSocioID.getText();
					int ActividadID = Integer.parseInt(tfActividadID.getText());
					
					for(ReservaActividad reserva: parser.getReservasactividad()){
						if(reserva.getActividadID() == ActividadID) {
							//Consigue la id de la reserva que coincide con la id de la actividad escrita en el tf
							ReservaID = reserva.getReservaID();
						}
					}
					
					try {
					Database.getInstance().getC().createStatement().execute("INSERT INTO SOCIOACTIVIDAD (socioID, actividadID, reservaID, presentado) VALUES (" 
								+ SocioID + "," + ActividadID + "," + ReservaID + "," + false + ");");
					} catch (SQLException e) {
						e.printStackTrace();
					}
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
			table = new JTable(){
		
			};
			table.setToolTipText("");
			
			
			table.setBounds(92, 35, 258, 342);

			DataTableModel dm = new DataTableModel(
					new Object[][] { { null, null,null }, {  null, null,null }, {  null, null,null }, { null, null,null },
							{  null,null,null }, {  null,null,null }, {  null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, {  null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, { null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, {  null, null,null }, {  null, null,null },
							{  null, null,null }, {  null, null,null }, {  null, null,null }, {  null, null,null }, },
					new String[] { "Lugar", "ReservaID", "Semanas" });
			table.setModel(dm);
		}
		return table;
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public void llenarTabla() {
	

		int i = 0;
		for (Actividad actividad : parser.getActividades()) {
			
						table.setValueAt(actividad.getActividad_nombre(), i, 0);
						table.setValueAt(actividad.getActividadID(), i, 1);
						table.setValueAt(actividad.getSemanas(), i, 2);
							
										i++;
						
					}
		}
	
}
