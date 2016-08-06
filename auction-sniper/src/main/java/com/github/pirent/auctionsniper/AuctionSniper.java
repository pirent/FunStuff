package com.github.pirent.auctionsniper;

import com.github.pirent.Auction;
import com.github.pirent.AuctionEventListener;
import com.github.pirent.Item;
import com.github.pirent.util.Announcer;

public class AuctionSniper implements AuctionEventListener {

	private final Announcer<SniperListener> announcer = Announcer.to(SniperListener.class);
	private Auction auction;
	private SniperSnapshot snapshot;
	private Item item;
	
	public AuctionSniper(Item item, Auction auction) {
		this.auction = auction;
		this.item = item;
		this.snapshot = SniperSnapshot.joining(item);
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
				if (item.allows(bid)) {
					auction.bid(bid);
					snapshot = snapshot.bidding(price, bid);
				}
				else {
					snapshot = snapshot.losing(price);
				}
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

	@Override
	public void auctionFailed() {
		snapshot = snapshot.failed();
		notifyChange();
	}
}
