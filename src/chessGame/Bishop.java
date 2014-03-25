package chessGame;

import chessGame.Board.Color;

/**
 * Subclass of a Piece specific to a Bishop. This handles all movements the bishop is capable
 * of making.
 * @author Pratik Naik
 */
public class Bishop extends Piece {
	
	/**
	 * Bishop constructor initializes name of piece to Bishop. Every other parameter is
	 * initialized by superclass.
	 * @param initX
	 * @param initY
	 * @param color
	 * @param board
	 */
	public Bishop(int initX, int initY, Color color, StandardBoard board) {
		super(initX, initY, color, board);
		this.nameOfPiece = "bishop";
	}
	
	/**
	 * Bishop specific implementation of abstract method.
	 * @see chessGame.Piece#isValidSpecialMove(int, int)
	 * @param newX
	 * @param newY
	 * @return boolean true if valid special move
	 */
	@Override
	boolean isValidSpecialMove(int newX, int newY) {
		int xDisplacement = newX - xLocation;
		int yDisplacement = newY - yLocation;
		if(isValidBishopMove(xDisplacement, yDisplacement)){
			// Total number of steps the piece has to take
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
		return false;
	}
	
	/**
	 * Helper method for Bishop specific move check (Diagonals)
	 * @param xDisplacement
	 * @param yDisplacement
	 * @return boolean 
	 */
	public static boolean isValidBishopMove(int xDisplacement, int yDisplacement) {
		if((Math.abs(xDisplacement) == Math.abs(yDisplacement)) && xDisplacement != 0)
			return true;
		return false;
	}

}
