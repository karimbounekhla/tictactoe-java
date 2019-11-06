
/**
 * This class represents the Referee of a Tic Tac Toe game and is used to track the Players and the Board. Objects of this class
 * contain 2 Players (one representing the mark 'X' and the other the 'O' mark) as well as the board in which the game will be 
 * played. In addition to getters and setters methods, the sole instance method is used to assign the opponents and start the game.
 * @author karimbounekhla
 *
 */
public class Referee {
	private Player xPlayer;
	private Player oPlayer;
	private Board board;
	private Player playerTurn;
	
	/**
	 * Assigns the opponent of each Player, display the board and start the game (X player starts)
	 */
	public void runTheGame() {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		xPlayer.setBoard(board);
		oPlayer.setBoard(board);
		board.clear();

		// Call play method of player X as he will start the game
		playerTurn = xPlayer;
		playerTurn.makeMove();
	}

	/**
	 * This method is called in between turns to assign the next turn to a player after their opponent has played.
	 */
	public void nextTurn() {
		// Check if game isn't done yet
		if (!getPlayerTurn().gameIsDone()) {
			// Set player turn to the opponent, and make a move
			setPlayerTurn(getPlayerTurn().getOpponent());
			getPlayerTurn().makeMove();
		}
	}
	
	/**
	 * Sets the Board for the game
	 * @param board Board object
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Assign reference to player with the 'O' mark
	 * @param oPlayer Player object with the 'O' mark
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	
	/**
	 * Assign reference to player with the 'X' mark
	 * @param xPlayer Player object with the 'X' mark
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}

	/**
	 * Set the current player turn
	 * @param player Player object
	 */
	public void setPlayerTurn(Player player) {
		playerTurn = player;
	}

	/**
	 * Get the player whose turn is it to play
	 * @return Player object
	 */
	public Player getPlayerTurn() {
		return playerTurn;
	}

}
