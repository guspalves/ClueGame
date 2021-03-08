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

	// getter for room name
	public String getRoomName() {
		return name;
	}

	// getter for the label cell
	public BoardCell getLabelCell() {
		return labelCell;
	}

	// getter for center cell
	public BoardCell getCenterCell() {
		return centerCell;
	}

	// setter for label cell
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	// setter for center cell
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public boolean isHasSecretpassage() {
		return hasSecretpassage;
	}

	public void setHasSecretpassage(boolean hasSecretpassage) {
		this.hasSecretpassage = hasSecretpassage;
	}

	public BoardCell getPassage() {
		return secretPassage;
	}

	public void setSecretPassage(BoardCell secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public void addDoorway(BoardCell doorWay) {
		doorWays.add(doorWay);
	}
	
	public ArrayList<BoardCell> getDoorWays() {
		return doorWays;
	}

}
