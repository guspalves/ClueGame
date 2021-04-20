/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Player Class to store information about each player
 */
package clueGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;

	protected int row;
	protected int col;

	protected Set<Card> seen;

	protected ArrayList<Card> cardArr;
	protected ArrayList<Card> deck;

	

	// Constructor
	public Player(String name, Color color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		cardArr = new ArrayList<Card>();
		seen = new HashSet<Card>();
		deck = new ArrayList<Card>();
	}

	public void updateHand(Card card) {
		cardArr.add(card);
	}

	public void updateSeen(Card seenCard) {
		seen.add(seenCard);
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

	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	// Draw function
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.setColor(this.color);
		g.fillOval(x, y, width, height);
		g.drawOval(x, y, width, height);
	}

	/*
	 * Setters
	 */
	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
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

	public Set<Card> getSeen() {
		return seen;
	}

}
