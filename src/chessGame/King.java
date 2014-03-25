package chessGame;

import chessGame.Board.Color;

/**
 * Subclass of a Piece specific to a King. This handles all movements the king is capable
 * of making.
 * @author Pratik Naik
 *
 */
public class King extends Piece {

	/**
	 * King constructor initializes name of piece to King. Every other parameter is
	 * initialized by superclass.
	 * @param initX
	 * @param initY
	 * @param color
	 * @param board
	 */
	public King(int initX, int initY, Color color, StandardBoard board) {
		super(initX, initY, color, board);
		this.nameOfPiece = "king";
	}
	
	/**
	 * King specific implementation of abstract method.
	 * @see chessGame.Piece#isValidSpecialMove(int, int)
	 * @param newX
	 * @param newY
	 * @return boolean true if valid special move
	 */
	@Override
	boolean isValidSpecialMove(int newX, int newY) {
		int xDisplacement = newX - xLocation;
		int yDisplacement = newY - yLocation;
		// No need to check for obstacles since it's a single step move.
		if(isValidKingMove(xDisplacement, yDisplacement))
			return true;
		else
			return false;
	}

	/**
	 * Helper method for King specific move check (One step in all directions)
	 * @param xDisplacement
	 * @param yDisplacement
	 * @return boolean true if valid King move
	 */
	private boolean isValidKingMove(int xDisplacement, int yDisplacement) {
		// Diagonal
		if(Math.abs(xDisplacement) == 1 && Math.abs(yDisplacement) == 1)
			return true;
		// Horizontal
		else if(Math.abs(xDisplacement) == 1 && Math.abs(yDisplacement) == 0)
			return true;
		// Vertical
		else if(Math.abs(xDisplacement) == 0 && Math.abs(yDisplacement) == 1)
			return true;
		else
			return false;
	}

}
