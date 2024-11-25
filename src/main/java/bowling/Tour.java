package bowling;
import java.util.ArrayList;
import java.util.List;

public class Tour {
	protected final List<Lancer> lancers = new ArrayList<>();
	private boolean spare = false;
	private boolean strike = false;

	public boolean ajouterLancer(Lancer lancer) {
		if (lancers.size() >= 2 && !isDernierTour() && !strike) {
			throw new IllegalStateException("Un tour normal ne peut avoir plus de 2 lancers.");
		}

		lancers.add(lancer);
		calculerStatut();

		return !isComplet();
	}

	private void calculerStatut() {
		int totalQuilles = getQuillesAbattues();
		if (totalQuilles == 10) {
			if (lancers.size() == 1) {
				strike = true;
			} else {
				spare = true;
			}
		}
	}

	public int getQuillesAbattues() {
		return lancers.stream().mapToInt(Lancer::getQuillesAbattues).sum();
	}

	public int getPremierLancer() {
		return lancers.isEmpty() ? 0 : lancers.get(0).getQuillesAbattues();
	}

	public int getDeuxiemeLancer() {
		return lancers.size() < 2 ? 0 : lancers.get(1).getQuillesAbattues();
	}

	public boolean isStrike() {
		return strike;
	}

	public boolean isSpare() {
		return spare;
	}

	public boolean isComplet() {
		return strike || lancers.size() == 2 || isDernierTour();
	}

	public boolean isDernierTour() {
		return false; // Sera modifié dans DernierTour
	}

	public int getProchainLancer() {
		return lancers.size() + 1;
	}

	public List<Lancer> getLancers() {
		return lancers;
	}
}