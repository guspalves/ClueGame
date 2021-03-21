package clueGame;

public class Card {
	String cardName;
	CardType type;
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
	public boolean equals(Card target) {
		return (this.type == target.type) && (this.cardName.equals(target.cardName));
	}
	
	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
}
