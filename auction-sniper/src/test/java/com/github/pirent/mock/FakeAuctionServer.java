package com.github.pirent.mock;

import static com.github.pirent.xmpp.XMPPAuction.BID_COMMAND_FORMAT;
import static com.github.pirent.xmpp.XMPPAuction.JOIN_COMMAND_FORMAT;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * <p>A stub server that allows test to check how the Auction Sniper
 * interacts with an auction using XMPP messages.</p>
 * 
 * <p>It has three responsibilities:
 * <ul>
 * <li>connect to an XMPP message broker and accept request to join
 * the chat from the Sniper</li>
 * <li>receive chat messages from the Sniper or fail if no message
 * arrives within some timeout</li>
 * <li>allow test to send back events (ie, messages back to the Sniper
 * with predefined format</li>
 * </ul></p>
 * 
 * @author pirent
 *
 */
public class FakeAuctionServer {

	public static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "localhost";
	public static final String AUCTION_PASSWORD = "auction";
	
	private final SingleMessageListener messageListener = new SingleMessageListener();
	
	private final String itemId;
	private final XMPPConnection connection;
	private Chat currentChat;
	
	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		connection = new XMPPConnection(XMPP_HOSTNAME);
	}

	public void startSellingItem() throws XMPPException {
		// First, it connects to the XMPP broker, using the item identifier
		// to construct the login name.
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
		
		// Then, it registers a ChatManagerListener
		// Smack will call this listener with a Chat object
		// that represents the session when a Sniper connects in
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				// hold the chat so it can exchange messages with the Sniper
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});		
	}

	/**
	 * To know whether a Join message has arrived.
	 * 
	 * @throws InterruptedException
	 */
	public void hasReceivedJoinRequestFromSniper(String sniperId) throws InterruptedException {
		receiveAMessageMatching(sniperId, equalTo(JOIN_COMMAND_FORMAT));
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;");
	}

	public void stop() {
		connection.disconnect();
	}

	public String getItemId() {
		return itemId;
	}

	public void reportPrice(int price, int increment, String bidderName)
			throws XMPPException {
		currentChat
				.sendMessage(String
						.format("SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s",
								price, increment, bidderName));
	}

	public void hasReceiveBid(int bid, String sniperId) throws InterruptedException {
		receiveAMessageMatching(sniperId,
				equalTo(String.format(BID_COMMAND_FORMAT, bid)));
	}
	
	private void receiveAMessageMatching(String sniperId,
			Matcher<? super String> messageMatcher) throws InterruptedException {
		messageListener.receiveAMessage(messageMatcher);
		
		// Check the Sniper's identifier after checking the contents of the message
		// This forces the server to wait until the message has arrived, which mean that
		// it must have accepted the connection and set up currentChat
		assertThat("Current chat's participant should match sniper",
				currentChat.getParticipant(), equalTo(sniperId));
	}

	public void sendInvalidMessageContaining(String brokenMessage) throws XMPPException {
		currentChat.sendMessage(brokenMessage);
	}

}
