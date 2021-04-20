/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Class for the different types of cards in the game
 */
package clueGame;

import java.awt.Color;

public class Card {
	String cardName;
	CardType type;
	Color color;

	// Card Constructor
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
		this.color = Color.white;
	}
	
	// Tests to see if this card type is the same as the card type passed into equals
	public boolean equals(Card target) {
		return (this.type == target.type) && (this.cardName.equals(target.cardName));
	}
	
	// Getters
	
	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
	
	public Color getColor() {
		return color;
	}
	
	// Setters
	public void setColor(Color color) {
		this.color = color;
	}
}
