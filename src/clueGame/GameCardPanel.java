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

public class GameCardPanel extends JPanel{
	private JPanel mainPanel = new JPanel();
	private ArrayList<JTextField> peopleSeenList;
	private ArrayList<JTextField> peopleInHandList;
	private ArrayList<JTextField> weaponSeenList;
	private ArrayList<JTextField> weaponInHandList;
	private ArrayList<JTextField> roomsSeenList;
	private ArrayList<JTextField> roomsInHandList;
	
	// Constructor
	public GameCardPanel() {
		// Initializing instance variables
		peopleSeenList = new ArrayList<JTextField>();
		peopleInHandList = new ArrayList<JTextField>();
		weaponSeenList = new ArrayList<JTextField>();
		weaponInHandList = new ArrayList<JTextField>();
		roomsSeenList = new ArrayList<JTextField>();
		roomsInHandList = new ArrayList<JTextField>();

		setLayout(new GridLayout(0,1));
		
		// Create a layout with 2 rows
		mainPanel.setLayout(new GridLayout(3, 1));
		mainPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		updatePanels();
		
		add(mainPanel);
	}


	private void updatePanels() {
		// Clearing out panel
		mainPanel.removeAll();
		
		// Adding new information to panel
		JPanel temp = new JPanel();
		temp = createPeoplePanel();
		mainPanel.add(temp);
		temp = createRoomsPanel();
		mainPanel.add(temp);
		temp = createWeaponPanel();
		mainPanel.add(temp);
		
		// Revalidating panel
		mainPanel.repaint();
		mainPanel.revalidate();
	}


	/*
	 * Creating People Panel
	 */

	private JPanel createPeoplePanel() {
		// Set up panel for people cards
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		
		// Create panels for people cards in hand and seen
		JPanel tempPanel = new JPanel();
		tempPanel = createPeople();
		panel.add(tempPanel);

		// Create border title for people panel
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return panel;
	}

	private JPanel createPeople() {
		// set up panel for in hand and seen
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		// create title for in hand
		JLabel titleLabel = new JLabel("In Hand:");
		panel.add(titleLabel);

		// if there are no people in hand, display "None"
		if(peopleInHandList.isEmpty()) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		} else {
			// else display all people cards in hand
			for(int i = 0; i < peopleInHandList.size(); i++) {
				JTextField tempField = peopleInHandList.get(i);
				tempField.setEditable(false);
				panel.add(tempField);
			}
		}

		// create title for seen
		titleLabel = new JLabel("Seen:");		
		panel.add(titleLabel);

