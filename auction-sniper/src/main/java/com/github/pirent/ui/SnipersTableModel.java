package com.github.pirent.ui;

import javax.swing.table.AbstractTableModel;

import com.github.pirent.Column;
import com.github.pirent.SniperSnapshot;
import com.github.pirent.SniperState;

public class SnipersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
	private static final String[] STATUS_TEXT = {
		MainWindow.STATUS_JOINING,
		MainWindow.STATUS_BIDDING
	};
	
	private String state = MainWindow.STATUS_JOINING;
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
		switch (Column.at(columnIndex)) {
			case ITEM_IDENTIFIER:
				return snapshot.getItemId();
			case LAST_PRICE:
				return snapshot.getLastPrice();
			case LAST_BID:
				return snapshot.getLastBid();
			case SNIPER_STATE:
				return state;
			default:
				throw new IllegalArgumentException("No column at " + columnIndex);
		}
	}

	public void setStatusText(String newStatusText) {
		state = newStatusText;
		fireTableRowsUpdated(0, 0);
	}

	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		this.snapshot = newSnapshot;
		state = STATUS_TEXT[newSnapshot.getSniperState().ordinal()];
		fireTableRowsUpdated(0, 0);
	}

}
