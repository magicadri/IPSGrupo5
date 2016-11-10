package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import db.Parser;
import logic.Instalacion;
import logic.Reserva;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaOcupacionAdminMejorada extends JDialog {

	private static final long serialVersionUID = -4412349867500367960L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblOcupacionDeInstalaciones;
	private JLabel lblFecha;
	private JLabel lblInstalacion;
	private JPanel pnDatos;
	private JComboBox<String> cbInstalacion;
	private String[] instalaciones;
	private JDateChooser dateChooser;
	private JButton btComprobar;
	private JPanel pnTabla;
	private JPanel pnDescripcion;
	private JTextArea taDescripcion;
	private JTable table;
	private Parser parser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaOcupacionAdminMejorada dialog = new VentanaOcupacionAdminMejorada();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaOcupacionAdminMejorada() {
		parser = new Parser();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Ventana de ocupacion");
		setBounds(100, 100, 1047, 901);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblOcupacionDeInstalaciones());
		contentPanel.add(getPnDatos());
		contentPanel.add(getPnTabla());
		contentPanel.add(getPnDescripcion());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btOk = new JButton("OK");
				btOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btOk.setActionCommand("OK");
				buttonPane.add(btOk);
				getRootPane().setDefaultButton(btOk);
			}
		}
	}

	private JLabel getLblOcupacionDeInstalaciones() {
		if (lblOcupacionDeInstalaciones == null) {
			lblOcupacionDeInstalaciones = new JLabel("Ocupaci\u00F3n de instalaciones:");
			lblOcupacionDeInstalaciones.setFont(new Font("Rockwell", Font.PLAIN, 60));
			lblOcupacionDeInstalaciones.setHorizontalAlignment(SwingConstants.CENTER);
			lblOcupacionDeInstalaciones.setBounds(61, 16, 883, 118);
		}
		return lblOcupacionDeInstalaciones;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(27, 132, 70, 31);
			lblFecha.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblFecha;
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n:");
			lblInstalacion.setBounds(15, 42, 82, 20);
			lblInstalacion.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblInstalacion;
	}

	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnDatos.setBounds(31, 182, 280, 465);
			pnDatos.setLayout(null);
			pnDatos.add(getLblInstalacion());
			pnDatos.add(getLblFecha());
			pnDatos.add(getCbInstalacion());
			pnDatos.add(getDateChooser());
			pnDatos.add(getBtComprobar());
		}
		return pnDatos;
	}

	private JComboBox<String> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<String>();
			try {
				instalaciones = fillInstalaciones();
			} catch (SQLException e) {
				System.err.println("[...ERROR AL RELLENAR EL COMBOBOX DE INSTALACIONES EN LA CLASE VENTANAOCUPACIONADMINMEJORADA...]");
			}
			DefaultComboBoxModel<String> modeloCB = new DefaultComboBoxModel<String>(instalaciones);
			cbInstalacion.setModel(modeloCB);
			cbInstalacion.setBounds(112, 37, 153, 31);
		}
		return cbInstalacion;
	}

	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setBounds(112, 132, 153, 31);
		}
		return dateChooser;
	}

	private JButton getBtComprobar() {
		if (btComprobar == null) {
			btComprobar = new JButton("Comprobar");
			btComprobar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(comprobarMinimos()){
						limpiarTabla();
						limpiarDescripcion();
						try {
							fillTabla();
						} catch (SQLException e1) {
							System.err.println("[...ERROR AL RELLENAR LA TABLA EN LA CLASE VENTANAOCUPACIONADMINMEJORADA...]");
						}
					}
				}
			});
			btComprobar.setBounds(150, 420, 115, 29);
		}
		return btComprobar;
	}

	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setBorder(new TitledBorder(null, "Tabla", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnTabla.setBounds(348, 182, 650, 465);
			pnTabla.setLayout(null);
			pnTabla.add(getTable());
		}
		return pnTabla;
	}

	private JPanel getPnDescripcion() {
		if (pnDescripcion == null) {
			pnDescripcion = new JPanel();
			pnDescripcion.setBorder(
					new TitledBorder(null, "Descripci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDescripcion.setBounds(31, 680, 970, 118);
			pnDescripcion.setLayout(null);
			pnDescripcion.add(getTaDescripcion());
		}
		return pnDescripcion;
	}

	private JTextArea getTaDescripcion() {
		if (taDescripcion == null) {
			taDescripcion = new JTextArea();
			taDescripcion.setEditable(false);
			taDescripcion.setBounds(15, 27, 940, 75);
		}
		return taDescripcion;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable(){
				
				private static final long serialVersionUID = -2655951525426742341L;

				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					Component comp = super.prepareRenderer(renderer, row, col);
					if(dateChooser.getDate()==null)
						return comp;
					
					String value = String.valueOf(getModel().getValueAt(row, col));
					try {
						if (itsAdmin(value,row,col) == 0) {
							comp.setBackground(Color.green);
						} else if (itsAdmin(value,row,col) == 1) {
							comp.setBackground(Color.red);
						} else
							comp.setBackground(Color.WHITE);
					} catch (SQLException e) {
						System.err.println("[...ERROR AL PINTAR VALORES DE LA TABLA EN LA CLASE VENTANAOCUPACIONADMINMEJORADA...]");
					}

					return comp;
				}
			};
			Object[] nombreColumnas = new Object[2];
			nombreColumnas[0] = "Hora";
			nombreColumnas[1] = "Reserva";
			DataTableModel modeloTabla = new DataTableModel(new Object[][] { { "Horas", "Reserva" }, { "00:00", null },
					{ "01:00", null }, { "02:00", null }, { "03:00", null }, { "04:00", null }, { "05:00", null },
					{ "06:00", null }, { "07:00", null }, { "08:00", null }, { "09:00", null }, { "10:00", null },
					{ "11:00", null }, { "12:00", null }, { "13:00", null }, { "14:00", null }, { "15:00", null },
					{ "16:00", null }, { "17:00", null }, { "18:00", null }, { "19:00", null }, { "20:00", null },
					{ "21:00", null }, { "22:00", null }, { "23:00", null }, },
					new String[] { "Horas", "Disponibilidad" });
			table.setModel(modeloTabla);
			table.setBounds(15, 38, 620, 411);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent ev) {
					if(table.getSelectedColumn()!=-1 && table.getSelectedRow()!=-1 && table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn())!="" && table.getSelectedRow()!=0
							&& table.getSelectedColumn()!=0 && table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn())!=null){
						// Pone en la descripcion el valor de la celda seleccionada
						try {
							taDescripcion.setText((String) table.getModel().getValueAt(table.getSelectedRow(), 0) + " - Reserva id: "
								+ table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()) + " - Usuario id: "
								+ getSocioId((int) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn())) + " - Modo de pago: "
								+ getModoPago((int) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()))
							);
						} catch (SQLException e) {
							System.err.println("[...ERROR AL ESCRIBIR DATOS DE LA DESCRIPCION EN LA CLASE VENTANAOCUPACIONADMINMEJORADA...]");
						}
					}
					else{
						limpiarDescripcion();
					}
				}
			});
		}
		return table;
	}

	/************************************************ SACAR VALORES DE TIMESTAMP ***********************************************/
	
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

	private int getMes(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.MONTH);
	}

	private int getYear(Timestamp t) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(t.getTime());
		return cal.get(Calendar.YEAR);
	}

	/**************************************************** EXTRA ***************************************************/
	
	/**
	 * Comprueba que no haya campos vacios
	 * @return
	 */
	private boolean comprobarMinimos(){
		if(dateChooser.getDate()==null){
			JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha", "Cuidado", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Consigue la id del socio dada su reserva id 
	 * @param reservaID
	 * @return
	 * @throws SQLException
	 */
	private String getSocioId(int reservaID) throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();

		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();
		
		for(Reserva res : r){
			if(res.getReservaID() == reservaID){
				return res.getSocioID();
			}
		}
		
		return "";
	}
	
	/**
	 * Consigue el modo de pago dada una reserva id
	 * @param reservaID
	 * @return
	 * @throws SQLException
	 */
	private String getModoPago(int reservaID) throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();

		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();
		
		for(Reserva res : r){
			if(res.getReservaID() == reservaID){
				return res.getModoPago();
			}
		}
		
		return "";
	}
	
	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	private void limpiarDescripcion() {
		taDescripcion.setText("");
	}
	
	/**
	 * Completa la lista de instalaciones introducidas en la comboBox
	 * 
	 * @throws SQLException
	 */
	private String[] fillInstalaciones() throws SQLException {
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();

		// Pasarlos a la clase actual
		ArrayList<Instalacion> i = new ArrayList<Instalacion>();
		i = parser.getInstalaciones();

		// Guardar el nombre
		String[] resultado = new String[i.size()];
		int j = 0;
		for (Instalacion ins : i) {
			resultado[j] = ins.getInstalacion_nombre();
			j++;
		}

		return resultado;
	}

	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	private void limpiarTabla() {
		for (int i = 1; i < table.getRowCount(); i++) {
			table.clearSelection();
			table.setValueAt("", i, 1);
		}
	}

	/**
	 * Rellena la tabla con las ids de las reservas para el dia e instalación seleccionados
	 * @throws SQLException
	 */
	private void fillTabla() throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();

		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();
		
		//Comprobar dia e instalacion, y poner en la tabla
		int insID = getInstalacionIDFromNombre((String) cbInstalacion.getSelectedItem());
		for (Reserva res : r) {
			if(res.getInstalacionID() == insID){
				if(getDia(new Timestamp(dateChooser.getDate().getTime())) == getDia(res.getHoraComienzo())){
					if(getMes(new Timestamp(dateChooser.getDate().getTime())) == getMes(res.getHoraComienzo())){
						if(getYear(new Timestamp(dateChooser.getDate().getTime())) == getYear(res.getHoraComienzo())){
							if(getHora(res.getHoraFinal()) - getHora(res.getHoraComienzo()) > 1){
								table.setValueAt(res.getReservaID(), getHora(res.getHoraComienzo())+1, 1);
								table.setValueAt(res.getReservaID(), getHora(res.getHoraComienzo())+2, 1);
							}else{
								table.setValueAt(res.getReservaID(), getHora(res.getHoraComienzo())+1, 1);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Consigue la id de una instalacion dado su nombre
	 * @param nombre
	 * @return
	 * @throws SQLException
	 */
	private int getInstalacionIDFromNombre(String nombre) throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();

		// Pasarlos a la clase actual
		ArrayList<Instalacion> i = new ArrayList<Instalacion>();
		i = parser.getInstalaciones();

		// Conseguir la id
		for (Instalacion ins : i) {
			if(ins.getInstalacion_nombre().equals(nombre)){
				return ins.getInstalacionID();
			}
		}

		return -1;
	}
	
	/**
	 * Comprueba si la reserva fue creada por Admin
	 * @param value
	 * @param row
	 * @param col
	 * @return
	 * @throws SQLException
	 */
	private int itsAdmin(String value, int row, int col) throws SQLException {
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		
		if(row==0 || col == 0 || value == null || value.contains(":") || value.equals(""))
			return 2;
		
		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();
		
		// Ver si es admin
		for(Reserva res : r){
			if(String.valueOf(res.getReservaID()).equals(value))
				if(res.getSocioID().equals("admin"))
					return 0;
		}
		return 1;
	}
}
