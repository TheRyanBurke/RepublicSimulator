package com.ansible.republic;

public class ProposedCard extends Card {
    public int up, left, down, right;

    public ProposedCard(int u, int l, int d, int r) {
        up = u;
        left = l;
        down = d;
        right = r;
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
    
    public int getValueOfSeat(int seat) {
    	switch(seat) {
    	case 0: return up;
    	case 1: return left;
    	case 2: return down;
    	case 3: return right;
    	default: log.error("Bad seat value passed to getValueOfSeat: " + seat); return 0;
    	}
    }

    public String toString() {
        return "Up: " + up + ", Left: " + left + ", Down: " + down
                + ", Right: " + right;
    }
}
