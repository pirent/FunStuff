package com.github.pirent.auctionsniper;

import com.github.pirent.Defect;

public enum SniperState {
	JOINING {

		@Override
		public SniperState whenAuctionClosed() {
			return LOST;
		}
		
	},
	BIDDING {

		@Override
		public SniperState whenAuctionClosed() {
			return LOST;
		}
		
	},
	WINNING {

		@Override
		public SniperState whenAuctionClosed() {
			return WON;
		}
		
	},
	LOSING {

		@Override
		public SniperState whenAuctionClosed() {
			return LOST;
		}
		
	},
	FAILED {

		@Override
		public SniperState whenAuctionClosed() {
			return FAILED;
		}
		
	},
	LOST,
	WON;

	public SniperState whenAuctionClosed() {
		throw new Defect("Auction is already closed");
	}
}
