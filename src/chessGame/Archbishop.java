package chessGame;

import chessGame.Board.Color;

/**
 * Subclass of a Piece specific to a Archbishop. This handles all movements the Archbishop is capable
 * of making.
 * @author Pratik Naik
 */
public class Archbishop extends Piece {
	/**
	 * Archbishop constructor initializes name of piece to Archbishop. Every other parameter is
	 * initialized by superclass.
	 * @param initX
	 * @param initY
	 * @param color
	 * @param board
	 */
	public Archbishop(int initX, int initY, Color color, StandardBoard board) {
		super(initX, initY, color, board);
		this.nameOfPiece = "archbishop";
	}

	/**
	 * Archbishop specific implementation of abstract method.
	 * @see chessGame.Piece#isValidSpecialMove(int, int)
	 * @param newX
	 * @param newY
	 * @return boolean true if valid special move
	 */
	@Override
	boolean isValidSpecialMove(int newX, int newY) {
		int xDisplacement = newX - xLocation;
		int yDisplacement = newY - yLocation;
		// Archbishop valid bishop movement
		if(Bishop.isValidBishopMove(xDisplacement, yDisplacement)) {
			int steps = Math.abs(xDisplacement);
			int xDirection = xDisplacement/steps;
			int yDirection = yDisplacement/steps;
			// Check for obstacles in path of Bishop.
			for(int i = 1; i < steps; i++){
				Square squareToCheck = currentBoard.squaresList[xLocation + i*xDirection][yLocation + i*yDirection];
				if(squareToCheck.isOccupied)
					return false;
			}
			return true;
		}
		// Archbishop valid Knight move
		else if(Knight.isValidKnightMove(xDisplacement, yDisplacement))
			return true;
		else
			return false;
	}

}
