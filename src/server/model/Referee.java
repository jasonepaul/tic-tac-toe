package server.model;
import java.io.IOException;
import server.controller.*;
/**
 * Provides data fields and methods for the referee of the game.
 * @author Jason Paul
 * @version 1.0
 * @since Oct 6, 2019
 */
public class Referee {
	
	/**
	 * The player that will use X's.
	 */
	private Player xPlayer;
	
	/**
	 * The player that will use O's.
	 */
	private Player oPlayer;
	
	/**
	 * Reference to the game controller.
	 */
	private ModelController ModelCon;
	
	
	/**
	 * Constructs a referee. The values of the data fields are not set with this
	 * constructor. 
	 */
	public Referee() {}
	
	/**
	 * Starts the game. Sets the opponent for each player, displays the board,
	 * and gives the first move to the X player.
	 * @throws IOException User input must be convertible to type int.
	 */
	public void runTheGame() throws IOException {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		xPlayer.play();
	}
	
	/**
	 * Setter for the X player.
	 * @param xPlayer The player with mark X.
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}
	
	/**
	 * Setter for the O player.
	 * @param oPlayer The player with mark O.
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	
	public void setModelController(ModelController ModelCon) {
		this.ModelCon = ModelCon;
	}

}
