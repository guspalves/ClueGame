/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Tests to ensure the ClueGame will function as expected
 */

package tests;


import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Experiment.TestBoard;
import Experiment.TestBoardCell;

class BoardTestExp {
	
	// Instance variable board
	TestBoard board;
	
	// Setting up the game board before each test
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	
	// Method to test the Adjacency list of the top left corner (location [0][0])
	@Test
	public void testAdjacencyTopLeft() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> adjTestList = cell.getAdjList();
		Assert.assertTrue(adjTestList.contains(board.getCell(0, 1)));
		Assert.assertTrue(adjTestList.contains(board.getCell(1,  0)));
		Assert.assertEquals(2, adjTestList.size());
	}
	
	// Method to test the Adjacency list of bottom right corner (location [3][3])
	@Test
	public void testAdjacencyBottomRight() {
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> adjTestList = cell.getAdjList();
		Assert.assertTrue(adjTestList.contains(board.getCell(2, 3)));
		Assert.assertTrue(adjTestList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, adjTestList.size());
	}

	// Method to test the adjacency list of a right edge
	@Test
	public void testRightEdge() {
		TestBoardCell cell = board.getCell(1, 3);
		Set<TestBoardCell> adjTestList = cell.getAdjList();
		Assert.assertTrue(adjTestList.contains(board.getCell(0, 3)));
		Assert.assertTrue(adjTestList.contains(board.getCell(1, 2)));
		Assert.assertTrue(adjTestList.contains(board.getCell(2, 3)));
		Assert.assertEquals(3, adjTestList.size());
	}
	
	// Method to test the adjacency list of the middle of the board
	@Test
	public void testCenter() {
		TestBoardCell cell = board.getCell(2, 2);
		Set<TestBoardCell> adjTestList = cell.getAdjList();
		Assert.assertTrue(adjTestList.contains(board.getCell(1, 2)));
		Assert.assertTrue(adjTestList.contains(board.getCell(2, 1)));
		Assert.assertTrue(adjTestList.contains(board.getCell(2, 3)));
		Assert.assertTrue(adjTestList.contains(board.getCell(3, 2)));
		Assert.assertEquals(4, adjTestList.size());
	}
	
	// Method to test the adjacency list of a left edge
	@Test
	public void testLeftEdge() {
		TestBoardCell cell = board.getCell(2, 0);
		Set<TestBoardCell> leftEdge = cell.getAdjList();
		Assert.assertTrue(leftEdge.contains(board.getCell(3, 0)));
		Assert.assertTrue(leftEdge.contains(board.getCell(1, 0)));
		Assert.assertTrue(leftEdge.contains(board.getCell(2, 1)));
		Assert.assertEquals(3, leftEdge.size());
	}
	
	/*
	 * Testing target creation on various the 4x4 board
	 */
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	// Ensuring that targets are expected with no rooms or occupied spaces
	@Test
	public void testTargetsEmpty() {
		TestBoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
	}
	
	// Testing targets with occupied cells and rooms
	@Test
	public void testTargetsMixed() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	// Testing targets with occupied cells on board
	@Test
	public void testTargetsOccupied() {
		board.getCell(3, 2).setOccupied(true);
		board.getCell(3, 0).setOccupied(true);
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
	}
	
	// Testing targets with rooms on board
	@Test
	public void testTargetsRoom() {
		board.getCell(0, 2).setRoom(true);
		board.getCell(2, 2).setRoom(true);
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(9, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
}