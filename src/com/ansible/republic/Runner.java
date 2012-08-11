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

		int simulations = 10000;
		int simulationsPerSet = 1000;

        log.info("Starting new game!");

        int[] totalWinsPerSeat = new int[4];
        totalWinsPerSeat[0] = 0;
        totalWinsPerSeat[1] = 0;
        totalWinsPerSeat[2] = 0;
        totalWinsPerSeat[3] = 0;

		int ties = 0;
		Map<String, Integer> cardToPasses = new HashMap<String, Integer>();
		Map<String, Integer> cardToAppearances = new HashMap<String, Integer>();

		for (int i = 0; i < simulations; i++) {
            Game g = new Game(aiValues);



			for (int j = 0; j < simulationsPerSet; j++) {
				int[] winners = g.executeGame();
				totalWinsPerSeat[0] += winners[0];
				totalWinsPerSeat[1] += winners[1];
				totalWinsPerSeat[2] += winners[2];
				totalWinsPerSeat[3] += winners[3];

				if (isGameTie(winners))
					ties++;

				for (Entry<String, Integer> e : g.metrics.entrySet()) {
					if (cardToPasses.containsKey(e.getKey()))
						cardToPasses.put(e.getKey(),
								cardToPasses.get(e.getKey()) + e.getValue());
					else
						cardToPasses.put(e.getKey(), e.getValue());

					if (cardToAppearances.containsKey(e.getKey()))
						cardToAppearances.put(e.getKey(),
								cardToAppearances.get(e.getKey()) + 1);
					else
						cardToAppearances.put(e.getKey(), 1);
				}
			}
        }

        log.info("Simulation complete! Results as follows:");
        log.info("Player 0: " + totalWinsPerSeat[0]);
        log.info("Player 1: " + totalWinsPerSeat[1]);
        log.info("Player 2: " + totalWinsPerSeat[2]);
        log.info("Player 3: " + totalWinsPerSeat[3]);
		double tiePercentage = ((double) ties
				/ (double) (simulations * simulationsPerSet) * 100);
		log.info("Number of tied rounds: " + ties + " = " + tiePercentage + "%");
		log.info("Card results");
		for (Entry<String, Integer> e : cardToPasses.entrySet()) {
			double winPercent = e.getValue().doubleValue()
					/ cardToAppearances.get(e.getKey()) * 100;
			log.info("Card " + e.getKey() + " -- Passes: " + e.getValue()
					+ " -- Appearances: " + cardToAppearances.get(e.getKey())
					+ " = " + winPercent + "%");
		}
    }

	static boolean isGameTie(int[] winners) {
		return winners[0] + winners[1] + winners[2] + winners[3] > 1;
	}

}
