package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.toedter.calendar.JDateChooser;

import db.Parser;
import logic.Instalacion;
import logic.Reserva;

import javax.swing.JTable;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class VentanaComprobarOcupacionAdmin extends JDialog {

	private static final long serialVersionUID = 2653474616019298991L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JPanel pnIzq;
	private JTextPane textPane;
	private Parser parser;
	private JDateChooser dateChooser;

	/**
	 * Create the dialog.
	 */
	public VentanaComprobarOcupacionAdmin() {
		parser = new Parser();
		setTitle("Comprobar disponibilidad");
		setBounds(100, 100, 1360, 582);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblComprobarDisponibilidadDe = new JLabel("Comprobar disponibilidad de instalaciones:");
			lblComprobarDisponibilidadDe.setFont(new Font("Tahoma", Font.BOLD, 34));
			lblComprobarDisponibilidadDe.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblComprobarDisponibilidadDe, BorderLayout.NORTH);
		}
		{
			pnIzq = new JPanel();
			contentPanel.add(pnIzq, BorderLayout.WEST);
			GridBagLayout gbl_pnIzq = new GridBagLayout();
			gbl_pnIzq.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_pnIzq.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_pnIzq.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
					Double.MIN_VALUE };
			gbl_pnIzq.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
					Double.MIN_VALUE };
			pnIzq.setLayout(gbl_pnIzq);
			{
				JLabel lblDia = new JLabel("Dia:");
				GridBagConstraints gbc_lblDia = new GridBagConstraints();
				gbc_lblDia.gridwidth = 2;
				gbc_lblDia.insets = new Insets(0, 0, 5, 5);
				gbc_lblDia.gridx = 1;
				gbc_lblDia.gridy = 1;
				pnIzq.add(lblDia, gbc_lblDia);
			}
			{
				dateChooser = new JDateChooser();
				GridBagConstraints gbc_dateChooser = new GridBagConstraints();
				gbc_dateChooser.anchor = GridBagConstraints.NORTHWEST;
				gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
				gbc_dateChooser.gridx = 5;
				gbc_dateChooser.gridy = 1;
				pnIzq.add(dateChooser, gbc_dateChooser);

			}
		}
		DataTableModel dm = new DataTableModel(
				new Object[][] { { "Horas", "Piscina", "Cancha de futbol", "Pista de tennis" },
						{ "00:00", null, null, null }, { "01:00", null, null, null }, { "02:00", null, null, null },
						{ "03:00", null, null, null }, { "04:00", null, null, null }, { "05:00", null, null, null },
						{ "06:00", null, null, null }, { "07:00", null, null, null }, { "08:00", null, null, null },
						{ "09:00", null, null, null }, { "10:00", null, null, null }, { "11:00", null, null, null },
						{ "12:00", null, null, null }, { "13:00", null, null, null }, { "14:00", null, null, null },
						{ "15:00", null, null, null }, { "16:00", null, null, null }, { "17:00", null, null, null },
						{ "18:00", null, null, null }, { "19:00", null, null, null }, { "20:00", null, null, null },
						{ "21:00", null, null, null }, { "22:00", null, null, null }, { "23:00", null, null, null }, },
				new String[] { "Horas", "Disponibilidad", "Llegada", "Salida" });
		{
			JButton btComprobar = new JButton("Comprobar");
			btComprobar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(1);
					limpiarDescripcion();
					System.out.println(2);
					limpiarTabla();
					System.out.println(3);
					llenarTabla();
				}
			});
			GridBagConstraints gbc_btComprobar = new GridBagConstraints();
			gbc_btComprobar.insets = new Insets(0, 0, 5, 5);
			gbc_btComprobar.gridx = 5;
			gbc_btComprobar.gridy = 3;
			pnIzq.add(btComprobar, gbc_btComprobar);

		}
		{
			JPanel pnCentral = new JPanel();
			contentPanel.add(pnCentral, BorderLayout.CENTER);
			pnCentral.setLayout(new BorderLayout(0, 0));
			table = new JTable(){
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					Component comp = super.prepareRenderer(renderer, row, col);
					if(dateChooser.getDate()==null)
						return comp;
					
					String value = String.valueOf(getModel().getValueAt(row, col));
					if (itsAdmin(value,row,col) == 0) {
						comp.setBackground(Color.green);
					} else if (itsAdmin(value,row,col) == 1) {
						comp.setBackground(Color.red);
					} else
						comp.setBackground(Color.WHITE);

					return comp;
				}
			};
			pnCentral.add(table, BorderLayout.CENTER);
			table.setModel(dm);
			{
				JPanel pnCNorte = new JPanel();
				pnCentral.add(pnCNorte, BorderLayout.NORTH);
			}
			// Listener para tomar los valores de las filas de la tabla
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent ev) {
					if(table.getSelectedColumn()!=-1 && table.getSelectedRow()!=-1){
						// Pone en la descripcion el valor de la celda seleccionada
						textPane.setText((String) table.getModel().getValueAt(table.getSelectedRow(), 0) + " - Reserva id: "
							+ table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn())
							+ " + [info deseada por el cliente]");
					}
				}
			});
			
			
		}
		{

			JPanel pnDcha = new JPanel();
			contentPanel.add(pnDcha, BorderLayout.EAST);
			pnDcha.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel lblDescripcion = new JLabel("Descripcion:");
				pnDcha.add(lblDescripcion);
			}
			{
				textPane = new JTextPane();
				pnDcha.add(textPane);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btFinalizar = new JButton("Finalizar");
				btFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 22));
				btFinalizar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btFinalizar.setActionCommand("OK");
				buttonPane.add(btFinalizar);
				getRootPane().setDefaultButton(btFinalizar);
			}
		}

	}

	private void llenarTabla() {		
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Reserva reserva : parser.getReservas()) {
			Date a = dateChooser.getDate();
			String dia = sacarDia(a);
			if (String.valueOf(getDia(reserva.getHoraComienzo())).equals(dia)) {
				if (getHora(reserva.getHoraComienzo()) - getHora(reserva.getHoraFinal()) == -1)
					table.setValueAt(String.valueOf(reserva.getReservaID()), getHora(reserva.getHoraComienzo()) + 1,
							reserva.getInstalacionID());
				else {
					table.setValueAt(reserva.getReservaID(), getHora(reserva.getHoraComienzo()) + 1,
							reserva.getInstalacionID());
					table.setValueAt(String.valueOf(reserva.getReservaID()), getHora(reserva.getHoraComienzo()) + 2,
							reserva.getInstalacionID());
				}
			}

		}
	}

	private int getHora(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	private int getDia(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
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

	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	private void limpiarTabla() {
		for (int j = 1; j < 4; j++) {
			for (int i = 1; i < table.getRowCount(); i++) {
				table.clearSelection();
				table.setValueAt("", i, j);
			}
		}
	}
	
	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	private void limpiarDescripcion() {
		textPane.setText("");
	}
	
	private int itsAdmin(String value, int row, int col) {
		if(row==0 || col == 0 || value == null || value.contains(":") || value.equals(""))
			return 2;
		
		for(Reserva res : parser.getReservas()){
			if(String.valueOf(res.getReservaID()).equals(value))
				if(res.getSocioID().equals("admin"))
					return 0;
		}
		return 1;
		
	}

}
