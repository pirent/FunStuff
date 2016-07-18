package com.github.pirent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.pirent.AuctionEventListener.PriceSource;

@RunWith(MockitoJUnitRunner.class)
public class AuctionSniperTest {

	@Mock
	private Auction auction;
	
	private SniperState sniperState = SniperState.IDLE;
	private AuctionSniper sniper;
	private SniperListener sniperListener;
	
	@Before
	public void init() {
		sniperListener = Mockito.spy(new SniperListenerStub());
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
	
	@Test
	public void reportsLostIfAuctionCloseImmediately() {
		sniper.auctionClosed();
		
		verify(sniperListener, atLeast(1)).sniperLost();
	}
	
	@Test
	public void reportsLostIfAuctionClosesWhenBidding() {
		sniper.currentPrice(123, 45, PriceSource.FROM_OTHER_SNIPPER);
		sniper.auctionClosed();
		
		assertEquals(sniperState, SniperState.BIDDING);
		verify(sniperListener, atLeast(1)).sniperLost();
	}
	
	@Test
	public void reportsWonIfAuctionClosesWhenWinning() {
		sniper.currentPrice(123, 45, PriceSource.FROM_SNIPER);
		sniper.auctionClosed();
		
		assertEquals(sniperState, SniperState.WINNING);
		verify(sniperListener, atLeast(1)).sniperWon();
	}
	
	private class SniperListenerStub implements SniperListener {

		@Override
		public void sniperLost() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sniperBidding() {
			sniperState = SniperState.BIDDING;
		}

		@Override
		public void sniperWinning() {
			sniperState = SniperState.WINNING;
		}

		@Override
		public void sniperWon() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private enum SniperState {
		IDLE, WINNING, BIDDING
	}
	
}
