package com.github.pirent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.github.pirent.ui.MainWindow;
import com.github.pirent.ui.SnipersTableModel;

public class Main {

	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "Lost";
	public static final String SNIPER_STATUS_NAME = "sniper status";

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID = 3;
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Event: BID; Price: %d";

	private final SnipersTableModel sniperListener = new SnipersTableModel();
	private MainWindow ui;
	
	@SuppressWarnings("unused")
	/**
	 * To make sure the chat is not garbage-collected by the Java runtime.
	 * For application specific purpose.
	 */
	private Collection<Chat> notToBeGCd = new ArrayList<Chat>();

	public static void main(String... args) throws Exception {
		Main main = new Main();
		XMPPConnection connection = connection(args[ARG_HOSTNAME],
				args[ARG_USERNAME], args[ARG_PASSWORD]);
		main.disconnectWhenUICloses(connection);
		main.joinAuction(connection, args[ARG_ITEM_ID]);
	}

	private static XMPPConnection connection(String hostname, String username,
			String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);

		return connection;
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	public Main() throws Exception {
		startUserInterface();
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				ui = new MainWindow(sniperListener);
			}
		});
	}
	
	private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
		Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), null);
		
		notToBeGCd.add(chat);
		
		Auction auction = new XMPPAuction(chat); 
		chat.addMessageListener(new AuctionMessageTranslator(connection
				.getUser(), new AuctionSniper(itemId, auction,
				new SwingThreadSniperListener(sniperListener))));
		
		auction.join();
	}

	private void disconnectWhenUICloses(final XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				connection.disconnect();
			}
			
		});
	}
	
	/**
	 * A Decorator to start {@link SniperListener} in a new Swing thread
	 * 
	 * @author pirent
	 *
	 */
	public class SwingThreadSniperListener implements SniperListener {
		
		private final SniperListener sniperListener;

		public SwingThreadSniperListener(SniperListener sniperListener) {
			super();
			this.sniperListener = sniperListener;
		}

		@Override
		public void sniperStateChanged(final SniperSnapshot sniperSnapshot) {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					sniperListener.sniperStateChanged(sniperSnapshot);
				}
			});
		}

	}
}
