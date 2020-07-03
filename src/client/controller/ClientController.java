package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import client.model.*;

/**
 * Provides fields, inner classes, and methods to act as a controller for the
 * game of Tic-Tac-Toe.
 * @author Jason Paul
 * @version 1.0
 * @since Nov 6, 2019
 */
public class ClientController implements Constants{
	
	/**
	 * The view (GUI).
	 */
	private GameView theView;

	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private char mark;
	private char opponentMark;
	private boolean yourTurn;
	private String playerName;
	
	
	/**
	 * Constructs a ClientController and sets the server name and port number for
	 * a socket connection.
	 * @param serverName Name of the server.
	 * @param portNumber Port number of the server.
	 * @param theView The client GUI class
	 */
	public ClientController (String serverName, int portNumber, GameView theView) {
		
		try {
			aSocket = new Socket (serverName, portNumber);
			//socket input stream
			socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
			socketOut = new PrintWriter((aSocket.getOutputStream()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		theView.addXoButtonListener(new XoButtonListener());
		theView.addNameButtonListener(new NameButtonListener());
		this.theView = theView;
	}
	
	/**
	 * Provides the loop for performing actions on the various requests received
	 * from the server. 
	 */
	public void communicate() {
		
		String inFromServer = "";
		boolean gameFinished = false;
		
		while(!gameFinished) {
			try {
				inFromServer = socketIn.readLine();
			} catch (IOException e) {
				System.err.println("could not read from socket!");
				e.printStackTrace();
				System.exit(0);
			}
			String position = null;
			if(inFromServer.startsWith("update")) {
				position = inFromServer.substring(11, 13);
				inFromServer = inFromServer.substring(0, 11);
			}
			switch(inFromServer) {
				case "gameStarted":
					gameStarted();
					break;
				case "yourTurn":
					yourTurn();
					break;
				case "notYourTurn":
					notYourTurn();
					break;
				case "setXMark":
					mark=LETTER_X;
					opponentMark=LETTER_O;
					theView.setPlayerMarkField("X");
					break;
				case "setOMark":
					mark=LETTER_O;
					opponentMark=LETTER_X;
					theView.setPlayerMarkField("O");
					break;
				case "updateBoard":
					updateBoard(position);
					break;
				case "winner":
					winner();
					gameFinished = true;
					break;
				case "loser":
					loser();
					gameFinished = true;
					break;
				case "gameEndsInTie":
					tie();
					gameFinished = true;
					break;
			}
		}
		
		try {
			socketIn.close();
			socketOut.close();
			aSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays message to client indicating that game has started.
	 */
	private void gameStarted() {
		printClientMessage("The game has started...");
		theView.setButtonStatus(true);
	}
		
	/**
	 * Displays message to client indicating that it is their turn.
	 */
	private void yourTurn() {
		printClientMessage(playerName + ", it's your turn...");
		yourTurn = true;
	}

	/**
	 * Displays message to client indicating that it is not their turn.
	 */
	private void notYourTurn() {
		printClientMessage("It's the other player's turn...");
		yourTurn=false;
	}

	/**
	 * updates the client side board, which keeps board state.
	 * @param position The position on the board to add a mark to.
	 */
	private void updateBoard(String position) {
		int row = Integer.parseInt(position.substring(0, 1));
		int col = Integer.parseInt(position.substring(1, 2));
		int buttonID = 0;
		if(row==0) {
			if(col==0)
				buttonID = 0;
			else if(col==1)
				buttonID = 1;
			else if(col==2)
				buttonID = 2;
		}
		else if (row==1) {
			if(col==0)
				buttonID = 3;
			else if(col==1)
				buttonID = 4;
			else if(col==2)
				buttonID = 5;
		}
		else if(row==2) {
			if(col==0)
				buttonID = 6;
			else if(col==1)
				buttonID = 7;
			else if(col==2)
				buttonID = 8;
		}
		theView.getXoButtons()[buttonID].setText(Character.toString(opponentMark));
	}

	/**
	 * Displays message to client that they have won the game.
	 */
	private void winner() {
		printClientMessage("You have won the game!");
		theView.setButtonStatus(false);
	}

	/**
	 * Displays message to client that they have lost the game.
	 */
	private void loser() {
		printClientMessage("You have lost the game.");
		theView.setButtonStatus(false);
	}

	/**
	 * Displays message to client that game has ended in a tie.
	 */
	private void tie() {
		printClientMessage("Game has ended in a tie!");
		theView.setButtonStatus(false);
	}

	/**
	 * Inner class to provide a listener for the name button on the GUI. It 
	 * reads in the names of both players and calls a helper method to start the
	 * game.
	 * @author Jason Paul
	 *
	 */
	public class NameButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			playerName = theView.getPlayerNameField();
			if ( (! playerName.equals("")) && (!(playerName == null))) {
				socketOut.println(playerName);
				theView.getNameButton().setEnabled(false);
			}
		}
	}
	
	/**
	 * Inner class to provide a listener for the buttons acting as the X and O
	 * grid.
	 * @author Jason Paul
	 *
	 */
	public class XoButtonListener implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if(source.getText().equals("X") || source.getText().equals("O") || !yourTurn) {
				return;
			}
			addMark(source);
		}
	}

	/**
	 * Adds the player's mark to the board and send move to Server.
	 * @param source The button object that was pressed.
	 */
	private void addMark(JButton source) {
		int row = 0, col = 0;
		if(source == theView.getXoButtons()[0]) {
			row = 0;
			col = 0;
		}
		else if(source == theView.getXoButtons()[1]) {
			row = 0;
			col = 1;
		}
		else if(source == theView.getXoButtons()[2]) {
			row = 0;
			col = 2;
		}
		else if(source == theView.getXoButtons()[3]) {
			row = 1;
			col = 0;
		}		
		else if(source == theView.getXoButtons()[4]) {
			row = 1;
			col = 1;
		}
		else if(source == theView.getXoButtons()[5]) {
			row = 1;
			col = 2;
		}		
		else if(source == theView.getXoButtons()[6]) {
			row = 2;
			col = 0;
		}		
		else if(source == theView.getXoButtons()[7]) {
			row = 2;
			col = 1;
		}
		else if(source == theView.getXoButtons()[8]){
			row = 2;
			col = 2;
		}
		socketOut.println(row+""+col);
		source.setText(Character.toString(mark));
	}
	
	/**
	 * Prints a message to the message box on the GUI.
	 * @param m
	 */
	private void printClientMessage(String m) {
		theView.setMessageText(m);
	}
	
	void setTheView(GameView theView) {
		this.theView = theView;
	}
	
	/**
	 * Entry point for the Client app.
	 * @param args
	 */
	public static void main(String[] args) {
		GameView theView = new GameView("Tic-Tak-Toe");
		ClientController aClient = new ClientController ("localhost", 9090, theView);
		aClient.communicate();
	}
	
}
