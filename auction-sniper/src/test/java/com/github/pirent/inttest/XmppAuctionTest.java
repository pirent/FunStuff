package com.github.pirent.inttest;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.CountDownLatch;

import junit.framework.Assert;

import org.jivesoftware.smack.XMPPConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.pirent.ApplicationRunner;
import com.github.pirent.AuctionEventListener;
import com.github.pirent.mock.FakeAuctionServer;
import com.github.pirent.xmpp.XMPPAuction;

public class XmppAuctionTest {

	private FakeAuctionServer auctionServer = new FakeAuctionServer("54321");
	private XMPPConnection connection;
	
	@Before
	public void openConnection() throws Exception {
		connection = new XMPPConnection(ApplicationRunner.XMPP_HOSTNAME);
		connection.connect();
		connection.login(ApplicationRunner.SNIPER_ID,
				ApplicationRunner.SNIPER_PASSWORD,
				ApplicationRunner.AUCTION_RESOURCE);
	}
	
	@After
	public void closeConnection() {
		if (connection != null) {
			connection.disconnect();
		}
	}
	
	@Before
	public void startAuction() throws Exception {
		auctionServer.startSellingItem();
	}
	
	@After
	public void stopAuction() {
		auctionServer.stop();
	}

	@Test
	public void receivesEventsFromAuctionServerAfterJoining() throws Exception {
		CountDownLatch auctionWasClosed = new CountDownLatch(1);
		
		XMPPAuction auction = new XMPPAuction(connection, auctionServer.getItemId());
		auction.addAuctionEventListener(auctionClosedListener(auctionWasClosed));
		
		auction.join();
		auctionServer.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auctionServer.announceClosed();
		
		Assert.assertTrue("should have been closed", auctionWasClosed.await(2, SECONDS));
	}

	private AuctionEventListener auctionClosedListener(final CountDownLatch auctionWasClosed) {
		return new AuctionEventListener() {

			@Override
			public void auctionClosed() {
				auctionWasClosed.countDown();
			}

			@Override
			public void currentPrice(int price, int increment, PriceSource priceSource) {
				// not yet implemented
			}
			
		};
	}
}
