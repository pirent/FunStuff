package com.github.pirent.auctionsniper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Maintains our Snipers and tell it listener when a new one is added.
 * 
 * So we can get rid of the notToBeGCd.
 * 
 * @author pirent
 *
 */
public class SniperPortfolio implements SniperCollector {

	private Collection<AuctionSniper> auctionSnipers = new ArrayList<AuctionSniper>();
	private Collection<PortfolioListener> portfolioListeners = new ArrayList<PortfolioListener>();

	public void addPortfolioListener(PortfolioListener listener) {
		portfolioListeners.add(listener);
	}

	@Override
	public void addSniper(AuctionSniper sniper) {
		auctionSnipers.add(sniper);
		for (PortfolioListener portfolioListener : portfolioListeners) {
			portfolioListener.sniperAdded(sniper);
		}
	}

}
