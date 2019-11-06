
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the Controller for the Tic Tac Toe game application. This class is used to
 * set up and control the flow of the game through instances of Model classes (Board, Referee) and
 * View classes (Game and Start).
 */
public class GameController {
    private Board theBoard;
    private Referee theRef;
    private GameView theGameView;
    private StartView theStartView;

    /**
     * Instantiates a new Game controller.
     *
     * @param gv the Game View
     * @param sv the Start View
     */
    public GameController(GameView gv, StartView sv) {
        theBoard = new Board();
        theRef = new Referee();
        theGameView = gv;
        theStartView = sv;
        addListeners();
    }

    /**
     * Start game.
     */
    public void startGame() {
        theGameView.run();
    }

    /**
     * Helper method to add Listeners to the View classes.
     */
    private void addListeners() {
        // Add listeners to each button of the 3x3 grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                theGameView.addGridListener(i, j, new gridListener());
            }
        }
        theGameView.addStartGameListener(new startListener());
        theStartView.addStartGameListener(new startGameListener());
    }

    ////////////////////////////////////////////////////
    ///////////// Listeners for Main Frame /////////////
    ////////////////////////////////////////////////////

    /**
     * This listener class runs the 'Start Game' View.
     */
    private class startListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theStartView.run();
        }
    }

    /**
     * This listener class is used by the grid to register which move is played.
     */
    private class gridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // This takes the ActionCommand that was assigned to each button to determine which
            // grid has been clicked. The format of the ActionCommand is 'ij', where 'i' represents
            // the row and 'j' represents the column
            int location = Integer.parseInt(e.getActionCommand());
            makeMove(location);
        }

        /**
         * Helper method to make a move based on grid button that was clicked
         * @param location 'ij' representing row i and column j of button that was clicked
         */
        private void makeMove(int location) {
            // Get individual digits representing row and col
            int row = location/10;
            int col = location%10;
            // Make move if valid, else do nothing
            if (theRef.getPlayerTurn().isMoveValid(row, col)) {
                // Call next turn
                theRef.nextTurn();
            }
        }
    }

    //////////////////////////////////////////////////////////
    ///////////// Listeners for Start Game Frame /////////////
    //////////////////////////////////////////////////////////

    /**
     * Listener of the 'Start Game' button on the Start View.
     * This listener sets up the game by creating players and assigning roles and objects.
     */
    private class startGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset the grid, assign roles, run game and then close the window to begin game
            theGameView.resetGrid();
            assignRoles();
            theRef.runTheGame();
            theStartView.dispose();
        }

        /**
         * Helper method that creates Player objects based on type selected.
         * By default, player 1 is X and player 2 is O
         */
        private void assignRoles() {
            // Player 1 is Human by default.
            Player playerOne = createPlayer(theStartView.getPlayerOneName(), Constants.LETTER_X,
                    "Human");
            Player playerTwo = createPlayer(theStartView.getPlayerTwoName(), Constants.LETTER_O,
                    theStartView.getPlayerTwoType());

            // Set Referee object to Players
            playerOne.setRef(theRef);
            playerTwo.setRef(theRef);

            // Setup Referee object with the Players and Board object
            theRef.setBoard(theBoard);
            theRef.setxPlayer(playerOne);
            theRef.setoPlayer(playerTwo);
        }

        /**
         * Helper method that creates a Player object with a name, mark and type
         * @param name player name
         * @param mark 'X' or 'O'
         * @param type type of the player (Human, AI Easy - Hard)
         * @return Player object
         */
        private Player createPlayer(String name, char mark, String type) {
            Player newPlayer = null;
            switch(type) {
                case "Human":
                    newPlayer = new HumanPlayer(name, mark, theGameView);
                    break;
                case "AI Easy":
                    newPlayer = new EasyAI(name, mark, theGameView, new RandomGenerator());
                    break;
                case "AI Medium":
                    newPlayer = new MediumAI(name, mark, theGameView, new RandomGenerator());
                    break;
                case "AI Hard":
                    newPlayer = new HardAI(name, mark, theGameView, new RandomGenerator());
                    break;
            }
            return newPlayer;
        }
    }
}
