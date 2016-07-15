package com.github.pirent;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * When it receives a raw message from the auction, translates it into something
 * that represents an auction event which, eventually, will prompt a Sniper
 * action and a change in the user interface.
 * 
 * @author pirent
 *
 */
public class AuctionMessageTranslator implements MessageListener {

	private AuctionEventListener listener;
	
	public AuctionMessageTranslator(AuctionEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void processMessage(Chat unusedChat, Message message) {
		/*
		 * Delegate the handling of an interpreted event to a collaborator
		 */
		listener.auctionClosed();
	}

}
