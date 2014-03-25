package chessTests;

import chessGame.Bishop;
import chessGame.Pawn;
import chessGame.Board.Color;
import chessGame.StandardBoard;
import junit.framework.TestCase;

/**
 * Tests for the Bishop Piece
 * @author Pratik Naik
 *
 */
public class BishopTest extends TestCase {
	
	/**
	 * Test valid Bishop move.
	 */
	public void testValidBishopMove(){
		StandardBoard board = new StandardBoard(8,8);
		Bishop newBishop = new Bishop(5, 3, Color.black, board);
		assertTrue(newBishop.canMove(1, 7));
	}
	
	/**
	 * Test invalid bishop move.
	 */
	public void testInvalidBishopMove(){
		StandardBoard board = new StandardBoard(8,8);
		Bishop newBishop = new Bishop(5, 3, Color.white, board);
		assertFalse(newBishop.canMove(4, 5));
	}
	
	/**
	 * Test valid blocking piece move. Piece not in path.
	 */
	public void testValidBlockingPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Bishop testBishop = new Bishop(1, 0, Color.white, board);
		Pawn blockingPawn = new Pawn(1, 1, Color.white, board);
		assertTrue(testBishop.canMove(0, 1));
	}
	
	/**
	 * Test invalid blocking piece move. Bishop getting stopped.
	 */
	public void testInvalidBlockingPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Bishop testBishop = new Bishop(1, 0, Color.white, board);
		Pawn blockingPawn = new Pawn(2, 1, Color.white, board);
		assertFalse(testBishop.canMove(3, 2));
	}
}
