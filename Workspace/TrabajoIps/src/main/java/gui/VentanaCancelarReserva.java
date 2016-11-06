package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logic.Reserva;
import db.Parser;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.awt.event.ActionEvent;

public class VentanaCancelarReserva extends JDialog {

	private static final long serialVersionUID = -7793623677539570777L;
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
	private String modoPago;
	private int precio;
	private VentanaCalendar vc;
	//---------------------------------------
	
	/**
	 * Create the dialog.
	 */
	public VentanaCancelarReserva(VentanaCalendar vc, String socioID, Timestamp fecha) {
		super(vc,true);
		this.socioID = socioID;
		this.fecha = fecha;
		this.vc=vc;
		setTitle("Ventana cancelar reserva");
		setBounds(100, 100, 891, 620);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblCanceleSuReserva = new JLabel("Cancele su reserva:");
			lblCanceleSuReserva.setFont(new Font("Tw Cen MT", Font.BOLD, 38));
			lblCanceleSuReserva.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblCanceleSuReserva, BorderLayout.NORTH);
		}
		{
			JPanel pnCentral = new JPanel();
			contentPanel.add(pnCentral, BorderLayout.CENTER);
			GridBagLayout gbl_pnCentral = new GridBagLayout();
			gbl_pnCentral.columnWidths = new int[]{214, 214, 214, 214, 0};
			gbl_pnCentral.rowHeights = new int[]{35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 0};
			gbl_pnCentral.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_pnCentral.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
								boolean res = cancelarReserva();
								if(res){
									JOptionPane.showMessageDialog(null, "Reserva eliminada");
									vc.limpiarTabla();
									vc.actualizar();
									dispose();
								}
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
	private boolean cancelarReserva() throws SQLException{
		Timestamp tt = new Timestamp(fecha.getTime());
		tt.setHours((int)spHoraComienzo.getValue());
		horaComienzo = tt;
		
		fecha.setHours((int)spHoraSalida.getValue());
		horaFinal = fecha;
		
		parser = new Parser();
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		int id = parser.getReservas().size();
		
		instalacionID = cbInstalaciones.getSelectedIndex();
		
		Timestamp nulo = null;
		
		reserva = new Reserva(id, this.socioID, this.instalacionID, this.horaComienzo, this.horaFinal, nulo, nulo, this.modoPago, false, this.precio);
		
		boolean result = reserva.cancelarReserva(socioID, this.horaComienzo, this.horaFinal);
		parser.removeArrays();
		parser.fillArrays();
		vc.llenarTabla(vc.getInstalacionFromNombre(String.valueOf(vc.getComboBoxInstalacion().getSelectedItem())));
		return result;
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
