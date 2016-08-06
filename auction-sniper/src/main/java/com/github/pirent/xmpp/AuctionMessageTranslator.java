package com.github.pirent.xmpp;

import static com.github.pirent.AuctionEventListener.PriceSource.FROM_OTHER_SNIPPER;
import static com.github.pirent.AuctionEventListener.PriceSource.FROM_SNIPER;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import com.github.pirent.AuctionEventListener;
import com.github.pirent.MissingValueException;
import com.github.pirent.AuctionEventListener.PriceSource;

/**
 * When it receives a raw message from the auction, translates it into something
 * that represents an auction event which, eventually, will prompt a Sniper
 * action and a change in the user interface.
 * 
 * @author pirent
 *
 */
public class AuctionMessageTranslator implements MessageListener {

	private static final String EVENT_CLOSE_TYPE = "CLOSE";
	private static final String EVENT_PRICE_TYPE = "PRICE";
	private static final Logger LOGGER = Logger.getLogger(AuctionMessageTranslator.class.getSimpleName());
	
	private final String snipperId;
	private final AuctionEventListener listener;
	private final XMPPFailureReporter failureReporter;
	
	public AuctionMessageTranslator(String snipperId,
			AuctionEventListener listener, XMPPFailureReporter failureReporter) {
		this.snipperId = snipperId;
		this.listener = listener;
		this.failureReporter = failureReporter;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		String messageBody = message.getBody();
		try {
			translate(messageBody);
		}
		catch (RuntimeException parseException) {
			failureReporter.cannotTranslateMessage(snipperId, messageBody, parseException);
			listener.auctionFailed();
		}
	}
	
	private void translate(String messageBody) {
		LOGGER.info("Receive auction event: " + messageBody);
		AuctionEvent event = AuctionEvent.from(messageBody);
		
		// Delegate the handling of an interpreted event to a collaborator
		String type = event.type();
		if (EVENT_CLOSE_TYPE.equals(type)) {
			listener.auctionClosed();
		}
		else if (EVENT_PRICE_TYPE.equals(type)) {
			listener.currentPrice(event.currentPrice(), event.increment(),
					event.isFrom(snipperId));
		}
	}

	private static class AuctionEvent {
		
		private final Map<String, String> fields = new HashMap<String, String>();
		
		public static AuctionEvent from(String messageBody) {
			AuctionEvent event = new AuctionEvent();
			
			for (String field : fieldsIn(messageBody)) {
				event.addField(field);
			}
			
			return event;
		}

		public PriceSource isFrom(String snipperId) {
			return snipperId.equals(bidder()) ? FROM_SNIPER : FROM_OTHER_SNIPPER;
		}

		private String bidder() {
			return get("Bidder");
		}

		private static String[] fieldsIn(String messageBody) {
			return messageBody.split(";");
		}

		public int increment() {
			return getInt("Increment");
		}

		public int currentPrice() {
			return getInt("CurrentPrice");
		}

		public String type() {
			return get("Event");
		}

		private String get(String fieldName) {
			String value = fields.get(fieldName);
			if (null == value) {
				throw new MissingValueException(fieldName);
			}
			return value;
		}
		
		private int getInt(String fieldName) {
			return Integer.parseInt(get(fieldName));
		}
		
		private void addField(String field) {
			String[] pair = field.split(":");
			fields.put(pair[0].trim(), pair[1].trim());
		}
	}
}
