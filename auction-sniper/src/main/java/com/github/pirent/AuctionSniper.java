package com.github.pirent;

public class AuctionSniper implements AuctionEventListener {

	private final SniperListener sniperListener;
	private final Auction auction;
	private boolean isWinning;
	private String itemId;
	
	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.auction = auction;
		this.sniperListener = sniperListener;
	}

	@Override
	public void auctionClosed() {
		if (isWinning) {
			sniperListener.sniperWon();
		}
		else {
			sniperListener.sniperLost();
		}
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		isWinning = PriceSource.FROM_SNIPER == priceSource;
		if (isWinning) {
			sniperListener.sniperWinning();
		}
		else {
			int bid = price + increment;
			auction.bid(bid);
			sniperListener.sniperStateChanged(new SniperSnapshot(itemId, price,
					increment, SniperState.BIDDING));
		}
	}

}
