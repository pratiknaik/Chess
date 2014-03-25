package chessGame;

import chessGame.Board.Color;

/**
 * Subclass of a Piece specific to a Pawn. This handles all movements the pawn is capable
 * of making.
 * @author Pratik Naik
 */
public class Pawn extends Piece {

	/**
	 * Pawn constructor initializes name of piece to pawn and sets firstMove. Every other parameter 
	 * is initialized by superclass.
	 * @param initX
	 * @param initY
	 * @param color
	 * @param board
	 */
	public Pawn(int initX, int initY, Color color, StandardBoard board) {
		super(initX, initY, color, board);
		this.nameOfPiece = "pawn";
	}

	/**
	 * Pawn specific implementation of abstract method.
	 * @see chessGame.Piece#isValidSpecialMove(int, int)
	 * @param newX
	 * @param newY
	 * @return boolean true if valid special move
	 */
	@Override
	boolean isValidSpecialMove(int newX, int newY) {
		int xDisplacement = newX - xLocation;
		int yDisplacement = newY - yLocation;
		if(isValidPawnMove(xDisplacement, yDisplacement)){
			Square squareToCheck = currentBoard.squaresList[xLocation + xDisplacement][yLocation + yDisplacement];
			// If the pawn moves forward the square should not be occupied
			if(xDisplacement == 0){
				if(squareToCheck.isOccupied)
					return false;
				return true;
			}
			// If the pawn moves to capture the square should be occupied
			else{
				if(squareToCheck.isOccupied)
					return true;
				return false;
			}
		}
		return false;
	}

	/**
	 * Helper method for Pawn specific move check. (First Move + Regular Move)
	 * @param xDisplacement
	 * @param yDisplacement
	 * @return boolean true if valid pawn move
	 */
	private boolean isValidPawnMove(int xDisplacement, int yDisplacement) {
		// Two steps allowed in first move
		if((this.yLocation == 6 && this.color.equals(Color.black)) || (this.yLocation == 1 && this.color.equals(Color.white))){
			return handlePawnFirstMove(xDisplacement, yDisplacement);
		}
		// Single steps allowed in future moves.
		else{
			return handleRegularPawnMove(xDisplacement, yDisplacement);
		}
	}

	/**
	 * Helper method for regular move of Pawn. (Vertical + Capture)
	 * @param xDisplacement
	 * @param yDisplacement
	 * @return boolean true if valid regular pawn move
	 */
	private boolean handleRegularPawnMove(int xDisplacement, int yDisplacement) {
		if(color.equals(Color.white)){
			// White capture or move upwards.
			if(yDisplacement == 1 && (xDisplacement == 0 || Math.abs(xDisplacement) == 1))
				return true;
			else
				return false;
		}
		else{
			// Black capture or move downwards.
			if(yDisplacement == -1 && (xDisplacement == 0 || Math.abs(xDisplacement) == 1))
				return true;
			else
				return false;
		}
	}

	/**
	 * Helper method for first move of Pawn. Two steps allowed.
	 * @param xDisplacement
	 * @param yDisplacement
	 * @return boolean true if valid first pawn move
	 */
	private boolean handlePawnFirstMove(int xDisplacement, int yDisplacement) {
		// White pawns can only move upwards.
		if(color.equals(Color.white)){
			// Two step without capture.
			if((yDisplacement == 1 || yDisplacement == 2) && (xDisplacement == 0))
				return true;
			// One step plus capture.
			else if(yDisplacement == 1 && Math.abs(xDisplacement) == 1)
				return true;
			else			
				return false;
		}
		// Black pawns can only move downwards.
		else{
			if((yDisplacement == -1 || yDisplacement == -2) && (xDisplacement == 0))
				return true;
			else if(yDisplacement == -1 && Math.abs(xDisplacement) == 1)
				return true;
			else
				return false;
		}
	}
}
