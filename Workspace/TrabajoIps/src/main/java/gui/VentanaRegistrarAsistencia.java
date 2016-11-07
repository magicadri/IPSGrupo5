package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import db.Parser;
import logic.Actividad;
import logic.SocioActividad;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

public class VentanaRegistrarAsistencia extends JDialog {

	private static final long serialVersionUID = -6965747340439723814L;
	private final JPanel contentPanel = new JPanel();
	private JButton btRegistrar;
	private JButton btQuitarRegistro;
	private Parser parser;
	private JList<String> listaSocio;
	private JList<String> listaRegistrados;
	private Actividad referencia;

	/**
	 * Create the dialog.
	 */
	public VentanaRegistrarAsistencia() {
		parser = new Parser();
		setTitle("Registrar asistencia");
		setBounds(100, 100, 781, 588);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel pnSuperior = new JPanel();
			contentPanel.add(pnSuperior, BorderLayout.NORTH);
			{
				JLabel lblActividad = new JLabel("Actividad:");
				lblActividad.setFont(new Font("Tahoma", Font.PLAIN, 21));
				pnSuperior.add(lblActividad);
			}
			{
				JComboBox<String> cbActividades = new JComboBox<String>();
				cbActividades.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fillSocios((String)cbActividades.getSelectedItem());
					}
				});
				String[] actividades = fillActividades();
				DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(actividades);
				cbActividades.setModel(dcbm);
				cbActividades.setFont(new Font("Tahoma", Font.PLAIN, 21));
				pnSuperior.add(cbActividades);
			}
		}
		{
			listaSocio = new JList<String>();
			listaSocio.setFont(new Font("Tahoma", Font.PLAIN, 21));
			DefaultListModel<String> modelo = new DefaultListModel<String>();
			
			modelo.addElement("Socios:");
			contentPanel.add(listaSocio, BorderLayout.WEST);
			listaSocio.setModel(modelo);
		}
		{
			listaRegistrados = new JList<String>();
			listaRegistrados.setFont(new Font("Tahoma", Font.PLAIN, 21));
			DefaultListModel<String> modelo = new DefaultListModel<String>();
			
			modelo.addElement("Registrados:");
			listaRegistrados.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			listaRegistrados.setModel(modelo);

			contentPanel.add(listaRegistrados, BorderLayout.EAST);
		}
		{
			JPanel pnCentral = new JPanel();
			contentPanel.add(pnCentral, BorderLayout.CENTER);
			GridBagLayout gbl_pnCentral = new GridBagLayout();
			gbl_pnCentral.columnWidths = new int[] { 565, 0 };
			gbl_pnCentral.rowHeights = new int[] { 145, 0, 0, 145, 0, 0, 145, 0 };
			gbl_pnCentral.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_pnCentral.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			pnCentral.setLayout(gbl_pnCentral);
			{
				btRegistrar = new JButton("->");
				btRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						registrar();
					}
				});
				btRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 21));
			}
			GridBagConstraints gbc_btRegistrar = new GridBagConstraints();
			gbc_btRegistrar.insets = new Insets(0, 0, 5, 0);
			gbc_btRegistrar.gridx = 0;
			gbc_btRegistrar.gridy = 2;
			pnCentral.add(btRegistrar, gbc_btRegistrar);
			{
				JLabel label = new JLabel("");
				GridBagConstraints gbc_label = new GridBagConstraints();
				gbc_label.fill = GridBagConstraints.BOTH;
				gbc_label.insets = new Insets(0, 0, 5, 0);
				gbc_label.gridx = 0;
				gbc_label.gridy = 3;
				pnCentral.add(label, gbc_label);
			}
			{
				btQuitarRegistro = new JButton("<-");
				btQuitarRegistro.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						desregistrar();
					}
				});
				btQuitarRegistro.setFont(new Font("Tahoma", Font.PLAIN, 21));
			}
			GridBagConstraints gbc_btQuitarRegistro = new GridBagConstraints();
			gbc_btQuitarRegistro.insets = new Insets(0, 0, 5, 0);
			gbc_btQuitarRegistro.gridx = 0;
			gbc_btQuitarRegistro.gridy = 4;
			pnCentral.add(btQuitarRegistro, gbc_btQuitarRegistro);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cambiarEstado();
						dispose();
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * Pasa los clientes en registrados a true
	 */
	private void cambiarEstado(){
		DefaultListModel<String> aux = (DefaultListModel<String>) listaRegistrados.getModel();
		for (int i = 1; i<aux.getSize(); i++)
			parser.actualizaRegistro(aux.getElementAt(i), referencia.getActividadID());
	}

	/**
	 * Pasa los usuarios seleccionados a la lista de socios
	 */
	private void desregistrar() {
		List<String> registrados = listaRegistrados.getSelectedValuesList();

		// Cambia de lado
		DefaultListModel<String> aux = (DefaultListModel<String>) listaSocio.getModel();
		for (String p : registrados)
			aux.addElement(p);
		listaSocio.setModel(aux);

		// Borra los viejos
		DefaultListModel<String> aux2 = (DefaultListModel<String>) listaRegistrados.getModel();
		for (String p : registrados)
			aux2.removeElement(p);
		listaRegistrados.setModel(aux2);
	}

	/**
	 * Pasa los usuarios seleccionados a la lista de presentados
	 */
	private void registrar() {
		List<String> presentados = listaSocio.getSelectedValuesList();

		// Cambia de lado
		DefaultListModel<String> aux = (DefaultListModel<String>) listaRegistrados.getModel();
		for (String p : presentados)
			aux.addElement(p);
		listaRegistrados.setModel(aux);

		// Borra los viejos
		DefaultListModel<String> aux2 = (DefaultListModel<String>) listaSocio.getModel();
		for (String p : presentados)
			aux2.removeElement(p);
		listaSocio.setModel(aux2);
	}

	/**
	 * Recoge las actividades de la base de datos
	 */
	private String[] fillActividades() {
		try {
			parser.fillArrays();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		ArrayList<Actividad> actividades = parser.getActividades();
		int size = actividades.size();
		String[] activ = new String[size];

		for (int i = 0; i < size; i++) {
			activ[i] = actividades.get(i).getActividad_nombre();
		}

		return activ;
	}

	/**
	 * Completa la lista de socios
	 * 
	 * @param actividad_nombre
	 */
	private void fillSocios(String actividad_nombre) {
		ArrayList<Actividad> act = parser.getActividades();
		Actividad actual = null;
		for (Actividad a : act) {
			if (a.getActividad_nombre().equals(actividad_nombre)) {
				actual = a;
			}
		}

		if(actual.equals(null))
			return;
		referencia = actual;
		ArrayList<SocioActividad> socact = parser.getSociosactividad();
		for (SocioActividad sa : socact) {
			if (sa.getActividadID() == actual.getActividadID()) {
				addListaSocio(sa.getSocioID());
			}
		}
	}

	private void addListaSocio(String socioId) {
		List<String> presentados = listaSocio.getSelectedValuesList();
		
		DefaultListModel<String> aux = (DefaultListModel<String>) listaSocio.getModel();
		for (String p : presentados)
			aux.addElement(p);
		aux.addElement(socioId);
		listaSocio.setModel(aux);
	}

}
