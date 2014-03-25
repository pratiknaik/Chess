package chessTests;

import chessGame.Archbishop;
import chessGame.Bishop;
import chessGame.Knight;
import chessGame.Pawn;
import chessGame.StandardBoard;
import chessGame.Board.Color;
import junit.framework.TestCase;

/**
 * Tests for the Archbishop Piece.
 * @author Pratik Naik
 *
 */
public class ArchbishopTest extends TestCase {
	
	/**
	 * Test valid Archbishop move
	 */
	public void testValidArchBishopMove() {
		StandardBoard board = new StandardBoard(8,8);
		Archbishop testBishop = new Archbishop(5, 3, Color.black, board);
		assertTrue(testBishop.canMove(1, 7));	
	}
	
	/**
	 * Test Invalid Arch Bishop Move
	 */
	public void testInvalidArchBishopMove(){
		StandardBoard board = new StandardBoard(8,8);
		Archbishop testBishop = new Archbishop(5, 3, Color.white, board);
		assertFalse(testBishop.canMove(4, 6));
	}
	
	/**
	 * Test valid Arch Knight move.
	 */	
	public void testValidArchKnightMove() {
		StandardBoard board = new StandardBoard(8,8);
		Archbishop testKnight = new Archbishop(1, 0, Color.black, board);
		assertTrue(testKnight.canMove(2, 2));	
	}
	
	/**
	 * Test invalid Arch Knight move
	 */
	public void testInvalidArchKnightMove(){
		StandardBoard board = new StandardBoard(8,8);
		Archbishop testKnight = new Archbishop(6, 2, Color.white, board);
		assertFalse(testKnight.canMove(6, 1));
	}
	
	/**
	 * Test valid blocking piece move. Knight skipping over.
	 */
	public void testValidBlockingPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Archbishop testKnight = new Archbishop(1, 0, Color.white, board);
		Pawn blockingPawn = new Pawn(1, 1, Color.white, board);
		assertTrue(testKnight.canMove(2, 2));
	}
	
	/**
	 * Test invalid blocking piece move. Bishop getting stopped.
	 */
	public void testInvalidBlockingPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Archbishop testBishop = new Archbishop(1, 0, Color.white, board);
		Pawn blockingPawn = new Pawn(2, 1, Color.white, board);
		assertFalse(testBishop.canMove(3, 2));
	}

}
