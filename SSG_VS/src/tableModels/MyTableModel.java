package tableModels;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columns;
	private Object[][] rows;

	public MyTableModel() {

	}

	public MyTableModel(Object[][] data, String[] columnName) {
		
		this.rows = data;
		this.columns = columnName;
		
	}
	
	public Class<?> getColumnClass(int column) {
		if(column == 1) {
			return Icon.class;
		}else {
			return getValueAt(0, column).getClass();
		}
	}

	@Override
	public int getRowCount() {
		return this.rows.length;
	}

	@Override
	public int getColumnCount() {
		return this.columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.rows[rowIndex] [columnIndex];
	}
	
	public String getColumnName(int col) {
		return this.columns[col];
	}

}
