package com.github.pirent.inttest;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.github.pirent.AuctionSniperDriver;
import com.github.pirent.Item;
import com.github.pirent.auctionsniper.SniperPortfolio;
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
		final ValueMatcherProbe<Item> itemProbe = new ValueMatcherProbe<Item>(
				equalTo(new Item("an item id", 789)), "join request");
		
		mainWindow.addUserRequestListener(new UserRequestListener() {

			@Override
			public void joinAuction(Item item) {
				itemProbe.setReceivedValue(item);
			}
			
		});
		
		// FIXME: there is a bug here that swallow the first 'a' character
		driver.startBiddingFor("aan item id", 789);
		
		// A driver's check() method repeatedly fires the given probe
		// until it's satisfied or times out.
		driver.check(itemProbe);
	}
}
