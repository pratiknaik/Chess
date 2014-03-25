package chessTests;

import chessGame.Board.Color;
import chessGame.King;
import chessGame.Pawn;
import chessGame.StandardBoard;
import junit.framework.TestCase;

/**
 * Test cases for the Chancellor
 * @author Pratik Naik
 *
 */
public class KingTest extends TestCase {
	
	/**
	 * Test valid horizontal move.
	 */
	public void testValidHorizontalKingMove(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(4, 0, Color.white, board);
		assertTrue(newKing.canMove(5, 0));
	}
	
	/**
	 * Test valid vertical move.
	 */
	public void testValidVerticalKingMove(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(4, 0, Color.white, board);
		assertTrue(newKing.canMove(4, 1));
	}
	
	/**
	 * Test valid Diagonal move
	 */
	public void testValidDiagonalKingMove(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(4, 1, Color.white, board);
		assertTrue(newKing.canMove(3, 2));
	}
	
	/**
	 * Test invalid King move.
	 */
	public void testInvalidKingMove(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(3, 7, Color.black, board);
		assertFalse(newKing.canMove(3, 5));
	}
	
	/**
	 * Test ally pice putting king in check
	 */
	public void testInvalidAllyPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(3, 7, Color.black, board);
		Pawn allyPawn = new Pawn(2, 6, Color.black, board);
		assertFalse(newKing.canMove(2, 6));
	}
	
	/**
	 * Test king capturing enemy piece.
	 */
	public void testValidEnemyPieceMove(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(3, 7, Color.black, board);
		Pawn enemyPawn = new Pawn(2, 6, Color.white, board);
		assertTrue(newKing.canMove(2, 6));
	}
	
	/**
	 * Test King putting itself in check
	 */
	public void testInvalidMoveToCheckLocation(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(3, 7, Color.black, board);
		board.blackKingTracker = newKing;
		Pawn enemyPawn = new Pawn(5, 5, Color.white, board);
		assertFalse(newKing.canMove(4, 6));
	}
	
	/**
	 * Test if King displays checked status
	 */
	public void testKingInCheck(){
		StandardBoard board = new StandardBoard(8,8);
		King newKing = new King(3, 7, Color.black, board);
		Pawn enemyPawn = new Pawn(4, 6, Color.white, board);
		assertTrue(newKing.isKingInCheck(newKing));
	}

}
