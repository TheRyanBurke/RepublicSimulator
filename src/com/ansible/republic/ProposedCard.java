package com.ansible.republic;

public class ProposedCard extends Card {
    int up, left, down, right;

    public ProposedCard(int u, int l, int d, int r) {
        up = u;
        left = l;
        down = d;
        right = r;
        log.info("New Card created: " + this.toString());
    }

    public ProposedCard() {
        this(0, 0, 0, 0);
    }

    void rotate() {
        int newUp = left;
        left = down;
        down = right;
        right = up;
        up = newUp;
    }

    public String toString() {
        return "Up: " + up + ", Left: " + left + ", Down: " + down
                + ", Right: " + right;
    }
}
