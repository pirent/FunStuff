package com.github.pirent;

import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static java.lang.String.valueOf;

import javax.swing.table.JTableHeader;

import com.github.pirent.ui.MainWindow;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTableHeaderDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

/**
 * An extension of a WindowLicker's {@link JFrameDriver} specialized for our
 * tests.
 * 
 * @author pirent
 *
 */
public class AuctionSniperDriver extends JFrameDriver {

	@SuppressWarnings("unchecked")
	public AuctionSniperDriver(int timeoutMillis) {
		// Attempts to find a visible top-level window for the Auction Sniper
		// within the given timeout
		super(new GesturePerformer(), JFrameDriver.topLevelFrame(
				named(MainWindow.MAIN_WINDOW_NAME), showingOnScreen()),
				new AWTEventQueueProber(timeoutMillis, 100));
	}

	@SuppressWarnings("unchecked")
	public void showSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
		// Look for the relevant singke-cell JTable in the user interface
		// and confirms that it shows the given status
		JTableDriver table = new JTableDriver(this);
		table.hasRow(matching(withLabelText(itemId),
				withLabelText(valueOf(lastPrice)),
				withLabelText(valueOf(lastBid)),
				withLabelText(statusText)));
	}

	@SuppressWarnings("unchecked")
	public void hasColumnTitles() {
		JTableHeaderDriver headers = new JTableHeaderDriver(this,
				JTableHeader.class);
		headers.hasHeaders(matching(withLabelText("Item"),
				withLabelText("Last Price"), withLabelText("Last Bid"),
				withLabelText("State")));
	}

}
