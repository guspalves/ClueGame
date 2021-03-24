/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Child class of player for computer player
 */

package clueGame;

import java.awt.Color;
import java.util.Random;

public class ComputerPlayer extends Player {
	// ComputerPlayer Constructor
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	
	public Solution createSuggestion(Card room) {
		//this.deck
		while(true){
			Random rand = new Random();
			int index = rand.nextInt(deck.size());
		}
		
		return 
	}
	
	public BoardCell selectTargets(Card room) {
		return new BoardCell();
	}
}
