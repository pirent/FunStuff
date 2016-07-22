package com.github.pirent;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.beans.FeatureDescriptor;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
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

	private static final String ITEM_ID = "item-5000";

	@Mock
	private Auction auction;
	
	private SniperTestInternalState sniperState = SniperTestInternalState.IDLE;
	private AuctionSniper sniper;
	private SniperListener sniperListener;
	
	@Before
	public void init() {
		sniperListener = Mockito.spy(new SniperListenerStub());
		sniper = new AuctionSniper(ITEM_ID, auction, sniperListener);
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
		final int bid = price + increment;
		
		sniper.currentPrice(price, increment, PriceSource.FROM_OTHER_SNIPPER);
		
		verify(auction).bid(bid);
		
		// We don't care if the Sniper notifies the listener more than once that it's bidding
		// it's just a status update, so atLeast(1) is used.
		verify(sniperListener, atLeast(1)).sniperBidding(new com.github.pirent.SniperSnapshot(ITEM_ID, price, bid));
	}
	
	private Matcher<SniperSnapshot> aSniperThatIs(final SniperState state) {
		return new FeatureMatcher<SniperSnapshot, SniperState>(equalTo(state), "sniper that is ", "was") {

			@Override
			protected SniperState featureValueOf(SniperSnapshot actual) {
				return actual.getSniperState();
			}
			
		};
	}
	
	@Test
	public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
		// First call is to force the Sniper to bid
		sniper.currentPrice(123, 12, PriceSource.FROM_OTHER_SNIPPER);
		
		// Again to tell that the Sniper that it's winning
		sniper.currentPrice(135, 45, PriceSource.FROM_SNIPER);
		
		
		
		assertEquals(sniperState, SniperTestInternalState.BIDDING);
		verify(sniperListener, atLeast(1)).sniperStateChanged(
				new SniperSnapshot(ITEM_ID, 135, 135, SniperState.WINNING));
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
		
		assertEquals(sniperState, SniperTestInternalState.BIDDING);
		verify(sniperListener, atLeast(1)).sniperLost();
	}
	
	@Test
	public void reportsWonIfAuctionClosesWhenWinning() {
		sniper.currentPrice(123, 45, PriceSource.FROM_SNIPER);
		sniper.auctionClosed();
		
		assertEquals(sniperState, SniperTestInternalState.WINNING);
		verify(sniperListener, atLeast(1)).sniperWon();
	}
	
	private class SniperListenerStub implements SniperListener {

		@Override
		public void sniperLost() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sniperBidding(com.github.pirent.SniperSnapshot state) {
			sniperState = SniperTestInternalState.BIDDING;
		}

		@Override
		public void sniperWinning() {
			sniperState = SniperTestInternalState.WINNING;
		}

		@Override
		public void sniperWon() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sniperStateChanged(SniperSnapshot sniperSnapshot) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * Internal state which purpose is used only for the test.
	 * 
	 * @author pirent
	 *
	 */
	private enum SniperTestInternalState {
		IDLE, WINNING, BIDDING
	}
	
}
