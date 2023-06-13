package tableModels;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class NumberedTableModel implements TableModel{
	
	protected TableModel myNumberedModel;
	
	public NumberedTableModel(TableModel model) {
		myNumberedModel = model;
	}
	
	@Override
	public int getRowCount() {
		return myNumberedModel.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return myNumberedModel.getColumnCount()+1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) return "row";
		return myNumberedModel.getColumnName(columnIndex - 1);
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0) {
			return Integer.class;
		}
		return myNumberedModel.getColumnClass(columnIndex - 1);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}
		return myNumberedModel.isCellEditable(rowIndex, columnIndex - 1);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(columnIndex == 0) {
			
			return Integer.valueOf(rowIndex + 1 ); 
		}return myNumberedModel.getValueAt(rowIndex, columnIndex - 1);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex != 0) {
			myNumberedModel.setValueAt(aValue, rowIndex, columnIndex - 1);
		}
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		myNumberedModel.addTableModelListener(l);
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		myNumberedModel.addTableModelListener(l);

		
	}

}
