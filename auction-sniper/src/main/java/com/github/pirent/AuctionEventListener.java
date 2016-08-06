package com.github.pirent;

import java.util.EventListener;

public interface AuctionEventListener extends EventListener {

	enum PriceSource {
		FROM_SNIPER, FROM_OTHER_SNIPPER
	}
	
	public void auctionClosed();

	public void currentPrice(int i, int j, PriceSource priceSource);
	
	public void auctionFailed();

}
