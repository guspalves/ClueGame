/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the GUI for the Game Control Panel
 */

package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameControlPanel extends JPanel{
	// Instance Variables for JTextFields
	private JTextField rollValue;
	private JTextField playerName;
	private JTextField guessValue;
	private JTextField guessResultPanel;
	private Board board = Board.getInstance();
	private static GameControlPanel controlPanel = new GameControlPanel();

	// Constructor
	public GameControlPanel() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));

		// Creating first panel and adding it to main panel
		JPanel panel = createFirstPanel();
		add(panel);

		// Creating second panel and adding it to main panel
		panel = createSecondPanel();
		add(panel);
	}

	// getter for controlPanel
	public static GameControlPanel getInstance() {
		return controlPanel;
	}


	private JPanel createFirstPanel() {
		// Setting up the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));

		// Creating the turn portion of the panel
		JPanel tempPanel = createTurnPanel();
		panel.add(tempPanel);

		// Creating the roll portion of the panel
		tempPanel = createRollPanel();
		panel.add(tempPanel);

		// Creating buttons
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");

		// Adding action to buttons
		nextButton.addActionListener(new NextButtonListener());

		// Adding buttons to panel
		panel.add(accusationButton);
		panel.add(nextButton);

		return panel;
	}

	private JPanel createRollPanel() {
		JPanel panel = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		panel.add(rollLabel);
		rollValue = new JTextField(5);
		rollValue.setEditable(false);
		panel.add(rollValue);

		return panel;
	}

	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		// Printing out the label
		JLabel nameLabel = new JLabel("Whose turn?");
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(nameLabel);

		// Create textfield
		playerName = new JTextField();
		playerName.setEditable(false);
		panel.add(playerName);

		return panel;
	}

	private JPanel createSecondPanel() {
		// Setting up panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));

		// Creating guess field
		JPanel tempPanel = createGuessPanel();
		panel.add(tempPanel);

		// Creating guess result field
		tempPanel = createGuessResultPanel();
		panel.add(tempPanel);

		return panel;
	}

	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		guessValue = new JTextField();
		guessValue.setEditable(false);
		panel.add(guessValue);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}

	private JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		guessResultPanel = new JTextField();
		guessResultPanel.setEditable(false);
		panel.add(guessResultPanel);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}

	// Methods to update data on panel
	public void setTurn(Player player, int roll) {
		playerName.setText(player.getName());
		playerName.setBackground(player.getColor());
		rollValue.setText(String.valueOf(roll));
	}

	private void setGuessResult(String guessResult) {
		guessResultPanel.setText(guessResult);
	}

	public void setGuess(String guess) {
		guessValue.setText(guess);
	}

	public void setRollValue(int roll) {
		rollValue.setText(Integer.toString(roll));
	}

	public void setPlayerName(String name, Color color) {
		playerName.setBackground(color);
		playerName.setText(name);
	}

	// Button Listener class
	private class NextButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			board.nextPlayerFlow();
		}
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
