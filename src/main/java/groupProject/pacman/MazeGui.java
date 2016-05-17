package groupProject.pacman;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.inject.Inject;

public class MazeGui extends JPanel implements KeyListener {
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

		this.addKeyListener(this);

		add(label1);
		add(label2);
		add(label3);
		add(label4);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case (KeyEvent.VK_UP): {
			setLabelIcon(label4, "blackSquare");
			setLabelIcon(label2, "pacman");
			break;
		}
		case (KeyEvent.VK_DOWN): {
			break;
		}
		case (KeyEvent.VK_LEFT): {
			break;
		}
		case (KeyEvent.VK_RIGHT): {
			break;
		}
		}
	}

	private void setLabelIcon(JLabel label, String iconString) {
		label.setIcon(new ImageIcon(iconString));
	}
}
