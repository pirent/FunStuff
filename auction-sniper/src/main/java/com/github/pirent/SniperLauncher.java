package com.github.pirent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

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
	public void joinAuction(String itemId) {
		// Respond to a request to join an auction
		// by launching a Sniper
		Auction auction = auctionHouse.auctionFor(itemId);
		AuctionSniper sniper = new AuctionSniper(itemId, auction);
		auction.addAuctionEventListener(sniper);
		collector.addSniper(sniper);
		auction.join();
	}

	/**
	 * A Decorator to start {@link SniperListener} in a new Swing thread
	 * 
	 * @author pirent
	 *
	 */
	public class SwingThreadSniperListener implements SniperListener {

		private final SniperListener sniperListener;

		public SwingThreadSniperListener(SniperListener sniperListener) {
			super();
			this.sniperListener = sniperListener;
		}

		@Override
		public void sniperStateChanged(final SniperSnapshot sniperSnapshot) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					sniperListener.sniperStateChanged(sniperSnapshot);
				}
			});
		}

	}
}
