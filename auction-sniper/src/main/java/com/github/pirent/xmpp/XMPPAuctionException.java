package com.github.pirent.xmpp;

public class XMPPAuctionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public XMPPAuctionException(String message, Exception e) {
		super(message, e);
	}

}
