/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Player Class to store information about each player
 */
package clueGame;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	
	protected int row;
	protected int col;
	
	private ArrayList<Card> cardArr;
	
	// Constructor
	public Player(String name, Color color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		cardArr = new ArrayList<Card>();
	}
	
	public abstract void updateCard(Card card);
	
	// Adding card to ArrayList
	public void addCard(Card card) {
		cardArr.add(card);
	}
	
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

	public ArrayList<Card> getCardArr(){
		return cardArr;
	}
	
}
