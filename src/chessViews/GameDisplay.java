package chessViews;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import chessGame.Board;
import chessGame.Piece;
import chessGame.Square;
import chessGame.StandardBoard;

/**
 * The game display class which is a subclass of JPanel. Discussed with tadeegan
 * @author Pratik Naik
 *
 */
public class GameDisplay extends JPanel {
	StandardBoard board;
	int squareSize;
	
	/**
	 * Constructor to initialize a game display
	 * @param gameBoard
	 * @param squareSize
	 */
	public GameDisplay(StandardBoard gameBoard, int squareSize){
		board = gameBoard;
		this.squareSize = squareSize;	
	}
	
	/**
	 * Method which overrides Jpanel's paintComponent function to draw custom components in the Panel.
	 * @param graphic
	 */
	@Override
	public void paintComponent(Graphics graphic){
		for(int i = 0; i < board.numXSquares; i++){
			for(int j = 0; j < board.numYSquares; j++){
				Square squareToDraw = board.squaresList[i][j];
				if(squareToDraw.color.equals(Board.Color.black)){
					graphic.setColor(new Color(58,95,205));
					graphic.fillRect((squareSize*i), (7-j)*squareSize, squareSize, squareSize);
					if(squareToDraw.isOccupied)
						squareToDraw.occupyingPiece.drawPiece(graphic, squareSize, i, j);
				}
				else{
					graphic.setColor(new Color(230, 230, 250));
					graphic.fillRect((squareSize*i), (7-j)*squareSize, squareSize, squareSize);
					if(squareToDraw.isOccupied)
						squareToDraw.occupyingPiece.drawPiece(graphic, squareSize, i, j);
				}
			}
		}
	}
	
}
