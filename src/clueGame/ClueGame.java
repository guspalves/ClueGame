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
	private GameCardPanel cardPanel;
	private Board board;
	private String name;
	private static ClueGame clueGame;
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

		cardPanel = new GameCardPanel();

		// format display for the game		
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);

		// Name the board
		setTitle("Clue Game");

		// Setting the name of the player
		name = board.getHumanPlayer().getName();
		Solution answer = board.getTheAnswer();
		System.out.println(answer);
		clueGame = this;
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

		Card susPlayerCard = new Card(suggestedPlayer, CardType.PERSON);
		Card susRoomCard = new Card(suggestedRoom, CardType.ROOM);
		Card susWeaponCard = new Card(suggestedWeapon, CardType.WEAPON);

		Solution suggestion = new Solution(susPlayerCard, susRoomCard, susWeaponCard);

		Card temp = board.handleSuggestion(suggestion);

		if(temp == null) {
			controlPanel.setGuessResult("No new clue", Color.white);
		}
		else {
			Color disproveColor = board.getSuggestionDisproveColor();
			controlPanel.setGuessResult(temp.cardName, disproveColor);
			board.getHumanPlayer().updateSeen(temp);
			cardPanel.updatePanels();
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

	public void winMessage() {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "You Win!", "Congratulations", 1);
		System.exit(0);
	}

	public void loseMessage() {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "You Lose!", "Loser", 0);
		System.exit(0);
	}

	public void computerWinMessage(String name) {
		// Message Dialog
		JOptionPane.showMessageDialog(this, "You lose, " + name + " has won.", "Congratulations", 1);
		System.exit(0);
	}

	public void computerLoseMessage(String name) {
		// Message Dialog
		JOptionPane.showMessageDialog(this, name + " has lost.", "Loser", 0);
		System.exit(0);
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);

		// Message Dialog
		JOptionPane.showMessageDialog(game, "You are " + game.name + ". \nCan you find the solution \nbefore the Computer players?", "Welcome to Clue", 1);

		Board board = Board.getInstance();
		board.nextPlayerFlow();
	}

	// Getters
	public static ClueGame getInstance() {
		return clueGame;
	}

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
