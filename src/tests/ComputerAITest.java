/**
 * @author Gustavo Alves
 * @author Noah Terry
 * 
 * Description: Creating tests for the Computer AI
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

class ComputerAITest {
	private static Board board;
	private static Card kitchenCard, diningCard, theaterCard, bowlingCard, libraryCard,
	gymCard, poolCard, greenhouseCard, bedroomCard, mustardCard, greenCard, plumCard, peacockCard,
	whiteCard, scarletCard, revolverCard, daggerCard, ropeCard, leadCard, candleCard, wrenchCard;
	private static ArrayList<Card> testDeck;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		testDeck = board.getDeck();
		
		// Setting up cards
		kitchenCard = new Card("Kitchen", CardType.ROOM);
		diningCard = new Card("Dining Room", CardType.ROOM);
		theaterCard = new Card("Theater", CardType.ROOM);
		bowlingCard = new Card("Bowling Alley", CardType.ROOM);
		libraryCard = new Card("Library", CardType.ROOM);
		gymCard = new Card("Gym", CardType.ROOM);
		poolCard = new Card("Pool", CardType.ROOM);
		greenhouseCard = new Card("Greenhouse", CardType.ROOM);
		bedroomCard = new Card("Bedroom", CardType.ROOM);

		mustardCard = new Card("Colonel Mustard", CardType.PERSON);
		greenCard = new Card("Mr. Green", CardType.PERSON);
		plumCard = new Card("Professor Plum", CardType.PERSON);
		peacockCard = new Card("Mrs. Peacock", CardType.PERSON);
		whiteCard = new Card("Mrs. White", CardType.PERSON);
		scarletCard = new Card("Miss Scarlet", CardType.PERSON);

		revolverCard = new Card("Revolver", CardType.WEAPON);
		daggerCard = new Card("Dagger", CardType.WEAPON);
		ropeCard = new Card("Rope", CardType.WEAPON);
		leadCard = new Card("Lead Pipe", CardType.WEAPON);
		candleCard = new Card("Candlestick", CardType.WEAPON);
		wrenchCard = new Card("Wrench", CardType.WEAPON);
	}

	// Testing the createSuggestion method
	@Test
	public void TestCreateSuggestion() {
		ComputerPlayer computer = new ComputerPlayer("Jim Jhonson", Color.green, 1, 9);
		
		// Setting the deck for the player
		computer.setDeck(testDeck);

		// Updating their hand/seen cards
		computer.updateHand(whiteCard);
		computer.updateHand(kitchenCard);
		computer.updateSeen(whiteCard);
		computer.updateSeen(kitchenCard);

		// Test to ensure suggestion is created correctly
		assertFalse(computer.createSuggestion(diningCard) == null);
		assertTrue(computer.createSuggestion(diningCard).getPerson().getType() == CardType.PERSON);
		assertTrue(computer.createSuggestion(diningCard).getWeapon().getType() == CardType.WEAPON);
	}

	// Testing selectTargets method
	@Test
	public void TestSelectTargets() {
		// Creating player
		ComputerPlayer computerPlayer = new ComputerPlayer("Jim Jhonson", Color.green, 6, 6);
		
		// Calculating targets
		board.calcTargets(board.getCell(6, 6), 2);
		Set<BoardCell> targets = board.getTargets();
		
		// Ensuring function works for a room in list, and making sure it doesn't return to that room
		assertTrue(computerPlayer.selectTargets(targets).equals(board.getCell(8,  2)));
		assertFalse(computerPlayer.selectTargets(targets).equals(board.getCell(8,  2)));
		
		// Creating second player and ensuring function works when no rooms are on list
		ComputerPlayer computerPlayer2 = new ComputerPlayer("Jim Jhonson", Color.green, 13, 14);
		board.calcTargets(board.getCell(13, 14), 2);
		Set<BoardCell> targets2 = board.getTargets();
		assertTrue(computerPlayer.selectTargets(targets) != null);
	}
}
