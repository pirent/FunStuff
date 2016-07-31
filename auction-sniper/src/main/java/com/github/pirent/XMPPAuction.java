package com.github.pirent;

import static java.lang.String.format;

import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuction implements Auction {

	private static final Logger LOGGER = Logger.getLogger(XMPPAuction.class.getSimpleName());
	
	private final Chat chat;
	
	public XMPPAuction(Chat chat) {
		this.chat = chat;
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
	
}