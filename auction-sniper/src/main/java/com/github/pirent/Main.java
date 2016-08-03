package com.github.pirent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import com.github.pirent.ui.MainWindow;

public class Main {

	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "Lost";
	public static final String SNIPER_STATUS_NAME = "sniper status";

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Event: BID; Price: %d";

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;

	private final SniperPortfolio portfolio = new SniperPortfolio();
	private MainWindow ui;
	
	public Main() throws Exception {
		startUserInterface();
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				ui = new MainWindow(portfolio);
			}
		});
	}

	public static void main(String... args) throws Exception {
		Main main = new Main();
		XMPPAuctionHouse auctionHouse = XMPPAuctionHouse.connect(
				args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
		main.disconnectWhenUICloses(auctionHouse);
		main.addUserRequestListenerFor(auctionHouse);
	}
	
	private void disconnectWhenUICloses(final AuctionHouse auctionHouse) {
		ui.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				auctionHouse.disconnect();
			}

		});
	}

	private void addUserRequestListenerFor(final AuctionHouse auctionHouse) {
		ui.addUserRequestListener(new SniperLauncher(auctionHouse, portfolio));
	}

}
