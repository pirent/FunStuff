package com.github.pirent.auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {

	void sniperStateChanged(SniperSnapshot sniperSnapshot);

}
