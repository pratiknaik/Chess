package chessGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import chessGame.Board.Color;

/**
 * Superclass Piece since all chess pieces have common variables and methods to execute.
 * Defines a standard piece and it's features.
 * @author Pratik Naik
 */
public abstract class Piece {
	
	/**
	 * Global variables that a piece needs to store.
	 */
	// Piece specific name will be stored here
	String nameOfPiece;
	// Black or White piece
	public Color color;
	// Reference to the board this piece is on to indirectly access squaresList
	StandardBoard currentBoard;
	// xLocation of piece on board.
	public int xLocation;
	// yLocation of piece on board.
	public int yLocation;
	
	/**
	 * Abstract method implemented by each subclass piece for their unique movement across the board.
	 * Each piece defined it's own move and can be called to check if it's a valid move.
	 * @param newX
	 * @param newY
	 * @return boolean true if piece has a valid special move
	 */
	abstract boolean isValidSpecialMove(int newX, int newY);

	/**
	 * Constructor to initialize the common parameters of a piece.
	 * @param initX
	 * @param initY
	 * @param color
	 * @param board
	 */
	public Piece(int initX, int initY, Color color, StandardBoard board) {
		this.color = color;
		board.squaresList[initX][initY].isOccupied = true;
		board.squaresList[initX][initY].occupyingPiece = this;
		this.currentBoard = board;
		this.xLocation = initX;
		this.yLocation = initY;
	}

	/**
	 * IMPORTANT method which determines whether a piece can be moved on the board.
	 * This method checks :
	 * - Is piece within board boundaries
	 * - Is piece following it's specific movement pattern
	 * - Is destination location unoccupied or filled by enemy piece for valid move/capture
	 * - Will moving this piece place it's king in danger (check) 
	 * @param newX
	 * @param newY
	 * @return boolean if piece can move
	 */
	public boolean canMove(int newX, int newY){
		if(!currentBoard.inBoardBounds(newX, newY))
			return false;
		if(!isValidSpecialMove(newX, newY))
			return false;
		if(!isEnemyPieceAtDestination(newX, newY))
			return false;
		if(isKingInDanger(newX, newY))
			return false;
		return true;
	}
	
	/**
	 * Helper method to check if destination location is unoccupied or has an enemy piece.
	 * @param newX
	 * @param newY
	 * @return boolean true if enemy is at destination
	 */
	private boolean isEnemyPieceAtDestination(int newX, int newY){
		Square squareToCheck = currentBoard.squaresList[newX][newY];
		if(squareToCheck.isOccupied){
			return isEnemyPiece(this.color, squareToCheck.occupyingPiece);
		}
		return true;
	}
	
	/**
	 * Method which executes a capture or move.
	 * Capture works similar to move if enemy piece is at destination.
	 * @param newX
	 * @param newY
	 */
	public void executeCaptureOrMove(int newX, int newY){
		movePiece(this, newX, newY);
	}
	
	/**
	 * IMPORTANT method which determines if the king is in a check state.
	 * Takes in the king(white or black) that we want to check and it's location.
	 * @param kingXLocation
	 * @param kingYLocation
	 * @param kingToCheck
	 * @return boolean true if given King is in check state
	 */
	public boolean isKingInCheck(King kingToCheck) {
		int kingXLocation = kingToCheck.xLocation;
		int kingYLocation = kingToCheck.yLocation;
		Color colorToCheck = kingToCheck.color;
		// Iterates through the squares on the board and checks if enemy pieces can attack king.
		for(int i = 0; i < currentBoard.numXSquares; i++){
			for(int j = 0; j < currentBoard.numYSquares; j++){
				Square squareToCheck = currentBoard.squaresList[i][j];
				if(squareToCheck.isOccupied){
					if(isEnemyPiece(colorToCheck, squareToCheck.occupyingPiece)){
						Piece enemyPiece = squareToCheck.occupyingPiece;
						if(enemyPiece.isValidSpecialMove(kingXLocation, kingYLocation))
							return true;
					}
				}
			}
		}
		return false;
		
	}
	
	/**
	 * Helper method which determines if moving a piece puts it's king in danger
	 * @param newPieceX
	 * @param newPieceY
	 * @return boolean true if moving current piece puts king in danger.
	 */
	private boolean isKingInDanger(int newPieceX, int newPieceY) {
		int oldPieceX = this.xLocation;
		int oldPieceY = this.yLocation;
		King kingToCheck;
		Square squareToCheck = currentBoard.squaresList[newPieceX][newPieceY];
		if(squareToCheck.isOccupied){
			Piece pieceToCheck = squareToCheck.occupyingPiece;
			if(isEnemyPieceAtDestination(newPieceX, newPieceY)){
				Piece enemyPiece = pieceToCheck;
				if(this.color.equals(Color.white)){
					if(currentBoard.whiteKingTracker == null)
						return false;
					kingToCheck = currentBoard.whiteKingTracker;
				}
				else{
					if(currentBoard.blackKingTracker == null)
						return false;
					kingToCheck = currentBoard.blackKingTracker;
				}
				movePiece(this, newPieceX, newPieceY);
				if(isKingInCheck(kingToCheck)){
					movePiece(this, oldPieceX, oldPieceY);
					movePiece(enemyPiece, newPieceX, newPieceY);
					return true;
				}
				movePiece(this, oldPieceX, oldPieceY);
				movePiece(enemyPiece, newPieceX, newPieceY);
			}
		}
		else{
			if(this.color.equals(Color.white)){
				if(currentBoard.whiteKingTracker == null)
					return false;
				kingToCheck = currentBoard.whiteKingTracker;
			}
			else{
				if(currentBoard.blackKingTracker == null)
					return false;
				kingToCheck = currentBoard.blackKingTracker;
			}
			movePiece(this, newPieceX, newPieceY);
			if(isKingInCheck(kingToCheck)){
				movePiece(this, oldPieceX, oldPieceY);
				return true;
			}
			movePiece(this, oldPieceX, oldPieceY);
		}
		return false;
	}
	
