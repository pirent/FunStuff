package com.github.pirent.client;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessagesReceiver implements MessageListener {

	private ConnectionFactory connectionFactory;
	private Queue queue;

	private String user;
	private String password;

	public MessagesReceiver() throws Exception {
		this(null, null);
	}

	public MessagesReceiver(String ip, String port) throws Exception {
		this(ip, port, "admin", "secret");
	}

	public MessagesReceiver(String ip, String port, String user, String password) throws Exception {
		this.user = user;
		this.password = password;

		JndiLookup jndiLookup = new JndiLookup(ip, port, user, password);
		connectionFactory = jndiLookup.lookup(ConnectionFactory.class, "jms/RemoteConnectionFactory");
		queue = jndiLookup.lookup(Queue.class, "jms/queue/test");
	}

	public static void main(String[] args) throws Exception {
		MessagesReceiver receiver;

		switch(args.length) {
			case 4:
				receiver = new MessagesReceiver(args[0], args[1], args[2], args[3]);
				break;
			case 2:
				receiver = new MessagesReceiver(args[0], args[1]);
				break;
			default:
				receiver = new MessagesReceiver();
		}

		receiver.consume();

		while(true) {
			TimeUnit.SECONDS.sleep(1);
		}
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				System.out.println("Received: " + ((TextMessage) message).getText());
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Received: " + message);
		}
	}

	private void consume() throws Exception {
		final Connection connection;

		if (user != null && password != null) {
			connection = connectionFactory.createConnection(user, password);
		} else {
			connection = connectionFactory.createConnection();
		}

		connection.setExceptionListener(new ExceptionListener() {

			@Override
			public void onException(JMSException exception) {
				System.out.println("Connection has exception...");
				exception.printStackTrace();
			}
		});

		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		final MessageConsumer consumer = session.createConsumer(queue);

		consumer.setMessageListener(this);
		connection.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				try {
					consumer.close();
					session.close();
					connection.close();
				}
				catch (JMSException e) {
					System.out.println("Exception while clean up JMS resources...");
					e.printStackTrace();
				}
			}
		});
	}
}