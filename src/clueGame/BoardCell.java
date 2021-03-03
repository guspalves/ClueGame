/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the BoardCell class
 */

package clueGame;

import java.util.*;

public class BoardCell {
	// Instance variables
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter, isRoom, isSecretPassage, isDoorway;
	private char secretPassage;
	Set<BoardCell> adjList;
	
	// Constructors
	public BoardCell(){
		
	}
	
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		
		roomLabel = roomCenter = isRoom = isSecretPassage = isDoorway = false;
		
		adjList = new HashSet<BoardCell>();
	}
	
	// Adding a cell to adjacency list
	void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	/*
	 * Getters
	 */

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	// Getter for adjacency list set
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public char getInitial() {
		return initial;
	}
	
	/*
	 * Setters
	 */
	
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public boolean isSecretPassage() {
		return isSecretPassage;
	}

	public void setSecretPassage(boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	
}
