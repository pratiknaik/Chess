package chessGame;

/**
 * Subclass of a board. Standard version of a chess Board. Has methods for creating a standard
 * chess board and populating it with regular chess pieces.
 * Can be used to create a standard game of chess.
 * @author Pratik Naik
 */
public class StandardBoard extends Board {
	
	/**
	 * Trackers for the white and black kings for check, checkmate and game ending conditions.
	 */
	public King whiteKingTracker;
	public King blackKingTracker;
	
	/**
	 * Method to initialize the chess board.
	 * @param xSquares
	 * @param ySquares
	 */
	public StandardBoard(int xSquares, int ySquares) {

		this.numXSquares = xSquares;
		this.numYSquares = ySquares;
		this.totalSquares = this.numXSquares * this.numYSquares;
		this.squaresList = new Square[this.numXSquares][this.numYSquares];
		populateBoardWithSquares();
		this.whiteKingTracker = null;
		this.blackKingTracker = null;
	}

	/**
	 * Method to populate our board with Squares.
	 * General pattern of white and black squares on the board.
	 */
	public void populateBoardWithSquares() {
		for (int i = 0; i < this.numXSquares; i++) {
			for (int j = 0; j < this.numYSquares; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0)
						squaresList[i][j] = new Square(false, Color.black);
					else
						squaresList[i][j] = new Square(false, Color.white);
				} 
				else {
					if (j % 2 == 0)
						squaresList[i][j] = new Square(false, Color.white);
					else
						squaresList[i][j] = new Square(false, Color.black);
				}
			}
		}
	}
	
	/**
	 * Method to populate our chess board with standard pieces.
	 */
	public void populateBoardWithPieces(boolean special) {
		if(special)
			setupSpecialPieces();
		else{
			setupKnights();
			setupBishops();
		}
		setupPawns();
		setupRooks();
		setupQueens();
		setupKings();
	}
	
	/**
	 * Method to setup Archbishop and Chancellor as special pieces in special game.
	 */
	public void setupSpecialPieces() {
		Archbishop whiteArchbishopOne = new Archbishop(2, 0, Color.white, this);
		Archbishop whiteArchbishopTwo = new Archbishop(5, 0, Color.white, this);
		Archbishop blackArchbishopOne = new Archbishop(2, 7, Color.black, this);
		Archbishop blackArchbishopTwo = new Archbishop(5, 7, Color.black, this);
		this.squaresList[2][0].isOccupied = true;
		this.squaresList[5][0].isOccupied = true;
		this.squaresList[2][0].occupyingPiece = whiteArchbishopOne;
		this.squaresList[5][0].occupyingPiece = whiteArchbishopTwo;
		this.squaresList[2][7].isOccupied = true;
		this.squaresList[5][7].isOccupied = true;
		this.squaresList[2][7].occupyingPiece = blackArchbishopOne;
		this.squaresList[5][7].occupyingPiece = blackArchbishopTwo;
		
		Chancellor whiteKnightOne = new Chancellor(1, 0, Color.white, this);
		Chancellor whiteKnightTwo = new Chancellor(6, 0, Color.white, this);
		Chancellor blackKnightOne = new Chancellor(1, 7, Color.black, this);
		Chancellor blackKnightTwo = new Chancellor(6, 7, Color.black, this);
		this.squaresList[1][0].isOccupied = true;
		this.squaresList[6][0].isOccupied = true;
		this.squaresList[1][0].occupyingPiece = whiteKnightOne;
		this.squaresList[6][0].occupyingPiece = whiteKnightTwo;
		this.squaresList[1][7].isOccupied = true;
		this.squaresList[6][7].isOccupied = true;
		this.squaresList[1][7].occupyingPiece = blackKnightOne;
		this.squaresList[6][7].occupyingPiece = blackKnightTwo;
		
	}

	/**
	 * Setup 8 black and 8 white pawns in their initial positions.
	 */
	public void setupPawns(){
		for(int i = 0; i < 8; i++){
			Pawn newWhitePawn = new Pawn(i, 1, Color.white, this);
			Pawn newBlackPawn = new Pawn(i, 6, Color.black, this);
			this.squaresList[i][1].isOccupied = true;
			this.squaresList[i][6].isOccupied = true;
			this.squaresList[i][1].occupyingPiece = newWhitePawn;
			this.squaresList[i][6].occupyingPiece = newBlackPawn;
			
		}
	}
	
	/**
	 * Setup 2 black rooks and 2 white rooks in their initial positions.
	 */
	public void setupRooks(){
		Rook whiteRookOne = new Rook(0, 0, Color.white, this);
		Rook whiteRookTwo = new Rook(7, 0, Color.white, this);
		Rook blackRookOne = new Rook(0, 7, Color.black, this);
		Rook blackRookTwo = new Rook(7, 7, Color.black, this);
		this.squaresList[0][0].isOccupied = true;
		this.squaresList[7][0].isOccupied = true;
		this.squaresList[0][0].occupyingPiece = whiteRookOne;
		this.squaresList[7][0].occupyingPiece = whiteRookTwo;
		this.squaresList[0][7].isOccupied = true;
		this.squaresList[7][7].isOccupied = true;
		this.squaresList[0][7].occupyingPiece = blackRookOne;
		this.squaresList[7][7].occupyingPiece = blackRookTwo;
		
	}
	
	/**
	 * Setup 2 black Bishops and 2 white Bishops in their initial positions.
	 */
	public void setupBishops(){
		Bishop whiteBishopOne = new Bishop(2, 0, Color.white, this);
		Bishop whiteBishopTwo = new Bishop(5, 0, Color.white, this);
		Bishop blackBishopOne = new Bishop(2, 7, Color.black, this);
		Bishop blackBishopTwo = new Bishop(5, 7, Color.black, this);
		this.squaresList[2][0].isOccupied = true;
		this.squaresList[5][0].isOccupied = true;
		this.squaresList[2][0].occupyingPiece = whiteBishopOne;
		this.squaresList[5][0].occupyingPiece = whiteBishopTwo;
		this.squaresList[2][7].isOccupied = true;
		this.squaresList[5][7].isOccupied = true;
		this.squaresList[2][7].occupyingPiece = blackBishopOne;
		this.squaresList[5][7].occupyingPiece = blackBishopTwo;
	}
	
	/**
	 * Setup 2 black Knights and 2 white Knights in their initial positions.
	 */
	public void setupKnights(){
		Knight whiteKnightOne = new Knight(1, 0, Color.white, this);
		Knight whiteKnightTwo = new Knight(6, 0, Color.white, this);
		Knight blackKnightOne = new Knight(1, 7, Color.black, this);
		Knight blackKnightTwo = new Knight(6, 7, Color.black, this);
		this.squaresList[1][0].isOccupied = true;
		this.squaresList[6][0].isOccupied = true;
		this.squaresList[1][0].occupyingPiece = whiteKnightOne;
		this.squaresList[6][0].occupyingPiece = whiteKnightTwo;
		this.squaresList[1][7].isOccupied = true;
		this.squaresList[6][7].isOccupied = true;
		this.squaresList[1][7].occupyingPiece = blackKnightOne;
		this.squaresList[6][7].occupyingPiece = blackKnightTwo;
	}
	
	/**
	 * Setup 2 queens white and black in their initial positions.
	 */	
	public void setupQueens(){
		Queen whiteQueen = new Queen(3, 0, Color.white, this);
		Queen blackQueen = new Queen(3, 7, Color.black, this);
		this.squaresList[3][0].isOccupied = true;
		this.squaresList[3][7].isOccupied = true;
		this.squaresList[3][0].occupyingPiece = whiteQueen;
		this.squaresList[3][7].occupyingPiece = blackQueen;
	}
	
	/**
	 * Setup 2 queens white and black in their initial positions.
	 */
	public void setupKings(){
		King whiteKing = new King(4, 0, Color.white, this);
		King blackKing = new King(4, 7, Color.black, this);
		this.squaresList[4][0].isOccupied = true;
		this.squaresList[4][7].isOccupied = true;
		this.squaresList[4][0].occupyingPiece = whiteKing;
		this.squaresList[4][7].occupyingPiece = blackKing;
		whiteKingTracker = whiteKing;
		blackKingTracker = blackKing;
	}
	
	/**
	 * Helper method to check if locations passed in are mapped on our generated board.
	 * @see chessGame.Board#inBoardBounds(int, int)
	 * @param newX
	 * @param newY
	 * @return boolean true if move is in board bounds
	 */
	public boolean inBoardBounds(int newX, int newY){
		if(newX < numXSquares && newY < numYSquares && newX > -1 && newY > -1){
			return true;
		}
		else
			return false;
	}

}
