package mainPackage;

public class ScorePanel {
	private int score;
	private int lives;
	private boolean gameOver;

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public ScorePanel() {
		score = 0;
		lives=3;
		gameOver=false;
	}

	public int getScore() {
		return score;
	}

	public void incrementScore(int score) {
		this.score += score;
	}
	public void decrementLives(){
		lives--;
		System.out.println("lives decremented");
		System.out.println("decremented");
		if(lives==0){
			gameOver=true;
		}
	}
}
