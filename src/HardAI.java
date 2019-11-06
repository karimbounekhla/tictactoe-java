
/**
 * Child class of Medium AI Player. This is an AI that actively tries to win the game.
 */
public class HardAI extends MediumAI {

    /**
     * This method initializes a player with a Name and a mark (X or O)
     *
     * @param name name of the player
     * @param mark X or O
     */
    public HardAI(String name, char mark, GameView grid, RandomGenerator rg) {
        super(name, mark, grid, rg);
    }

    /**
     * Checks all possible options if there is a way to Win the game outright. Otherwise, calls parent
     * method which checks whether there is a way to block the opponent or at last resort, chooses a random spot
     */
    @Override
    protected void makeMove() {
        // For each row and col, check if current player can win by playing that row/col combination.
        // If it wins, play the move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                if (testForWinning(i, j) && isMoveValid(i, j)) {
                    gameIsDone();
                    return;
                }
        }
        // If not found any way to win, call parent method to test for blocking
        super.makeMove();
    }

    /**
     * Method that checks if a given move (row and col) would win the game for the current Player.
     * @param row row
     * @param col col
     * @return true if winning move, false if not
     */
    protected boolean testForWinning(int row, int col) {
        // Get copy of the board in its current state and add mark to the row/col
        Board testBoard = getBoard().copyBoard();
        testBoard.addMark(row, col, this.getMark());

        // Check if there is victory after adding mark
        if (testBoard.checkWinner(this.getMark()) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
