package com.github.pirent;

import com.github.pirent.ui.SnipersTableModel;

/**
 * Represent column in the sniper table
 * 
 * @author pirent
 *
 */
public enum Column {
	ITEM_IDENTIFIER("Item") {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getItem().identifier;
		}
	},
	LAST_PRICE("Last Price") {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastPrice();
		}
	},
	LAST_BID("Last Bid") {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastBid();
		}
	},
	SNIPER_STATE("State") {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return SnipersTableModel.textFor(snapshot.getSniperState());
		}
	};
	
	public final String name;

	private Column(String name) {
		this.name = name;
	}
	
	public static Column at(int offset) {
		return values()[offset];
	}
	
	abstract public Object valueIn(SniperSnapshot snapshot);
}
