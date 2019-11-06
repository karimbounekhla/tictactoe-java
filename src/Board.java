
/**
 * This class represents a 3 x 3 board for a Tic Tac Toe game. An object of this class contains a 2D
 * array containing the 'mark' (or a space if it is empty) for all locations on the board where players
 * can make moves. In addition, it contains a counter of how many moves were made, whereas 9 moves automatically
 * terminates the game as the board will be full. This class provides instance methods to display the board, make
 * a play and check whether a player has won or there is a tie.
 * 
 * This class implements the Constants interface for game variables (X, O, space)
 *
 */
public class Board implements Constants {
	private char theBoard[][];
	private int markCount;
	
	/**
	 * Initializes a Tic-tac-toe board of 3 x 3 character, and assigns an empty space to each row/col
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Returns a deep copy of the Board in its current state (with all 'X' and 'O's in place)
	 * @return copy of the board
	 */
	public Board copyBoard() {
		Board temp = new Board();
		for (int i = 0; i < 3; i++) {
			temp.theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				temp.theBoard[i][j] = this.theBoard[i][j];
		}
		return temp;
	}
	
	/**
	 * Gives the content (space, 'X' or 'O' of a box on the board
	 * @param row row
	 * @param col col
	 * @return content of the box
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}
	
	/**
	 * Checks if the board is full (i.e. all 9 boxes have been filled with either a 'X' or a 'O')
	 * @return true if board full, false if not
	 */
	public boolean isFull() {
		return markCount == 9;
	}
	
	/**
	 * Check if the player using 'X' has won
	 * @return true if 'X' is winner, false if not
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Check if the player using 'O' has won
	 * @return true if 'O' is winner, false if not
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * Draws the current state of the Board in the terminal.
	 * This is used as debug tool to determine if the GUI grid matches the back-end game board.
	 */
	public void display() {
		// Use helper functions to draw headers and hyphens
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}
	
	/**
	 * Add a mark to the board at a specified row and column
	 * @param row row
	 * @param col column
	 * @param mark mark, which is either 'X' or 'O'
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}
	
	/**
	 * Clears the board of all marks
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}
	
	/**
	 * Checks the board whether a mark ('X' or 'O') has won the game. A mark has won
	 * the game when it is made up of 3 consecutive marks on a line (straight or diagonal)
	 * @param mark mark to check victory status of
	 * @return '1' if the mark has won, 0 if not
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;
		
		// Check each row for horizontal victory
		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		// Check each column for vertical victory
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}
		
		// Check diagonal victory
		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		
		// Check other diagonal victory
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}
	
	/**
	 * Helper function to display column header
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}
	
	/**
	 * Helper function to add hyphens and help visualize the board
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}
	
	/**
	 * Helper function to add space in between board boxes
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
}
