package com.github.pirent;

import static org.mockito.Mockito.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.pirent.AuctionEventListener.PriceSource;

@RunWith(MockitoJUnitRunner.class)
public class AuctionMessageTranslatorTest {

	public static final Chat UNUSED_CHAT = null;

	private static final String SNIPER_ID = null;
	
	@Mock
	private AuctionEventListener listener;
	
	private AuctionMessageTranslator translator;
	
	@Before
	public void init() {
		translator = new AuctionMessageTranslator(SNIPER_ID, listener);
	}
	
	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() {
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: CLOSE;");
		
		translator.processMessage(UNUSED_CHAT, message);
		
		verify(listener).auctionClosed();
	}
	
	@Test
	public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		
		translator.processMessage(UNUSED_CHAT, message);
		
		verify(listener).currentPrice(192, 7, PriceSource.FROM_OTHER_SNIPPER);
	}
	
	@Test
	public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5, Bidder: " + SNIPER_ID + ";");
		
		translator.processMessage(UNUSED_CHAT, message);
		
		verify(listener).currentPrice(234, 5, PriceSource.FROM_SNIPER);
	}
}
