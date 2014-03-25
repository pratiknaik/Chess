package chessControllers;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import chessGame.King;
import chessGame.Piece;
import chessGame.StandardBoard;
import chessGame.Board;
import chessViews.GameDisplay;

/**
 * Game class to setup a complete Chess Game. Will move to Controllers once implemented properly.
 * @author Pratik Naik
 *
 */
public class Game {
	
	/**
	 * Global variables of the game 
	 * - Players
	 * - Game board, turn, type, board square size.
	 * - References of gamepanel, sidepanels, buttons, score labels.
	 * - Reference to piece being moved.
	 * - Moves stack.
	 */
	static Player whitePlayer;
	static Player blackPlayer;
	chessGame.Board.Color gameTurn;
	StandardBoard gameBoard;
	boolean gameOver;
	static boolean gameType;
	int squareSize;
	JFrame window;
	JPanel gamePanel;
	JPanel sidePanel;
	JLabel whiteLabel;
	JLabel blackLabel;
	JLabel whiteScore;
	JLabel blackScore;
	JButton forfeitButton;
	JButton undoButton;
	JButton restartButton;
	Piece movingPiece;
	Stack<MoveCommand> commandStack;
	
	/**
	 * Method to initialize gameBoard, populate it with pieces according to gameType 
	 * and start a new move stack.
	 * @param gameType 
	 */
	public void gameInit(boolean gameType) {
		gameBoard = new StandardBoard(8,8);
		gameBoard.populateBoardWithPieces(gameType);
		gameTurn = chessGame.Board.Color.white;
		gameOver = false;
		squareSize = 80;
		commandStack = new Stack();
	}

	/**
	 * Helper method to instantiate players of the current game.
	 */
	private static void getGamePlayers() {
		String whiteName = JOptionPane.showInputDialog("Please input White player name");
		if(whiteName == "" || whiteName == null)
			whiteName = "Bob Dylan";
		String blackName = JOptionPane.showInputDialog("Please input Black player name");
		if(blackName == "" || blackName == null)
			blackName = "Frank Sinatra";
		whitePlayer = new Player(whiteName, chessGame.Board.Color.white);
		blackPlayer = new Player(blackName, chessGame.Board.Color.black);
	}
	
	/**
	 * Helper method to get the type of game. Special includes Archbishop and Chancellors.
	 * @return boolean true if special game
	 */
	private static boolean getGameType() {
		int response = JOptionPane.showConfirmDialog(null, "Do you want to play a Special Game?", "Game Type", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION)
			gameType = true;
		else
			gameType = false;
		return gameType;
	}
	
	/**
	 * Method to start off a game thread and start running a game loop.
	 */
	public void gameStart(){
		Thread gameThread = new Thread(){
			@Override
			public void run(){
				gameLoop();
			}
		};
		gameThread.start();
		
	}

	/**
	 * Helper method to run the main game loop. If the game is over the game loop breaks
	 * and the gamePanel stops getting repainted (updated).
	 */
	private void gameLoop(){
		while(true){
			if(gameOver)
				break;
			gamePanel.repaint();
		}
	}
	
