package com.github.pirent.xmpp;

public interface XMPPFailureReporter {
	void cannotTranslateMessage(String auctionId, String failedMessage,
			Exception exception);
}
