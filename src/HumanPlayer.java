
/**
 * Child class of Player, representing a Human Player.
 */
public class HumanPlayer extends Player {

    /**
     * This method initializes a human player with a Name and a mark (X or O)
     *
     * @param name name of the player
     * @param mark X or O
     */
    public HumanPlayer(String name, char mark, GameView grid) {
        super(name, mark, grid);
    }

    /**
     * This method displays a message on the grid indicating the player name and mark
     */
    protected void makeMove() {
        gridView.setMessage("\n" + getName() +", your turn to place a " + getMark());
    }
}
