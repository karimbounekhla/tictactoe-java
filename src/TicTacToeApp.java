
/**
 * Tic Tac Toe Main Application View - Used to instantiate MVC objects and run the program.
 */
public class TicTacToeApp {
    private String serverName;
    private int portNum;

    public TicTacToeApp(String serverName, int portNum) {
        this.serverName = serverName;
        this.portNum = portNum;
    }

    /**
     * Method to initiate frames, controller and start the game.
     */
    public void playGame() {
        GameView gv = new GameView();
        StartView sv = new StartView();
        GameClient gc = new GameClient(gv, sv, serverName, portNum);
        gc.startGame();
    }

    /**
     * Instantiate MVC objects, linking Model and View via the Controller.
     * @param args unused
     */
    public static void main(String[] args) {
        // AWS
//        String ip = "18.221.109.44";
//        TicTacToeApp tttApp = new TicTacToeApp(ip, 8475);

        // Local
        TicTacToeApp tttApp = new TicTacToeApp("localhost", 8475);

        tttApp.playGame();
    }
}
