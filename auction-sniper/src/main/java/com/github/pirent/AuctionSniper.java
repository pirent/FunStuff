package com.github.pirent;

public class AuctionSniper implements AuctionEventListener {

	private SniperListener sniperListener;
	private final Auction auction;
	
	private SniperSnapshot snapshot;
	
	public AuctionSniper(String itemId, Auction auction) {
		this.auction = auction;
		this.snapshot = SniperSnapshot.joining(itemId);
	}
	
	public void addSniperListener(SniperListener sniperListener) {
		this.sniperListener = sniperListener;
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
		sniperListener.sniperStateChanged(snapshot);
	}

	public SniperSnapshot getSnapshot() {
		return snapshot;
	}

}
