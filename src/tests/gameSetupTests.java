package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

class gameSetupTests {
	
	// Static variable for board since only one board is used
	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
	}
	
	@Test
	public void SizeOfPlayers() {
		assertEquals(board.getPlayerArray().size(), 6);
	}
	
	@Test
	public void PlayerInfo() {
		ArrayList<Player> playerArr = board.getPlayerArray();
		assertTrue(playerArr.get(0) instanceof HumanPlayer);
		assertTrue(playerArr.get(3) instanceof ComputerPlayer);
		assertTrue(playerArr.get(playerArr.size()-1) instanceof ComputerPlayer);
	}
	
	@Test
	public void TestHumanPlayer() {
		ArrayList<Player> playerArr = board.getPlayerArray();
		HumanPlayer tmp = (HumanPlayer) playerArr.get(0);
		assertTrue(tmp.getName().equals("Colonel Mustard"));
		assertTrue(tmp.getColor() == Color.yellow);
		assertEquals(tmp.getRow(), 1);
		assertEquals(tmp.getCol(), 9);
	}
	
	@Test
	public void TestComputerPlayer() {
		ArrayList<Player> playerArr = board.getPlayerArray();
		ComputerPlayer tmp = (ComputerPlayer) playerArr.get(5);
		assertTrue(tmp.getName().equals("Miss Scarlet"));
		assertTrue(tmp.getColor() == Color.red);
		assertEquals(tmp.getRow(), 13);
		assertEquals(tmp.getCol(), 21);
	}
}
