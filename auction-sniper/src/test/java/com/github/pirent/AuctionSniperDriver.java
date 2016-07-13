package com.github.pirent;

import org.hamcrest.Matchers;

import com.github.pirent.ui.MainWindow;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
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
	public void showSniperStatus(String statusText) {
		// Look for the relevant label in the user interface
		// and confirms that it shows the given status
		new JLabelDriver(this, named(Main.SNIPER_STATUS_NAME)).hasText(Matchers
				.equalTo(statusText));
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

}
