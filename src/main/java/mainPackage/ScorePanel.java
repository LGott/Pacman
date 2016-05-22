package mainPackage;

public class ScorePanel {
	private int score;

	public ScorePanel() {

	}

	public int getScore() {
		return score;
	}

	public void incrementScore(int score) {
		this.score += score;
	}
}
