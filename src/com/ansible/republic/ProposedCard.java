package com.ansible.republic;

public class ProposedCard extends Card {
    public int up, left, down, right;

	public String id;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProposedCard other = (ProposedCard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ProposedCard(int u, int l, int d, int r) {
        up = u;
        left = l;
        down = d;
        right = r;
		id = "" + up + left + down + right;
        log.debug("New Card created: " + this.toString());
    }

    public ProposedCard() {
        this(0, 0, 0, 0);
    }

    public void rotate() {
        int newUp = left;
        left = down;
        down = right;
        right = up;
        up = newUp;
    }
    
    public int getHighest() {
    	int highest = up;
    	if(left > highest)
    		highest = left;
    	if(down > highest)
    		highest = down;
    	if(right > highest)
    		highest = right;
    	return highest;
    }
    
	public int getLowest() {
		int lowest = up;
		if (left < lowest)
			lowest = left;
		if (down < lowest)
			lowest = down;
		if (right < lowest)
			lowest = right;
		return lowest;
	}

    public int getValueOfSeat(int seat) {
    	switch(seat) {
    	case 0: return up;
    	case 1: return left;
    	case 2: return down;
    	case 3: return right;
    	default: log.error("Bad seat value passed to getValueOfSeat: " + seat); return 0;
    	}
    }

	public int getRotatedValueOfSeat(int seat) {
		switch (seat) {
		case 0:
			return left;
		case 1:
			return down;
		case 2:
			return right;
		case 3:
			return up;
		default:
			log.error("Bad seat value passed to getValueOfSeat: " + seat);
			return 0;
		}
	}

    public String toString() {
		return id;
    }
}
