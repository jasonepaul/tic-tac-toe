package server.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import client.model.Constants;
import server.model.*;

/**
 * Provides a server side game controller, which receives requests from the game 
 * and players to send messages to the clients.
 * @author Jason Paul
 * @version 1.0
 * @since Feb 8, 2020
 */
public class ModelController implements Runnable, Constants{

	private Referee theRef;
	private Game theGame;
	private Player xPlayer, oPlayer;
	private BufferedReader xSocketIn, oSocketIn;
	private PrintWriter xSocketOut, oSocketOut;

	/**
	 * Constructs a game controller and sets the input and output streams to the 
	 * sockets connecting to the clients.
	 * @param xSocketIn input stream for Player X socket.
	 * @param xSocketOut output stream for Player X socket.
	 * @param oSocketIn input stream for Player O socket.
	 * @param oSocketOut output stream for Player O socket.
	 */
	public ModelController(BufferedReader xSocketIn, PrintWriter xSocketOut, 
			BufferedReader oSocketIn, PrintWriter oSocketOut) {
		theRef = new Referee();
		theRef.setModelController(this);
		theGame = new Game();
		this.xSocketIn = xSocketIn;
		this.oSocketIn = oSocketIn;
		this.xSocketOut = xSocketOut;
		this.oSocketOut = oSocketOut;
	}
		
	/**
	 * Instantiates two player objects and appoints the referee to start the game.
	 */
	@Override
    public  void run() {
		
		createPlayers();
		sendClientsMessage("gameStarted");
		
		//set marks of the client players
		xSocketOut.println("setXMark");
		oSocketOut.println("setOMark");
		
		// assign board and players to referee
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
		
        try {
			theGame.appointReferee(theRef);
		} catch (IOException e) {
			System.out.println("referee could not be appointed");
			e.printStackTrace();
		}
        try {
			xSocketIn.close();
	        xSocketOut.close();
	        oSocketIn.close();
	        oSocketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates two player objects.
	 */
	private void createPlayers() {
		
		String xName = null;
		String oName = null;
		try {
			xName = xSocketIn.readLine();
			oName = oSocketIn.readLine();
		} catch (IOException e) {
			System.out.println("Could not read player's name.");
			e.printStackTrace();
			System.exit(0);
		}
		
		// get name of the X player
		xPlayer = new Player(xName, LETTER_X);
		xPlayer.setBoard(theGame.getBoard());
		xPlayer.setModelCon(this);
		// get name of the O player
		oPlayer = new Player(oName, LETTER_O);
		oPlayer.setModelCon(this);
		oPlayer.setBoard(theGame.getBoard());
	}

	/**
	 * Sends a common message to both players.
	 * @param string The message to send.
	 */
	private void sendClientsMessage(String string) {
		xSocketOut.println(string);
		oSocketOut.println(string);
	}

	/**
	 * Requests a move from a client.
	 * @param mark The player's mark.
	 * @return The player's desired mark position.
	 */
	public String getClientMove(char mark) {
		String move = "";
		try {
			if(mark==LETTER_X) {
				xSocketOut.println("yourTurn");
				oSocketOut.println("notYourTurn");
				move = xSocketIn.readLine();
			}
			else {
				oSocketOut.println("yourTurn");
				xSocketOut.println("notYourTurn");
				move = oSocketIn.readLine();
			}
		} catch (IOException e) {
			System.out.println("Could not read player's move");
			e.printStackTrace();
			System.exit(0);
		}
		return move;
	}

	/**
	 * Sends a request to client to update their board state with the opponent's
	 * latest move.
	 * @param row
	 * @param col
	 * @param mark
	 */
	public void updateOpponentBoard(int row, int col, char mark) {
		if(mark=='X') {
			xSocketOut.println("updateBoard"+row+col);
		}
		else {
			oSocketOut.println("updateBoard"+row+col);
		}
	}

	/**
	 * Send message to both clients that player X has won the game.
	 */
	public void sendXWinsMessage() {
		xSocketOut.println("winner");
		oSocketOut.println("loser");
	}

	/**
	 * Send message to both clients that player O has won the game.
	 */
	public void sendOWinsMessage() {
		xSocketOut.println("loser");
		oSocketOut.println("winner");
	}
	
	/**
	 * Send message to both clients that the game has ended in a tie.
	 */
	public void sendTieMessage() {
		xSocketOut.println("gameEndsInTie");
		oSocketOut.println("gameEndsInTie");
	}
	
}
