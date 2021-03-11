 /**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the Room class
 */

package clueGame;

import java.util.ArrayList;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private boolean hasSecretpassage;
	private BoardCell secretPassage;
	private ArrayList<BoardCell> doorWays;
	
	

	// Room default constructor
	public Room(String name) {
		super();
		this.name = name;
		doorWays = new ArrayList<BoardCell>();
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
	
	public ArrayList<BoardCell> getDoorWays() {
		return doorWays;
	}

	// Adding doorWay to ArrayList of Doorways
	public void addDoorway(BoardCell doorWay) {
		doorWays.add(doorWay);
	}
}
