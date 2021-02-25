package Experiment;

import java.util.*;

public class TestBoard {
	// Instance variable
	Set<TestBoardCell> targets;
	// Constructor
	public TestBoard() {
		targets = new HashSet<TestBoardCell>();
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		return;
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell tmp = new TestBoardCell(row, col);
		return tmp;
	}
}
