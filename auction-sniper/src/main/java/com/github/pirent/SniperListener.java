package com.github.pirent;

import java.util.EventListener;

public interface SniperListener extends EventListener {

	void sniperLost();

	void sniperBidding(SniperSnapshot sniperState);

	void sniperWinning();

	void sniperWon();

	void sniperStateChanged(SniperSnapshot sniperSnapshot);

}
