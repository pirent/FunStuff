package com.github.pirent;

public class AuctionSniper implements AuctionEventListener {

	private final SniperListener sniperListener;
	private final Auction auction;
	
	private SniperSnapshot snapshot;
	
	public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
		this.auction = auction;
		this.sniperListener = sniperListener;
		this.snapshot = SniperSnapshot.join(itemId);
	}

	@Override
	public void auctionClosed() {
		snapshot = snapshot.closed();
		notifyChange();
	}

	private void notifyChange() {
		sniperListener.sniperStateChanged(snapshot);
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

}
