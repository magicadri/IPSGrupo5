package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaMonitorMenu extends JFrame {

	private static final long serialVersionUID = 8146164374400367650L;
	private JPanel contentPane;
	private JLabel lblMenuDeMonitor;
	private JPanel pnCentral;
	private JButton btnRegistrarAsistencia;
	private JLabel label;
	private JLabel label_1;

	/**
	 * Create the frame.
	 */
	public VentanaMonitorMenu() {
		setTitle("Menu de monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLblMenuDeMonitor(), BorderLayout.NORTH);
		contentPane.add(getPnCentral(), BorderLayout.CENTER);
	}

	private JLabel getLblMenuDeMonitor() {
		if (lblMenuDeMonitor == null) {
			lblMenuDeMonitor = new JLabel("Menu de monitor:");
			lblMenuDeMonitor.setFont(new Font("Tahoma", Font.BOLD, 30));
			lblMenuDeMonitor.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblMenuDeMonitor;
	}
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnCentral.add(getBtnRegistrarAsistencia());
			pnCentral.add(getLabel());
			pnCentral.add(getLabel_1());
		}
		return pnCentral;
	}
	private JButton getBtnRegistrarAsistencia() {
		if (btnRegistrarAsistencia == null) {
			btnRegistrarAsistencia = new JButton("Registrar asistencia");
			btnRegistrarAsistencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaRegistrarAsistenciaMejorada vram = new VentanaRegistrarAsistenciaMejorada();
					vram.setVisible(true);
					vram.setModal(true);
				}
			});
		}
		return btnRegistrarAsistencia;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("");
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("");
		}
		return label_1;
	}
}
