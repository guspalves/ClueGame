/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Setting up the TestBoard to run JUnit tests
 */

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
		assertTrue(tmp.getName().equals("Miss Scarlet"));
		assertTrue(tmp.getColor().equals(new Color(255,87,51)));
		assertEquals(tmp.getRow(), 2);
		assertEquals(tmp.getCol(), 10);
	}
	
	@Test
	public void TestComputerPlayer() {
		ArrayList<Player> playerArr = board.getPlayerArray();
		ComputerPlayer tmp = (ComputerPlayer) playerArr.get(5);
		assertTrue(tmp.getName().equals("Colonel Mustard"));
		assertTrue(tmp.getColor() == Color.yellow);
		assertEquals(tmp.getRow(), 14);
		assertEquals(tmp.getCol(), 22);
	}
	
	@Test
	public void TestDeckSize() {
		assertEquals(board.getDeck().size(), 21);
	}
	
	@Test
	public void TestCardSetup() {
		ArrayList<Card> deck = board.getDeck();
		assertTrue(deck.get(0).getType().equals(CardType.ROOM));
		assertTrue(deck.get(0).getCardName().equals("Kitchen"));
		assertTrue(deck.get(8).getType().equals(CardType.ROOM));
		assertTrue(deck.get(8).getCardName().equals("Bedroom"));
		
		assertTrue(deck.get(9).getType().equals(CardType.PERSON));
		assertTrue(deck.get(9).getCardName().equals("Miss Scarlet"));
		assertTrue(deck.get(14).getType().equals(CardType.PERSON));
		assertTrue(deck.get(14).getCardName().equals("Colonel Mustard"));
		
		assertTrue(deck.get(15).getType().equals(CardType.WEAPON));
		assertTrue(deck.get(15).getCardName().equals("Revolver"));
		assertTrue(deck.get(20).getType().equals(CardType.WEAPON));
		assertTrue(deck.get(20).getCardName().equals("Wrench"));
	}
	
	@Test
	public void testSolutionExists() {
		assertTrue(board.getTheAnswer() != null);
	}
	
	@Test
	public void testCardsDealt() {
		ArrayList<Player> playerArr = board.getPlayerArray();
		HumanPlayer human = (HumanPlayer) playerArr.get(0);
		assertEquals(human.getCardArr().size(), 3);
		
		ComputerPlayer compPlayer1 = (ComputerPlayer) playerArr.get(5);
		assertEquals(compPlayer1.getCardArr().size(), 3);

		ComputerPlayer compPlayer2 = (ComputerPlayer) playerArr.get(3);
		assertEquals(compPlayer2.getCardArr().size(), 3);
	}
	
	@Test
	public void testSolutionCase() {
		Card person = new Card("Joe", CardType.PERSON);
		Card room = new Card("Kitchen", CardType.ROOM);
		Card weapon = new Card("Revolver", CardType.WEAPON);
		
		Solution tmpSolution = new Solution(person, room, weapon);
		assertTrue(tmpSolution.isSolution(person, room, weapon));
		
		Card newPerson = new Card("Smith", CardType.PERSON);
		assertFalse(tmpSolution.isSolution(newPerson, room, weapon));

	}
}
