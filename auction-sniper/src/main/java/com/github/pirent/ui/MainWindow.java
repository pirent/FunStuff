package com.github.pirent.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.github.pirent.Main;

public class MainWindow extends JFrame {

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_LOST = "Lost";
	public static final String STATUS_BIDDING = "Bidding";

	private final JLabel sniperStatus = createLabel(Main.STATUS_JOINING);
	
	public MainWindow() {
		super("Auction Sniper");
		setName(MAIN_WINDOW_NAME);
		add(sniperStatus);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JLabel createLabel(String initialText) {
		JLabel result = new JLabel(initialText);
		
		result.setName(SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.BLACK));
		
		return result;
	}

	public void showStatus(String status) {
		sniperStatus.setText(status);
	}

}
