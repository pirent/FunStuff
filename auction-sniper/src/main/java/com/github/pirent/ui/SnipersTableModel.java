package com.github.pirent.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.github.pirent.AuctionSniper;
import com.github.pirent.Column;
import com.github.pirent.Defect;
import com.github.pirent.SniperCollector;
import com.github.pirent.SniperListener;
import com.github.pirent.SniperSnapshot;
import com.github.pirent.SniperState;

public class SnipersTableModel extends AbstractTableModel implements
		SniperListener, SniperCollector {

	private static final long serialVersionUID = 1L;
	private static final String[] STATUS_TEXT = {
		"Joining", "Bidding", "Winning", "Lost", "Won"
	};
	
	private final List<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();
	
	/**
	 * To make sure the chat is not garbage-collected by the Java runtime. For
	 * application specific purpose.
	 */
	private final Collection<AuctionSniper> notToBeGCd = new ArrayList<AuctionSniper>();
	
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

	@Override
	public void addSniper(AuctionSniper sniper) {
		notToBeGCd.add(sniper);
		addSniperSnapshot(sniper.getSnapshot());
		sniper.addSniperListener(new SwingThreadSniperListener(this));
	}

	private void addSniperSnapshot(SniperSnapshot sniperSnapshot) {
		snapshots.add(sniperSnapshot);
		int row = snapshots.size() - 1;
		fireTableRowsInserted(row, row);
	}
	
	/**
	 * A Decorator to start {@link SniperListener} in a new Swing thread
	 * 
	 * @author pirent
	 *
	 */
	public class SwingThreadSniperListener implements SniperListener {

		private final SniperListener sniperListener;

		public SwingThreadSniperListener(SniperListener sniperListener) {
			super();
			this.sniperListener = sniperListener;
		}

		@Override
		public void sniperStateChanged(final SniperSnapshot sniperSnapshot) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					sniperListener.sniperStateChanged(sniperSnapshot);
				}
			});
		}

	}
}
