package com.github.pirent;

public class AuctionSniper implements AuctionEventListener {

	private final SniperListener sniperListener;
	private final Auction auction;
	
	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.auction = auction;
		this.sniperListener = sniperListener;
	}

	public void auctionClosed() {
		sniperListener.sniperLost();
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		switch (priceSource) {
			case FROM_SNIPER:
				sniperListener.sniperWinning();
				break;

			case FROM_OTHER_SNIPPER:
				auction.bid(price + increment);
				sniperListener.sniperBidding();
				break;
		}
	}

}
