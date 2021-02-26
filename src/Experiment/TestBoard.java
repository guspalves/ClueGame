/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoard to run JUnit tests
 */

package Experiment;

import java.util.*;

public class TestBoard {
	// Instance variable
	Set<TestBoardCell> targets;
	
	// Constructor
	public TestBoard() {
		targets = new HashSet<TestBoardCell>();
	}
	
	// calcTargets function
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		return;
	}
	
	// Returning set of targets
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	// Obtaining cell at specific row and col
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell tmp = new TestBoardCell(row, col);
		return tmp;
	}
}
