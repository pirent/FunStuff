package com.github.pirent.xmpp;

import static java.lang.String.format;

import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.github.pirent.Auction;
import com.github.pirent.AuctionEventListener;
import com.github.pirent.util.Announcer;

public class XMPPAuction implements Auction {
	
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Event: BID; Price: %d";
	
	private static final String AUCTION_RESOURCE = "Auction";
	private static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
	private static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;

	private static final Logger LOGGER = Logger.getLogger(XMPPAuction.class.getSimpleName());
	private static final Logger CHAT_DISCONNECTOR_LOGGER = Logger.getLogger("Chat Disconnector");
	
	private final Announcer<AuctionEventListener> auctionEventListeners = Announcer
			.to(AuctionEventListener.class);
	private final Chat chat;
	
	public XMPPAuction(XMPPConnection connection, String itemId, XMPPFailureReporter failureReporter) {
		AuctionMessageTranslator translator = translatorFor(connection, failureReporter);
		this.chat = connection.getChatManager().createChat(
				auctionId(itemId, connection), translator);
		addAuctionEventListener(chatDisconnectorFor(translator));
	}
	
	private AuctionMessageTranslator translatorFor(XMPPConnection connection,
			XMPPFailureReporter failureReporter) {
		return new AuctionMessageTranslator(connection.getUser(),
				auctionEventListeners.announce(), failureReporter);
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}
	
	private AuctionEventListener chatDisconnectorFor(
			final AuctionMessageTranslator translator) {
		return new AuctionEventListener() {
			
			@Override
			public void currentPrice(int i, int j, PriceSource priceSource) {}
			
			@Override
			public void auctionFailed() {
				CHAT_DISCONNECTOR_LOGGER.info("Disconnect translator for " + chat.getParticipant());
				chat.removeMessageListener(translator);
			}
			
			@Override
			public void auctionClosed() {}
		};
	}

	@Override
	public void bid(int amount) {
		sendMessage(format(BID_COMMAND_FORMAT, amount));
	}
	
	@Override
	public void join() {
		sendMessage(JOIN_COMMAND_FORMAT);
	}

	private void sendMessage(String message) {
		try {
			chat.sendMessage(message);
			LOGGER.info(chat.getParticipant() + " send command: " + message);
		}
		catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addAuctionEventListener(
			AuctionEventListener auctionEventListener) {
		auctionEventListeners.addListener(auctionEventListener);
	}
	
}