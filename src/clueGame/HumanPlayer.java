package clueGame;

import java.awt.Color;
import java.util.function.BooleanSupplier;

public class HumanPlayer extends Player{

	public HumanPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	@Override
	public void updateCard(Card card) {
		super.addCard(card);
	}
}
