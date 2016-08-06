package com.github.pirent.xmpp;

import java.util.logging.Logger;

public class LoggingXMPPFailureReporter implements XMPPFailureReporter {

	private final Logger logger;
	
	public LoggingXMPPFailureReporter(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void cannotTranslateMessage(String auctionId, String failedMessage,
			Exception exception) {
		String formatMessage = "<%s> Could not translate message \"%s\" because \"%s\"";
		logger.severe(String.format(formatMessage, auctionId, failedMessage, exception));
	}

}
