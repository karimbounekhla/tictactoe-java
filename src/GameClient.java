import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class represents the Controller for the Tic Tac Toe game application. This class is used to
 * set up and control the flow of the game through instances of Model classes (Board, Referee) and
 * View classes (Game and Start).
 */
public class GameClient implements Constants {
    private Board theBoard;
    private Referee theRef;
    private GameView theGameView;
    private StartView theStartView;
    private char playerMark;
    private boolean myTurn;

    private PrintWriter socketOut;
    private Socket ticTacToeSocket;
    private BufferedReader stdIn;
    private BufferedReader socketIn;
    private String serverName;
    private int portNumber;

    /**
     * Instantiates a new Game controller.
     *
     * @param gv the Game View
     * @param sv the Start View
     */
    public GameClient(GameView gv, StartView sv, String serverName, int portNumber) {
        theBoard = new Board();
        theRef = new Referee();
        theGameView = gv;
        theStartView = sv;
        this.serverName = serverName;
        this.portNumber = portNumber;
        addListeners();
    }

    public void connectToGame() {
        try {
            System.out.println("Connecting..");
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            ticTacToeSocket = new Socket(serverName, portNumber);

            socketIn = new BufferedReader(new InputStreamReader(
                    ticTacToeSocket.getInputStream()));

            socketOut = new PrintWriter((ticTacToeSocket.getOutputStream()), true);

            String thisPlayerMark = socketIn.readLine();
            playerMark = thisPlayerMark.charAt(0);
            myTurn = (playerMark == 'X') ? true : false;

            if (playerMark == 'O') {
                theGameView.setMessage("Waiting for opponent to move!");
            }
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * New thread which listens to server messages
     */
    private void waitForServer() {
        new Thread(() -> {
            try {
                while (true) {
                    String response = socketIn.readLine();
                    switchBoard(response);
                }
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        }).start();
    }

    /**
     * Takes in server messages and executes the appropriate action.
     * @param message message from the server
     */
    private void switchBoard(String message) {
        if (message.startsWith("valid")) {
            String[] move = message.split(",");
            theRef.getPlayerTurn().isMoveValid(Integer.parseInt(move[1]), Integer.parseInt(move[2]));
            theGameView.setMessage("Waiting for opponent to move!");
            myTurn = !myTurn;
        } else if (message.startsWith("opponentmove")) {
            String[] move = message.split(",");
            theRef.getPlayerTurn().getOpponent().isMoveValid(Integer.parseInt(move[1]), Integer.parseInt(move[2]));
            theGameView.setMessage("Your turn to play!");
            myTurn = !myTurn;
        } else if (message.equals("winner")) {
            theGameView.setMessage("Game Over - You win!");
            theGameView.gameOver("Congratulations, you win!");
        } else if (message.equals("loser")) {
            theGameView.setMessage("Game Over - You lose");
            theGameView.gameOver("Better luck next time!");
        } else if (message.equals("tie")) {
            theGameView.setMessage("Game Over - Tie");
            theGameView.gameOver("Game is a TIE!");
        } else if (message.equals("waitingforopponent")) {
            theGameView.setMessage("Connected! Waiting for opponent.");
        } else if (message.equals("startgame")) {
            theGameView.setMessage("Opponent Connected! Your turn.");
        } else if (message.equals("startgamewait")) {
            theGameView.setMessage("Connected! Your opponent's turn");
        }
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

            if (myTurn) {
                socketOut.println("move," + row + "," + col);
            } else {
                theGameView.setMessage("Not your turn yet!");
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
            connectToGame();
            assignRoles();
            theRef.runTheGame();
            theStartView.dispose();
            waitForServer();
        }

        /**
         * Helper method that creates Player objects based on type selected.
         * By default, player 1 is X and player 2 is O
         */
        private void assignRoles() {
            // Create Player 1 and 2
            // Player 2 simulates the opponent
            Player playerOne = new HumanPlayer(theStartView.getPlayerOneName(), Constants.LETTER_X, theGameView);
            Player playerTwo = new HumanPlayer("Opponent", Constants.LETTER_O, theGameView);

            // Set Referee object to Players
            playerOne.setRef(theRef);
            playerTwo.setRef(theRef);

            // Setup Referee object with the Players and Board object
            theRef.setBoard(theBoard);
            theRef.setxPlayer(playerOne);
            theRef.setoPlayer(playerTwo);
        }
    }
}