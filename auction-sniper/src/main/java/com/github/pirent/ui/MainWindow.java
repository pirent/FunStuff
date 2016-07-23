package com.github.pirent.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.github.pirent.SniperSnapshot;

public class MainWindow extends JFrame {

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	
//	public static final String STATUS_LOST = "Lost";
//	public static final String STATUS_BIDDING = "Bidding";
//	public static final String STATUS_WINNING = "Winning";
//	public static final String STATUS_WON = "Won";
//	public static final String STATUS_JOINING = "Joining";
	
	private static final String APPLICATION_TITLE = "Auction Sniper";	
	private static final long serialVersionUID = 1L;
	private static final String SNIPERS_TABLE_NAME = "Sniper Table";
	
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	public MainWindow() {
		super(APPLICATION_TITLE);
		setName(MAIN_WINDOW_NAME);
		fillContentPane(makeSniperTable());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void fillContentPane(JTable snipersTable) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
	}

	private JTable makeSniperTable() {
		final JTable snipersTable = new JTable(snipers);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		return snipersTable;
	}

	@Deprecated
	public void showState(String statusText) {
		snipers.setStatusText(statusText);
	}

	public void sniperStateChanged(SniperSnapshot sniperState) {
		snipers.sniperStateChanged(sniperState);
	}

}