		// if there are no people seen, display "None"
		if(peopleSeenList.isEmpty()) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		} else {
			// else display all people cards seen
			for(int i = 0; i < peopleSeenList.size(); i++) {
				JTextField tempField = peopleSeenList.get(i);
				tempField.setEditable(false);
				panel.add(tempField);
			}
		}

		return panel;
	}

	/*
	 * Creating Rooms Panel
	 */

	private JPanel createRoomsPanel() {
		// Set up panel for room cards
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));

		// Create panels for room cards in hand and seen
		JPanel tempPanel = new JPanel();
		tempPanel = createRooms();
		panel.add(tempPanel);

		// Create border title for room panel
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		return panel;
	}

	private JPanel createRooms() {
		// Set up panel for room cards in hand and seen
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		// Create title for room cards in hand
		JLabel titleLabel = new JLabel("In Hand:");
		panel.add(titleLabel);

		// If there are no room cards in hand, display "None"
		if(roomsInHandList.isEmpty()) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		} else {
			// Else display all room cards in hand
			for(int i = 0; i < roomsInHandList.size(); i++) {
				JTextField tempField = roomsInHandList.get(i);
				tempField.setEditable(false);
				panel.add(tempField);
			}
		}

		// Create title for room cards seen
		titleLabel = new JLabel("Seen:");		
		panel.add(titleLabel);

		// If there are no room cards seen, display "None"
		if(roomsSeenList.isEmpty()) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		} else {
			// Else display all room cards seen
			for(int i = 0; i < roomsSeenList.size(); i++) {
				JTextField tempField = roomsSeenList.get(i);
				tempField.setEditable(false);
				panel.add(tempField);
			}
		}

		return panel;
	}

	/*
	 * Creating Weapon Panel
	 */

	private JPanel createWeaponPanel() {
		// Set up panel for weapon cards
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		
		// Create panels for weapon cards in hand and seen
		JPanel tempPanel = new JPanel();
		tempPanel = createWeapon();
		panel.add(tempPanel);

		// Create border title for weapon panel
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
		return panel;
	}

	private JPanel createWeapon() {
		// Set up panel for weapon cards in hand and seen
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		// Create title for weapon cards in hand
		JLabel titleLabel = new JLabel("In Hand:");
		panel.add(titleLabel);

		// If there are no weapon cards in hand, display "None"
		if(weaponInHandList.isEmpty()) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		} else {
			// else display all weapon cards in hand
			for(int i = 0; i < weaponInHandList.size(); i++) {
				JTextField tempField = weaponInHandList.get(i);
				tempField.setEditable(false);
				panel.add(tempField);
			}
		}

		// Create title for weapon cards seen
		titleLabel = new JLabel("Seen:");		
		panel.add(titleLabel);

		// if there are no weapon cards seen, display "None"
		if(weaponSeenList.isEmpty()) {
			JTextField emptyField = new JTextField("None");
			emptyField.setEditable(false);
			panel.add(emptyField);
		} else {
			// else display all weapon cards seen
			for(int i = 0; i < weaponSeenList.size(); i++) {
				JTextField tempField = weaponSeenList.get(i);
				tempField.setEditable(false);
				panel.add(tempField);
			}
		}

		return panel;
	}

	/*
	 * Update Functions
	 */
	
	public void updatePeopleInHand(Card personInHand) {
		JTextField tempField = new JTextField(personInHand.getCardName());
		peopleInHandList.add(tempField);
		updatePanels();
	}
	
	public void updatePeopleSeen(Card personSeen, Color color) {
		JTextField tempField = new JTextField(personSeen.getCardName());
		tempField.setBackground(color);
		peopleSeenList.add(tempField);
		updatePanels();
	}
	
	public void updateRoomsInHand(Card roomInHand) {
		JTextField tempField = new JTextField(roomInHand.getCardName());
		roomsInHandList.add(tempField);
		updatePanels();
	}
	
	public void updateRoomsSeen(Card roomSeen, Color color) {
		JTextField tempField = new JTextField(roomSeen.getCardName());
		tempField.setBackground(color);
		roomsSeenList.add(tempField);
		updatePanels();
	}
	
	public void updateWeaponInHand(Card weaponInHand) {
		JTextField tempField = new JTextField(weaponInHand.getCardName());
		weaponInHandList.add(tempField);
		updatePanels();
	}
	
	public void updateWeaponSeen(Card weaponSeen, Color color) {
		JTextField tempField = new JTextField(weaponSeen.getCardName());
		tempField.setBackground(color);
		weaponSeenList.add(tempField);
		updatePanels();
	}

	public static void main(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 1000);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// Testing the update features
		
		// People cards
		Card person = new Card("Colonel Mustard", CardType.PERSON);
		panel.updatePeopleInHand(person);
		panel.updatePeopleSeen(new Card("Mrs. White", CardType.PERSON), Color.orange);
		panel.updatePeopleSeen(new Card("Miss Scarlet", CardType.PERSON), Color.white);
		panel.updatePeopleSeen(new Card("Mrs. Peacock", CardType.PERSON), Color.green);
		panel.updatePeopleSeen(new Card("Reverend Green", CardType.PERSON), Color.cyan);

		// Room Cards
		panel.updateRoomsSeen(new Card("Hall", CardType.ROOM), Color.orange);
		panel.updateRoomsSeen(new Card("Ballroom", CardType.ROOM), Color.white);
		panel.updateRoomsSeen(new Card("Kitchen", CardType.ROOM), Color.white);
		panel.updateRoomsSeen(new Card("Biliard Room", CardType.ROOM), Color.green);
		panel.updateRoomsSeen(new Card("Conservatory", CardType.ROOM), Color.green);
		panel.updateRoomsSeen(new Card("Library", CardType.ROOM), Color.cyan);
		panel.updateRoomsSeen(new Card("Lounge", CardType.ROOM), Color.cyan);
		panel.updateRoomsSeen(new Card("Dining Room", CardType.ROOM), Color.pink);
		
		// Weapons Cards
		panel.updateWeaponInHand(new Card("Wrench", CardType.WEAPON));
		panel.updateWeaponInHand(new Card("Rope", CardType.WEAPON));

		panel.updateWeaponSeen(new Card("Lead Pipe", CardType.WEAPON), Color.orange);
		panel.updateWeaponSeen(new Card("Dagger", CardType.WEAPON), Color.pink);
		panel.updateWeaponSeen(new Card("Revolver", CardType.WEAPON), Color.pink);


	}
}