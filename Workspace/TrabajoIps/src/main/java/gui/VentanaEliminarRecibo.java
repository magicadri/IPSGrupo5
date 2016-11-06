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
import logic.ObjetoCuota;
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

public class VentanaEliminarRecibo extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4966634129535544025L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelDatos;
	private JPanel panelTabla;
	private JLabel lblSocio;
	private JTextField textFieldSocio;
	private JScrollPane scrollPane;
	private JTable table;
	private ArrayList<ObjetoCuota> marcada;
	
	private DefaultTableModel modelTable = new DefaultTableModel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private Parser parser = new Parser();
	private JButton btnBuscar;
	private ArrayList<String> sociosBuscados;
	private JPanel panelQuitarRecibo;
	private JButton btnQuitarRecibo;
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
	public VentanaEliminarRecibo() {
		setResizable(false);
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		marcada = new ArrayList<>();
		sociosBuscados = new ArrayList<>();
		setBounds(100, 100, 524, 348);
		{
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
		}
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPanelDatos(), BorderLayout.NORTH);
		contentPanel.add(getPanelTabla(), BorderLayout.CENTER);
		contentPanel.add(getPanelQuitarRecibo(), BorderLayout.SOUTH);
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
		modelTable.addColumn("Fecha");
		modelTable.addColumn("Importe");
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
	
	private void llenarTabla()
	{
		limpiarTabla();
		Object row[] = new Object[3];
		if (buscarSocio(textFieldSocio.getText()) && !sociosBuscados.contains(getTextFieldSocio().getText())) {
			sociosBuscados.add(textFieldSocio.getText());
			for (ObjetoCuota each : parser.getObjetosCuota()) {
					if(!marcada.contains(each) && !each.getPagado())
					{
						row[0] = each.getSocioID();
						row[1] = each.getFecha();
						row[2] = each.getImporte();
						modelTable.addRow(row);
						
				}
				}
			}
		}
	
	private void limpiarTabla()
	{
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
	private JPanel getPanelQuitarRecibo() {
		if (panelQuitarRecibo == null) {
			panelQuitarRecibo = new JPanel();
			GridBagLayout gbl_panelQuitarRecibo = new GridBagLayout();
			panelQuitarRecibo.setLayout(gbl_panelQuitarRecibo);
			GridBagConstraints gbc_btnQuitarRecibo = new GridBagConstraints();
			gbc_btnQuitarRecibo.gridx = 12;
			gbc_btnQuitarRecibo.gridy = 0;
			panelQuitarRecibo.add(getBtnQuitarRecibo(), gbc_btnQuitarRecibo);
		}
		return panelQuitarRecibo;
	}
	private JButton getBtnQuitarRecibo() {
		
		if (btnQuitarRecibo == null) {
			btnQuitarRecibo = new JButton("Eliminar deuda ");
			btnQuitarRecibo.addActionListener(new ActionListener() {
				String socio;
				Timestamp horaComienzo;
				public void actionPerformed(ActionEvent e) {
					if(table.getSelectedRow()!=-1)
					{
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
		return btnQuitarRecibo;
	}
	
	private void crearRecibo(String socio, Timestamp hora) throws SQLException
	{
		ObjetoCuota pagar = null;
		for(ObjetoCuota each: parser.getObjetosCuota())
			if(each.getSocioID().equals(socio) && each.getFecha().equals(hora))
				pagar= each;
		if(pagar!=null)
		{
			Database.getInstance().getC().createStatement().execute("UPDATE OBJETOCUOTA SET pagado=TRUE WHERE reciboID= "+ pagar.getReciboID() +  ";" );
			Database.getInstance().getC().createStatement().execute("UPDATE CUOTA SET importe=importe -" + pagar.getImporte() + " WHERE socioID='" + pagar.getSocioID()+ "' and mes =" + sacarMes(pagar.getFecha()) +";" );
			Database.getInstance().getC().createStatement().execute("INSERT INTO Recibo(socioID, importe, descripcion) VALUES ('"+ pagar.getSocioID() + "'," + pagar.getImporte() + "," +"'Descripcion: " + pagar.getFecha()+ " Instalacion:" + getInstalacionNombre(pagar.getReciboID()) + "');");
			JOptionPane jp = new JOptionPane();
			jp.showMessageDialog(this, "Recibo generado, el importe ha sido reducido de la cuota del cliente");
			marcada.add(pagar);
		}
	}
	
	private int sacarMes(Timestamp t)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.MONTH);
	}
}
