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
		final Item item = new Item("item 123", Integer.MAX_VALUE);
		when(auctionHouse.auctionFor(item)).thenReturn(auction);
		InOrder inOrder = inOrder(auction, sniperCollector);
		
		// When
		testee.joinAuction(item);
		
		// Then
		
		inOrder.verify(auction).addAuctionEventListener(
				argThat(is(sniperForItem(item))));
		inOrder.verify(sniperCollector).addSniper(argThat(is(sniperForItem(item))));
		inOrder.verify(auction).join();
	}

	/**
	 * Return a {@link Matcher} that matches any {@link AuctionSniper}
	 * associated with the given item.
	 * 
	 * @param item
	 * @return
	 */
	private Matcher<AuctionSniper> sniperForItem(Item item) {
		return new FeatureMatcher<AuctionSniper, Item>(equalTo(item),
				"a sniper that is", "was") {

			@Override
			protected Item featureValueOf(AuctionSniper actual) {
				return actual.getSnapshot().getItem();
			}
		};
	}
}
