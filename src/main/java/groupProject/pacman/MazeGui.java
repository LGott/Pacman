package groupProject.pacman;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.inject.Inject;

public class MazeGui extends JPanel {
	private Pacman pacman1;
	private Pacman pacman2;
	JLabel label1, label2, label3, label4;

	@Inject
	public MazeGui(Pacman pacman1, Pacman pacman2) {
		this.pacman1 = pacman1;
		this.pacman2 = pacman2;
		setLayout(new GridLayout(2, 2));

		label1 = new JLabel(new ImageIcon("blackSquare.jpg"));
		label2 = new JLabel(new ImageIcon("blackSquare.jpg"));
		label3 = new JLabel(new ImageIcon("blackSquare.jpg"));
		label4 = new JLabel(new ImageIcon("pacman.jpg"));

		add(label1);
		add(label2);
		add(label3);
		add(label4);
	}

	public void setLabelIcon(JLabel label, String iconString) {
		label.setIcon(new ImageIcon(iconString));
	}

	public JLabel getLabel1() {
		return label1;
	}

	public JLabel getLabel2() {
		return label2;
	}

	public JLabel getLabel3() {
		return label3;
	}

	public JLabel getLabel4() {
		return label4;
	}
}
