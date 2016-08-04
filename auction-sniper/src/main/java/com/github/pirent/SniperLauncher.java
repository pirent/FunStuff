package com.github.pirent;

import com.github.pirent.ui.UserRequestListener;

public class SniperLauncher implements UserRequestListener {

	private final AuctionHouse auctionHouse;
	private final SniperCollector collector;

	public SniperLauncher(AuctionHouse auctionHouse, SniperCollector collector) {
		super();
		this.auctionHouse = auctionHouse;
		this.collector = collector;
	}

	@Override
	public void joinAuction(Item item) {
		// Respond to a request to join an auction
		// by launching a Sniper
		Auction auction = auctionHouse.auctionFor(item);
		AuctionSniper sniper = new AuctionSniper(item, auction);
		auction.addAuctionEventListener(sniper);
		collector.addSniper(sniper);
		auction.join();
	}

}
