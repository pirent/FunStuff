package com.github.pirent;

/**
 * Represent column in the sniper table
 * 
 * @author pirent
 *
 */
public enum Column {
	ITEM_IDENTIFIER,
	LAST_PRICE,
	LAST_BID,
	SNIPER_STATUS;
	
	public static Column at(int offset) {
		return values()[offset];
	}
}
