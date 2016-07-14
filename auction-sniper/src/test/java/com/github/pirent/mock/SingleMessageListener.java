package com.github.pirent.mock;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * A {@link MessageListener} for a chat to accept messages from the Sniper.
 * 
 * For now, we only have one chat in the server, we expect to process
 * only one message at a time. We also must coordinate between the thread
 * that runs the test and the Smack thread that feeds messages to the listener
 * - the test has to wait for messages to arrive and time out if they don't - 
 * so we'll use a single-element {@link BlockingQueue}
 * 
 * @author pirent
 *
 */
public class SingleMessageListener implements MessageListener {

	private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);

	@Override
	public void processMessage(Chat chat, Message message) {
		messages.add(message);
	}

	/**
	 * Check that the Listener has received a message within the timeout period.
	 * 
	 * @param messageMatcher
	 *            pull up the {@link Matcher} clause to give it caller more
	 *            flexibility in defining what it will accept as a message
	 * 
	 * @throws InterruptedException
	 */
	public void receiveAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
		final Message message = messages.poll(5, SECONDS);
		assertThat("Message is not recevied within timeout period",
				message, is(notNullValue()));
		assertThat("Message does not match", message.getBody(), messageMatcher);
	}

}