	/**
	 * Method to setup initial display of the Board. Sets up the gamePanel and sidePanel in the
	 * game's main frame.
	 */
	public void setupDisplay(){
		window = new JFrame("Super Mega Death Chess");
        gamePanel = initializeGamePanel(gameBoard); 
        Container contentPanel = window.getContentPane();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.LINE_AXIS));
        sidePanel = initializeSidePanel();
        contentPanel.add(gamePanel);
        contentPanel.add(sidePanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.validate();
        window.pack();

	}
	
	/**
	 * Helper method to initialize a JPanel for the game.
	 * @param gameBoard
	 * @return A JPanel instance of the game Board window.
	 */
	private JPanel initializeGamePanel(StandardBoard gameBoard) {
        GameDisplay gameDisplay = new GameDisplay(gameBoard, squareSize);
        gameDisplay.setPreferredSize(new Dimension(640,640));
        gameDisplay.setLayout(new BorderLayout());
        return gameDisplay;
    }
	
	/**
	 * Helper method to initialize a side JPanel for the game. 
	 * Contains :
	 *  - Player Name and Scores.
	 *  - Indicators of whose turn it is.
	 *  - Undo, Restart, and Forfeit button.
	 * @return a JPanel with all the above components
	 */
	private JPanel initializeSidePanel(){
		JPanel sideDisplay = new JPanel();
		undoButton = new JButton("Undo Move");
		restartButton = new JButton("Restart Game");
		forfeitButton = new JButton("Forfeit Game");
		setupButtonListeners();
		whiteLabel = new JLabel("WHITE PLAYER : ".concat(whitePlayer.playerName) + " ");
		whiteLabel.setForeground(Color.BLUE);
		blackLabel = new JLabel("BLACK PLAYER : ".concat(blackPlayer.playerName) + " ");
		whiteScore = new JLabel(whitePlayer.playerName + " Score : " + whitePlayer.playerScore);
		blackScore = new JLabel(blackPlayer.playerName + " Score : " + blackPlayer.playerScore);
		sideDisplay.setLayout(new BoxLayout(sideDisplay, BoxLayout.PAGE_AXIS));
		sideDisplay.add(whiteLabel);
		sideDisplay.add(blackLabel);
		sideDisplay.add(undoButton);
		sideDisplay.add(forfeitButton);
		sideDisplay.add(restartButton);
		sideDisplay.add(whiteScore);
		sideDisplay.add(blackScore);
		return sideDisplay;
	}
	
	/**
	 * Helper method to setup the button listeners for Undo, Restart and Forfeit buttons.
	 */
	private void setupButtonListeners() {
		undoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				undoMove();
			}
		});
		restartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				restartGame();
			}
		});
		forfeitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				forfeitGame();
			}
		});
	}

	/**
	 * Main method to control and update results of Mouse Actions. 
	 * It implements a Mouse Adapter to analyze moves made on the chess board.
	 * Mouse Pressed : 
	 * - Calculates Origin x and y of the move
	 * - Get reference to moving piece.
	 * Mouse Released : 
	 * - Calculate Destination x and y of the move
	 * - Check for valid gameTurn and valid move of the moving piece.
	 * - Make the move and add it to the move commandStack
	 * - Change the gameTurn and update it visually on the sideDisplay.
	 * - Check King in check or Checkmate using helper method.
	 * - If illegal move display error message.
	 */
	public void mouseActions(){
		gamePanel.addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent me){
				int xOrigin = me.getX();
				int yOrigin = me.getY();
				xOrigin = xOrigin/squareSize;
				yOrigin = yOrigin/squareSize;
				yOrigin = 7 - yOrigin;
				movingPiece = gameBoard.squaresList[xOrigin][yOrigin].occupyingPiece;
			}
			
			public void mouseReleased(MouseEvent me) {
			    int xDestination = me.getX();
				int yDestination = me.getY();
				xDestination = xDestination/squareSize;
				yDestination = yDestination/squareSize;
				yDestination = 7 - yDestination;
				if(movingPiece.color == gameTurn && movingPiece.canMove(xDestination, yDestination)){
					Piece enemyPiece = null;
					if(gameBoard.squaresList[xDestination][yDestination].isOccupied)
						enemyPiece = gameBoard.squaresList[xDestination][yDestination].occupyingPiece;
					MoveCommand newCommand = new MoveCommand(movingPiece, enemyPiece, xDestination, yDestination);
					commandStack.add(newCommand);
					newCommand.execute();
					if(movingPiece.color.equals(chessGame.Board.Color.white)){
						gameTurn = gameTurn.opposite();
						blackLabel.setForeground(Color.BLUE);
						whiteLabel.setForeground(Color.BLACK);
						checkKingStatus(gameBoard.blackKingTracker);
					 }
					 else{
						 gameTurn = gameTurn.opposite();
						 whiteLabel.setForeground(Color.BLUE);
						 blackLabel.setForeground(Color.BLACK);
						 checkKingStatus(gameBoard.whiteKingTracker);
					 }
					 
				}
				else{
					messageBox("This is an Illegal Move!", "Illegal move message");
				}	
			}
		});
	}

	/**
	 * Helper method to check if the passed king is in check.
	 * It calls the respective check and checkmate helper methods in our library 
	 * and displays appropriate messages and updates the score.
	 * @param kingToCheck
	 */
	protected void checkKingStatus(King kingToCheck) {
		Player currentPlayer;
		Player otherPlayer;
		if(kingToCheck.color == chessGame.Board.Color.white){
			currentPlayer = whitePlayer;
			otherPlayer = blackPlayer;
		}
		else{
			currentPlayer = blackPlayer;
			otherPlayer = whitePlayer;
		}
		if(kingToCheck.isKingInCheck(kingToCheck)) {
			if(kingToCheck.isKingCheckmate(kingToCheck)) {
				messageBox(currentPlayer.playerName + " ,Your King is in Checkmate\nYou Lost\nPlease Click Restart to Play again", "GAME OVER!!");
				gameOver = true;
				otherPlayer.playerScore++;
				whiteScore.setText(whitePlayer.playerName + " Score : " + whitePlayer.playerScore);
				blackScore.setText(blackPlayer.playerName + " Score : " + blackPlayer.playerScore);
				return;
			}
			messageBox(currentPlayer.playerName + " ,Your King is in Check", "King in Check!!");
		}
	}
	
	/**
	 * Undo button calls this method. This pops the last command off the command stack 
	 * and executes it's reverse using the undo helper method in our MoveCommand class.
	 * It also switches the game turn accordingly.
	 */
	private void undoMove(){
		if(!commandStack.isEmpty()){
			MoveCommand move = commandStack.pop();
			move.undo();
			if(gameTurn == chessGame.Board.Color.white){
				blackLabel.setForeground(Color.BLUE);
				whiteLabel.setForeground(Color.BLACK);
			}
			else{
				whiteLabel.setForeground(Color.BLUE);
			 	blackLabel.setForeground(Color.BLACK);
			}
			gameTurn = gameTurn.opposite();
		}
	}
	
	/**
	 * Restart button calls this method which checks if your opponent wants to restart as well 
	 * and if so it starts a new game.
	 */
	private void restartGame(){
		String player;
		if(gameTurn.equals(chessGame.Board.Color.white))
			player = blackPlayer.playerName;
		else
			player = whitePlayer.playerName;
		int response = JOptionPane.showConfirmDialog(null, player + " , would you like to restart?", "Restart", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			gameOver = true;
			window.setVisible(false);
			startNewGame();
		}
	}
	
	/**
	 * Forfeit button calls this method to forfeit the game.
	 * Asks the forfeiting player if they are sure. If yes updates the score of their opponent. 
	 */
	private void forfeitGame() {
		Player currentPlayer;
		Player otherPlayer;
		if(gameTurn == chessGame.Board.Color.white){
			currentPlayer = whitePlayer;
			otherPlayer = blackPlayer;
		}
		else{
			currentPlayer = blackPlayer;
			otherPlayer = whitePlayer;
		}
		int response = JOptionPane.showConfirmDialog(null, currentPlayer.playerName + " , Are you sure you want to forfeit", "Forfeit", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			gameOver = true;
			otherPlayer.playerScore++;
			whiteScore.setText(whitePlayer.playerName + " Score : " + whitePlayer.playerScore);
			blackScore.setText(blackPlayer.playerName + " Score : " + blackPlayer.playerScore);
			messageBox(currentPlayer.playerName + " ,You Lost\nPlease Click Restart to Play again", "GAME OVER!!");
		}
	}

	/**
	 * Main method to get the game players and start a new game.
	 * @param args
	 */
	public static void main(String args[]){
		getGamePlayers();
		startNewGame();
	}

	/**
	 * Helper method to start off a new game.
	 * Called when the players want to restart or when new players join in initially.
	 */
	private static void startNewGame() {
		Game newGame = new Game();
		newGame.gameInit(getGameType());
		newGame.setupDisplay();
		newGame.gameStart();
		newGame.mouseActions();
		
	}

	/**
	 * Helper method to display a warning message of illegal moves, check and checkmate!
	 * @param message
	 * @param title
	 */
	public static void messageBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
	
}
