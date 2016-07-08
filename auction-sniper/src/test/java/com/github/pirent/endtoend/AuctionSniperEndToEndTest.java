package com.github.pirent.endtoend;

import org.junit.After;
import org.junit.Test;

import com.github.pirent.ApplicationRunner;
import com.github.pirent.mock.FakeAuctionServer;

public class AuctionSniperEndToEndTest {

	/*
	 * Not like the unit testing, where a test drive object in a same thread,
	 * so it can make assertion about its state and behavior.
	 * 
	 * Strategy: control the application and step throught the scenario.
	 * At each stage, the test waits for an assertion to pass, then send an
	 * event to wake the application for the next step.
	 */
	
	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	
	private final ApplicationRunner application = new ApplicationRunner();
	
	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper();
		auction.announceClosed();
		application.showSniperHasLostAuction();
	}
	
	@After
	public void stopAuction() {
		auction.stop();
	}
	
	@After
	public void stopApplication() {
		application.stop();
	}
}
