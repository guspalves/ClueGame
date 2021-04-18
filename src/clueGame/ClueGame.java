/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Frame to display the Game
 */

package clueGame;

import java.awt.*;

import javax.swing.*;

public class ClueGame extends JFrame {
	private GameControlPanel controlPanel = GameControlPanel.getInstance();
	private GameCardPanel cardPanel = new GameCardPanel();
	private Board board;
	private String name;
	private static ClueGame clueGame = new ClueGame();
	private SuggestionPanel suggestion;
	private String suggestedPlayer, suggestedRoom, suggestedWeapon;

	// Constructor
	public ClueGame(){
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the board
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		// Getting the human player's in hand cards to show
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
	
	public void updatePanel() {
		clueGame.invalidate();
		clueGame.repaint();
		clueGame.revalidate();
	}

	public void humanPlayerSuggestion(String roomName) {
		suggestion = new SuggestionPanel(roomName);
		suggestion.setSize(new Dimension(300, 150));

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		suggestion.setLocation(dim.width/2-suggestion.getSize().width/2 - 500, dim.height/2-suggestion.getSize().height/2);

		suggestion.setVisible(true);

	}

	public void suggestionSubmit() {
		suggestedPlayer = suggestion.getPlayer();
		suggestedRoom = suggestion.getRoom();
		suggestedWeapon = suggestion.getWeapon();

		controlPanel.setGuess(suggestedPlayer + ", " + suggestedRoom + ", " + suggestedWeapon, board.getHumanPlayer().getColor());

		board.moveSuggestedPlayer(suggestedRoom);
		Card temp = board.disproveSuggestion();

		if(temp == null) {
			controlPanel.setGuessResult("No new clue");
		}
		else {
			controlPanel.setGuessResult(temp.cardName);
			board.getHumanPlayer().updateSeen(temp);

			switch(temp.getType()) {
			case PERSON:
				cardPanel.updatePeopleSeen(temp, Color.red);
				updatePanel();
				break;

			case ROOM:
				cardPanel.updateRoomsSeen(temp, Color.red);
				updatePanel();

				break;

			case WEAPON:
				cardPanel.updateWeaponSeen(temp, Color.red);
				updatePanel();

				break;
			}
		}
	}

	public void humanPlayerAccusation() {
		suggestion = new SuggestionPanel();
		suggestion.setSize(new Dimension(300, 150));

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		suggestion.setLocation(dim.width/2-suggestion.getSize().width/2 - 500, dim.height/2-suggestion.getSize().height/2);

		suggestion.setVisible(true);
	}

	public void notFinishedMessage() {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "Please finish your turn.", "Error Message", 0);
	}

	public void notTargetMessage() {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "Not a target.", "Error Message", 0);
	}

	public void notYourTurn() {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "It is not your turn.", "Error Message", 0);
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

	// getters
	public String getSuggestedPlayer() {
		return suggestedPlayer;
	}

	public String getSuggestedRoom() {
		return suggestedRoom;
	}

	public String getSuggestedWeapon() {
		return suggestedWeapon;
	}

}
