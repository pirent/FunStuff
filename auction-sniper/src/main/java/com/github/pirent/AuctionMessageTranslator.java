package com.github.pirent;

import java.util.HashMap;
import java.util.Map;

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
	public void processMessage(Chat chat, Message message) {
		Map<String, String> event = unpackEventFrom(message);
		
		/*
		 * Delegate the handling of an interpreted event to a collaborator
		 */
		String type = event.get("Event");
		if ("CLOSE".equals(type)) {
			listener.auctionClosed();
		}
		else if ("PRICE".equals(type)) {
			listener.currentPrice(Integer.parseInt(event.get("CurrentPrice")),
					Integer.parseInt(event.get("Increment")));
		}
	}

	private Map<String, String> unpackEventFrom(Message message) {
		Map<String, String> event = new HashMap<String, String>();
		
		for (String element : message.getBody().split(";")) {
			String[] pair = element.split(":");
			event.put(pair[0].trim(), pair[1].trim());
		}
		
		return event;
	}

}
