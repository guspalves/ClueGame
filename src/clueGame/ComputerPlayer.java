package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	@Override
	public void updateCard(Card card) {
		super.addCard(card);		
	}
}
