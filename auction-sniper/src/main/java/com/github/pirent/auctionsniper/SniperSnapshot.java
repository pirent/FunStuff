package com.github.pirent.auctionsniper;

import com.github.pirent.Item;

/**
 * A description of the Sniper's relationship with the auction at this moment in time.
 * 
 * @author pirent
 *
 */
public class SniperSnapshot {

	private final Item item;
	private final int lastPrice;
	private final int lastBid;
	private final SniperState sniperState;

	public SniperSnapshot(Item item, int lastPrice, int lastBid,
			SniperState sniperState) {
		super();
		this.item = item;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
		this.sniperState = sniperState;
	}

	public Item getItem() {
		return item;
	}

	public SniperState getSniperState() {
		return sniperState;
	}

	public int getLastPrice() {
		return lastPrice;
	}

	public int getLastBid() {
		return lastBid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + lastBid;
		result = prime * result + lastPrice;
		result = prime * result
				+ ((sniperState == null) ? 0 : sniperState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SniperSnapshot other = (SniperSnapshot) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		}
		else if (!item.equals(other.item))
			return false;
		if (lastBid != other.lastBid)
			return false;
		if (lastPrice != other.lastPrice)
			return false;
		if (sniperState != other.sniperState)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SniperSnapshot [item=" + item + ", lastPrice=" + lastPrice
				+ ", lastBid=" + lastBid + ", sniperState=" + sniperState + "]";
	}

	public static SniperSnapshot joining(Item item) {
		return new SniperSnapshot(item, 0, 0, SniperState.JOINING);
	}

	public SniperSnapshot winning(int newLastPrice) {
		return new SniperSnapshot(item, newLastPrice, lastBid,
				SniperState.WINNING);
	}

	public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
		return new SniperSnapshot(item, newLastPrice, newLastBid,
				SniperState.BIDDING);
	}
	
	public SniperSnapshot losing(int newLastPrice) {
		return new SniperSnapshot(item, newLastPrice, lastBid, SniperState.LOSING);
	}

	public SniperSnapshot closed() {
		return new SniperSnapshot(item, lastPrice, lastBid,
				sniperState.whenAuctionClosed());
	}

	/**
	 * Deciding whether it's referring to the same item via identifier
	 * 
	 * @return
	 */
	public boolean isForSameItemAs(SniperSnapshot otherSnapshot) {
		return this.item.equals(otherSnapshot.getItem());
	}
}
