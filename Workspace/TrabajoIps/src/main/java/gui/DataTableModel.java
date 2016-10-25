package gui;

import javax.swing.table.DefaultTableModel;

public class DataTableModel extends DefaultTableModel{
	
	public DataTableModel(Object[][] d , String[] s)
	{
		super(d,s);
	}
	
	public String getStatus(int rownum)
	{
		return (String) this.getValueAt(rownum, 1);
	}
	
	@Override
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}

}
