package clueGame;

import java.awt.BorderLayout;

import javax.swing.*;

public class ClueGame extends JFrame {
	GameControlPanel controlPanel = new GameControlPanel();
	GameCardPanel cardPanel = new GameCardPanel();
	Board board;
	
	public ClueGame(){
		setSize(800,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
}
