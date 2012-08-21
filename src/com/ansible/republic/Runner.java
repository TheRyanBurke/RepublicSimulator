package com.ansible.republic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
					String containsKey = null;
					for (String key : cardToPasses.keySet()) {
						String result = ProposedCard
								.areTwoIdsTransposesOfEachOther(e.getKey(), key);
						if (result != null) {
							containsKey = key;
							break;
						}
					}
					if (containsKey != null) {
						try {
							cardToPasses.put(
									containsKey,
								cardToPasses.get(containsKey) + e.getValue());
						} catch (NullPointerException exception) {
							log.error("errorz");
						}
					} else
						cardToPasses.put(e.getKey(), e.getValue());

					containsKey = null;
					for (String key : cardToAppearances.keySet()) {
						String result = ProposedCard
								.areTwoIdsTransposesOfEachOther(e.getKey(), key);
						if (result != null) {
							containsKey = key;
							break;
						}
					}
					if (containsKey != null)
						cardToAppearances.put(containsKey,
								cardToAppearances.get(containsKey) + 1);
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

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter("output.txt"));
		} catch (IOException e) {
		}
		for (Entry<String, Integer> e : cardToPasses.entrySet()) {
			double winPercent = e.getValue().doubleValue()
					/ cardToAppearances.get(e.getKey()) * 100;
			log.info("Card " + e.getKey() + " -- Passes: " + e.getValue()
					+ " -- Appearances: " + cardToAppearances.get(e.getKey())
					+ " = " + winPercent + "%");
			try {
				out.write("x" + e.getKey() + "," + winPercent + "%\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

	static boolean isGameTie(int[] winners) {
		return winners[0] + winners[1] + winners[2] + winners[3] > 1;
	}

}
