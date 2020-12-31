package exercise5k;

/**
 * This class represents a Player of a Tic Tac Toe game. An object of this class contains a name, mark (X or O) and reference to
 * the Board in which the game is played and a opponent Player. This class provides instance methods to play the game by making
 * moves on the board until a player has won or the game is tied.
 * 
 * This class implements the Constants interface for game variables (X, O, space)
 * @author karimbounekhla
 */
abstract class Player implements Constants {
	protected String name;
	protected Board board;
	protected Player opponent;
	protected Referee ref;
	protected char mark;
	protected GameView gridView;
	
	/**
	 * This method initializes a player with a Name and a mark (X or O)
	 * @param name name of the player
	 * @param mark X or O
	 */
	public Player(String name, char mark, GameView grid) {
		setName(name);
		setMark(mark);
		gridView = grid;
	}
	
	/**
	 * This method is used to run the game by continuously asking each player for a move until there is a winner
	 * or the game is tied. 
	 */


	public boolean gameIsDone() {
		if (checkWinner()) {
			return true;
		} else if (getBoard().isFull()) {
			gridView.gameOver("Game Over - Tie!");
			return true;
		} else {
			return false;
		}
	}

	public void setRef(Referee ref) {
		this.ref = ref;
	}

	/**
	 * This abstract method is implemented by the subclasses and determines how a move is made
	 */
	protected abstract void makeMove();
	
	/**
	 * Sets the Opponent of the current Player object
	 * @param opponent Player object representing the opponent
	 */
	protected void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
	/**
	 * Gets the opponent of the current Player object
	 * @return Player object representing the opponent
	 */
	public Player getOpponent() {
		return opponent;
	}
	
	/**
	 * Sets the board that the Player object will play in
	 * @param theBoard Board object
	 */
	protected void setBoard(Board theBoard) {
		this.board = theBoard;
	}

	/**
	 * Gets board.
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the name of the player
	 * @return name of the Player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the Player
	 * @param name name of the Player
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the mark (X or O) of the Player
	 * @return mark of the player (X or O)
	 */
	public char getMark() {
		return mark;
	}
	
	/**
	 * Sets the mark of the player to either X or O
	 * @param mark mark of the player
	 */
	public void setMark(char mark) {
		this.mark = mark;
	}
	
	/**
	 * Helper method to check if a move (row and col combination) is valid, and if so - add the mark to the board
	 * @param row row
	 * @param col col
	 * @return true if move valid, false if notne
	 */
	protected boolean isMoveValid(int row, int col) {
		if ((row >= 0 && row < 3) && (col >= 0 && col < 3) && this.board.getMark(row, col) == SPACE_CHAR) {
			// If move valid, add mark to board
			board.addMark(row, col, mark);
			gridView.addMarkToGrid(row, col, mark);
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method to check if a Player has won, and display the winner.
	 * @return true if a Player has won, false is not
	 */
	protected boolean checkWinner() {
		if (board.xWins()) {
			// Display winner based on whether current Player is X or O mark
			if (this.mark == LETTER_X) {
				gridView.gameOver(this.name + " is the winner!");
			} else {
				gridView.gameOver(opponent.getName() + " is the winner!");
			}
			return true;
			} 
		
		if (board.oWins()) {
			if (this.mark == LETTER_O) {
				gridView.gameOver(this.name + " is the winner!");
			} else {
				gridView.gameOver(opponent.getName() + " is the winner!");
			}
			return true;
		}
		
		return false;
	}

}
