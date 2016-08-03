package com.github.pirent;

import com.github.pirent.util.Announcer;

public class AuctionSniper implements AuctionEventListener {

	private final Announcer<SniperListener> announcer = Announcer.to(SniperListener.class);
	private Auction auction;
	private SniperSnapshot snapshot;
	
	public AuctionSniper(String itemId, Auction auction) {
		this.auction = auction;
		this.snapshot = SniperSnapshot.joining(itemId);
	}
	
	public void addSniperListener(SniperListener sniperListener) {
		announcer.addListener(sniperListener);
	}

	@Override
	public void auctionClosed() {
		snapshot = snapshot.closed();
		notifyChange();
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		switch (priceSource) {
			case FROM_SNIPER:
				snapshot = snapshot.winning(price);
				break;
			case FROM_OTHER_SNIPPER:
				int bid = price + increment;
				auction.bid(bid);
				snapshot = snapshot.bidding(price, bid);
				break;
		}
		notifyChange();
	}
	
	private void notifyChange() {
		announcer.announce().sniperStateChanged(snapshot);
	}

	public SniperSnapshot getSnapshot() {
		return snapshot;
	}

}
