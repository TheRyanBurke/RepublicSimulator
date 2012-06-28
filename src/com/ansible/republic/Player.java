package com.ansible.republic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ansible.republic.ActionCard.ACTION;

public class Player {

    static Logger log = Logger.getLogger(Player.class);

    List<ActionCard> hand;
    List<ActionCard> used;
    ActionCard current;

    int score;

    /**
     * 0: random
     * 
     * 1: pick lowest index
     * 
     * 2: pass if highest number is in front, then random
     * 
     * 3: fail if highest number not in front, then random
     * 
     * 4: rotate if highest number is one off, then random
     * 
     * 5: rotate if highest number is two off, then random
     * 
     * 6: rotate if highest number is three off, then random
     * 
     * 7: pass or meh if highest number in front, then random
     * 
     * 8: fail if highest number is in front, then random
     * 
     * 3 digit value: order that cards are played in
     * 
     * YAY = 1, NAY = 2, MEH = 3, ROTATE = 4
     * 
     * ai value of 123 = YAY, NAY, MEH played in that order
     * 
     * all 3 digit combos: 123, 132, 124, 142, 134, 143, 213, 214, 231, 241,
     * 234, 243, 312, 321, 314, 341, 324, 342, 412, 421, 413, 431, 423, 432
     * 
     * ai value of 2341 = NAY, MEH, ROTATE, then YAY leads next round
     */
    int ai;

    public Player() {
        this(0);
    }

    public Player(int _ai) {
        ai = _ai;
        populateHand();
        score = 0;

        log.info("New Player registered with ai: " + ai);
    }

    void populateHand() {
        hand = new ArrayList<ActionCard>();
        used = new ArrayList<ActionCard>();
        hand.add(new ActionCard(ACTION.YAY));
        hand.add(new ActionCard(ACTION.NAY));
        hand.add(new ActionCard(ACTION.MEH));
        hand.add(new ActionCard(ACTION.ROTATE));
    }

    /**
     * Given the ProposedCard, return an ACTION
     * 
     * @param proposal
     * @return
     */
    ACTION pickCard(ProposedCard proposal, int seat) {
        int pickIndex = selectCard(proposal, seat);

        current = hand.get(pickIndex);

        log.info("Player " + seat + " chooses action: "
                + current.type.toString());
        return current.type;
    }

    /**
     * Return a legal index of a card in the hand list
     * 
     * @return
     */
    int selectCard(ProposedCard proposal, int seat) {
        // logic for card selection
        if (ai == 0) {
            int rand = (int) (Math.random() * hand.size());
            return rand;
        } else if (ai == 1) {
            return 0;
        } else if (ai >= 100 && ai < 1000) {
            // three digit order
            char[] order = Integer.toString(ai).toCharArray();
            for (int i = 0; i < order.length; i++) {
                if (handContainsAction(Integer.valueOf(order[i])))
                    return order[i];
            }
            log.error("Evaluated all cards in ai routine, didn't find one player had in hand.");
        } else if (ai > 1000) {
            // cyclical order, more than 3 card order preference

        }
        return 0;
    }

    boolean handContainsAction(int actionVal) {
        for (ActionCard a : hand) {
            if (a.type.equals(actionVal))
                return true;
        }
        return false;
    }

    void cleanupTurn(ProposedCard card, int seat) {
        moveCardToUsed();
        current = null;

        int gainPoints = 0;
        switch (seat) {
        case 0:
            gainPoints += card.up;
            break;
        case 1:
            gainPoints += card.left;
            break;
        case 2:
            gainPoints += card.down;
            break;
        case 3:
            gainPoints += card.right;
            break;
        default:
            break;
        }
        score += gainPoints;
        log.info("Player " + seat + " scored " + gainPoints
                + " points. Now at " + score);
    }

    void moveCardToUsed() {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).type.equals(current.type)) {
                used.add(hand.remove(i));
                break;
            }
        }
    }

    void cleanupRound() {
        moveUsedCardsToHand();
    }

    void moveUsedCardsToHand() {
        for (int i = 0; i < used.size(); i++) {
            hand.add(used.remove(i));
        }
    }
}
