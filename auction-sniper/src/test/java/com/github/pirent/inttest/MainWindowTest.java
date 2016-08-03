package com.github.pirent.inttest;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.github.pirent.AuctionSniperDriver;
import com.github.pirent.SniperPortfolio;
import com.github.pirent.ui.MainWindow;
import com.github.pirent.ui.UserRequestListener;
import com.objogate.wl.swing.probe.ValueMatcherProbe;

public class MainWindowTest {

	private final MainWindow mainWindow = new MainWindow(new SniperPortfolio());
	private final AuctionSniperDriver driver = new AuctionSniperDriver(200);
	
	@Test
	public void makesUserRequestWhenJoinButtonClicked() {
		// What to test: using a probe compares a value against a Hamcrest matcher
		// wait for the UserRequestListener's joinAuction() to be called
		// with the right auction identifier 
		
		// A probe is an object that checks for a given state.
		final ValueMatcherProbe<String> buttonProbe = new ValueMatcherProbe<String>(
				equalTo("an item id"), "join request");
		
		mainWindow.addUserRequestListener(new UserRequestListener() {

			@Override
			public void joinAuction(String itemId) {
				buttonProbe.setReceivedValue(itemId);
			}
			
		});
		
		driver.startBiddingFor("aan item id");
		
		// A driver's check() method repeatedly fires the given probe
		// until it's satisfied or times out.
		driver.check(buttonProbe);
	}
}
