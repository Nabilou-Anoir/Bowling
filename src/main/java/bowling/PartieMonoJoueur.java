package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMonoJoueur {
	private static final int MAX_TOURS = 10;

	private final List<Tour> tours = new ArrayList<>();
	private int score = 0;

	public PartieMonoJoueur() {
		tours.add(new Tour());
	}

	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon	
	 */	

	public boolean enregistreLancer(int quillesAbattues) {
		Tour tourActuel = getTourActuel();

		if (estTerminee()) {
			throw new IllegalStateException("Le jeu est terminé, aucun lancer supplémentaire n'est autorisé.");
		}

		boolean leTourContinue = tourActuel.ajouterLancer(new Lancer(quillesAbattues));

		if (!tourActuel.isComplet() && !tourActuel.isDernierTour()) {
			return leTourContinue;
		}

		if (tours.size() < MAX_TOURS || (tourActuel.isDernierTour() && tourActuel.isComplet())) {
			tours.add(tours.size() == MAX_TOURS - 1 ? new DernierTour() : new Tour());
		}

		return leTourContinue;
	}
	/**
	 * Cette méthode donne le score du joueur.
	 * Si la partie n'est pas terminée, on considère que les lancers restants
	 * abattent 0 quille.
	 * @return Le score du joueur
	 */
	
	public int score() {
		int totalScore = 0;

		for (int i = 0; i < tours.size(); i++) {
			Tour tour = tours.get(i);
			totalScore += tour.getQuillesAbattues();

			if (tour.isSpare() && i + 1 < tours.size()) {
				totalScore += tours.get(i + 1).getPremierLancer();
			}

			if (tour.isStrike() && i + 1 < tours.size()) {
				totalScore += calculerBonusStrike(i);
			}
		}

		return totalScore;
	}

	private int calculerBonusStrike(int index) {
		int bonus = 0;
		if (index + 1 < tours.size()) {
			Tour prochainTour = tours.get(index + 1);
			bonus += prochainTour.getPremierLancer();
			if (prochainTour.isStrike() && index + 2 < tours.size()) {
				bonus += tours.get(index + 2).getPremierLancer();
			} else if (prochainTour.getLancers().size() > 1) {
				bonus += prochainTour.getDeuxiemeLancer();
			}
		}
		return bonus;
	}
	/**
	 * @return vrai si la partie est terminée pour ce joueur, faux sinon
	 */
	public boolean estTerminee() {
		return tours.size() == MAX_TOURS && getTourActuel().isComplet();
	}

	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
		return estTerminee() ? 0 : tours.size();
	}
	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini
	 */
	public int numeroProchainLancer() {
		// Si la partie est terminée, lever une exception
		if (estTerminee()) {
			throw new UnsupportedOperationException("La partie est terminée, aucun lancer supplémentaire n'est possible.");
		}
		
		Tour tourActuel = getTourActuel();
		
		if (tourActuel == null) {
			throw new UnsupportedOperationException("Le tour actuel est invalide.");
		}
		
		return tourActuel.getLancers().size() + 1;
	}

	private Tour getTourActuel() {
		return tours.get(tours.size() - 1);
	}
}