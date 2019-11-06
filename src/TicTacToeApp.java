
/**
 * Tic Tac Toe Main Application View - Used to instantiate MVC objects and run the program.
 */
public class TicTacToeApp {

    /**
     * Instantiate MVC objects, linking Model and View via the Controller.
     * @param args unused
     */
    public static void main(String[] args) {
        // Instantiate Model, View and Controller
        GameView gv = new GameView();
        StartView sv = new StartView();
        GameController gc = new GameController(gv, sv);

        // Start Game
        gc.startGame();
    }
}
