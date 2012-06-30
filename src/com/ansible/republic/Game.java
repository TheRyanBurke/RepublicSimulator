package com.ansible.republic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.ansible.republic.ActionCard.ACTION;

public class Game {

    static Logger log = Logger.getLogger(Game.class);

    int round;
    List<Player> players;
    List<ProposedCard> cards;

    public Game(int[] aiValues) {
        round = 0;

        players = new ArrayList<Player>();
        populatePlayers(aiValues);

        cards = new ArrayList<ProposedCard>();
        populateCards();
    }

    int[] executeGame() {
        List<ACTION> playerActions;

        int roundVoteSum;

        // one per round
        for (ProposedCard card : cards) {
        	// rotate flipped card
            int randomRotates = (int) (Math.random() * 4);
            for (int i = 0; i < randomRotates; i++)
                card.rotate();
            log.debug("New round! Card is: " + card.toString());
            
            if (players.get(0).hand.size() < 2) {
                // new set
                log.debug("New set of 3 starts now! Refreshing hands.");
                for (Player p : players)
                    p.cleanupRound();
            }
            playerActions = new ArrayList<ACTION>();

            // each player picks a card
            for (int i = 0; i < players.size(); i++) {
                playerActions.add(players.get(i).pickCard(card, i));
            }

            // resolve cards
            roundVoteSum = 1;
            for (ACTION action : playerActions) {
                if (action.equals(ACTION.YAY)) {
                    roundVoteSum++;
                } else if (action.equals(ACTION.NAY)) {
                    roundVoteSum--;
                } else if (action.equals(ACTION.ROTATE)) {
                    card.rotate();
                }
            }
            if (roundVoteSum > 0) {
                log.debug("Card passes! Final vote was: " + (roundVoteSum - 1));
                scoreCard(card);
            } else {
                log.debug("Card doesn't pass! Final vote was: "
                        + (roundVoteSum - 1));
            }
        }

        // game over
        int[] winners = getWinners();
        log.debug("Game over! Winners:");
        for (int i = 0; i < winners.length; i++) {
            if (winners[i] > 0)
                log.debug("Player " + i);
        }
        return winners;
    }

    int[] getWinners() {
        int highestScore = -99;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).score > highestScore) {
                highestScore = players.get(i).score;
            }
        }
        int[] winners = new int[4];
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).score == highestScore)
                winners[i] = 1;
            else
                winners[i] = 0;
        }
        return winners;
    }

    void scoreCard(ProposedCard card) {
        players.get(0).cleanupTurn(card, 0);
        players.get(1).cleanupTurn(card, 1);
        players.get(2).cleanupTurn(card, 2);
        players.get(3).cleanupTurn(card, 3);
    }

    void populatePlayers(int[] aiValues) {
        players.add(new Player(aiValues[0]));
        players.add(new Player(aiValues[1]));
        players.add(new Player(aiValues[2]));
        players.add(new Player(aiValues[3]));

    }

    /**
     * Add cards with values UP LEFT DOWN RIGHT
     */
    void populateCards() {
        cards.add(new ProposedCard(0, 3, 1, 0));
        cards.add(new ProposedCard(0, 3, 1, 0));

        cards.add(new ProposedCard(1, 0, 3, 2));
        cards.add(new ProposedCard(1, 0, 3, 2));

        cards.add(new ProposedCard(2, 3, 0, 1));
        cards.add(new ProposedCard(2, 3, 0, 1));

        cards.add(new ProposedCard(0, 2, 0, 2));
        cards.add(new ProposedCard(0, 2, 0, 2));

        cards.add(new ProposedCard(2, 0, 0, 2));
        cards.add(new ProposedCard(2, 0, 0, 2));

        cards.add(new ProposedCard(2, 5, 2, 2));
        cards.add(new ProposedCard(2, 5, 2, 2));

        Collections.shuffle(cards);
    }

}
