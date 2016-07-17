package com.github.pirent;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.pirent.AuctionEventListener.PriceSource;

@RunWith(MockitoJUnitRunner.class)
public class AuctionSniperTest {

	@Mock
	private SniperListener sniperListener;
	
	@Mock
	private Auction auction;
	
	private AuctionSniper sniper;
	
	@Before
	public void init() {
		sniper = new AuctionSniper(auction, sniperListener);
	}
	
	@Test
	public void reportsLostWhenAuctionClosed() {
		sniper.auctionClosed();
		
		verify(sniperListener).sniperLost();
	}
	
	@Test
	public void bidHigherAndReportsBiddingWhenANewPriceArrives() {
		final int price = 1001;
		final int increment = 25;
		
		sniper.currentPrice(price, increment, PriceSource.FROM_OTHER_SNIPPER);
		
		verify(auction).bid(price + increment);
		
		// We don't care if the Sniper notifies the listener more than once that it's bidding
		// it's just a status update, so atLeast(1) is used.
		verify(sniperListener, atLeast(1)).sniperBidding();
	}
	
	@Test
	public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
		sniper.currentPrice(123, 45, PriceSource.FROM_SNIPER);
		
		verify(sniperListener, atLeast(1)).sniperWinning();
	}
}
