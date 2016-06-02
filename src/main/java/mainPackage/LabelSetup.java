package mainPackage;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LabelSetup {
	private MazeGui mazeGui;
	private ObservableList<Node> group;
	private Label scoreLabel;
	private Label scoreValueLabel;
	private Label scoreLabel2;
	private Label scoreValueLabel2;
	private Label gameOverLabel;
	private Label outLabel;
	private Label logo;
	private Label pacmanLife1;
	private Label pacmanLife2;

	public LabelSetup(MazeGui mazeGui) {
		this.mazeGui = mazeGui;
		this.gameOverLabel = mazeGui.getGameOverLabel();
		this.outLabel = mazeGui.getOutLabel();
		this.group = mazeGui.getGroup();
	}

	public void setLabels() {
		gameOverLabel = new Label("   GAME OVER" + "\n" + "Press R to restart");
		gameOverLabel.setFont(new Font(50));
		gameOverLabel.setTranslateX(125);
		gameOverLabel.setTranslateY(150);
		gameOverLabel.setTextFill(Color.WHITE);
		gameOverLabel.setVisible(false);

		outLabel = new Label("BOOM!!"); // Subject to change lol
		outLabel.setFont(new Font(90));
		outLabel.setTranslateX(195);
		outLabel.setTranslateY(250);
		outLabel.setTextFill(Color.WHITE);
		outLabel.setVisible(false);

		group.add(gameOverLabel);
		group.add(outLabel);
	}

	public void setScoreLabels() {
		scoreLabel = new Label("Pacman 1 Score: ");
		setLabel(scoreLabel, 25, 25);
		scoreValueLabel = new Label();
		setLabel(scoreValueLabel, 140, 25);

		scoreLabel2 = new Label("Pacman 2 Score: ");
		setLabel(scoreLabel2, 25, 45);
		scoreValueLabel2 = new Label();
		setLabel(scoreValueLabel2, 140, 45);

		setLifeLabels();
		addToGroup();
	}

	private void addToGroup() {
		group.add(scoreLabel);
		group.add(scoreValueLabel);
		group.add(scoreLabel2);
		group.add(scoreValueLabel2);
		group.add(pacmanLife1);
		group.add(pacmanLife2);
	}

	private void setLifeLabels() {
		pacmanLife1 = new Label("Pacman 1");
		pacmanLife2 = new Label("Pacman 2");
		setLabel(pacmanLife1, 570, 10);
		setLabel(pacmanLife2, 570, 60);
	}

	private void setLabel(Label label, int x, int y) {

		label.setTranslateX(x);
		label.setTranslateY(y);
		label.setTextFill(Color.YELLOW);

		this.logo = new Label("");
		Image image = new Image(getClass().getResourceAsStream("/PacManLogo.png"));
		ImageView img = new ImageView(image);
		img.setFitWidth(300);
		img.setPreserveRatio(true);
		logo.setGraphic(img);
		logo.setTranslateX(200);
		logo.setTranslateY(1);
		group.add(logo);

	}
}
