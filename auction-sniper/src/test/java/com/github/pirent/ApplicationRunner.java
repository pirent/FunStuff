package com.github.pirent;

import static com.github.pirent.auctionsniper.SniperState.JOINING;
import static com.github.pirent.ui.SnipersTableModel.textFor;

import com.github.pirent.auctionsniper.SniperState;
import com.github.pirent.mock.FakeAuctionServer;
import com.github.pirent.ui.MainWindow;
import com.github.pirent.ui.SnipersTableModel;
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
	public static final String AUCTION_RESOURCE = "Auction";
	
	private static final int NO_STOP_PRICE = Integer.MAX_VALUE;
		
	private AuctionSniperDriver driver;
	
	public void startBiddingIn(final FakeAuctionServer... auctions) {
		startSniper(auctions);
		
		for (FakeAuctionServer auction : auctions) {
			startBiddingFor(auction, NO_STOP_PRICE);
		}
	}
	
	public void startBiddingWithStopPrice(FakeAuctionServer auction, int stopPrice) {
		startSniper(auction);
		startBiddingFor(auction, stopPrice);
	}
	
	private void startBiddingFor(FakeAuctionServer auction, int stopPrice) {
		String itemId = auction.getItemId();
		driver.startBiddingFor(itemId, stopPrice);
		
		// Wait for the status to change to Joining
		// This assertion says that somewhere in the user interface. there is a label
		// that describes the Sniper's state
		driver.showSniperStatus(auction.getItemId(), 0, 0, textFor(JOINING));
	}
	
	private void startSniper(final FakeAuctionServer... auctions) {
		// WindowLicker can control Swing components if they're in the same JVM
		// so we start the Sniper in the new thread
		Thread thread = new Thread("Test Application") {

			@Override
			public void run() {
				try {
					// Call the application through its main() function
					Main.main(arguments(auctions));
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
		
		driver.hasTitle(MainWindow.APPLICATION_TITLE);
		driver.hasColumnTitles();
	}

	protected static String[] arguments(FakeAuctionServer[] auctions) {
		String[] arguments = new String[auctions.length + 3];
		arguments[0] = XMPP_HOSTNAME;
		arguments[1] = SNIPER_ID;
		arguments[2] = SNIPER_PASSWORD;
		for (int i = 0; i < auctions.length; i++) {
			arguments[i + 3] = auctions[i].getItemId();
		}
		return arguments;
	}

	public void stop() {
		if (driver != null) {
			// Dispose the window to make sure it won't be picked up
			// in another test
			driver.dispose();
		}
	}
	
	public void hasShownSniperIsBidding(FakeAuctionServer auction, int lastPrice, int lastBid) {
		driver.showSniperStatus(auction.getItemId(), lastPrice, lastBid,
				SnipersTableModel.textFor(SniperState.BIDDING));
	}

	public void hasShownSniperIsWinning(FakeAuctionServer auction, int winningBid) {
		driver.showSniperStatus(auction.getItemId(), winningBid, winningBid,
				SnipersTableModel.textFor(SniperState.WINNING));
	}

	public void hasShownSniperIsLosing(FakeAuctionServer auction, int lastPrice, int lastBid) {
		driver.showSniperStatus(auction.getItemId(), lastPrice, lastBid,
				SnipersTableModel.textFor(SniperState.LOSING));
	}
	
	public void showsSniperHasLostAuction(FakeAuctionServer auction, int lastPrice, int lastBid) {
		driver.showSniperStatus(auction.getItemId(), lastPrice, lastBid,
				SnipersTableModel.textFor(SniperState.LOST));
	}
	
	public void showsSniperHasWonAuction(FakeAuctionServer auction, int lastPrice) {
		driver.showSniperStatus(auction.getItemId(), lastPrice, lastPrice,
				SnipersTableModel.textFor(SniperState.WON));
	}

}
