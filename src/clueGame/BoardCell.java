/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the BoardCell class
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class BoardCell {
	// Instance variables
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter, isRoom, isSecretPassage, isDoorway, isOccupied, isTarget;
	private String roomName;
	private char secretPassage;
	private char entryToRoom;
	Set<BoardCell> adjList;
	private Card roomCard;
	
	// Constructors
	public BoardCell(){
		
	}
	
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		
		adjList = new HashSet<BoardCell>();
	}
	
	// Adding a cell to adjacency list
	void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	// Draw function
	public void draw(Graphics g, Color color, Color lineColor, int x, int y, int width, int height) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		g.setColor(lineColor);
		g.drawRect(x, y, width-1, height-1);
	}
	
	// Draw function for doors
	public void drawDoor(Graphics g, int x, int y, int dx, int dy) {
		g.setColor(Color.blue);
		g.fillRect(x,y,dx,dy);
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
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public char getInitial() {
		return initial;
	}
	
	public boolean getIsOccupied() {
		return isOccupied;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public char getEntryToRoom() {
		return entryToRoom;
	}
	

	public String getRoomName() {
		return roomName;
	}
	
	public boolean isSecretPassage() {
		return isSecretPassage;
	}
	
	public Card getRoomCard() {
		return roomCard;
	}
	
	public boolean isTarget() {
		return isTarget;
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

	public void setSecretPassageChar(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public void setIsSecretPassage(boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;	
	}

	public void setEntryToRoom(char entryToRoom) {
		this.entryToRoom = entryToRoom;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setRoomCard(Card roomCard) {
		this.roomCard = roomCard;
	}

	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
}

