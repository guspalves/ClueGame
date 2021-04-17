package clueGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class SuggestionPanel extends JDialog {
	private JTextField roomField;
	private JComboBox<String> roomBox;
	private JComboBox<String> playerBox;
	private JComboBox<String> weaponBox;
	private String room;
	private String player;
	private String weapon;

	// Suggestion Constructor
	public SuggestionPanel(String room) {
		JPanel panel = new JPanel();
		this.room = room;
		panel = createRoomEnterSuggestion();
		add(panel);
		setTitle("Make an Suggestion");
	}

	// Accusation Constructor
	public SuggestionPanel() {
		JPanel panel = new JPanel();
		panel = createButtonAccusation();
		add(panel);
		setTitle("Make an Accusation");
	}

	/*
	 * Suggestion Box When Clicking Button
	 */
	private JPanel createButtonAccusation() {
		JPanel main = new JPanel();

		main.setLayout(new GridLayout(1, 2));

		JPanel panel = leftHandSideAccusation();

		// Adding left hand side to panel
		main.add(panel, BorderLayout.WEST);

		// Getting right hand side and adding it to panel
		panel = rightHandSideAccusation();
		main.add(panel, BorderLayout.EAST);

		return main;
	}

	private JPanel leftHandSideAccusation() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 1));

		// Creating labels and first button
		JLabel currentRoomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitButtonListener());

		// Adding it to our panel
		panel.add(currentRoomLabel);
		panel.add(personLabel);
		panel.add(weaponLabel);
		panel.add(submitButton);

		return panel;
	}

	private JPanel rightHandSideAccusation() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 1));

		// Creating fields button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
		
		roomBox = createRoomBox();
		roomBox.addActionListener(new ComboListener());
		roomBox.setSelectedIndex(0);

		playerBox = createPlayerBox();
		playerBox.addActionListener(new ComboListener());
		playerBox.setSelectedIndex(0);

		weaponBox = createWeaponBox();
		weaponBox.addActionListener(new ComboListener());
		weaponBox.setSelectedIndex(0);

		// Adding it to our panel
		panel.add(roomBox);
		panel.add(playerBox);
		panel.add(weaponBox);
		panel.add(cancelButton);

		return panel;
	}

	/*
	 * Suggestion Box When Entering A Room
	 */

	public JPanel createRoomEnterSuggestion() {
		roomField = new JTextField(room);
		JPanel main = new JPanel();

		main.setLayout(new GridLayout(1, 2));

		JPanel panel = leftHandSideEnterRoom();

		// Adding left hand side to panel
		main.add(panel, BorderLayout.WEST);

		// Getting right hand side and adding it to panel
		panel = rightHandSideEnterRoom();
		main.add(panel, BorderLayout.EAST);

		return main;
	}

	public JPanel leftHandSideEnterRoom() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 1));

		// Creating labels and first button
		JLabel currentRoomLabel = new JLabel("Current Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitButtonListener());

		// Adding it to our panel
		panel.add(currentRoomLabel);
		panel.add(personLabel);
		panel.add(weaponLabel);
		panel.add(submitButton);

		return panel;
	}

	public JPanel rightHandSideEnterRoom() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 1));

		// Creating fields button
		roomField.setEditable(false);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
		
		playerBox = createPlayerBox();
		playerBox.addActionListener(new ComboListener());
		playerBox.setSelectedIndex(0);

		weaponBox = createWeaponBox();
		weaponBox.addActionListener(new ComboListener());
		weaponBox.setSelectedIndex(0);

		// Adding it to our panel
		panel.add(roomField);
		panel.add(playerBox);
		panel.add(weaponBox);
		panel.add(cancelButton);

		return panel;
	}

	/*
	 * Creating Box Functions
	 */

	private JComboBox<String> createWeaponBox() {
		Board board = Board.getInstance();
		ArrayList<Card> weaponArr = board.getWeaponArr();

		JComboBox<String> weaponCombo = new JComboBox<String>();

		for(Card c : weaponArr) {
			weaponCombo.addItem(c.getCardName());
		}

		return weaponCombo;
	}

	private JComboBox<String> createPlayerBox(){
		Board board = Board.getInstance();
		ArrayList<Player> playerArr = board.getPlayerArray();

		JComboBox<String> playerCombo = new JComboBox<String>();

		for(Player p : playerArr) {
			playerCombo.addItem(p.getName());
		}

		return playerCombo;
	}

	private JComboBox<String> createRoomBox() {
		Board board = Board.getInstance();
		ArrayList<Card> roomArr = board.getRoomArr();

		JComboBox<String> roomCombo = new JComboBox<String>();

		for(Card c : roomArr) {
			roomCombo.addItem(c.getCardName());
		}

		return roomCombo;
	}

	public void updateRoomField(String room) {
		this.room = room;
		roomField.setText(room);
	}

	/*
	 * Action Listeners
	 */
	private class CancelButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	private class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(room + " " + player + " " + weapon);
		}
	}

	private class ComboListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == weaponBox) {
				weapon = weaponBox.getSelectedItem().toString();
			} else if (e.getSource() == playerBox) {
				player = playerBox.getSelectedItem().toString();
			} else {
				room = roomBox.getSelectedItem().toString();
			}
		}
	}

	/*
	 * Getters
	 */

	public String getRoom() {
		return room;
	}

	public String getPlayer() {
		return player;
	}

	public String getWeapon() {
		return weapon;
	}
}
