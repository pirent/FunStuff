package com.github.pirent;

import static org.mockito.Mockito.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuctionMessageTranslatorTest {

	public static final Chat UNUSED_CHAT = null;
	
	@Mock
	private AuctionEventListener listener;
	
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);
	
	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() {
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: CLOSE;");
		
		translator.processMessage(UNUSED_CHAT, message);
		
		verify(listener).auctionClosed();
	}
}
