/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Child class of player for human player
 */

package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{
	private boolean isFinished = true;
	
	// Human Player Constructor
	public HumanPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	// Getters
	public boolean isFinished() {
		return isFinished;
	}

	// Setters
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

}
