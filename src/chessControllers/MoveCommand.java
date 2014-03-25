package chessControllers;

import chessGame.Piece;

/**
 * Class to keep track of a move on the chess board.
 * @author Pratik Naik
 *
 */
public class MoveCommand {
	
	/**
	 * Each move command consists of these variables.
	 */
	Piece movingPiece;
	Piece enemyPiece;
	int xDestination;
	int yDestination;
	int xOrigin;
	int yOrigin;
	
	/**
	 * Constructor to initialize a new move command to add it to the stack of moves.
	 * @param movingPiece
	 * @param enemyPiece
	 * @param xDestination
	 * @param yDestination
	 */
	public MoveCommand(Piece movingPiece, Piece enemyPiece, int xDestination, int yDestination){
		this.movingPiece = movingPiece;
		this.enemyPiece = enemyPiece;
		this.xOrigin = movingPiece.xLocation;
		this.yOrigin = movingPiece.yLocation;
		this.xDestination = xDestination;
		this.yDestination = yDestination;
	}
	
	/**
	 * Method to undo the last move made on the move command stack.
	 */
	public void undo(){
		this.movingPiece.executeCaptureOrMove(xOrigin, yOrigin);
		if(this.enemyPiece != null)
			this.enemyPiece.executeCaptureOrMove(xDestination, yDestination);
	}
	
	/**
	 * Method to execute a move in the chess game.
	 */
	public void execute(){
		movingPiece.executeCaptureOrMove(xDestination, yDestination);
	}

}
