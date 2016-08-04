package com.github.pirent.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.github.pirent.Item;
import com.github.pirent.auctionsniper.SniperPortfolio;
import com.github.pirent.util.Announcer;

public class MainWindow extends JFrame {

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	public static final String APPLICATION_TITLE = "Auction Sniper";
	public static final String NEW_ITEM_ID_NAME = "item id";
	public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";
	public static final String JOIN_BUTTON_NAME = "join button";
	
	private static final long serialVersionUID = 1L;
	private static final String SNIPERS_TABLE_NAME = "Sniper Table";
		
	private final Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);

	public MainWindow(SniperPortfolio portfolio) {
		super(APPLICATION_TITLE);
		setName(MAIN_WINDOW_NAME);
		fillContentPane(makeSnipersTable(portfolio), makeControls());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void fillContentPane(JTable snipersTable, JPanel controls) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(controls, BorderLayout.NORTH);
		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
	}

	private JTable makeSnipersTable(SniperPortfolio portfolio) {
		SnipersTableModel model = new SnipersTableModel();
		portfolio.addPortfolioListener(model);
		final JTable snipersTable = new JTable(model);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		return snipersTable;
	}
	
	private JPanel makeControls() {
		JPanel controls = new JPanel(new FlowLayout());
		
		final JTextField itemIdField = new JTextField();
		itemIdField.setColumns(25);
		itemIdField.setName(NEW_ITEM_ID_NAME);
		controls.add(itemIdField);
		
		final JFormattedTextField stopPriceField = new JFormattedTextField();
		stopPriceField.setColumns(25);
		stopPriceField.setName(NEW_ITEM_STOP_PRICE_NAME);
		stopPriceField.setFormatterFactory(new AbstractFormatterFactory() {
			
			@Override
			public AbstractFormatter getFormatter(JFormattedTextField tf) {
				return new AbstractFormatter() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public String valueToString(Object value) throws ParseException {
						return String.valueOf(value);
					}
					
					@Override
					public Object stringToValue(String text) throws ParseException {
						return Integer.parseInt(text);
					}
				};
			}
		});
		controls.add(stopPriceField);
		
		JButton joinAuctionButton = new JButton("Join Auction");
		joinAuctionButton.setName(JOIN_BUTTON_NAME);
		controls.add(joinAuctionButton);
		
		joinAuctionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Item item = new Item(itemId(), stopPrice());
				userRequests.announce().joinAuction(item);
			}

			private int stopPrice() {
				return ((Number) stopPriceField.getValue()).intValue();
			}
			
			private String itemId() {
				return itemIdField.getText();
			}
		});
		
		return controls;
	}

	public void addUserRequestListener(UserRequestListener userRequestListener) {
		userRequests.addListener(userRequestListener);
	}

}
