package server.model;
import java.io.*;
import server.controller.*;
/**
 * Provides fields and methods for each of the players of the Tic-Tak-Toe game.
 * @author Jason Paul
 * @version 1.0
 * @since Oct 6, 2019
 */
public class Player {
	
	/**
	 * The name of the player
	 */
	private String name;
	
	/**
	 * The game's board.
	 */
	private Board board;
	
	/**
	 * The player's opponent.
	 */
	private Player opponent;
	
	/**
	 * The player's mark (X or O).
	 */
	private char mark;
	
	/**
	 * The reference to the game controller class
	 */
	private ModelController modelCon;
	
	/**
	 * Constructs a player with the name and mark of the player.
	 * @param name The player's name.
	 * @param mark the player's mark (X or O).
	 */
	public Player(String name, char mark) {
		this.name = name;
		this.mark = mark;
	}
	
	/**
	 * Calls a method to make a move, displays the board to the console, checks
	 * if the player has won the game or if the board is full with no winner,
	 * and if the player didn't win or if the board is not full, turns over play 
	 * to the opponent.
	 * @throws IOException User input must be convertible to type int.
	 */
	public void play() throws IOException {
		makeMove();
		if(board.xWins()) {
			modelCon.sendXWinsMessage();
			return;
		}
		if(board.oWins()){
			modelCon.sendOWinsMessage();
			return;	
		}
		if(board.isFull()) {
			modelCon.sendTieMessage();
			return;
		}
		opponent.play();
	}
	
	/**
	 * Prompts the player for the row and column to put the player's mark into,
	 * and adds that mark to the server side board the the opponent client's board.
	 * @throws IOException User input must be convertible to type int.
	 */
	public void makeMove() throws IOException{
		
		String move = modelCon.getClientMove(mark);
		int row = Integer.parseInt(move.substring(0, 1));
		int col = Integer.parseInt(move.substring(1, 2));
		board.addMark(row, col, mark);
		modelCon.updateOpponentBoard(row, col, opponent.mark);
	}

	/**
	 * Sets the players opponent to be the other player.
	 * @param opponent The other player.
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	/**
	 * Sets the board of the player.
	 * @param theBoard The board to assign to the player.
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}

	public void setModelCon(ModelController modelCon) {
		this.modelCon = modelCon;
	}
	
}
