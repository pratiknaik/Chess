package chessTests;

import chessGame.Board.Color;
import chessGame.Pawn;
import chessGame.Rook;
import chessGame.StandardBoard;
import junit.framework.TestCase;

/**
 * Test cases for the Rook.
 * @author Pratik Naik
 *
 */
public class RookTest extends TestCase {

	/**
	 * Test valid vertical Rook move.
	 */
	public void testValidVerticalRookMove(){
		StandardBoard board = new StandardBoard(8,8);
		Rook newRook = new Rook(0, 0, Color.white, board);
		assertTrue(newRook.canMove(0, 6));
	}
	
	/**
	 * Test valid horizontal rook move.
	 */
	public void testValidHorizontalRookMove(){
		StandardBoard board = new StandardBoard(8,8);
		Rook newRook = new Rook(0, 0, Color.white, board);
		assertTrue(newRook.canMove(7, 0));
	}
	
	/**
	 * Test invalid rook move.
	 */
	public void testInvalidRookMove(){
		StandardBoard board = new StandardBoard(8,8);
		Rook newRook = new Rook(1, 5, Color.black, board);
		assertFalse(newRook.canMove(7, 0));
	}
	
	/**
	 * Test invalid ally at destination piece move.
	 */
	public void testInvalidAllyPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Rook newRook = new Rook(0, 0, Color.black, board);
		Rook allyRook = new Rook(4, 0, Color.black, board);
		assertFalse(newRook.canMove(4, 0));
	}
	
	/**
	 * Test valid enemy capture move.
	 */
	public void testValidEnemyPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Rook newRook = new Rook(0, 0, Color.black, board);
		Rook enemyRook = new Rook(4, 0, Color.white, board);
		assertTrue(newRook.canMove(4, 0));
	}
	
	/**
	 * Test obstacle in path move.
	 */
	public void testBlockingPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Rook newRook = new Rook(2, 1, Color.white, board);
		Pawn blockingPawn = new Pawn(4, 1, Color.white, board);
		assertFalse(newRook.canMove(6, 1));
	}

}
