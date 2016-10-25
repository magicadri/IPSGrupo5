package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Clase para pintar el background de la tabla de reservas
 */
public class ColorCellGreen extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -1340894153161397218L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		DataTableModel tableModel = (DataTableModel) table.getModel();

		// Si hay una reserva en esa fila
		if (tableModel.getStatus(row) == "Reserva Piscina" || tableModel.getStatus(row) == "Reserva Tenis"
				|| tableModel.getStatus(row) == "Reserva Futbol") {
			l.setBackground(Color.GREEN); // Ocupado
		} else {
			l.setBackground(Color.WHITE); // Disponible
		}
		return l;
	}

}