	/**
	 * Helper method to move piece into given location.
	 * Called in 2 cases:
	 * - When the move is valid and needs to be executed.
	 * - To check if making this move would put ally king in danger.
	 * @param pieceToMove
	 * @param newPieceX
	 * @param newPieceY
	 */
	private void movePiece(Piece pieceToMove, int newPieceX, int newPieceY){
		Square currentSquare = currentBoard.squaresList[pieceToMove.xLocation][pieceToMove.yLocation];
		Square targetSquare = currentBoard.squaresList[newPieceX][newPieceY];
		currentSquare.isOccupied = false;
		currentSquare.occupyingPiece = null;
		targetSquare.isOccupied = true;
		targetSquare.occupyingPiece = pieceToMove;
		pieceToMove.xLocation = newPieceX;
		pieceToMove.yLocation = newPieceY;
	}

	/**
	 * Helper method comparing colors to determine ally or enemy.
	 * @param colorToCheck
	 * @param occupyingPiece
	 * @return boolean true if piece is your enemy.
	 */
	private boolean isEnemyPiece(Color colorToCheck, Piece occupyingPiece) {
		if(colorToCheck.equals(occupyingPiece.color))
			return false;
		else
			return true;
	}
	
	/**
	 * Method to draw the pieces on the board. This method also determines piece asset to draw.
	 * Takes in graphics which we are using and square size of the board.
	 * @param graphic
	 * @param squareSize
	 * @param y 
	 * @param x 
	 * 
	 */
	public void drawPiece(Graphics graphic, int squareSize, int x, int y){
		if(this.color.equals(Color.black)){
			String name = this.nameOfPiece.concat(".png");
			String imagePath = "assets/black_";
			String imageName = imagePath.concat(name);
			drawPieceHelper(graphic, squareSize, imageName, x, y);
		}
		else{
			String name = this.nameOfPiece.concat(".png");
			String imagePath = "assets/white_";
			String imageName = imagePath.concat(name);
			drawPieceHelper(graphic, squareSize, imageName, x, y);
		}
	}

	/**
	 * A helper method to draw the piece in the proper coordinates on the board.
	 * @param graphic
	 * @param squareSize
	 * @param imageName
	 * @param y 
	 * @param x 
	 */
	private void drawPieceHelper(Graphics graphic, int squareSize, String imageName, int x, int y) {
		File imageFile = new File(imageName);
		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();
		int heightPadding = (squareSize - imageHeight)/2;
		int widthPadding = (squareSize - imageWidth)/2;
		graphic.drawImage(image, (squareSize*x) + widthPadding, ((7-y)*squareSize) + heightPadding, imageWidth, imageHeight, null);
	}
	
	/**
	 * Helper method to check if the king is in checkmate!
	 * @param kingToCheck
	 * @return boolean true of king passed in is in checkmate
	 */
	public boolean isKingCheckmate(King kingToCheck){
		if(!isKingInCheck(kingToCheck))
			return false;
		Color colorToCheck = kingToCheck.color;
		for(int i = 0; i < currentBoard.numXSquares; i++){
			for(int j = 0; j < currentBoard.numYSquares; j++){
				Square squareToCheck = currentBoard.squaresList[i][j];
				if(squareToCheck.isOccupied){
					if(!isEnemyPiece(colorToCheck, squareToCheck.occupyingPiece)){
						Piece allyPiece = squareToCheck.occupyingPiece;
						 if(!checkmateHelper(allyPiece, kingToCheck))
							 return false;
					}
				}	
			}
		}
		return true;
	}

	/**
	 * Helper method to iterate through the pieces to check if any move can break the check.
	 * @param allyPiece
	 * @param kingToCheck
	 * @return boolean true if the king is well and truly in checkmate.
	 */
	private boolean checkmateHelper(Piece allyPiece, King kingToCheck) {
		int oldPieceX = allyPiece.xLocation;
		int oldPieceY = allyPiece.yLocation;
		for(int i = 0; i < currentBoard.numXSquares; i++){
			for(int j = 0; j < currentBoard.numYSquares; j++){
				Square squareToCheck = currentBoard.squaresList[i][j];
				if(isEnemyPieceAtDestination(i,j)){
					if(allyPiece.isValidSpecialMove(i, j)){
						if(squareToCheck.isOccupied){
							Piece enemyPiece = squareToCheck.occupyingPiece;
							movePiece(allyPiece, i, j);
							if(!isKingInCheck(kingToCheck)){
								movePiece(allyPiece, oldPieceX, oldPieceY);
								movePiece(enemyPiece, i, j);
								return false;
							}
							movePiece(allyPiece, oldPieceX, oldPieceY);
							movePiece(enemyPiece, i, j);
						}
						else{
							movePiece(allyPiece, i, j);
							if(!isKingInCheck(kingToCheck)){
								movePiece(allyPiece, oldPieceX, oldPieceY);
								return false;
							}
							movePiece(allyPiece, oldPieceX, oldPieceY);
						}
					}
				}
			}
		}
		return true;
	}
	
}


