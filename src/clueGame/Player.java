/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Player Class to store information about each player
 */
package clueGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

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
	
	public Card disproveSuggestion(Solution suggestion) {
		// Getting cards
		Card person = suggestion.getPerson();
		Card room = suggestion.getRoom();
		Card weapon = suggestion.getWeapon();
		
		// Array to store index of same values
		ArrayList<Integer> sameArr = new ArrayList<Integer>();
		
		// Finding which indexes of cards match
		for(int i = 0; i < cardArr.size(); i++) {
			if(cardArr.get(i).equals(person)) {
				sameArr.add(i);
			}
			
			if(cardArr.get(i).equals(room)) {
				sameArr.add(i);
			}
			
			if(cardArr.get(i).equals(weapon)) {
				sameArr.add(i);
			}
		}
		
		// Returning either correct or random correct card
		if(sameArr.size() == 1) {
			return cardArr.get(sameArr.get(0));
		} else if (sameArr.size() > 1) {
			Random rand = new Random();
			int index = rand.nextInt(sameArr.size());
			return cardArr.get(sameArr.get(index));
			
		}
		
		// Return null if no way to disprove is found
		return null;
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
