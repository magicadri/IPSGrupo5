package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import db.Database;
import db.Parser;
import logic.Reserva;

import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class VentanaCrearActividad extends JFrame {

	private JPanel contentPane;
	private JLabel lblTitulo;
	private JPanel panelSpinners;
	private JPanel panelBotones;
	private JDateChooser dateChooser;
	private JLabel lblComienzo;
	private DefaultComboBoxModel cmodel;
	private JLabel lblHoraComienzo;
	private JLabel lblHoraFinal;
	private JSpinner spComienzo;
	private JSpinner spFinal;
	private JButton btnCrear;
	private JButton btnCancelar;
	private Parser parser;
	private JLabel lblInstalacion;
	private JComboBox comboBoxInstalacion;
	private JLabel lblSemanas;
	private JSpinner spSemanas;
	private JLabel lblNombre;
	private VentanaCrearActividad ref = this;
	private JTextField textFieldNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCrearActividad frame = new VentanaCrearActividad();
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
	public VentanaCrearActividad() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		parser = new Parser();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getLblTitulo(), BorderLayout.NORTH);
		contentPane.add(getPanelSpinners(), BorderLayout.CENTER);
		contentPane.add(getPanelBotones(), BorderLayout.SOUTH);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setBounds(440, 330, 95, 20);
		}
		return dateChooser;
	}

	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Crear actividad");
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		}
		return lblTitulo;
	}
	private JPanel getPanelSpinners() {
		if (panelSpinners == null) {
			panelSpinners = new JPanel();
			GridBagLayout gbl_panelSpinners = new GridBagLayout();
			gbl_panelSpinners.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
			panelSpinners.setLayout(gbl_panelSpinners);
			GridBagConstraints gbc_lblComienzo = new GridBagConstraints();
			gbc_lblComienzo.anchor = GridBagConstraints.WEST;
			gbc_lblComienzo.insets = new Insets(0, 0, 5, 5);
			gbc_lblComienzo.gridx = 2;
			gbc_lblComienzo.gridy = 0;
			panelSpinners.add(getLblComienzo(), gbc_lblComienzo);
			GridBagConstraints gbc_dateChooser = new GridBagConstraints();
			gbc_dateChooser.insets = new Insets(0, 0, 5, 0);
			gbc_dateChooser.gridx = 4;
			gbc_dateChooser.gridy = 0;
			panelSpinners.add(getDateChooser(), gbc_dateChooser);
			GridBagConstraints gbc_lblHoraComienzo = new GridBagConstraints();
			gbc_lblHoraComienzo.anchor = GridBagConstraints.WEST;
			gbc_lblHoraComienzo.insets = new Insets(0, 0, 5, 5);
			gbc_lblHoraComienzo.gridx = 2;
			gbc_lblHoraComienzo.gridy = 1;
			panelSpinners.add(getLblHoraComienzo(), gbc_lblHoraComienzo);
			GridBagConstraints gbc_spComienzo = new GridBagConstraints();
			gbc_spComienzo.insets = new Insets(0, 0, 5, 0);
			gbc_spComienzo.gridx = 4;
			gbc_spComienzo.gridy = 1;
			panelSpinners.add(getSpComienzo(), gbc_spComienzo);
			GridBagConstraints gbc_lblHoraFinal = new GridBagConstraints();
			gbc_lblHoraFinal.anchor = GridBagConstraints.WEST;
			gbc_lblHoraFinal.insets = new Insets(0, 0, 5, 5);
			gbc_lblHoraFinal.gridx = 2;
			gbc_lblHoraFinal.gridy = 2;
			panelSpinners.add(getLblHoraFinal(), gbc_lblHoraFinal);
			GridBagConstraints gbc_spFinal = new GridBagConstraints();
			gbc_spFinal.insets = new Insets(0, 0, 5, 0);
			gbc_spFinal.gridx = 4;
			gbc_spFinal.gridy = 2;
			panelSpinners.add(getSpFinal(), gbc_spFinal);
			GridBagConstraints gbc_comboBoxInstalacion = new GridBagConstraints();
			gbc_comboBoxInstalacion.insets = new Insets(0, 0, 5, 0);
			gbc_comboBoxInstalacion.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxInstalacion.gridx = 4;
			gbc_comboBoxInstalacion.gridy = 4;
			panelSpinners.add(getComboBoxInstalacion(), gbc_comboBoxInstalacion);
			GridBagConstraints gbc_lblSemanas = new GridBagConstraints();
			gbc_lblSemanas.anchor = GridBagConstraints.WEST;
			gbc_lblSemanas.insets = new Insets(0, 0, 5, 5);
			gbc_lblSemanas.gridx = 2;
			gbc_lblSemanas.gridy = 3;
			panelSpinners.add(getLblSemanas(), gbc_lblSemanas);
			GridBagConstraints gbc_spSemanas = new GridBagConstraints();
			gbc_spSemanas.insets = new Insets(0, 0, 5, 0);
			gbc_spSemanas.gridx = 4;
			gbc_spSemanas.gridy = 3;
			panelSpinners.add(getSpSemanas(), gbc_spSemanas);
			GridBagConstraints gbc_lblInstalacion = new GridBagConstraints();
			gbc_lblInstalacion.anchor = GridBagConstraints.WEST;
			gbc_lblInstalacion.insets = new Insets(0, 0, 5, 5);
			gbc_lblInstalacion.gridx = 2;
			gbc_lblInstalacion.gridy = 4;
			panelSpinners.add(getLblInstalacion(), gbc_lblInstalacion);
			GridBagConstraints gbc_lblNombre = new GridBagConstraints();
			gbc_lblNombre.anchor = GridBagConstraints.WEST;
			gbc_lblNombre.insets = new Insets(0, 0, 0, 5);
			gbc_lblNombre.gridx = 2;
			gbc_lblNombre.gridy = 5;
			panelSpinners.add(getLblNombre(), gbc_lblNombre);
			GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
			gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldNombre.gridx = 4;
			gbc_textFieldNombre.gridy = 5;
			panelSpinners.add(getTextFieldNombre(), gbc_textFieldNombre);
		}
		return panelSpinners;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			GridBagLayout gbl_panelBotones = new GridBagLayout();
			gbl_panelBotones.columnWidths = new int[]{167, 89, 0, 0};
			gbl_panelBotones.rowHeights = new int[]{23, 0};
			gbl_panelBotones.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panelBotones.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panelBotones.setLayout(gbl_panelBotones);
			GridBagConstraints gbc_btnCrear = new GridBagConstraints();
			gbc_btnCrear.insets = new Insets(0, 0, 0, 5);
			gbc_btnCrear.anchor = GridBagConstraints.NORTH;
			gbc_btnCrear.gridx = 0;
			gbc_btnCrear.gridy = 0;
			panelBotones.add(getBtnCrear(), gbc_btnCrear);
			GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
			gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
			gbc_btnCancelar.gridx = 1;
			gbc_btnCancelar.gridy = 0;
			panelBotones.add(getBtnCancelar(), gbc_btnCancelar);
		}
		return panelBotones;
	}
	private JLabel getLblComienzo() {
		if (lblComienzo == null) {
			lblComienzo = new JLabel("Fecha comienzo :");
		}
		return lblComienzo;
	}
	private JLabel getLblHoraComienzo() {
		if (lblHoraComienzo == null) {
			lblHoraComienzo = new JLabel("Hora comienzo:");
		}
		return lblHoraComienzo;
	}
	private JLabel getLblHoraFinal() {
		if (lblHoraFinal == null) {
			lblHoraFinal = new JLabel("Hora final:");
		}
		return lblHoraFinal;
	}
	private JSpinner getSpComienzo() {
		if (spComienzo == null) {
			spComienzo = new JSpinner();
			spComienzo.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		}
		return spComienzo;
	}
	private JSpinner getSpFinal() {
		if (spFinal == null) {
			spFinal = new JSpinner();
			spFinal.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		}
		return spFinal;
	}
	private JButton getBtnCrear() {
		if (btnCrear == null) {
			btnCrear = new JButton("Crear");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(! textFieldNombre.getText().equals("") )
					{
						if(dateChooser.getDate().after(new Date()) && comboBoxInstalacion.getSelectedIndex()!=0 && (int)spComienzo.getModel().getValue() <  (int)spFinal.getModel().getValue())
						{
							try {
								crearActividad();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else 
					{
						JOptionPane jo = new JOptionPane();
						jo.showMessageDialog(ref, "Nombre de actividad incorrecto");
					}
				}
			});
		}
		return btnCrear;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaCrearActividad.this.dispose();
				}
			});
		}
		return btnCancelar;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalación");
		}
		return lblInstalacion;
	}
	
	
	
	private JComboBox getComboBoxInstalacion() {
		String[] modelItems = new String[] { "Elija instalacion:", "Piscina", "Cancha fútbol", "Pista tenis" };
		cmodel = new DefaultComboBoxModel(modelItems);
		if (comboBoxInstalacion == null) {
			comboBoxInstalacion = new JComboBox();
			comboBoxInstalacion.setBounds(10, 105, 157, 20);
			comboBoxInstalacion.setModel(cmodel);
		}
		return comboBoxInstalacion;
	}
	private JLabel getLblSemanas() {
		if (lblSemanas == null) {
			lblSemanas = new JLabel("Semanas:");
		}
		return lblSemanas;
	}
	private JSpinner getSpSemanas() {
		if (spSemanas == null) {
			spSemanas = new JSpinner();
			spSemanas.setModel(new SpinnerNumberModel(1, 1, 8, 1));
		}
		return spSemanas;
	}
	
	
	private void crearActividad() throws SQLException
	{
		int res=0;
		Database.getInstance().getC().createStatement().execute("INSERT INTO Actividad (actividadID, instalacionID, actividad_nombre, semanas) VALUES (" 
				+ parser.getActividades().size()+1 + "," + comboBoxInstalacion.getSelectedIndex() + ",'" + textFieldNombre.getText() + "'," + (int) spSemanas.getValue()  + ");");
		for(int i=0;i<(int)spSemanas.getValue()*7;i=i+7)
		{
			res = hacerReserva(aumentarFecha(dateChooser.getDate(),i));
			Database.getInstance().getC().createStatement().execute("INSERT INTO ReservaActividad (actividadID, reservaID) VALUES (" 
					+ parser.getActividades().size()+1 + "," + res + ");");
		}
		
		
	}
	
	private Date aumentarFecha(Date date, int cantidad)
	{
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, cantidad); //Add a week
        return cal.getTime();
	}


