package Trials;

import javax.swing.JComponent;

public class Tile extends JComponent{
private int row;
private int col;
	public Tile(int row, int col){
	this.row=row;
	this.col=col;
	
}
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
}
