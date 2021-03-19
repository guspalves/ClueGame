package clueGame;

import java.awt.*;

public abstract class Player {
	private String name;
	private Color color;
	
	protected int row;
	protected int col;
	
	// Constructor
	public Player(String name, Color color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
	}
	
	public abstract void updateCard(Card card);

	/*
	 * Getters
	 */
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
