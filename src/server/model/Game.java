package server.model;

import java.io.*;

/**
 * Provides data fields and method to play a game of Tic-Tak-Toe.
 * Initializes a new game, a game board, two players (which enter their names),
 * assigns each player the board, appoints a referee, and assigns the board and
 * players to the referee.
 * @author ?
 * @version 1.0
 * @since Oct 6, 2019
 *
 */
public class Game implements Constants{
	/**
	 * The X's and O's board.
	 */
	private Board theBoard;
	
	/**
	 * The referee for the game.
	 */
	private Referee theRef;
	
	
	/**
	 * Constructs a game. The value of the data field for a new board is assigned.
	 * Assigns a new board to the game.
	 */
    public Game() {
        theBoard  = new Board();
	}
    
    /**
     * Assigns a referee to the game, then calls the referee to run the game.
     * @param r The referee.
     * @throws IOException User input should convert to type int.
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
	
    public Board getBoard() {
    	return theBoard;
    }
}
