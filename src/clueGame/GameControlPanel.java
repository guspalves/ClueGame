package clueGame;

import javax.swing.*;
import java.awt.*;

public class GameControlPanel extends JPanel{
	private String playerName;
	private int roll;
	private String guess;
	private String guessResult;
	
	public GameControlPanel() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = createFirstPanel();
		add(panel);
	}

	private JPanel createFirstPanel() {
		JPanel panel = new JPanel();
		JPanel tempPanel = createTurnPanel();
		panel.add(tempPanel);
		
		
		tempPanel = createRollPanel();
		panel.add(tempPanel);
		
		tempPanel = createButtonPanel();
		panel.add(tempPanel);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");
		JPanel panel = new JPanel();
		panel.add(accusationButton);
		panel.add(nextButton);
		
		return panel;
	}

	private JPanel createRollPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JLabel rollLabel = new JLabel("Roll:");
		panel.add(rollLabel);
		JTextField rollValue = new JTextField(roll);
		panel.add(rollValue);
		
		return panel;
	}

	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		
		// Printing out the label
		JLabel nameLabel = new JLabel("Whose turn?");
		panel.add(nameLabel);
		
		// Create textfield
		JTextField name = new JTextField(playerName);
		name.setEditable(false);
		panel.add(name);
		
		return panel;
	}
	
	
	
	public void setTurn(ComputerPlayer computerPlayer, int roll) {
		playerName = computerPlayer.getName();
		this.roll = roll;
	}
	
	private void setGuessResult(String guessResult) {
		this.guessResult = guessResult;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

}
