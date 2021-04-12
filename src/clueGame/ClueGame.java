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
	private GameControlPanel controlPanel = GameControlPanel.getInstance();
	private GameCardPanel cardPanel = new GameCardPanel();
	private Board board;
	private String name;
	private static ClueGame clueGame = new ClueGame();
	
	

	// Constructor
	public ClueGame(){
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set up the board
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		Player human = board.getHumanPlayer();
		for(Card card : human.getCardArr()) {
			if(card.getType() == CardType.ROOM) {
				cardPanel.updateRoomsInHand(card);
			}
			if(card.getType() == CardType.WEAPON) {
				cardPanel.updateWeaponInHand(card);
			}
			if(card.getType() == CardType.PERSON) {
				cardPanel.updatePeopleInHand(card);
			}
		}
		
		
		
		// format display for the game
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
		
		// Name the board
		setTitle("Clue Game");
		
		// Setting the name of the player
		name = board.getHumanPlayer().getName();
	}
	
	public void errorMessage() {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "Please finish your turn.", "Error Message", 0);
	}
	
	public void notTargetMessage() {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "Not a target.", "Error Message", 0);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		
		// Message Dialog
		JOptionPane.showMessageDialog(game, "You are " + game.name + ". \nCan you find the solution \nbefore the Computer players?", "Welcome to Clue", 1);
		
		Board board = Board.getInstance();
		board.nextPlayerFlow();
	}

	public static ClueGame getInstance() {
		return clueGame;
	}
}
