package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaAdminMenu extends JFrame {

	private JPanel contentPane;
	private JButton btnHacerReserva;
	private JButton btnConsultarDisponibilidad;
	private JButton btnPasarPago;
	private JButton btnQuitarRecibo;
	private JButton btnAdministracion;
	private JButton btnMonitor;
	private JButton btnActividad;

	/**
	 * Create the frame.
	 */
	public VentanaAdminMenu() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_btnAdministracion = new GridBagConstraints();
		gbc_btnAdministracion.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdministracion.gridx = 3;
		gbc_btnAdministracion.gridy = 1;
		contentPane.add(getBtnAdministracion(), gbc_btnAdministracion);
		GridBagConstraints gbc_btnHacerReserva = new GridBagConstraints();
		gbc_btnHacerReserva.insets = new Insets(0, 0, 5, 0);
		gbc_btnHacerReserva.gridx = 7;
		gbc_btnHacerReserva.gridy = 1;
		gbc_btnHacerReserva.ipadx = 60;
		gbc_btnHacerReserva.gridwidth = 10;
		contentPane.add(getBtnHacerReserva(), gbc_btnHacerReserva);
		GridBagConstraints gbc_btnMonitor = new GridBagConstraints();
		gbc_btnMonitor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMonitor.insets = new Insets(0, 0, 5, 5);
		gbc_btnMonitor.gridx = 3;
		gbc_btnMonitor.gridy = 3;
		contentPane.add(getBtnMonitor(), gbc_btnMonitor);
		GridBagConstraints gbc_btnConsultarDisponibilidad = new GridBagConstraints();
		gbc_btnConsultarDisponibilidad.insets = new Insets(0, 0, 5, 0);
		gbc_btnConsultarDisponibilidad.gridx = 7;
		gbc_btnConsultarDisponibilidad.gridy = 3;
		gbc_btnConsultarDisponibilidad.ipadx = 5;
		gbc_btnConsultarDisponibilidad.gridwidth = 10;
		contentPane.add(getBtnConsultarDisponibilidad(), gbc_btnConsultarDisponibilidad);
		GridBagConstraints gbc_btnActividad = new GridBagConstraints();
		gbc_btnActividad.insets = new Insets(0, 0, 5, 5);
		gbc_btnActividad.gridx = 3;
		gbc_btnActividad.gridy = 5;
		contentPane.add(getBtnActividad(), gbc_btnActividad);
		GridBagConstraints gbc_btnPasarPago = new GridBagConstraints();
		gbc_btnPasarPago.insets = new Insets(0, 0, 5, 0);
		gbc_btnPasarPago.gridx = 7;
		gbc_btnPasarPago.gridy = 5;
		gbc_btnPasarPago.ipadx = 80;
		gbc_btnPasarPago.gridwidth = 10;
		contentPane.add(getBtnPasarPago(), gbc_btnPasarPago);
		GridBagConstraints gbc_btnQuitarRecibo = new GridBagConstraints();
		gbc_btnQuitarRecibo.gridx = 7;
		gbc_btnQuitarRecibo.gridy = 7;
		gbc_btnQuitarRecibo.ipadx = 70;
		gbc_btnQuitarRecibo.gridwidth = 10;
		contentPane.add(getBtnQuitarRecibo(), gbc_btnQuitarRecibo);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}

	private JButton getBtnHacerReserva() {
		if (btnHacerReserva == null) {
			btnHacerReserva = new JButton("Hacer reserva");
			btnHacerReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaReservaAdmin vra = new VentanaReservaAdmin();
					vra.setVisible(true);
				}
			});
		}
		return btnHacerReserva;
	}
	
	/**
	 * (historia --> Como admin ver la ocupacion de instalaciones para conocer su disponibilidad)
	 * (historia --> Como admin quiero ver la informacion de de una reserva)
	 */
	private JButton getBtnConsultarDisponibilidad() {
		if (btnConsultarDisponibilidad == null) {
			btnConsultarDisponibilidad = new JButton("Consultar disponibilidad");
			btnConsultarDisponibilidad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// Abrimos la ventana para comprobar disponibilidad de instalaciones
					VentanaComprobarOcupacionAdmin vcoa = new VentanaComprobarOcupacionAdmin();
					vcoa.setVisible(true);
					vcoa.setModal(true);
				}
			});
		}
		return btnConsultarDisponibilidad;
	}
	private JButton getBtnPasarPago() {
		if (btnPasarPago == null) {
			btnPasarPago = new JButton("Pasar pago");
			btnPasarPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						VentanaMandarRecibo vmr = new VentanaMandarRecibo();
						vmr.setVisible(true);
				}
			});
		}
		return btnPasarPago;
	}
	private JButton getBtnQuitarRecibo() {
		if (btnQuitarRecibo == null) {
			btnQuitarRecibo = new JButton("Quitar recibo");
			btnQuitarRecibo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaEliminarRecibo ver = new VentanaEliminarRecibo();
					ver.setVisible(true);
				}
			});
		}
		return btnQuitarRecibo;
	}
	private JButton getBtnAdministracion() {
		if (btnAdministracion == null) {
			btnAdministracion = new JButton("Administracion");
			btnAdministracion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaCalendarAdmin VCA = new VentanaCalendarAdmin();
					VCA.setVisible(true);
				}
			});
		}
		return btnAdministracion;
	}
	private JButton getBtnMonitor() {
		if (btnMonitor == null) {
			btnMonitor = new JButton("Monitor");
			btnMonitor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaMonitorMenu vmm = new VentanaMonitorMenu();
					vmm.setVisible(true);
				}
			});
		}
		return btnMonitor;
	}
	private JButton getBtnActividad() {
		if (btnActividad == null) {
			btnActividad = new JButton("Crear actividad");
			btnActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaCrearActividad vca = new VentanaCrearActividad();
					vca.setVisible(true);
				}
			});
		}
		return btnActividad;
	}
}
