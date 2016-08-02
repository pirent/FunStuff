package com.github.pirent;

/**
 * A factory for creating an instance of an {@link Auction}
 * with a given item.
 * 
 * @author pirent
 *
 */
public interface AuctionHouse {

	Auction auctionFor(String itemId);
	
	void disconnect();
}
