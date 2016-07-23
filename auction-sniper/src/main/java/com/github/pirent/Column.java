package com.github.pirent;

/**
 * Represent column in the sniper table
 * 
 * @author pirent
 *
 */
public enum Column {
	ITEM_IDENTIFIER {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getItemId();
		}
	},
	LAST_PRICE {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastPrice();
		}
	},
	LAST_BID {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastBid();
		}
	},
	SNIPER_STATE {
		@Override
		public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getSniperState();
		}
	};
	
	public static Column at(int offset) {
		return values()[offset];
	}
	
	abstract public Object valueIn(SniperSnapshot snapshot);
}
