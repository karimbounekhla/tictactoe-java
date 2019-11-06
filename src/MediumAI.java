
/**
 * Child class of Easy AI Player, this is an AI that actively tries to block opponent from winning.
 */
public class MediumAI extends EasyAI {
    /**
     * This method initializes a player with a Name and a mark (X or O)
     *
     * @param name name of the player
     * @param mark X or O
     */
    public MediumAI(String name, char mark, GameView grid, RandomGenerator rg) {
        super(name, mark, grid, rg);
    }

    /**
     * Checks all possible options if there is a way to block opponent from winning. Otherwise, calls parent
     * method which chooses a random spot
     */
    @Override
    protected void makeMove() {
        // For each row and col, check if current player can block a opponent win by playing that row/col combination.
        // If it blocks a win, play the move
        boolean blocked = false;
        outer: for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                if (testForBlocking(i, j) && isMoveValid(i, j)) {
                    blocked = true;
                    break outer;
                }
        }

        // If no blocking candidates found, call parent method to play random move
        if (blocked == true) {
            AIpassTurn();
        } else {
            super.makeMove();
        }
    }

    /**
     * Method that checks if a given move (row and col) will block the opponent from winning on their next move
     * @param row row
     * @param col col
     * @return true if move would block a win, false if not
     */
    protected boolean testForBlocking(int row, int col) {
        // Get copy of the board in its current state and add opponent mark to the row/col
        Board testBoard = getBoard().copyBoard();
        testBoard.addMark(row, col, this.getOpponent().getMark());

        // Check if opponent will win if that mark is played by them in the next move
        if (testBoard.checkWinner(this.getOpponent().getMark()) == 1) {
            return true;
        } else {
            return false;
        }
    }


}
