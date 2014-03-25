package chessGame;

import chessGame.Board.Color;

/**
 * Subclass of a Piece specific to a Chancellor. This handles all movements the Chancellor is capable
 * of making.
 * @author Pratik Naik
 *
 */
public class Chancellor extends Piece {

	/**
	 * Chancellor constructor initializes name of piece to Chancellor. Every other parameter is
	 * initialized by superclass.
	 * @param initX
	 * @param initY
	 * @param color
	 * @param board
	 */
	public Chancellor(int initX, int initY, Color color, StandardBoard board) {
		super(initX, initY, color, board);
		this.nameOfPiece = "chancellor";
	}

	/**
	 * Chancellor specific implementation of abstract method.
	 * @see chessGame.Piece#isValidSpecialMove(int, int)
	 * @param newX
	 * @param newY
	 * @return boolean true if valid special move
	 */
	@Override
	boolean isValidSpecialMove(int newX, int newY) {
		int xDisplacement = newX - xLocation;
		int yDisplacement = newY - yLocation;
		// Chancellor valid rook movement
		if(Rook.isValidRookMove(xDisplacement, yDisplacement)) {
			int steps = Math.max(Math.abs(xDisplacement), Math.abs(yDisplacement));
			int xDirection = xDisplacement/steps;
			int yDirection = yDisplacement/steps;
			// Check for obstacles in path of Rook.
			for(int i = 1; i < steps; i++){
				Square squareToCheck = currentBoard.squaresList[xLocation + i*xDirection][yLocation + i*yDirection];
				if(squareToCheck.isOccupied)
					return false;
			}
			return true;
		}
		// Chancellor valid Knight movement
		else if(Knight.isValidKnightMove(xDisplacement, yDisplacement))
			return true;
		else
			return false;
	}
}
