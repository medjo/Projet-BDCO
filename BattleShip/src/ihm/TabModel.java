package ihm;

import java.awt.*;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TabModel extends AbstractTableModel {

	private final String[] entetes = {"|v|","A","B","C","D","E","F","G","H","I","J"};	
	ArrayList<Entete> tab= new ArrayList<Entete>();
	
	public void initTab(){
		for(int i= 0; i<10;i++){
			Entete e= new Entete(i);
			tab.add(e);
		}
	}
	
	@Override
	public int getColumnCount() {
		return entetes.length;
	}

		

	@Override
	public String getColumnName(int columnIndex){
		return entetes[columnIndex];
	}
	
	@Override
	public int getRowCount() {
		return tab.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (entetes[columnIndex]) {

		case "A":
			return tab.get(rowIndex);
		case "B":
			return tab.get(rowIndex);
		case "C":
			return tab.get(rowIndex);
		case "D":
			return tab.get(rowIndex);
		case "E":
			return tab.get(rowIndex);
		case "F":
			return tab.get(rowIndex);
		case "G": 
			return tab.get(rowIndex);
		case "H": 
			return tab.get(rowIndex);
		case "I": 
			return tab.get(rowIndex);
		case "J": 
			return tab.get(rowIndex);
		default:
			throw new IllegalArgumentException();
		}
	}

}
