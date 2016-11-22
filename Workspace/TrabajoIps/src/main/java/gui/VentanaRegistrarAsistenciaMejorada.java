package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import db.Parser;
import logic.Actividad;
import logic.Reserva;
import logic.ReservaActividad;
import logic.SocioActividad;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

public class VentanaRegistrarAsistenciaMejorada extends JDialog {
	public VentanaRegistrarAsistenciaMejorada() {
		parser = new Parser();
		setBounds(100, 100, 589, 527);
		getContentPane().setBackground(Color.WHITE);
		setTitle("Ventana para registrar asistencia");
		getContentPane().setLayout(null);
		getContentPane().add(getLblActividad());
		getContentPane().add(getComboBox());
		getContentPane().add(getScrollIzq());
		getContentPane().add(getScrollDch());
		getContentPane().add(getBtDch());
		getContentPane().add(getButton());
		getContentPane().add(getBtnSalir());
		getContentPane().add(getBtnRegistrar());
		getContentPane().add(getLblNoPresentados());
		getContentPane().add(getLblPresentados());
		getContentPane().add(getPnHorario());
	}

	private static final long serialVersionUID = -6729417827694538682L;
	private JLabel lblActividad;
	private JComboBox<String> comboBox;
	private JScrollPane scrollIzq;
	private JScrollPane scrollDch;
	private JList<String> listaPorRegistrar;
	private JList<String> listaRegistrados;
	private JButton btDch;
	private JButton button;
	private JButton btnSalir;
	private JButton btnRegistrar;
	private Parser parser;
	private JLabel lblNoPresentados;
	private JLabel lblPresentados;
	private JSpinner spDesde;
	private JPanel pnHorario;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JSpinner spHasta;
	private Actividad referencia;

	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad:");
			lblActividad.setHorizontalAlignment(SwingConstants.TRAILING);
			lblActividad.setBounds(20, 16, 57, 32);
		}
		return lblActividad;
	}

	private JComboBox<String> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<String>();
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fillPorRegistrar();
				}
			});
			comboBox.setBounds(87, 18, 125, 29);
			String[] actividades = fillActividades();
			DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(actividades);
			comboBox.setModel(dcbm);
			
		}
		return comboBox;
	}

	/**
	 * Recoge las actividades de la base de datos
	 */
	private String[] fillActividades() {
		try {
			parser.fillArrays();
		} catch (SQLException e) {
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

	private JScrollPane getScrollIzq() {
		if (scrollIzq == null) {
			scrollIzq = new JScrollPane();
			scrollIzq.setBounds(20, 100, 192, 337);
			scrollIzq.setViewportView(getListaPorRegistrar());
		}
		return scrollIzq;
	}

	private JScrollPane getScrollDch() {
		if (scrollDch == null) {
			scrollDch = new JScrollPane();
			scrollDch.setBounds(346, 100, 205, 337);
			scrollDch.setViewportView(getListaRegistrados());
		}
		return scrollDch;
	}

	private JList<String> getListaPorRegistrar() {
		if (listaPorRegistrar == null) {
			listaPorRegistrar = new JList<String>();
			DefaultListModel<String> modelo = new DefaultListModel<String>();
			listaPorRegistrar.setModel(modelo);
		}
		return listaPorRegistrar;
	}

	private JList<String> getListaRegistrados() {
		if (listaRegistrados == null) {
			listaRegistrados = new JList<String>();
			DefaultListModel<String> modelo = new DefaultListModel<String>();
			listaRegistrados.setModel(modelo);
		}
		return listaRegistrados;
	}

	private JButton getBtDch() {
		if (btDch == null) {
			btDch = new JButton("\u2192");
			btDch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					registrar();
				}
			});
			btDch.setBorderPainted(false);
			btDch.setFont(new Font("Tahoma", Font.PLAIN, 43));
			btDch.setBackground(Color.WHITE);
			btDch.setBounds(235, 203, 101, 41);
		}
		return btDch;
	}
	
	/**
	 * Pasa los usuarios seleccionados a la lista de presentados
	 */
	private void registrar() {
		List<String> presentados = listaPorRegistrar.getSelectedValuesList();

		// Cambia de lado
		DefaultListModel<String> aux = (DefaultListModel<String>) listaRegistrados.getModel();
		for (String p : presentados)
			aux.addElement(p);
		listaRegistrados.setModel(aux);

		// Borra los viejos
		DefaultListModel<String> aux2 = (DefaultListModel<String>) listaPorRegistrar.getModel();
		for (String p : presentados)
			aux2.removeElement(p);
		listaPorRegistrar.setModel(aux2);
	}

	private JButton getButton() {
		if (button == null) {
			button = new JButton("\u2190");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					desregistrar();
				}
			});
			button.setBorderPainted(false);
			button.setFont(new Font("Tahoma", Font.PLAIN, 43));
			button.setBackground(Color.WHITE);
			button.setBounds(235, 255, 101, 41);
		}
		return button;
	}
	
	/**
	 * Pasa los usuarios seleccionados a la lista de socios
	 */
	private void desregistrar() {
		List<String> registrados = listaRegistrados.getSelectedValuesList();

		// Cambia de lado
		DefaultListModel<String> aux = (DefaultListModel<String>) listaPorRegistrar.getModel();
		for (String p : registrados)
			aux.addElement(p);
		listaPorRegistrar.setModel(aux);

		// Borra los viejos
		DefaultListModel<String> aux2 = (DefaultListModel<String>) listaRegistrados.getModel();
		for (String p : registrados)
			aux2.removeElement(p);
		listaRegistrados.setModel(aux2);
	}

	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setModal(false);
					dispose();
				}
			});
			btnSalir.setBounds(463, 451, 88, 29);
		}
		return btnSalir;
	}

	private JButton getBtnRegistrar() {
		if (btnRegistrar == null) {
			btnRegistrar = new JButton("Registrar");
			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiarEstado();
				}
			});
			btnRegistrar.setBounds(356, 451, 88, 29);
		}
		return btnRegistrar;
	}
	
	/**
	 * Pasa los clientes en registrados a true y los no registrados a false
	 */
	private void cambiarEstado(){
		DefaultListModel<String> aux = (DefaultListModel<String>) listaRegistrados.getModel();
		DefaultListModel<String> aux2 = (DefaultListModel<String>) listaPorRegistrar.getModel();
		for (int i = 0; i<aux.getSize(); i++){
			parser.actualizaRegistroT(aux.getElementAt(i), referencia.getActividadID());
		}
		for (int i = 0; i<aux2.getSize(); i++){
			parser.actualizaRegistroF(aux2.getElementAt(i), referencia.getActividadID());
		}
	}

	private JLabel getLblNoPresentados() {
		if (lblNoPresentados == null) {
			lblNoPresentados = new JLabel("No presentados:");
			lblNoPresentados.setBounds(20, 69, 125, 20);
		}
		return lblNoPresentados;
	}

	private JLabel getLblPresentados() {
		if (lblPresentados == null) {
			lblPresentados = new JLabel("Presentados:");
			lblPresentados.setBounds(353, 69, 115, 20);
		}
		return lblPresentados;
	}

	private JSpinner getSpDesde() {
		if (spDesde == null) {
			spDesde = new JSpinner();
			spDesde.setModel(new SpinnerNumberModel(0, 0, 23, 1));
			spDesde.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					fillPorRegistrar();
				}
			});
			spDesde.setBounds(85, 21, 58, 26);
		}
		return spDesde;
	}

	private JPanel getPnHorario() {
		if (pnHorario == null) {
			pnHorario = new JPanel();
			pnHorario.setBorder(new TitledBorder(null, "Horario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnHorario.setBackground(Color.WHITE);
			pnHorario.setBounds(265, 11, 286, 58);
			pnHorario.setLayout(null);
			pnHorario.add(getSpDesde());
			pnHorario.add(getLblDesde());
			pnHorario.add(getLblHasta());
			pnHorario.add(getSpHasta());
		}
		return pnHorario;
	}

	private JLabel getLblDesde() {
		if (lblDesde == null) {
			lblDesde = new JLabel("Desde:");
			lblDesde.setHorizontalAlignment(SwingConstants.TRAILING);
			lblDesde.setBounds(23, 24, 52, 20);
		}
		return lblDesde;
	}

	private JLabel getLblHasta() {
		if (lblHasta == null) {
			lblHasta = new JLabel("hasta:");
			lblHasta.setHorizontalAlignment(SwingConstants.TRAILING);
			lblHasta.setBounds(153, 24, 44, 20);
		}
		return lblHasta;
	}

	private JSpinner getSpHasta() {
		if (spHasta == null) {
			spHasta = new JSpinner();
			spHasta.setModel(new SpinnerNumberModel(0, 0, 23, 1));
			spHasta.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					fillPorRegistrar();
				}
			});
			spHasta.setBounds(207, 21, 58, 26);
		}
		return spHasta;
	}

	private void fillPorRegistrar() {
		ArrayList<Actividad> actividades = parser.getActividades();
		ArrayList<Actividad> actividadesHoy = new ArrayList<Actividad>();
		// Encuentra las actividades de hoy
		for (Actividad a : actividades) {
			if (isHoy(a)) {
				actividadesHoy.add(a);
			}
		}
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		DefaultListModel<String> modelo2 = new DefaultListModel<String>();
		for(Actividad a : actividadesHoy){
			if(a.getActividad_nombre().equals(comboBox.getSelectedItem())){
				if(isHora(a)){
					for(SocioActividad sa : parser.getSociosactividad()){
						if(sa.getActividadID() == a.getActividadID()){
							if(!noPresentado(sa.getSocioID()))
								modelo.addElement(sa.getSocioID());
							else
								modelo2.addElement(sa.getSocioID());
						}
					}
				}
			}
		}
		
		listaPorRegistrar.setModel(modelo);
		listaRegistrados.setModel(modelo2);
	}
	
	private boolean noPresentado(String id){
		for(SocioActividad sa : parser.getSociosactividad()){
			if(id.equals(sa.getSocioID())){
				return sa.getPresentado();
			}
		}
		return false;
	}

	private boolean isHoy(Actividad a){
		for(ReservaActividad ra: parser.getReservasactividad()){
			if(a.getActividadID() == ra.getActividadID()){
				for(Reserva r : parser.getReservas())
					if(ra.getReservaID() == r.getReservaID()){
						Date hoy = Date.valueOf(LocalDate.now());
						if(getMes(new Timestamp(hoy.getTime())) == getMes(r.getHoraComienzo()) && getDia(new Timestamp(hoy.getTime())) == getDia(r.getHoraComienzo()) && getYear(new Timestamp(hoy.getTime())) == getYear(r.getHoraComienzo())){
							return true;
						}
					}
			}
		}
		
		return false;
	}
	
	private boolean isHora(Actividad a){
		for(ReservaActividad ra: parser.getReservasactividad()){
			if(a.getActividadID() == ra.getActividadID()){
				for(Reserva r : parser.getReservas())
					if(ra.getReservaID() == r.getReservaID()){
						if(getHora(r.getHoraComienzo()) == (Integer) spDesde.getValue() && getHora(r.getHoraFinal()) == (Integer) spHasta.getValue()){
							referencia = a;
							return true;
						}
					}
			}
		}
		
		return false;
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
}
