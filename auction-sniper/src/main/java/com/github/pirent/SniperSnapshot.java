package com.github.pirent;

/**
 * A description of the Sniper's relationship with the auction at this moment in time.
 * 
 * @author pirent
 *
 */
public class SniperSnapshot {

	private final String itemId;
	private final int lastPrice;
	private final int lastBid;
	private final SniperState sniperState;

	public SniperSnapshot(String itemId, int lastPrice, int lastBid,
			SniperState sniperState) {
		super();
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
		this.sniperState = sniperState;
	}

	public String getItemId() {
		return itemId;
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
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
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
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		}
		else if (!itemId.equals(other.itemId))
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
		return "SniperSnapshot [itemId=" + itemId + ", lastPrice=" + lastPrice
				+ ", lastBid=" + lastBid + ", sniperState=" + sniperState + "]";
	}

}
