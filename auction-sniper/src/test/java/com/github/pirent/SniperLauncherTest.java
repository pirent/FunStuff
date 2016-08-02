package com.github.pirent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SniperLauncherTest {

	private SniperLauncher testee;
	
	@Mock
	private SniperCollector sniperCollector;
	
	@Mock
	private AuctionHouse auctionHouse;
	
	@Mock
	private Auction auction;
	
	@Before
	public void init() {
		testee = new SniperLauncher(auctionHouse, sniperCollector);
	}
	
	@Test
	public void addsNewSniperToCollectorAndThenJoinsAuction() {
		// To confirm one behavior is that we only join the auction
		// after everything else is set up.
		// Given
		final String itemId = "item 123";
		when(auctionHouse.auctionFor(itemId)).thenReturn(auction);
		InOrder inOrder = inOrder(auction, sniperCollector);
		
		// When
		testee.joinAuction(itemId);
		
		// Then
		
		inOrder.verify(auction).addAuctionEventListener(
				argThat(is(sniperForItem(itemId))));
		inOrder.verify(sniperCollector).addSniper(argThat(is(sniperForItem(itemId))));
		inOrder.verify(auction).join();
	}

	/**
	 * Return a {@link Matcher} that matches any {@link AuctionSniper}
	 * associated with the given item identifier.
	 * 
	 * @param itemId
	 * @return
	 */
	private Matcher<AuctionSniper> sniperForItem(String itemId) {
		return new FeatureMatcher<AuctionSniper, String>(equalTo(itemId),
				"a sniper that is", "was") {

			@Override
			protected String featureValueOf(AuctionSniper actual) {
				return actual.getSnapshot().getItemId();
			}
		};
	}
}
