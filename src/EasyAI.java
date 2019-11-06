
/**
 * Child class of Player. This Easy AI makes random moves.
 */
public class EasyAI extends Player {
    protected RandomGenerator rg;

    /**
     * This method initializes a player with a Name and a mark (X or O)
     *
     * @param name name of the player
     * @param mark X or O
     */
    public EasyAI(String name, char mark, GameView grid, RandomGenerator rg) {
        super(name, mark, grid);
        this.rg = rg;
    }

    /**
     * Picks a random row and column and repeatedly checks if it is a valid move. If it's valid, then make the move
     */
    @Override
    protected void makeMove() {
        // Generate random row and col (0-2) and loops until a valid move is found
        while (true) {
            int row = rg.discrete(0,2);
            int col = rg.discrete(0, 2);

            if (isMoveValid(row, col)) {
                break;
            }
        }
        AIpassTurn();
    }

    /**
     * This helper class is used by the AI's to pass to the next turn once they've played.
     */
    protected void AIpassTurn() {
        ref.nextTurn();
    }

}
