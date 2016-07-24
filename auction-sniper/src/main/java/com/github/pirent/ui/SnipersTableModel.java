package com.github.pirent.ui;

import javax.swing.table.AbstractTableModel;

import com.github.pirent.Column;
import com.github.pirent.SniperListener;
import com.github.pirent.SniperSnapshot;
import com.github.pirent.SniperState;

public class SnipersTableModel extends AbstractTableModel implements
		SniperListener {

	private static final long serialVersionUID = 1L;
	private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
	private static final String[] STATUS_TEXT = {
		"Joining", "Bidding", "Winning", "Lost", "Won"
	};
	
	private SniperSnapshot snapshot = STARTING_UP;

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public int getColumnCount() {
		return Column.values().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return Column.at(columnIndex).valueIn(snapshot);
	}

	public static String textFor(SniperState sniperState) {
		return STATUS_TEXT[sniperState.ordinal()];
	}

	@Override
	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		this.snapshot = newSnapshot;
		fireTableRowsUpdated(0, 0);
	}

	@Override
	public String getColumnName(int column) {
		return Column.at(column).name;
	}
}
