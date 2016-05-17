package groupProject.pacman;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MazeTile extends JLabel {

	private ImageIcon icon;

	public MazeTile() {
	}

	protected void setIcon(String img) {
		icon = new ImageIcon(img);
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
	}

}
