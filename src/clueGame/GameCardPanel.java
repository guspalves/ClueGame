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

public class GameCardPanel extends JPanel{

	// Constructor
	public GameCardPanel() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		// Creating first panel and adding it to main panel
		JPanel panel = createPeoplePanel();
		add(panel);
		
		panel = createRoomsPanel();
		add(panel);
		
		panel = createWeaponsPanel();
		add(panel);
		
	}

	private JPanel createPeoplePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		
		JPanel tempPanel = new JPanel();
		tempPanel = createInHandPanel();
		panel.add(tempPanel);
		
		tempPanel = createSeenPanel();
		panel.add(tempPanel);
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return panel;
	}
	
	private JPanel createRoomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		
		JPanel tempPanel = new JPanel();
		tempPanel = createInHandPanel();
		panel.add(tempPanel);
		
		tempPanel = createSeenPanel();
		panel.add(tempPanel);
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return panel;
	}
	
	private JPanel createWeaponsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		
		JPanel tempPanel = new JPanel();
		tempPanel = createInHandPanel();
		panel.add(tempPanel);
		
		tempPanel = createSeenPanel();
		panel.add(tempPanel);
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		return panel;
	}
	
	private JPanel createSeenPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JLabel titleLabel = new JLabel("Seen: ");
		JTextField emptyField = new JTextField("None");
		emptyField.setEditable(false);
		
		panel.add(titleLabel);
		panel.add(emptyField);
		
		return panel;
	}

	private JPanel createInHandPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JLabel titleLabel = new JLabel("In Hand:");
		JTextField emptyField = new JTextField("None");
		emptyField.setEditable(false);
		
		panel.add(titleLabel);
		panel.add(emptyField);
		
		return panel;
	}

	public static void main(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
	}
}