/**
 * Hace la reserva seleccionada por el usuario
 * @throws SQLException 
 */
@SuppressWarnings("deprecation")
private int hacerReserva(Date fecha) throws SQLException{
	Timestamp tt = new Timestamp(fecha.getTime());
	tt.setHours((int)spComienzo.getValue());
	Timestamp horaComienzo = tt;
	Timestamp hf = new Timestamp(fecha.getTime());
	hf.setHours((int)spFinal.getValue());
	Timestamp horaFinal = hf;
	try {
		parser.fillArrays();
	} catch (SQLException e) {
		e.printStackTrace();
	} 
	
	int id = parser.getReservas().size()+1;
	
	int instalacionID = comboBoxInstalacion.getSelectedIndex();
	
	Timestamp nulo = null;
	
	Reserva reserva = new Reserva(id, "admin", instalacionID, horaComienzo, horaFinal, null, null, "Cuota", false, 0);
	
	reserva.hacerReservaAdmin("admin", instalacionID, id , horaComienzo, horaFinal, null, null, "Cuota" ,false, 0);
	parser.fillArrays();
	
	return reserva.getReservaID();
	
}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre de la actividad:");
		}
		return lblNombre;
	}
	private JTextField getTextFieldNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.setColumns(10);
		}
		return textFieldNombre;
	}
}