package com.github.pirent.xmpp;

import static java.lang.String.format;

import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.github.pirent.Auction;
import com.github.pirent.AuctionEventListener;
import com.github.pirent.AuctionMessageTranslator;
import com.github.pirent.Main;
import com.github.pirent.util.Announcer;

public class XMPPAuction implements Auction {
	
	private static final String AUCTION_RESOURCE = "Auction";
	private static final String ITEM_ID_AS_LOGIN = "auction-item-%s";
	private static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;

	private static final Logger LOGGER = Logger.getLogger(XMPPAuction.class.getSimpleName());
	
	private final Announcer<AuctionEventListener> auctionEventListeners = Announcer
			.to(AuctionEventListener.class);
	private final Chat chat;
	
	public XMPPAuction(XMPPConnection connection, String itemId) {
		this.chat = connection.getChatManager().createChat(
				auctionId(itemId, connection),
				new AuctionMessageTranslator(connection.getUser(),
						auctionEventListeners.announce()));
	}
	
	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	@Override
	public void bid(int amount) {
		sendMessage(format(Main.BID_COMMAND_FORMAT, amount));
	}
	
	@Override
	public void join() {
		sendMessage(Main.JOIN_COMMAND_FORMAT);
	}

	private void sendMessage(String message) {
		try {
			chat.sendMessage(message);
			LOGGER.info(chat.getParticipant() + " send message: " + message);
		}
		catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addAuctionEventListener(AuctionEventListener auctionSniper) {
		auctionEventListeners.addListener(auctionSniper);
	}
	
}