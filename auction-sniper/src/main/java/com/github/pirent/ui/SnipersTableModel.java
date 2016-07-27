package com.github.pirent.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.pirent.Column;
import com.github.pirent.Defect;
import com.github.pirent.SniperListener;
import com.github.pirent.SniperSnapshot;
import com.github.pirent.SniperState;

public class SnipersTableModel extends AbstractTableModel implements
		SniperListener {

	private static final long serialVersionUID = 1L;
	private static final String[] STATUS_TEXT = {
		"Joining", "Bidding", "Winning", "Lost", "Won"
	};
	
	private List<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();

	@Override
	public int getRowCount() {
		return snapshots.size();
	}

	@Override
	public int getColumnCount() {
		return Column.values().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SniperSnapshot snapshot = snapshots.get(rowIndex);
		return Column.at(columnIndex).valueIn(snapshot);
	}

	public static String textFor(SniperState sniperState) {
		return STATUS_TEXT[sniperState.ordinal()];
	}

	@Override
	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		int row = rowMatching(newSnapshot);
		snapshots.set(row, newSnapshot);
		fireTableRowsUpdated(row, row);
	}

	private int rowMatching(SniperSnapshot snapshot) {
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshot.isForSameItemAs(snapshots.get(i))) {
				return i;
			}
		}
		throw new Defect("Cannot find match for " + snapshot);
	}

	@Override
	public String getColumnName(int column) {
		return Column.at(column).name;
	}

	public void addSniper(SniperSnapshot snapshot) {
		snapshots.add(snapshot);
		int rowToBeInserted = snapshots.size();
		fireTableRowsInserted(rowToBeInserted, rowToBeInserted);
	}
}
