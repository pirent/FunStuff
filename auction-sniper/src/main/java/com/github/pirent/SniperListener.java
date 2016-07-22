package com.github.pirent;

import java.util.EventListener;

public interface SniperListener extends EventListener {

	void sniperLost();

	void sniperWon();

	void sniperStateChanged(SniperSnapshot sniperSnapshot);

}
