/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Frame to display the Game
 */

package clueGame;

import java.awt.BorderLayout;

import javax.swing.*;

public class ClueGame extends JFrame {
	GameControlPanel controlPanel = new GameControlPanel();
	GameCardPanel cardPanel = new GameCardPanel();
	Board board;
	
	// Constructor
	public ClueGame(){
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set up the board
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// format display for the game
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
}
