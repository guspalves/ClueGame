/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Testing to ensure algorithms for targets and adjancecy are working properly.
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// Testing gym which has a single door and a secret room
		Set<BoardCell> testList = board.getAdjList(21, 4);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(20, 8)));
		assertTrue(testList.contains(board.getCell(9,19)));

		// Testing greenhouse with two doors and no secret rooms
		testList = board.getAdjList(22, 14);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 13)));
		assertTrue(testList.contains(board.getCell(17, 14)));

		// one more room, the kitchen with two doors and a secret room
		testList = board.getAdjList(9, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(2, 22)));
		assertTrue(testList.contains(board.getCell(11, 17)));
		assertTrue(testList.contains(board.getCell(11, 18)));
	}


	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(6, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 2)));
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(6, 6)));

		testList = board.getAdjList(17, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(14, 3)));
		assertTrue(testList.contains(board.getCell(17, 1)));
		assertTrue(testList.contains(board.getCell(17, 3)));

		testList = board.getAdjList(2, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 12)));
		assertTrue(testList.contains(board.getCell(1, 15)));
		assertTrue(testList.contains(board.getCell(3, 15)));
		assertTrue(testList.contains(board.getCell(2, 16)));
	}

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(23, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 6)));

		// Test near a door but not adjacent
		testList = board.getAdjList(5, 4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 3)));
		assertTrue(testList.contains(board.getCell(4, 4)));
		assertTrue(testList.contains(board.getCell(5, 5)));

		// Test adjacent to walkways
		testList = board.getAdjList(14, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 19)));
		assertTrue(testList.contains(board.getCell(14, 18)));
		assertTrue(testList.contains(board.getCell(14, 20)));

		// Test next to closet
		testList = board.getAdjList(5,21);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(4, 21)));
		assertTrue(testList.contains(board.getCell(3, 21)));
		assertTrue(testList.contains(board.getCell(5, 20)));

	}


	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInTheater() {
		// test a roll of 1
		board.calcTargets(board.getCell(19, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(10, 5)));	

		// test a roll of 3
		board.calcTargets(board.getCell(8, 2), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(9, 6)));	
		assertTrue(targets.contains(board.getCell(11, 4)));
		assertTrue(targets.contains(board.getCell(6, 7)));	

		// test a roll of 4
		board.calcTargets(board.getCell(8, 2), 4);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(5, 3)));
		assertTrue(targets.contains(board.getCell(8, 6)));	
		assertTrue(targets.contains(board.getCell(6, 8)));
		assertTrue(targets.contains(board.getCell(11, 7)));
		assertTrue(targets.contains(board.getCell(12, 4)));
	}

	@Test
	public void testTargetsInBedRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(2, 20)));
		assertTrue(targets.contains(board.getCell(4, 5)));	

		// test a roll of 3
		board.calcTargets(board.getCell(2, 3), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(4, 3)));
		assertTrue(targets.contains(board.getCell(4, 7)));	
		assertTrue(targets.contains(board.getCell(3, 6)));
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(5, 4)));
		assertTrue(targets.contains(board.getCell(2, 20)));

		// test a roll of 4
		board.calcTargets(board.getCell(2, 3), 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(4, 2)));
		assertTrue(targets.contains(board.getCell(4, 5)));	
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(5, 5)));
		assertTrue(targets.contains(board.getCell(2, 20)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(14, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(14, 21)));
		assertTrue(targets.contains(board.getCell(14, 19)));	
		assertTrue(targets.contains(board.getCell(13, 20)));
		assertTrue(targets.contains(board.getCell(19, 20)));

		// test a roll of 3
		board.calcTargets(board.getCell(14, 20), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(11, 20)));
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(12, 21)));	
		assertTrue(targets.contains(board.getCell(13, 17)));
		assertTrue(targets.contains(board.getCell(14, 21)));
		assertTrue(targets.contains(board.getCell(19, 20)));	

		// test a roll of 4
		board.calcTargets(board.getCell(14, 20), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(19, 20)));
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(12, 18)));	
		assertTrue(targets.contains(board.getCell(18, 18)));
		assertTrue(targets.contains(board.getCell(15, 17)));
		assertTrue(targets.contains(board.getCell(13, 21)));
		assertTrue(targets.contains(board.getCell(19, 20)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(22, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(21, 17)));
		assertTrue(targets.contains(board.getCell(23, 17)));
		assertTrue(targets.contains(board.getCell(22, 18)));

		// test a roll of 2
		board.calcTargets(board.getCell(22, 17), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertTrue(targets.contains(board.getCell(23, 18)));	

		// test a roll of 3
		board.calcTargets(board.getCell(22, 17), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(20, 18)));
		assertTrue(targets.contains(board.getCell(22, 18)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(12, 7)));	

		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));	

		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(15, 7).setOccupied(true);
		board.calcTargets(board.getCell(13, 7), 4);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		assertFalse( targets.contains( board.getCell(15, 7))) ;
		assertFalse( targets.contains( board.getCell(17, 7))) ;

		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(12, 20).setOccupied(true);
		board.getCell(8, 18).setOccupied(true);
		board.calcTargets(board.getCell(8, 17), 1);
		board.getCell(12, 20).setOccupied(false);
		board.getCell(8, 18).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 16)));	
		assertTrue(targets.contains(board.getCell(12, 20)));	

		// check leaving a room with a blocked doorway
		board.getCell(12, 15).setOccupied(true);
		board.calcTargets(board.getCell(12, 20), 3);
		board.getCell(12, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(8, 19)));	
		assertTrue(targets.contains(board.getCell(8, 15)));

	}
}
