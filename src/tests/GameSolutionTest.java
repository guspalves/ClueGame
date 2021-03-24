package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;

import clueGame.*;
import org.junit.jupiter.api.Test;

class GameSolutionTest {
	// Static variable for board since only one board is used
	private static Board board;
	private static Card kitchenCard, diningCard, theaterCard, bowlingCard, libraryCard,
	gymCard, poolCard, greenhouseCard, bedroomCard, mustardCard, greenCard, plumCard, peacockCard,
	whiteCard, scarletCard, revolverCard, daggerCard, ropeCard, leadCard, candleCard, wrenchCard;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
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
	
	@Test
	public void TestCheckAccusation() {
		board.setAnswer(mustardCard, gymCard, revolverCard);
		
		// Checking if it's wrong solution
		assertFalse(board.checkAccusation(new Solution(mustardCard, gymCard, daggerCard)));
		assertFalse(board.checkAccusation(new Solution(mustardCard, poolCard, daggerCard)));
		assertFalse(board.checkAccusation(new Solution(greenCard, gymCard, daggerCard)));

		// Checking if it's right solution
		assertTrue(board.checkAccusation(new Solution(mustardCard, gymCard, revolverCard)));
	}
	
	@Test
	public void TestDisproveSuggestion() {
		Solution testSuggestion = new Solution(mustardCard, gymCard, revolverCard);
		Player tempPlayer = new ComputerPlayer("Joe Smith", Color.red, 1, 9);
		
		tempPlayer.updateCard(plumCard);
		// Seeing if disproveSuggestion returns null for no matching card
		assertEquals(tempPlayer.disproveSuggestion(testSuggestion), null);
		
		// Seeing if disproveSuggestion returns card with 1 matching card
		tempPlayer.updateCard(gymCard);
		assertEquals(tempPlayer.disproveSuggestion(testSuggestion), gymCard);
		
		// Seeing if disproveSuggestion returns random card with >1 matching card
		tempPlayer.updateCard(revolverCard);
		for(int i = 0; i < 5; i++) {
			Card tmp = tempPlayer.disproveSuggestion(testSuggestion);
			if(tmp.equals(gymCard) || tmp.equals(revolverCard)) {
				assertTrue(true);
			} else {
				System.out.println(tmp.getCardName());
				assertTrue(false);
			}
		}
	}
	
	@Test
	public void TestHandleSuggestion() {
		Solution testSuggestion = new Solution(greenCard, kitchenCard, candleCard);
		Player human = new HumanPlayer("Joe Smith", Color.black, 4, 1);
		Player computer1 = new ComputerPlayer("Jim Jhonson", Color.green, 1, 9);
		Player computer2 = new ComputerPlayer("Joe Williams", Color.yellow, 4, 21);
		Player computer3 = new ComputerPlayer("Sam Miller", Color.red, 11, 1);
		
		human.updateCard(greenhouseCard);
		human.updateCard(daggerCard);		
		
		computer1.updateCard(wrenchCard);
		computer1.updateCard(plumCard);
		
		computer2.updateCard(whiteCard);
		computer2.updateCard(kitchenCard);
		
		computer3.updateCard(peacockCard);
		computer3.updateCard(theaterCard);
		
		// testing with disputes
		ArrayList<Player> playerArr1 = new ArrayList<Player>();
		
		playerArr1.add(human);
		playerArr1.add(computer1);
		playerArr1.add(computer2);
		playerArr1.add(computer3);
		
		board.setPlayerArr(playerArr1);
		
		assertEquals(board.handleSuggestion(testSuggestion), kitchenCard);
		
		// Testing with no disputes
		ArrayList<Player> playerArr2 = new ArrayList<Player>();
		playerArr2.add(human);
		playerArr2.add(computer1);
		playerArr2.add(computer3);
		
		board.setPlayerArr(playerArr2);
		assertEquals(board.handleSuggestion(testSuggestion), null);
		
		// Testing that first player that can dispute is the one that disputes
		human.updateCard(greenCard);
		board.setPlayerArr(playerArr1);
		assertEquals(board.handleSuggestion(testSuggestion), greenCard);
		
	}
}
