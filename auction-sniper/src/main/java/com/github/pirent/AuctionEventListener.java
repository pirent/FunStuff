package com.github.pirent;

public interface AuctionEventListener {

	public void auctionClosed();

	public void currentPrice(int i, int j);

}
