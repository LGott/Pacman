package groupProject.pacman;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PlayPacman {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new GameModule());
		BoardGui game = injector.getInstance(BoardGui.class);
		game.setVisible(true);
	}
}
