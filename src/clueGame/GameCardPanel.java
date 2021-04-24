/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the GUI for the Game Card Panel
 */

package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class GameCardPanel extends JPanel{
	private static JPanel mainPanel = new JPanel();

	private JPanel personPanel;
	private JPanel roomPanel;
	private JPanel weaponPanel;

	// Constructor
	public GameCardPanel() {
		personPanel = new JPanel();
		roomPanel = new JPanel();
		weaponPanel = new JPanel();

		setLayout(new GridLayout(0,1));
		mainPanel.setPreferredSize(new Dimension(250, 820));

		// Create a layout with 2 rows
		mainPanel.setLayout(new GridLayout(3, 1));
		mainPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		updatePanels();

		add(mainPanel);
	}


	public void updatePanels() {
		// Clearing panel
		mainPanel.removeAll();

		// Rebuilding panels
		updatePanel(personPanel, CardType.PERSON);
		updatePanel(roomPanel, CardType.ROOM);
		updatePanel(weaponPanel, CardType.WEAPON);

		// Adding new panels to mainPanel
		mainPanel.add(personPanel);
		mainPanel.add(roomPanel);
		mainPanel.add(weaponPanel);

		// Revalidation of mainPanel
		mainPanel.repaint();
		mainPanel.revalidate();
	}

	//New Update
	private void updatePanel(JPanel panel, CardType type) {
		// Grabbing board
		Board board = Board.getInstance();
		HumanPlayer player = (HumanPlayer) board.getHumanPlayer();

		// Clearing panel
		panel.removeAll();

		panel.setLayout(new GridLayout(0, 1));

		// Figuring out the title of the panel
		if(type == CardType.PERSON) {
			panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		} else if (type == CardType.ROOM) {
			panel.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		} else {
			panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
		}

		// Adding the in hand cards of the user to the panel
		JLabel titleLabel = new JLabel("In Hand:");
		panel.add(titleLabel);

		ArrayList<Card> inHandCards = player.getCardArr();

		// Setting up counter to see if no cards of selected type are in hand
		int counter = 0;

		for(Card card : inHandCards) {
			if(card.getType() == type) {
				JTextField tempField = new JTextField(card.getCardName());
				tempField.setEditable(false);
				panel.add(tempField);
				counter++;
			}
		}

		// Printing none to the in hand cards
		if(counter == 0) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		}

		
		// Creating the seen section
		titleLabel.setText("Seen:");
		panel.add(titleLabel);

		Set<Card> seenCards = player.getSeen();

		// Resetting counter
		counter = 0;

		for(Card card : seenCards) {
			if(card.getType() == type) {
				JTextField tempField = new JTextField(card.getCardName());
				tempField.setEditable(false);
				tempField.setBackground(card.getColor());
				panel.add(tempField);
				counter++;
			}
		}

		// Placing the none in the seen category 
		if(counter == 0) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		}

		// Revalidation of panel
		panel.repaint();
		panel.revalidate();
	}
}