package com.github.pirent;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.pirent.AuctionEventListener.PriceSource;

@RunWith(MockitoJUnitRunner.class)
public class AuctionSniperTest {

	private static final Item ITEM = new Item("item-5000", 1234);

	@Mock
	private Auction auction;
	
	@Mock
	private SniperListener sniperListener;
	
	private AuctionSniper sniper;
	
	@Before
	public void init() {
		sniper = new AuctionSniper(ITEM, auction);
		sniper.addSniperListener(sniperListener);
	}
	
	@Test
	public void reportsLostWhenAuctionClosed() {
		sniper.auctionClosed();
		
		verify(sniperListener).sniperStateChanged(
				argThat(is(aSniperThatIs(SniperState.LOST))));
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
		verify(sniperListener, atLeast(1)).sniperStateChanged(
				new SniperSnapshot(ITEM, price, bid, SniperState.BIDDING));
	}
	
	@Test
	public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
		InOrder inOrder = Mockito.inOrder(sniperListener);
		
		// First call is to force the Sniper to bid
		sniper.currentPrice(123, 12, PriceSource.FROM_OTHER_SNIPPER);
		
		// Again to tell that the Sniper that it's winning
		sniper.currentPrice(135, 45, PriceSource.FROM_SNIPER);
		
		inOrder.verify(sniperListener, atLeast(1)).sniperStateChanged(
				new SniperSnapshot(ITEM, 123, 135, SniperState.BIDDING));
			
		inOrder.verify(sniperListener, atLeast(1)).sniperStateChanged(
				new SniperSnapshot(ITEM, 135, 135, SniperState.WINNING));
	}
	
	@Test
	public void reportsLostIfAuctionCloseImmediately() {
		sniper.auctionClosed();
		
		verify(sniperListener, atLeast(1)).sniperStateChanged(
				argThat(is(aSniperThatIs(SniperState.LOST))));
	}
	
	@Test
	public void reportsLostIfAuctionClosesWhenBidding() {
		InOrder inOrder = Mockito.inOrder(sniperListener);
		
		sniper.currentPrice(123, 45, PriceSource.FROM_OTHER_SNIPPER);
		
		inOrder.verify(sniperListener).sniperStateChanged(
				argThat(is(aSniperThatIs(SniperState.BIDDING))));
		
		sniper.auctionClosed();
		
		inOrder.verify(sniperListener, atLeast(1)).sniperStateChanged(
				argThat(is(aSniperThatIs(SniperState.LOST))));
	}
	
	@Test
	public void reportsWonIfAuctionClosesWhenWinning() {
		InOrder inOrder = Mockito.inOrder(sniperListener);
		
		sniper.currentPrice(123, 45, PriceSource.FROM_SNIPER);
		
		inOrder.verify(sniperListener).sniperStateChanged(
				argThat(is(aSniperThatIs(SniperState.WINNING))));
		
		sniper.auctionClosed();
		
		inOrder.verify(sniperListener, atLeast(1)).sniperStateChanged(
				argThat(is(aSniperThatIs(SniperState.WON))));
	}
	
	@Test
	public void doesNotBidAndReportsLosingIfSubsequentPriceIsAboveTopPrice() {
		sniper.currentPrice(123, 45, PriceSource.FROM_OTHER_SNIPPER);
		sniper.currentPrice(2345, 25, PriceSource.FROM_OTHER_SNIPPER);
		
		final int bid = 123 + 45;
		verify(auction).bid(bid);
		verify(sniperListener, atLeast(1)).sniperStateChanged(
				new SniperSnapshot(ITEM, 2345, bid, SniperState.LOSING));
	}
	
	/**
	 * Create a {@link Matcher} to check that a {@link SniperSnapshot}
	 * has its state match with the provided one.
	 * 
	 * @param state a SniperState to match
	 * @return
	 */
	private Matcher<SniperSnapshot> aSniperThatIs(final SniperState state) {
		return new FeatureMatcher<SniperSnapshot, SniperState>(equalTo(state),
				"sniper that is ", "was") {

			@Override
			protected SniperState featureValueOf(SniperSnapshot actual) {
				return actual.getSniperState();
			}
			
		};
	}
}
