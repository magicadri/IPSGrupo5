package gui;

import db.Parser;
import logic.Instalacion;
import logic.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.Date;


public class VentanaReservasFuturas extends JDialog {

  private DefaultTableModel modelTable;
  private JTable table;
  private Parser parser = new Parser();
  private String socioId;
  private int precio;
  private JPanel panel;
  private JTextField textFieldPrecio;
  private JLabel lblPrecio;

  public VentanaReservasFuturas(JDialog owner, String socioId) {
    super(owner, true);
    this.socioId = socioId;

    JPanel content = new JPanel(new BorderLayout());
    setContentPane(content);

    try {
      parser.fillArrays();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    content.add(new JScrollPane(getTable()), BorderLayout.CENTER);
    content.add(getPanel(), BorderLayout.SOUTH);
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
    String row[] = new String[4];
    for (Reserva each : parser.getReservas()) {
      if (each.getSocioID().equals(socioId) && each.getHoraComienzo().after(new Date())) {
        row[0] = getInstalacionNombre(each.getInstalacionID());
        row[1] = each.getHoraComienzo().toString();
        row[2] = each.getHoraFinal().toString();
        row[3] = String.valueOf(each.getPrecio());
        precio+=each.getPrecio();
        modelTable.addRow(row);
      }
    }
    textFieldPrecio.setText(String.valueOf(precio));
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
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblPrecio());
			panel.add(getTextFieldPrecio());
		}
		return panel;
	}
	private JTextField getTextFieldPrecio() {
		if (textFieldPrecio == null) {
			textFieldPrecio = new JTextField();
			textFieldPrecio.setEditable(false);
			textFieldPrecio.setColumns(10);
		}
		return textFieldPrecio;
	}
	private JLabel getLblPrecio() {
		if (lblPrecio == null) {
			lblPrecio = new JLabel("Precio total :");
		}
		return lblPrecio;
	}
}
