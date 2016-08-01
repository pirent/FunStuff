package com.github.pirent.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.github.pirent.SniperSnapshot;
import com.github.pirent.util.Announcer;

public class MainWindow extends JFrame {

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	public static final String APPLICATION_TITLE = "Auction Sniper";
	
	private static final long serialVersionUID = 1L;
	private static final String SNIPERS_TABLE_NAME = "Sniper Table";
	public static final String NEW_ITEM_ID_NAME = "item id";
	public static final String JOIN_BUTTON_NAME = "join button";
	
	private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getSimpleName());
	
	private final SnipersTableModel snipers;
	
	private final Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);
	
	public MainWindow(SnipersTableModel snipers) {
		super(APPLICATION_TITLE);
		this.snipers = snipers;
		setName(MAIN_WINDOW_NAME);
		fillContentPane(makeSniperTable(), makeControls());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JPanel makeControls() {
		JPanel controls = new JPanel(new FlowLayout());
		
		final JTextField itemIdField = new JTextField();
		itemIdField.setColumns(25);
		itemIdField.setName(NEW_ITEM_ID_NAME);
		controls.add(itemIdField);
		
		JButton joinAuctionButton = new JButton("Join Auction");
		joinAuctionButton.setName(JOIN_BUTTON_NAME);
		controls.add(joinAuctionButton);
		
		joinAuctionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Perform action");
				userRequests.announce().joinAuction(itemIdField.getText());
			}
		});
		
		return controls;
	}

	private void fillContentPane(JTable snipersTable, JPanel controls) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(controls, BorderLayout.NORTH);
		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
	}

	private JTable makeSniperTable() {
		final JTable snipersTable = new JTable(snipers);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		return snipersTable;
	}

	public void sniperStateChanged(SniperSnapshot sniperState) {
		snipers.sniperStateChanged(sniperState);
	}

	public void addUserRequestListener(UserRequestListener userRequestListener) {
		userRequests.addListener(userRequestListener);
	}

}
