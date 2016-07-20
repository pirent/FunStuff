package com.github.pirent;

import com.github.pirent.mock.FakeAuctionServer;
import com.github.pirent.ui.MainWindow;
import com.objogate.wl.swing.driver.ComponentDriver;

/**
 * <p>Enable user to work with the publicly visible features of the application</p>
 * 
 * <p>Hide all the messy code for manipulating Swing, showing things in term of
 * the relationship between a Sniper and its auction. It runs the application as if
 * from the command line, obtaining and holding a reference to its main window
 * for querying the state of the GUI and for shutting down the application at the
 * end of the test</p>
 * 
 * <p>We don't have to do much here, because we rely on WindowLicker to do the hard
 * work: find and control Swing GUI components, synchronize with Swing's threads and
 * event queue. WindowLicker has the concept of a {@link ComponentDriver}: an object
 * that can manipulate a feature in a Swing user interface. If a ComponentDriver can't
 * find the Swing component it refers to, it will time out with an error.</p>
 * 
 * @author pirent
 *
 */
public class ApplicationRunner {
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	public static final String XMPP_HOSTNAME = "localhost";
	public static final String SNIPER_XMPP_ID = "sniper@127.0.0.1/Auction";
	private AuctionSniperDriver driver;
	private String itemId;
	
	public void startBiddingIn(final FakeAuctionServer auction) {
		this.itemId = auction.getItemId();
		
		// WindowLicker can control Swing components if they're in the same JVM
		// so we start the Sniper in the new thread
		Thread thread = new Thread("Test Application") {

			@Override
			public void run() {
				try {
					// Call the application through its main() function
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD,
							auction.getItemId().toString());
				}
				catch (Exception e) {
					// Later development stage will handle exceptions properly
					System.err.println("Got exception when running main: " + e);
				}
			}
			
		};
		thread.setDaemon(true);
		thread.start();
		
		// We turn down the timeout period for finding frames and components
		// Default values are longer than this and it will slow down the tests
		// One second will be enough to smooth over minor runtime delays
		driver = new AuctionSniperDriver(1000);
		
		// Wait for the status to change to Joining
		// This assertion says that somewhere in the user interface. there is a label
		// that describes the Sniper's state
		// FIXME: have no idea what is the input for the last price and last bid
		driver.showSniperStatus(itemId, -1, -1, Main.STATUS_JOINING);
	}

	public void showSniperHasLostAuction() {
		// FIXME: have no idea what is the input for the last price and last bid
		driver.showSniperStatus(itemId, -1, -1, Main.STATUS_LOST);
	}

	public void stop() {
		if (driver != null) {
			// Dispose the window to make sure it won't be picked up
			// in another test
			driver.dispose();
		}
	}

	public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
		driver.showSniperStatus(itemId, lastPrice, lastBid, MainWindow.STATUS_BIDDING);
	}

	public void hasShownSniperIsWinning(int winningBid) {
		driver.showSniperStatus(itemId, winningBid, winningBid, MainWindow.STATUS_WINNING);
	}

	public void showsSniperHasWonAuction(int lastPrice) {
		driver.showSniperStatus(itemId, lastPrice, lastPrice, MainWindow.STATUS_WON);
	}

}
