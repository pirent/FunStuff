package com.github.pirent.endtoend;

import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Test;

import com.github.pirent.ApplicationRunner;
import com.github.pirent.mock.FakeAuctionServer;

public class AuctionSniperEndToEndTest {

	/*
	 * Not like the unit testing, where a test drive object in a same thread,
	 * so it can make assertion about its state and behavior.
	 * 
	 * Strategy: control the application and step through the scenario.
	 * At each stage, the test waits for an assertion to pass, then send an
	 * event to wake the application for the next step.
	 */
	
	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	
	private final ApplicationRunner application = new ApplicationRunner();
	
	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auction.announceClosed();
		application.showSniperHasLostAuction();
	}
	
	@Test
	public void sniperMakesAHigherBidButLoses() throws XMPPException, InterruptedException {
		auction.startSellingItem();
		
		application.startBiddingIn(auction);
		// Wait for the stub auction to receive the Join request
		// before continuing with the test
		// Use this assertion to synchronize the Sniper with the auction
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		
		// Tell the stub auction to send message back to Sniper with the news about price
		auction.reportPrice(1000, 98, "other bidder");
		// Check if Sniper shows that it's now bidding after receiving the price update msg
		application.hasShownSniperIsBidding();
		
		// Tell the stub auction to check that it has received a bid from the Sniper
		// that is equal to the last price plus the minimum increment
		auction.hasReceiveBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.announceClosed();
		application.showSniperHasLostAuction();
	}
	
	@Test
	public void sniperWinsAnAuctionByBiddingHigher() throws Exception {
		auction.startSellingItem();
		
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding();
		
		auction.hasReceiveBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		application.hasShownSniperIsWinning();
		
		auction.announceClosed();
		application.showsSniperHasWonAuction();
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
