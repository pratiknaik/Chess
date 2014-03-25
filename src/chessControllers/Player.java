package chessControllers;

import chessGame.Board;
import chessGame.Board.Color;

/**
 * Class reprensenting a Chess Player.
 * @author Pratik Naik
 *
 */
public class Player {
	
	/**
	 * Global variables of a chess Player
	 * - Name
	 * - Color being played.
	 * - Score of the player in game.
	 */
	public String playerName;
	Color playerColor;
	public int playerScore;

	/**
	 * Constructor to add a new player to the game.
	 * @param playerName
	 * @param playerColor
	 */
	public Player(String playerName, Color playerColor) {
		this.playerName = playerName;
		this.playerColor = playerColor;
		this.playerScore = 0;
		
	}
}
