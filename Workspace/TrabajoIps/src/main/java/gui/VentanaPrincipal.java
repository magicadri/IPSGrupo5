package gui;

import db.Database;
import db.Parser;
import logic.Reserva;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import com.toedter.calendar.JCalendar;
//import com.toedter.calendar.JMonthChooser;
import java.util.ArrayList;
import java.util.List;


public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = -2863489269021675801L;
	private JPanel contentPane;
    private JPanel panelEscoger;
    private JButton btnSocio;
    private VentanaPrincipal ref = this;
    private JButton btnAdmin;
    private String socioID;
    private static Parser parser;
    
	private Parser Parser = new Parser();

    /**
     * Launch the application.
     */
	public static void main(String[] args) {
        try {
        	parser= new Parser();
            Database.getInstance();
            VentanaPrincipal frame = new VentanaPrincipal();
            frame.setVisible(true);
            parser.fillArrays();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the frame.
     */
    public VentanaPrincipal() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 758, 448);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(1, 0, 0, 0));
        contentPane.add(getPanelEscoger());
    }

    private JPanel getPanelEscoger() {
        if (panelEscoger == null) {
            panelEscoger = new JPanel();
            panelEscoger.setLayout(null);
            panelEscoger.add(getBtnSocio());
            panelEscoger.add(getBtnAdmin());

            JLabel lblBienvenidoAlClub = new JLabel("Bienvenido/a al club deportivo X");
            lblBienvenidoAlClub.setFont(new Font("Tahoma", Font.BOLD, 24));
            lblBienvenidoAlClub.setBounds(151, 59, 452, 48);
            panelEscoger.add(lblBienvenidoAlClub);
        }
        return panelEscoger;
    }

    private JButton getBtnSocio() {
        if (btnSocio == null) {
            btnSocio = new JButton("Socio");
            btnSocio.setBounds(26, 168, 329, 138);
            btnSocio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                	socioID =JOptionPane.showInputDialog("Introduce tu ID de socio: ");
                	for (Reserva reserva : parser.getReservas()) {
            			if(socioID.equals(reserva.getSocioID())) { 
            				VentanaCalendar VC = new VentanaCalendar(socioID);
                    		VC.setVisible(true);
                    		break;
            			}
            			else {
            				JOptionPane.showMessageDialog(ref, "No existe esa ID de socio");
            				break;
            			}
            		}           		
                }
            });
        }
        return btnSocio;
    }

    private JButton getBtnAdmin() {
        if (btnAdmin == null) {
            btnAdmin = new JButton("Admin");
            btnAdmin.setBounds(397, 168, 308, 138);
            btnAdmin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                	VentanaAdminMenu vam = new VentanaAdminMenu();
                	vam.setVisible(true);

                }
            });
        }
        return btnAdmin;
    }
    
    public String getSocioID() {
		return socioID;
	}

	public void setSocioID(String socioID) {
		this.socioID = socioID;
	}
}
