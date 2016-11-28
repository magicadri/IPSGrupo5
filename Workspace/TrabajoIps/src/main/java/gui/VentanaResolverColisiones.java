package gui;

import db.Database;
import db.Parser;
import logic.Instalacion;
import logic.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VentanaResolverColisiones extends JDialog {

  private DefaultTableModel modelTable;
  private JTable table;
  private Parser parser = new Parser();
  private ArrayList<Reserva> colisiones;
  private int precio;
  private Reserva reserva;
  private JPanel panel;
  private JPanel panel_1;
  private JButton btnSobreescribir;
  private JButton btnCancelar;

  public VentanaResolverColisiones(JDialog owner, ArrayList<Reserva> colisiones, Reserva reserva) {
    super(owner, true);
    setTitle("Por favor resuelva las colisiones:");
    this.reserva=reserva;
    this.colisiones=colisiones;
    JPanel content = new JPanel(new BorderLayout());
    setContentPane(content);

    try {
      parser.fillArrays();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    content.add(new JScrollPane(getTable()), BorderLayout.CENTER);
    content.add(getPanel_1(), BorderLayout.SOUTH);
    llenarTabla();

    pack();
    setLocationRelativeTo(owner);
  }

  private JTable getTable() {
    if (table == null) {
      table = new JTable();
      modelTable = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
          return false;
        }
      };
      modelTable.addColumn("Usuario");
      modelTable.addColumn("Instalación");
      modelTable.addColumn("Fecha comienzo");
      modelTable.addColumn("Fecha final");
      modelTable.addColumn("Precio");
      table.setModel(modelTable);
    }
    return table;
  }

  private void llenarTabla() {
    limpiarTabla();
    String row[] = new String[5];
    for (Reserva each : colisiones) {
    	row[0] = each.getSocioID();
        row[1] = getInstalacionNombre(each.getInstalacionID());
        row[2] = each.getHoraComienzo().toString();
        row[3] = each.getHoraFinal().toString();
        row[4] = String.valueOf(each.getPrecio());
        precio+=each.getPrecio();
        modelTable.addRow(row);
    }
  }

  private void limpiarTabla() {
    getTable().clearSelection();
    DefaultTableModel md = (DefaultTableModel) table.getModel();
    md.setRowCount(0);
  }

  private String getInstalacionNombre(int id) {
    for (Instalacion each : parser.getInstalaciones())
      if (each.getInstalacionID() == id)
        return each.getInstalacion_nombre();
    return "";
  }

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getBtnSobreescribir());
			panel_1.add(getBtnCancelar());
		}
		return panel_1;
	}
	private JButton getBtnSobreescribir() {
		if (btnSobreescribir == null) {
			btnSobreescribir = new JButton("Sobreescribir reservas");
			btnSobreescribir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for(Reserva r : colisiones)
					{
						removeReservaDeBase(r);
						disculpaSocio(r.getSocioID() + ", lo sentimos, su reserva " + r.getReservaID() + " ha sido cancelada.");
					}
					boolean aux = addReservaABase(reserva);
					if (aux) {
						JOptionPane.showMessageDialog(null, "Reserva " + reserva.getReservaID() + " añadida a la base de datos.");
					} else {
						JOptionPane.showMessageDialog(null,
								"Reserva " + reserva.getReservaID() + " no ha podido ser añadida a la base de datos.");
					}
					dispose();
				}
			});
		}
		return btnSobreescribir;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}
	
	private void removeReservaDeBase(Reserva reserva) {
		try {
			Database.getInstance().getC().createStatement()
					.execute("DELETE FROM Reserva WHERE reservaID =" + reserva.getReservaID() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer removeReservaABase con la reserva : " + reserva.getReservaID());
		}
	}
	
	private void disculpaSocio(String disculpanomas) {
		System.out.println(disculpanomas);
	}
	
	private boolean addReservaABase(Reserva reserva) {
		try {
			if (reserva.getHoraEntrada() != null)
				Database.getInstance().getC().createStatement().execute(
						"INSERT INTO Reserva (reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada, horaSalida, modoPago, reciboGenerado, precio) VALUES ("
								+ reserva.getReservaID() + ",'" + reserva.getSocioID() + "',"
								+ reserva.getInstalacionID() + ",'" + reserva.getHoraComienzo() + "','"
								+ reserva.getHoraFinal() + "','" + reserva.getHoraEntrada() + "','"
								+ reserva.getHoraSalida() + "','" + reserva.getModoPago() + "',"
								+ reserva.getReciboGenerado() + "," + reserva.getPrecio() + ");");
			else
				Database.getInstance().getC().createStatement().execute(
						"INSERT INTO Reserva (reservaID, socioID, instalacionID, horaComienzo, horaFinal, horaEntrada, horaSalida, modoPago, reciboGenerado, precio) VALUES ("
								+ reserva.getReservaID() + ",'" + reserva.getSocioID() + "',"
								+ reserva.getInstalacionID() + ",'" + reserva.getHoraComienzo() + "','"
								+ reserva.getHoraFinal() + "'," + reserva.getHoraEntrada() + ","
								+ reserva.getHoraSalida() + ",'" + reserva.getModoPago() + "',"
								+ reserva.getReciboGenerado() + "," + reserva.getPrecio() + ");");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al hacer addReservaABase con la reserva : " + reserva.getReservaID());
			return false;
		}
	}
}
