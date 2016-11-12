package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import db.Parser;
import logic.Reserva;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class VentanaReservaAdmin extends JDialog {

	private static final long serialVersionUID = -524388848704387421L;
	private Reserva reserva;
	private Parser parser;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> cbInstalaciones;
	private JSpinner spHoraComienzo;
	private JSpinner spHoraSalida;
	private JLabel lblPrecioShow;
	private String[] opcionesInstalacion;
	private SpinnerNumberModel spnmHoraComienzo;
	private SpinnerNumberModel spnmHoraFinal;
	private Timestamp fecha;
	
	//----------------------------------------
	private String socioID;
	private int instalacionID;
	private Timestamp horaComienzo;
	private Timestamp horaFinal;
	private JDateChooser dateChooser;
	private String modoPago;
	private int precio;
	//---------------------------------------
	
	/**
	 * Create the dialog.
	 */
	public VentanaReservaAdmin() {
		this.socioID = socioID;
		this.fecha = fecha;
		setTitle("Ventana reserva");
		setBounds(100, 100, 891, 620);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblRealiceSuReserva = new JLabel("Realice su reserva:");
			lblRealiceSuReserva.setFont(new Font("Tw Cen MT", Font.BOLD, 38));
			lblRealiceSuReserva.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblRealiceSuReserva, BorderLayout.NORTH);
		}
		{
			JPanel pnCentral = new JPanel();
			contentPanel.add(pnCentral, BorderLayout.CENTER);
			GridBagLayout gbl_pnCentral = new GridBagLayout();
			gbl_pnCentral.columnWidths = new int[]{214, 214, 214, 214, 0};
			gbl_pnCentral.rowHeights = new int[]{35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 0};
			gbl_pnCentral.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_pnCentral.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			pnCentral.setLayout(gbl_pnCentral);
			{
				JLabel lblSeleccioneInstalacion = new JLabel("Seleccione instalacion: ");
				lblSeleccioneInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 25));
				GridBagConstraints gbc_lblSeleccioneInstalacion = new GridBagConstraints();
				gbc_lblSeleccioneInstalacion.fill = GridBagConstraints.BOTH;
				gbc_lblSeleccioneInstalacion.insets = new Insets(0, 0, 5, 5);
				gbc_lblSeleccioneInstalacion.gridx = 1;
				gbc_lblSeleccioneInstalacion.gridy = 3;
				pnCentral.add(lblSeleccioneInstalacion, gbc_lblSeleccioneInstalacion);
			}
			{
				cbInstalaciones = new JComboBox<String>();
				cbInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 25));
				opcionesInstalacion = new String[] {"", "Piscina", "Cancha fútbol", "Pista tenis"};
				DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>(opcionesInstalacion);
				cbInstalaciones.setModel(modelo);
				GridBagConstraints gbc_cbInstalaciones = new GridBagConstraints();
				gbc_cbInstalaciones.fill = GridBagConstraints.BOTH;
				gbc_cbInstalaciones.insets = new Insets(0, 0, 5, 5);
				gbc_cbInstalaciones.gridx = 2;
				gbc_cbInstalaciones.gridy = 3;
				pnCentral.add(cbInstalaciones, gbc_cbInstalaciones);
			}
			{
				JLabel lblHoraComienzo = new JLabel("Hora de comienzo:");
				lblHoraComienzo.setFont(new Font("Tahoma", Font.PLAIN, 25));
				GridBagConstraints gbc_lblHoraComienzo = new GridBagConstraints();
				gbc_lblHoraComienzo.fill = GridBagConstraints.BOTH;
				gbc_lblHoraComienzo.insets = new Insets(0, 0, 5, 5);
				gbc_lblHoraComienzo.gridx = 1;
				gbc_lblHoraComienzo.gridy = 5;
				pnCentral.add(lblHoraComienzo, gbc_lblHoraComienzo);
			}
			{
				spHoraComienzo = new JSpinner();
				spHoraComienzo.setFont(new Font("Tahoma", Font.PLAIN, 25));
				spnmHoraComienzo = new SpinnerNumberModel(0, 0, 23, 1);
				spHoraComienzo.setModel(spnmHoraComienzo);
				GridBagConstraints gbc_spHoraComienzo = new GridBagConstraints();
				gbc_spHoraComienzo.fill = GridBagConstraints.BOTH;
				gbc_spHoraComienzo.insets = new Insets(0, 0, 5, 5);
				gbc_spHoraComienzo.gridx = 2;
				gbc_spHoraComienzo.gridy = 5;
				pnCentral.add(spHoraComienzo, gbc_spHoraComienzo);
			}
			{
				JLabel lblHoraFinal = new JLabel("Hora de salida:");
				lblHoraFinal.setFont(new Font("Tahoma", Font.PLAIN, 25));
				GridBagConstraints gbc_lblHoraFinal = new GridBagConstraints();
				gbc_lblHoraFinal.fill = GridBagConstraints.BOTH;
				gbc_lblHoraFinal.insets = new Insets(0, 0, 5, 5);
				gbc_lblHoraFinal.gridx = 1;
				gbc_lblHoraFinal.gridy = 7;
				pnCentral.add(lblHoraFinal, gbc_lblHoraFinal);
			}
			{
				spHoraSalida = new JSpinner();
				spHoraSalida.setFont(new Font("Tahoma", Font.PLAIN, 25));
				spnmHoraFinal = new SpinnerNumberModel(0, 0, 23, 1);
				spHoraSalida.setModel(spnmHoraFinal);
				GridBagConstraints gbc_spHoraSalida = new GridBagConstraints();
				gbc_spHoraSalida.fill = GridBagConstraints.BOTH;
				gbc_spHoraSalida.insets = new Insets(0, 0, 5, 5);
				gbc_spHoraSalida.gridx = 2;
				gbc_spHoraSalida.gridy = 7;
				pnCentral.add(spHoraSalida, gbc_spHoraSalida);
			}
			{
				JLabel lblFecha = new JLabel("Fecha");
				lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 25));
				GridBagConstraints gbc_lblFecha = new GridBagConstraints();
				gbc_lblFecha.anchor = GridBagConstraints.WEST;
				gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
				gbc_lblFecha.gridx = 1;
				gbc_lblFecha.gridy = 9;
				pnCentral.add(lblFecha, gbc_lblFecha);
			}
			{
				dateChooser = new JDateChooser();
				dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(dateChooser.getDate()!=null)
							fecha = new Timestamp(dateChooser.getDate().getTime());
					}
				});
				GridBagConstraints gbc_dateChooser = new GridBagConstraints();
				gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
				gbc_dateChooser.fill = GridBagConstraints.BOTH;
				gbc_dateChooser.gridx = 2;
				gbc_dateChooser.gridy = 9;
				pnCentral.add(dateChooser, gbc_dateChooser);
			}
			{
				lblPrecioShow = new JLabel("");
				lblPrecioShow.setFont(new Font("Tahoma", Font.PLAIN, 25));
				lblPrecioShow.setEnabled(false);
				GridBagConstraints gbc_lblPrecioShow = new GridBagConstraints();
				gbc_lblPrecioShow.fill = GridBagConstraints.BOTH;
				gbc_lblPrecioShow.insets = new Insets(0, 0, 5, 5);
				gbc_lblPrecioShow.gridx = 2;
				gbc_lblPrecioShow.gridy = 11;
				pnCentral.add(lblPrecioShow, gbc_lblPrecioShow);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						boolean isPosible = hacerComprobaciones();
						if(isPosible){
							try {
								hacerReserva();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							resetearVentana();
						}
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Hace la reserva seleccionada por el usuario
	 * @throws SQLException 
	 */
	@SuppressWarnings("deprecation")
	private void hacerReserva() throws SQLException{
		Timestamp tt = new Timestamp(fecha.getTime());
		tt.setHours((int)spHoraComienzo.getValue());
		tt.setMinutes(0);
		tt.setSeconds(0);
		fecha.setNanos(0);
		horaComienzo = tt;
		
		fecha.setHours((int)spHoraSalida.getValue());
		fecha.setMinutes(0);
		fecha.setSeconds(0);
		fecha.setNanos(0);
		horaFinal = fecha;
		
		modoPago = "Cuota";
		
		precio = 0; //Precio?
		
		parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		int id = parser.getReservas().size()+1;
		
		instalacionID = cbInstalaciones.getSelectedIndex();
		
		Timestamp nulo = null;
		
		reserva = new Reserva(id, "admin", instalacionID, horaComienzo, horaFinal, nulo, nulo, "Cuota", false, this.precio);
		
		reserva.hacerReservaAdmin("admin", instalacionID,  id, horaComienzo, horaFinal, nulo, nulo, "Cuota" ,false, precio);
		parser.fillArrays();
	}
	
	
	/**
	 * Comprueba que el usuario no ha introducido ningun dato erroneo
	 * 
	 * @return true si todos los datos estan bien, false si no lo estan
	 * 
	 * @author David
	 */
	private boolean hacerComprobaciones(){
		boolean resultado = false;
		
		if(cbInstalaciones.getSelectedIndex()==0){
			JOptionPane.showMessageDialog(null, "El campo instalacion no puede estar vacio.");
			return resultado;
		}else if((Integer) spHoraSalida.getModel().getValue() - (Integer) spHoraComienzo.getModel().getValue() < 1){
			JOptionPane.showMessageDialog(null, "La hora de salida no puede ser igual o inferior a la hora de entrada.");
			return resultado;
		} else if (dateChooser.getDate()==null)
		{
			JOptionPane.showMessageDialog(null, "Por favor seleccione una fecha.");
			return resultado;
		}
		return !resultado;
	}
	
	/**
	 * Reinicia la ventana de reserva
	 *  
	 * @author David
	 */
	private void resetearVentana(){
		cbInstalaciones.setSelectedItem(null);
		spHoraComienzo.setValue(spnmHoraComienzo.getMinimum());
		spHoraSalida.setValue(spnmHoraFinal.getMinimum());
		lblPrecioShow.setText("");
	}

}
