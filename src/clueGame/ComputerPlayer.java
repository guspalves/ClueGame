/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Child class of player for computer player
 */

package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {
	// ComputerPlayer Constructor
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	
	// ComputerPlayer implementation for update card. Adds card to card array
	@Override
	public void updateCard(Card card) {
		super.addCard(card);		
	}
}
