package com.ansible.republic;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Runner {

    static Logger log = Logger.getLogger(Runner.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");

        int[] aiValues = new int[4];
        aiValues[0] = 2;
        aiValues[1] = 2;
        aiValues[2] = 2;
        aiValues[3] = 2;
        log.info("Starting new game!");

        int[] totalWinsPerSeat = new int[4];
        totalWinsPerSeat[0] = 0;
        totalWinsPerSeat[1] = 0;
        totalWinsPerSeat[2] = 0;
        totalWinsPerSeat[3] = 0;

        for (int i = 0; i < 10000; i++) {
            Game g = new Game(aiValues);
            int[] winners = g.executeGame();
            totalWinsPerSeat[0] += winners[0];
            totalWinsPerSeat[1] += winners[1];
            totalWinsPerSeat[2] += winners[2];
            totalWinsPerSeat[3] += winners[3];
        }

        log.info("Simulation complete! Results as follows:");
        log.info("Player 0: " + totalWinsPerSeat[0]);
        log.info("Player 1: " + totalWinsPerSeat[1]);
        log.info("Player 2: " + totalWinsPerSeat[2]);
        log.info("Player 3: " + totalWinsPerSeat[3]);
    }

}
