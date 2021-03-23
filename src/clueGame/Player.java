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
	
	public void updateCard(Card card) {
		cardArr.add(card);
	}
	
	public void updateHand(Card card) {
		
	}
	
	public void updateSeen(Card seenCard) {
		
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
