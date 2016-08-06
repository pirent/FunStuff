package com.github.pirent.xmpp;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.jivesoftware.smack.XMPPConnection;

import com.github.pirent.Auction;
import com.github.pirent.AuctionHouse;
import com.github.pirent.Item;

public class XMPPAuctionHouse implements AuctionHouse {

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String LOG_FILE_NAME = "auction-sniper.log";
	private static final String LOGGER_NAME = "XMPP Auction Logger";

	private XMPPConnection connection;
	private XMPPFailureReporter failureReporter;

	public XMPPAuctionHouse(String hostname, String username, String password)
			throws Exception {
		connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		
		failureReporter = new LoggingXMPPFailureReporter(makeLogger());
	}

	private Logger makeLogger() {
		Logger logger = Logger.getLogger(LOGGER_NAME);
		logger.setUseParentHandlers(false);
		logger.addHandler(simpleFileHandler());
		return logger;
	}

	private FileHandler simpleFileHandler() {
		try {
			FileHandler handler = new FileHandler(LOG_FILE_NAME);
			handler.setFormatter(new SimpleFormatter());
			return handler;
		}
		catch (Exception e) {
			throw new XMPPAuctionException(
					"Could not create logger FileHandler "
							+ getFullPath(LOG_FILE_NAME), e);
		}
	}

	private String getFullPath(String logFileName) {
		return new File(logFileName).getAbsolutePath();
	}

	@Override
	public Auction auctionFor(Item item) {
		return new XMPPAuction(connection, item.identifier, failureReporter);
	}

	@Override
	public void disconnect() {
		connection.disconnect();
	}

	public static XMPPAuctionHouse connect(String hostname, String username,
			String password) throws Exception {
		return new XMPPAuctionHouse(hostname, username, password);
	}

}
