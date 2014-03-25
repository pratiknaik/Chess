package chessTests;

import chessGame.Chancellor;
import chessGame.Pawn;
import chessGame.StandardBoard;
import chessGame.Board.Color;
import junit.framework.TestCase;

/**
 * Test cases for the Chancellor
 * @author Pratik Naik
 *
 */
public class ChancellorTest extends TestCase {
	
	/**
	 * Test invalid Chancellor move
	 */
	public void testInvalidChancellorMove(){
		StandardBoard board = new StandardBoard(8,8);
		Chancellor testChan = new Chancellor(4, 3, Color.white, board);
		assertFalse(testChan.canMove(3, 2));
	}
	
	/**
	 * Test valid Chancellor Knight move
	 */
	public void testValidChancellorKnightMove() {
		StandardBoard board = new StandardBoard(8,8);
		Chancellor testKnight = new Chancellor(4, 3, Color.black, board);
		assertTrue(testKnight.canMove(3, 1));	
	}
	
	/**
	 * Test valid Chancellor Rook move
	 */
	public void testValidChancellorRookMove() {
		StandardBoard board = new StandardBoard(8,8);
		Chancellor testRook = new Chancellor(4, 3, Color.black, board);
		assertTrue(testRook.canMove(5, 3));	
	}
	
	/**
	 * Test invalid blocking piece for Rook move
	 */
	public void testInvalidBlockingPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Chancellor testRook = new Chancellor(4, 3, Color.white, board);
		Pawn blockingPawn = new Pawn(4, 4, Color.white, board);
		assertFalse(testRook.canMove(4, 5));
	}
	
	/**
	 * Test valid blocking piece for Knight move.
	 */
	public void testValidBlockingPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Chancellor testKnight = new Chancellor(4, 3, Color.white, board);
		Pawn blockingPawn = new Pawn(3, 4, Color.white, board);
		assertTrue(testKnight.canMove(2, 4));
	}
}
