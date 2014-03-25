package chessTests;

import chessGame.Board.Color;
import chessGame.King;
import chessGame.Pawn;
import chessGame.StandardBoard;
import junit.framework.TestCase;

/**
 * Test cases for the Pawn
 * @author Pratik Naik
 *
 */
public class PawnTest extends TestCase {
	
	/**
	 * Test valid white pawn move.
	 */
	public void testValidWhitePawnMove(){
		StandardBoard board = new StandardBoard(8,8);
		Pawn newPawn = new Pawn(0, 1, Color.white, board);
		assertTrue(newPawn.canMove(0, 2));
	}
	
	/**
	 * Test valid black pawn move.
	 */
	public void testValidBlackPawnMove(){
		StandardBoard board = new StandardBoard(8,8);
		Pawn newPawn = new Pawn(0, 1, Color.black, board);
		assertTrue(newPawn.canMove(0, 0));
	}
	
	/**
	 * Test invalid pawn move.
	 */
	public void testInvalidPawnMove(){
		StandardBoard board = new StandardBoard(8,8);
		Pawn newPawn = new Pawn(1, 4, Color.black, board);
		assertFalse(newPawn.canMove(1, 6));
	}
	
	/**
	 * Test valid enemy piece move/capture.
	 */
	public void testValidEnemyPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		Pawn newPawn = new Pawn(2, 2, Color.white, board);
		Pawn enemyPawn = new Pawn(3, 3, Color.black, board);
		assertTrue(newPawn.canMove(3, 3));
	}
	
	/**
	 * Test valid First white pawn move.
	 */
	public void testValidFirstPawnMove(){
		StandardBoard board = new StandardBoard(8,8);
		Pawn newPawn = new Pawn(0, 1, Color.white, board);
		assertTrue(newPawn.canMove(0, 3));
	}
	
	/**
	 * Test valid First black pawn move.
	 */
	public void testValidFirstBlackPawnMove(){
		StandardBoard board = new StandardBoard(8,8);
		Pawn newPawn = new Pawn(3, 6, Color.black, board);
		assertTrue(newPawn.canMove(3, 4));
	}
	
	/**
	 * Test valid black pawn first move.
	 */
	public void testValidFirstPawnEnemyMove(){
		StandardBoard board = new StandardBoard(8,8);
		Pawn newPawn = new Pawn(0, 1, Color.white, board);
		Pawn enemyPawn = new Pawn(1, 2, Color.black, board);
		assertTrue(newPawn.canMove(1, 2));
	}

}
