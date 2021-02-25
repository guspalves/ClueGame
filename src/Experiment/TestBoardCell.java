/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoardCell to run JUnit tests on Clue game board.
 */

package Experiment;

import java.util.*;

public class TestBoardCell {
	// Instance variables
	Set<TestBoardCell> adjList;
	
	// Constructor
	public TestBoardCell(int row, int col) {
		adjList = new HashSet<TestBoardCell>();
	}
	
	// Adding a cell to adjacency list
	void addAdjacency(TestBoardCell cell) {
		return;
	}
	
	// Getter for adjacency list set
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}

	// Setter and Getter for isRoom
	public void setRoom(boolean isRoom) {
		
	}
	
	public boolean getRoom() {
		return (Boolean) null;
	}
	
	// Setter and Getter for isOccuppied
	public void setOccupied(boolean isOccupied) {
		
	}
	
	public boolean getOccupied() {
		return (Boolean) null;
	}
}
