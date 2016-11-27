package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import db.Database;
import db.Parser;
import logic.Actividad;
import logic.Instalacion;
import logic.Reserva;
import logic.ReservaActividad;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextArea;

public class VentanaCalendarMejorada extends JDialog {

	private static final long serialVersionUID = -4412349867500367960L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCalendarioParaSocios;
	private JLabel lblFecha;
	private JLabel lblInstalacion;
	private JPanel pnDatos;
	private JComboBox<String> cbInstalacion;
	private String[] instalaciones;
	private JDateChooser dateChooser;
	private JButton btComprobar;
	private JPanel pnTabla;
	private JPanel pnAcciones;
	private JTable table;
	private Parser parser;
	private JButton btnReservar;
	private JButton btnCancelarReserva;
	private JButton btnMisReservas;
	private JButton btnMisActividades;
	private JButton btnReservasFuturas;
	private JPanel pnAccionesInterno;
	private VentanaCalendarMejorada ref = this;
	private String socioID;
	private JTextArea textArea;
	private JButton btnApuntarme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaCalendarMejorada dialog = new VentanaCalendarMejorada("adri");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCalendarMejorada(String socioID) {
		parser = new Parser();
		setSocioID(socioID);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Calendario para socios");
		setBounds(100, 100, 1059, 660);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblCalendarioParaSocios());
		contentPanel.add(getPnDatos());
		contentPanel.add(getPnTabla());
		contentPanel.add(getPnAcciones());
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Descripcion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(847, 72, 161, 448);
		contentPanel.add(panel);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 27, 141, 397);
		panel.add(textArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
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
	
	/**
	 * Limpia los valores de la tabla en la columna de las reservas
	 */
	private void limpiarDescripcion() {
		textArea.setText("");
	}

	private JLabel getLblCalendarioParaSocios() {
		if (lblCalendarioParaSocios == null) {
			lblCalendarioParaSocios = new JLabel("Calendario para socios");
			lblCalendarioParaSocios.setFont(new Font("Rockwell", Font.PLAIN, 46));
			lblCalendarioParaSocios.setHorizontalAlignment(SwingConstants.CENTER);
			lblCalendarioParaSocios.setBounds(10, 11, 742, 57);
		}
		return lblCalendarioParaSocios;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(12, 98, 70, 31);
			lblFecha.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblFecha;
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n:");
			lblInstalacion.setBounds(0, 42, 82, 20);
			lblInstalacion.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblInstalacion;
	}

	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setBackground(Color.WHITE);
			pnDatos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnDatos.setBounds(28, 72, 232, 390);
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
			cbInstalacion.setBounds(92, 37, 123, 31);
		}
		return cbInstalacion;
	}

	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setBounds(92, 98, 123, 31);
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
						try {
							fillTabla();
						} catch (SQLException e1) {
							System.err.println("[...ERROR AL RELLENAR LA TABLA EN LA CLASE VENTANAOCUPACIONADMINMEJORADA...]");
						}
					}
				}
			});
			btComprobar.setBounds(100, 348, 115, 29);
		}
		return btComprobar;
	}

	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setBackground(Color.WHITE);
			pnTabla.setBorder(new TitledBorder(null, "Tabla", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnTabla.setBounds(292, 72, 535, 448);
			pnTabla.setLayout(null);
			pnTabla.add(getTable());
		}
		return pnTabla;
	}

	private JPanel getPnAcciones() {
		if (pnAcciones == null) {
			pnAcciones = new JPanel();
			pnAcciones.setBackground(Color.WHITE);
			pnAcciones.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnAcciones.setBounds(28, 538, 912, 57);
			pnAcciones.setLayout(new GridLayout(0, 1, 0, 0));
			pnAcciones.add(getPnAccionesInterno());
		}
		return pnAcciones;
	}
	
	class SharedListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	        lsm.setSelectionMode(1);
	       
	    }
	}
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable(){
				
				private static final long serialVersionUID = -2655951525426742341L;
				
				/**
				 * Necesario para los colores
				 */
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					Component comp = super.prepareRenderer(renderer, row, col);
					
					if(dateChooser.getDate()==null)
						return comp;
					
					//Permite seleccionar mas de una linea
					if(isCellSelected(row, col)){           
			            comp.setBackground(Color.lightGray);
			            return comp;             
			        }
					
					//Verde propias, Rojo ajenas
					String value = String.valueOf(getModel().getValueAt(row, col));
					try {
						
						if (itsAdmin(value,row,col) == 1) {
							comp.setBackground(Color.green);
							
						} else if (itsAdmin(value,row,col) == 0) {
							comp.setBackground(Color.red);
						} else
							comp.setBackground(Color.WHITE);
					} catch (SQLException e) {
						System.err.println("[...ERROR AL PINTAR VALORES DE LA TABLA EN LA CLASE VENTANAOCUPACIONADMINMEJORADA...]");
					}
					
					return comp;
				}
			};
			table.setCellSelectionEnabled(true);
			Object[] nombreColumnas = new Object[2];
			nombreColumnas[0] = "Hora";
			nombreColumnas[1] = "Reserva";
			DataTableModel modeloTabla = new DataTableModel(new Object[][] { { "Horas", "Reserva","Actividad" }, { "00:00", null, null },
					{ "01:00", null, null }, { "02:00", null, null }, { "03:00", null, null }, { "04:00", null, null }, { "05:00", null, null },
					{ "06:00", null, null }, { "07:00", null, null }, { "08:00", null, null }, { "09:00", null, null }, { "10:00", null, null },
					{ "11:00", null, null }, { "12:00", null, null }, { "13:00", null, null }, { "14:00", null, null }, { "15:00", null, null },
					{ "16:00", null, null }, { "17:00", null, null }, { "18:00", null, null }, { "19:00", null, null }, { "20:00", null, null },
					{ "21:00", null, null }, { "22:00", null, null }, { "23:00", null, null }, },
					new String[] { "Horas", "Disponibilidad","Actividad" });
			table.setModel(modeloTabla);
			table.setBounds(23, 22, 491, 415);	
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent ev) {
					if(table.getSelectedColumn()!=-1 && table.getSelectedRow()!=-1 && table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn())!="" && table.getSelectedRow()!=0
							&& table.getSelectedColumn()!=0 && table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn())!=null){
						// Pone en la descripcion el valor de la celda seleccionada
						try {
							textArea.setText((String) table.getModel().getValueAt(table.getSelectedRow(), 0) + " - Reserva id: "
								+ table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()) + " -\nModo de pago: "
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
	
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					//Coger metodo de pago
					Object[] options = { "Cuota", "Efectivo"};
					int n = JOptionPane.showOptionDialog(ref, "Seleccione un método de pago", "Modo de pago", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					int[] selecciones = table.getSelectedRows();
					if(ocupado()){
						return;
					}
					if(selecciones.length > 2){
						JOptionPane.showMessageDialog(ref, "No puedes seleccionar más de dos horas para realizar una reserva", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(selecciones.length == 2){
						try {
							hacerReserva(selecciones[0], selecciones[1], options[n]);
						} catch (SQLException e1) {
							
						}
					}else if(selecciones.length == 1){
						try {
							hacerReserva(selecciones[0], -1, options[n]);
						} catch (SQLException e1) {
							
						}
					}else{
						JOptionPane.showMessageDialog(ref, "Proceso de reserva cancelado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
				}

				/**
				 * Si ya está pintada la tabla, no se puede poner nada encima
				 * @return true si está ocupada, false si no
				 */
				private boolean ocupado() {
					for(int i : table.getSelectedRows()){
						if(table.getValueAt(i, 1) !=""){
							JOptionPane.showMessageDialog(ref, "No puede reservar encima de otra reserva");
							return true;
						}
					}
					return false;
				}
			});
		}
		return btnReservar;
	}
	private JButton getBtnCancelarReserva() {
		if (btnCancelarReserva == null) {
			btnCancelarReserva = new JButton("Cancelar reserva");
			btnCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int[] selecciones = table.getSelectedRows();
					boolean result = false;
					if(selecciones.length > 2){
						JOptionPane.showMessageDialog(ref, "No puedes seleccionar más de dos horas para realizar una reserva", "Error", JOptionPane.ERROR_MESSAGE);
					}else if(selecciones.length == 2){
						try {
							result = cancelarReserva(selecciones[0], selecciones[1]);
						} catch (SQLException e1) {
							
						}
					}else if(selecciones.length == 1){
						try {
							result = cancelarReserva(selecciones[0], -1);
						} catch (SQLException e1) {
							
						}
					}else{
						JOptionPane.showMessageDialog(ref, "Proceso de cancelacion erroneo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
					if(result){
						JOptionPane.showMessageDialog(ref, "Reserva cancelada con exito", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnCancelarReserva;
	}
	private JButton getBtnMisReservas() {
		if (btnMisReservas == null) {
			btnMisReservas = new JButton("Mis reservas");
			btnMisReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MisReservas ms = new MisReservas(getSocioID());
					ms.setVisible(true);
				}
			});
		}
		return btnMisReservas;
	}
	private JButton getBtnMisActividades() {
		if (btnMisActividades == null) {
			btnMisActividades = new JButton("Mis actividades");
			btnMisActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MisActividades MA = new MisActividades(getSocioID());
					MA.setVisible(true);
				}
			});
		}
		return btnMisActividades;
	}
	private JButton getBtnReservasFuturas() {
		if (btnReservasFuturas == null) {
			btnReservasFuturas = new JButton("Controlar gastos");
			btnReservasFuturas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaReservasFuturas RF = new VentanaReservasFuturas(ref, getSocioID());
					RF.setVisible(true);
				}
			});
		}
		return btnReservasFuturas;
	}
	private JPanel getPnAccionesInterno() {
		if (pnAccionesInterno == null) {
			pnAccionesInterno = new JPanel();
			pnAccionesInterno.setLayout(new GridLayout(1, 0, 0, 0));
			pnAccionesInterno.add(getBtnReservar());
			pnAccionesInterno.add(getBtnCancelarReserva());
			pnAccionesInterno.add(getBtnMisReservas());
			pnAccionesInterno.add(getBtnMisActividades());
			pnAccionesInterno.add(getBtnReservasFuturas());
			pnAccionesInterno.add(getBtnApuntarme());
		}
		return pnAccionesInterno;
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
	 * Hace la reserva seleccionada por el usuario
	 * @throws SQLException 
	 */
	@SuppressWarnings("deprecation")
	private boolean cancelarReserva(int horaComienzo, int horaFinal) throws SQLException{
				
		Reserva reserva = new Reserva();
		Date date = dateChooser.getDate();
		date.setHours(horaComienzo-1);
		Date date2 = dateChooser.getDate();
		date2.setHours(horaComienzo);
		Date date3 = dateChooser.getDate();
		date3.setHours(horaFinal);
		
		boolean result;
		if(horaFinal != -1)
			result = reserva.cancelarReserva(getSocioID(), new Timestamp(date.getTime()), new Timestamp(date3.getTime()));
		else
			result = reserva.cancelarReserva(getSocioID(), new Timestamp(date.getTime()), new Timestamp(date2.getTime()));
		parser.removeArrays();
		parser.fillArrays();
		
		limpiarTabla();
		fillTabla();
		
		return result;
	}
	
	/**
	 * Hace la reserva seleccionada por el usuario
	 * @throws SQLException 
	 */
	@SuppressWarnings("deprecation")
	private void hacerReserva(int horaComienzo, int horaComienzo2, Object modoPago) throws SQLException{
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		
		
		Date date = dateChooser.getDate();
		date.setHours(horaComienzo-1);
		Date date2 = dateChooser.getDate();
		date2.setHours(horaComienzo);
		Date date3 = dateChooser.getDate();
		date3.setHours(horaComienzo2);

		// Crear y guardar la reserva
		Reserva reserva = new Reserva();
		if(horaComienzo2 != -1)
			reserva.hacerReserva(getSocioID(), getInstalacionIDFromNombre((String) cbInstalacion.getSelectedItem()), parser.getReservas().size()+1,
				new Timestamp(date.getTime()), new Timestamp(date3.getTime()), null, null, (String) modoPago,
				false, getPrecioFromNombre((String) cbInstalacion.getSelectedItem()));
		else
			reserva.hacerReserva(getSocioID(), getInstalacionIDFromNombre((String) cbInstalacion.getSelectedItem()), parser.getReservas().size()+1,
					new Timestamp(date.getTime()), new Timestamp(date2.getTime()), null, null, (String) modoPago,
					false, getPrecioFromNombre((String) cbInstalacion.getSelectedItem()));
		// Actualizar datos de la base
		parser.removeArrays();
		parser.fillArrays();
		
		// Rellenar la tabla		
		limpiarTabla();
		fillTabla();
	}
	
	public String getSocioID() {
		return socioID;
	}

	public void setSocioID(String socioID) {
		this.socioID = socioID;
	}
	
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
			table.setValueAt("", i, 2);

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
		
		
		for(ReservaActividad reservaActividad : parser.getReservasactividad()){
		for(Actividad actividad : parser.getActividades()){
		for(Reserva reserva : parser.getReservas()){
			if(reservaActividad.getReservaID() == reserva.getReservaID())
			table.setValueAt(actividad.getActividad_nombre(),getHora(reserva.getHoraComienzo())+1, 2);
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
				if(res.getSocioID().equals("admin") || !res.getSocioID().equals(getSocioID()))
					return 0;
		}
		return 1;
	}
	
	/**
	 * Da el precio de una reserva dado el nombre de una instalacion
	 * @param nombre
	 * @return
	 * @throws SQLException
	 */
	private int getPrecioFromNombre(String nombre) throws SQLException{ 
		int id = getInstalacionIDFromNombre(nombre);
		
		// Pasarlos a la clase actual
		ArrayList<Reserva> r = new ArrayList<Reserva>();
		r = parser.getReservas();

		// Ver si es admin
		for (Reserva res : r) {
			if (res.getInstalacionID() == id)
				return res.getPrecio();
		}
		return -1;
	}
	private JButton getBtnApuntarme() {
		if (btnApuntarme == null) {
			btnApuntarme = new JButton("Apuntarme actividad");
			btnApuntarme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Reserva actividad como socio DB 
					String nombre = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
					for(Actividad actividad : parser.getActividades()){
						for(ReservaActividad ractividad : parser.getReservasactividad()){
							if(nombre.equals(actividad.getActividad_nombre())){
					try {
						Database.getInstance().getC().createStatement().execute(
								"INSERT INTO SocioActividad (SocioID, ActividadID, ReservaID, Presentado, NoSocioID) VALUES ('"
										+ socioID + "'," + ractividad.getActividadID() + ","
										+ ractividad.getReservaID() + ","
										+ null + "," + null + ");");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
							}
					}
					}
				}
			});
		}
		return btnApuntarme;
	}
}
