/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the Room class
 */

package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private boolean hasSecretpassage;
	private BoardCell secretPassage;
	private ArrayList<BoardCell> doorWays;
	private Card roomCard;

	// Room default constructor
	public Room(String name) {
		super();
		this.name = name;
		doorWays = new ArrayList<BoardCell>();
	}

	// Draw Room Name
	public void draw(Graphics g, int x, int y) {
		Font font = new Font("Serif", Font.BOLD, 14);
		g.setFont(font);
		g.setColor(Color.blue);
		// Draw a string such that its base line is at x, y
		g.drawString(this.name, x, y);
	}
	/*
	 * Getters
	 */
	public String getRoomName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public boolean isHasSecretpassage() {
		return hasSecretpassage;
	}

	public BoardCell getPassage() {
		return secretPassage;
	}

	public Card getRoomCard() {
		return roomCard;
	}

	public ArrayList<BoardCell> getDoorWays() {
		return doorWays;
	}

	/*
	 * Setters
	 */

	public void setSecretPassage(BoardCell secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public void setHasSecretpassage(boolean hasSecretpassage) {
		this.hasSecretpassage = hasSecretpassage;
	}

	// Adding doorWay to ArrayList of Doorways
	public void addDoorway(BoardCell doorWay) {
		doorWays.add(doorWay);
	}

	public void setRoomCard(Card roomCard) {
		this.roomCard = roomCard;
	}
}
