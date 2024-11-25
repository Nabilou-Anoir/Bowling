package bowling;
public class DernierTour extends Tour {
	@Override
	public boolean isDernierTour() {
		return true;
	}

	@Override
	public boolean isComplet() {
		if (getQuillesAbattues() < 10) {
			return lancers.size() == 2;
		} else {
			return lancers.size() == 3;
		}
	}
}