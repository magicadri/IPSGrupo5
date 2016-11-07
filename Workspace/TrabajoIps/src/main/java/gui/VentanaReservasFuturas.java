package gui;

import db.Parser;
import logic.Instalacion;
import logic.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by jorge on 07/11/2016.
 */
public class VentanaReservasFuturas extends JDialog {

  private DefaultTableModel modelTable;
  private JTable table;
  private Parser parser = new Parser();
  private String socioId;

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
      modelTable.addColumn("Instalaci�n");
      modelTable.addColumn("Fecha comienzo");
      modelTable.addColumn("Fecha final");
      table.setModel(modelTable);
    }
    return table;
  }

  private void llenarTabla() {
    limpiarTabla();
    String row[] = new String[3];
    for (Reserva each : parser.getReservas()) {
      if (each.getSocioID().equals(socioId) && each.getHoraComienzo().after(new Date())) {
        row[0] = getInstalacionNombre(each.getInstalacionID());
        row[1] = each.getHoraComienzo().toString();
        row[2] = each.getHoraFinal().toString();
        modelTable.addRow(row);
      }
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
}
