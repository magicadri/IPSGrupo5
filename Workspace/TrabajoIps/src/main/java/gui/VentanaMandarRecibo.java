package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import db.Database;
import db.Parser;
import logic.Instalacion;
import logic.Reserva;
import logic.Socio;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class VentanaMandarRecibo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelDatos;
	private JPanel panelTabla;
	private JLabel lblSocio;
	private JTextField textFieldSocio;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel modelTable = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private Parser parser = new Parser();
	private JButton btnBuscar;
	private ArrayList<String> sociosBuscados;
	private ArrayList<Reserva> reservasMarcadas;
	private JPanel panelMandarRecibo;
	private JButton btnMandarRecibo;
	private int recibo;

	public int getRecibo() {
		return recibo;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaMandarRecibo dialog = new VentanaMandarRecibo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 */
	public VentanaMandarRecibo() {
		setResizable(false);
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sociosBuscados = new ArrayList<>();
		reservasMarcadas = new ArrayList<>();
		setBounds(100, 100, 524, 348);
		{
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
		}
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPanelDatos(), BorderLayout.NORTH);
		contentPanel.add(getPanelTabla(), BorderLayout.CENTER);
		contentPanel.add(getPanelMandarRecibo(), BorderLayout.SOUTH);
	}

	private JPanel getPanelDatos() {
		if (panelDatos == null) {
			panelDatos = new JPanel();
			panelDatos.add(getLblSocio());
			panelDatos.add(getTextFieldSocio());
			panelDatos.add(getBtnBuscar());
		}
		return panelDatos;
	}

	private JPanel getPanelTabla() {
		if (panelTabla == null) {
			panelTabla = new JPanel();
			panelTabla.add(getScrollPane());
		}
		return panelTabla;
	}

	private JLabel getLblSocio() {
		if (lblSocio == null) {
			lblSocio = new JLabel("Introduzca socioID:");
		}
		return lblSocio;
	}

	private JTextField getTextFieldSocio() {
		if (textFieldSocio == null) {
			textFieldSocio = new JTextField();
			textFieldSocio.setColumns(10);
		}
		return textFieldSocio;
	}

	private boolean buscarSocio(String socioID) {
		for (Socio each : parser.getSocios())
			if (each.getSocioID().equals(socioID))
				return true;
		return false;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();

		}
		modelTable.addColumn("Socio");
		modelTable.addColumn("Fecha comienzo");
		modelTable.addColumn("Fecha final");
		modelTable.addColumn("Instalación");
		modelTable.addColumn("Precio");
		table.setModel(modelTable);
		return table;
	}

	private JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					llenarTabla();
				}
			});
		}
		return btnBuscar;
	}

	private void llenarTabla() {
		limpiarTabla();
		Object row[] = new Object[5];
		if (buscarSocio(textFieldSocio.getText()) && !sociosBuscados.contains(getTextFieldSocio().getText())) {
			sociosBuscados.add(textFieldSocio.getText());
			for (Reserva each : parser.getReservas()) {
				if (!each.getReciboGenerado() && each.getSocioID().equals(textFieldSocio.getText())
						&& !(reservasMarcadas.contains(each))) {
					row[0] = each.getSocioID();
					row[1] = each.getHoraComienzo();
					row[2] = each.getHoraFinal();
					row[3] = getInstalacionNombre(each.getInstalacionID());
					row[4] = each.getPrecio();
					modelTable.addRow(row);

				}
			}
		}
	}

	private void limpiarTabla() {
		table.clearSelection();
		DefaultTableModel md = (DefaultTableModel) table.getModel();
		md.setRowCount(0);
		sociosBuscados.clear();
	}

	private String getInstalacionNombre(int id) {
		for (Instalacion each : parser.getInstalaciones())
			if (each.getInstalacionID() == id)
				return each.getInstalacion_nombre();
		return "";
	}

	private JPanel getPanelMandarRecibo() {
		if (panelMandarRecibo == null) {
			panelMandarRecibo = new JPanel();
			GridBagLayout gbl_panelMandarRecibo = new GridBagLayout();
			panelMandarRecibo.setLayout(gbl_panelMandarRecibo);
			GridBagConstraints gbc_btnMandarRecibo = new GridBagConstraints();
			gbc_btnMandarRecibo.gridx = 12;
			gbc_btnMandarRecibo.gridy = 0;
			panelMandarRecibo.add(getBtnMandarRecibo(), gbc_btnMandarRecibo);
		}
		return panelMandarRecibo;
	}

	private JButton getBtnMandarRecibo() {

		if (btnMandarRecibo == null) {
			btnMandarRecibo = new JButton("A\u00F1adir a cuota");
			btnMandarRecibo.addActionListener(new ActionListener() {
				String socio;
				Timestamp horaComienzo;

				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() != -1) {
						socio = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
						horaComienzo = (Timestamp) table.getValueAt(table.getSelectedRow(), 1);
						try {
							crearRecibo(socio, horaComienzo);
							llenarTabla();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return btnMandarRecibo;
	}

	private void crearRecibo(String socio, Timestamp hora) throws SQLException {
		Reserva pagar = null;
		for (Reserva each : parser.getReservas()) {
			if (each.getSocioID().equals(socio) && each.getHoraComienzo().equals(hora)) {
				pagar = each;
			}

		}
		if (pagar != null) {
			Database.getInstance().getC().createStatement()
					.execute("UPDATE RESERVA SET reciboGenerado=TRUE WHERE reservaID= " + pagar.getReservaID() + ";");
			Database.getInstance().getC().createStatement()
					.execute("INSERT INTO OBJETOCUOTA(reciboID, socioID, importe, fecha) VALUES ("
							+ pagar.getReservaID() + ", '" + pagar.getSocioID() + "'," + pagar.getPrecio() + ",'"
							+ pagar.getHoraComienzo() + "');");
			Database.getInstance().getC().createStatement()
					.execute("UPDATE CUOTA SET importe=importe +" + pagar.getPrecio() + " WHERE socioID='"
							+ pagar.getSocioID() + "' and mes =" + sacarMes(pagar.getHoraComienzo()) + ";");
			JOptionPane jp = new JOptionPane();
			jp.showMessageDialog(this, "El importe ha sido añadido a la cuota del socio");
			reservasMarcadas.add(pagar);
			llenarTabla();
		}
	}

	private int sacarMes(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.MONTH);
	}
}
