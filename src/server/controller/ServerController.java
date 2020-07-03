package server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

/**
 * Provides a server controller that sets up a threadpool and allows multiple games
 * of tic-tak-toe to be played simultaneously, one game per thread. Accepts socket 
 * connections for two players for each game.
 * @author Jason Paul
 * @version 1.0
 * @since Feb 8, 2020
 */
public class ServerController {

	private ServerSocket serverSocket;
	private Socket xSocket;
	private Socket oSocket;
	private BufferedReader xSocketIn, oSocketIn;
	private PrintWriter xSocketOut, oSocketOut;
	private ModelController gameCon;
	private ExecutorService pool;
	
	/**
	 * Constructs a server controller, instantiating a server socket and thread
	 * pool.
	 */
	public ServerController() {
		try {
			serverSocket = new ServerSocket(9090);
			pool = Executors.newFixedThreadPool(3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * accepts connections to two players for each game
	 */
	public void runServer() {
		try {
			while(true) {
				// accept player 1
				xSocket = serverSocket.accept();
				System.out.println("Connection to player 1 accepted by server!");
				xSocketIn = new BufferedReader(new InputStreamReader(xSocket.getInputStream()));
				xSocketOut = new PrintWriter((xSocket.getOutputStream()), true);
				// accept player 2
				oSocket = serverSocket.accept();
				System.out.println("Connection to player 2 accepted by server!\n");
				oSocketIn = new BufferedReader(new InputStreamReader(oSocket.getInputStream()));
				oSocketOut = new PrintWriter((oSocket.getOutputStream()), true);	

				// instantiate game for the 2 players
				gameCon = new ModelController (xSocketIn, xSocketOut, 
						oSocketIn, oSocketOut);
				pool.execute(gameCon);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			xSocketIn.close();
			xSocketOut.close();
			xSocket.close();
			oSocketIn.close();
			oSocketOut.close();
			oSocket.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException {

		ServerController myServer = new ServerController();
		myServer.runServer();
	}

}
