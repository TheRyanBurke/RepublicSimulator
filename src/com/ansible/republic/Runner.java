package com.ansible.republic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		aiValues[0] = 9;
		aiValues[1] = 9;
		aiValues[2] = 9;
		aiValues[3] = 9;

		int simulations = 1000;

        log.info("Starting new game!");

        int[] totalWinsPerSeat = new int[4];
        totalWinsPerSeat[0] = 0;
        totalWinsPerSeat[1] = 0;
        totalWinsPerSeat[2] = 0;
        totalWinsPerSeat[3] = 0;

		Map<String, Integer> overallMetrics = new HashMap<String, Integer>();

		for (int i = 0; i < simulations; i++) {
            Game g = new Game(aiValues);
            int[] winners = g.executeGame();
            totalWinsPerSeat[0] += winners[0];
            totalWinsPerSeat[1] += winners[1];
            totalWinsPerSeat[2] += winners[2];
            totalWinsPerSeat[3] += winners[3];
			for (Entry<String, Integer> e : g.metrics.entrySet()) {
				if (overallMetrics.containsKey(e.getKey()))
					overallMetrics.put(e.getKey(),
							overallMetrics.get(e.getKey()) + e.getValue());
				else
					overallMetrics.put(e.getKey(), e.getValue());
			}
        }

        log.info("Simulation complete! Results as follows:");
        log.info("Player 0: " + totalWinsPerSeat[0]);
        log.info("Player 1: " + totalWinsPerSeat[1]);
        log.info("Player 2: " + totalWinsPerSeat[2]);
        log.info("Player 3: " + totalWinsPerSeat[3]);
		log.info("Card results");
		for (Entry<String, Integer> e : overallMetrics.entrySet()) {
			double winPercent = e.getValue().doubleValue() / 2000 * 100;
			log.info("Card " + e.getKey() + " -- Passes: " + e.getValue()
					+ " = " + winPercent + "%");
		}
    }

}
