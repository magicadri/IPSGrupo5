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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdminMenu frame = new VentanaAdminMenu();
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
	public VentanaAdminMenu() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_btnHacerReserva = new GridBagConstraints();
		gbc_btnHacerReserva.insets = new Insets(0, 0, 5, 0);
		gbc_btnHacerReserva.gridx = 7;
		gbc_btnHacerReserva.gridy = 1;
		gbc_btnHacerReserva.ipadx = 60;
		gbc_btnHacerReserva.gridwidth = 10;
		contentPane.add(getBtnHacerReserva(), gbc_btnHacerReserva);
		GridBagConstraints gbc_btnConsultarDisponibilidad = new GridBagConstraints();
		gbc_btnConsultarDisponibilidad.insets = new Insets(0, 0, 5, 0);
		gbc_btnConsultarDisponibilidad.gridx = 7;
		gbc_btnConsultarDisponibilidad.gridy = 3;
		gbc_btnConsultarDisponibilidad.ipadx = 5;
		gbc_btnConsultarDisponibilidad.gridwidth = 10;
		contentPane.add(getBtnConsultarDisponibilidad(), gbc_btnConsultarDisponibilidad);
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
		}
		return btnHacerReserva;
	}
	private JButton getBtnConsultarDisponibilidad() {
		if (btnConsultarDisponibilidad == null) {
			btnConsultarDisponibilidad = new JButton("Consultar disponibilidad");
			btnConsultarDisponibilidad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
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
}
