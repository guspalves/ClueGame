/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Class for the different types of cards in the game
 */
package clueGame;

public class Card {
	String cardName;
	CardType type;
	
	// Card Constructor
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
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
}
