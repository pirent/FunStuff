package com.github.pirent;

import static org.mockito.Mockito.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.pirent.AuctionEventListener.PriceSource;
import com.github.pirent.xmpp.AuctionMessageTranslator;
import com.github.pirent.xmpp.XMPPFailureReporter;

@RunWith(MockitoJUnitRunner.class)
public class AuctionMessageTranslatorTest {

	public static final Chat UNUSED_CHAT = null;

	private static final String SNIPER_ID = "localhost";
	
	@Mock
	private AuctionEventListener listener;
	
	@Mock
	private XMPPFailureReporter failureReporter;
	
	private AuctionMessageTranslator translator;
	
	@Before
	public void init() {
		translator = new AuctionMessageTranslator(SNIPER_ID, listener, failureReporter);
	}
	
	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() {
		String message = "SQLVersion: 1.1; Event: CLOSE;";
		
		translator.processMessage(UNUSED_CHAT, message(message));
		
		verify(listener).auctionClosed();
	}
	
	@Test
	public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
		String message = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;";
		
		translator.processMessage(UNUSED_CHAT, message(message));
		
		verify(listener).currentPrice(192, 7, PriceSource.FROM_OTHER_SNIPPER);
	}
	
	@Test
	public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
		String message = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: "
				+ SNIPER_ID + ";";
		
		translator.processMessage(UNUSED_CHAT, message(message));
		
		verify(listener).currentPrice(234, 5, PriceSource.FROM_SNIPER);
	}
	
	@Test
	public void notifiesAuctionFailedWhenBadMessageReceived() {
		String badMessage = "a bad message";
		
		translator.processMessage(UNUSED_CHAT, message(badMessage));
		
		expectFailureWithMessage(badMessage);
	}
	
	@Test
	public void notifiesAuctionFailedWhenEventTypeIsMissing() {
		String message = "SOLVersion: 1.1; CurrentPrice: 234; Increment: 5; Bidder: "
				+ SNIPER_ID + ";";

		translator.processMessage(UNUSED_CHAT, message(message));

		expectFailureWithMessage(message);
	}
	
	@Test
	public void notifiesAuctionFailedWhenBidderIsMissing() {
		String message = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5;";

		translator.processMessage(UNUSED_CHAT, message(message));

		expectFailureWithMessage(message);
	}
	
	@Test
	public void notifiesAuctionFailedWhenCurrentPriceIsMissing() {
		String message = "SOLVersion: 1.1; Event: PRICE; Increment: 5; Bidder: " + SNIPER_ID + ";";

		translator.processMessage(UNUSED_CHAT, message(message));

		expectFailureWithMessage(message);
	}
	
	private Message message(String body) {
		Message message = new Message();
		message.setBody(body);
		return message;
	}
	
	private void expectFailureWithMessage(final String badMessage) {
		verify(listener).auctionFailed();
		verify(failureReporter).cannotTranslateMessage(Mockito.eq(SNIPER_ID),
				Mockito.eq(badMessage), Mockito.any(Exception.class));
	}
}
