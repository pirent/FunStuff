package com.github.pirent;

import org.jivesoftware.smack.XMPPConnection;

import com.github.pirent.xmpp.XMPPAuction;

public class XMPPAuctionHouse implements AuctionHouse {

	public static final String AUCTION_RESOURCE = "Auction";

	private XMPPConnection connection;

	public XMPPAuctionHouse(String hostname, String username, String password)
			throws Exception {
		connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
	}

	@Override
	public Auction auctionFor(Item item) {
		return new XMPPAuction(connection, item.identifier);
	}

	@Override
	public void disconnect() {
		connection.disconnect();
	}

	public static XMPPAuctionHouse connect(String hostname, String username,
			String password) throws Exception {
		return new XMPPAuctionHouse(hostname, username, password);
	}

}
