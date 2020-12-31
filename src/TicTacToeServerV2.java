import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server running a Thread Pool, allowing 2 clients to connect and face each other in a game of Tic Tac Toe
 */
public class TicTacToeServerV2 implements Constants {
    private ServerSocket serverSocket;
    private ExecutorService pool;

    /**
     * Constructor that initiates the server and thread pool
     */
    public TicTacToeServerV2() {
        try {
            serverSocket = new ServerSocket(8475);
            pool = Executors.newFixedThreadPool(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait for 2 opponents to connect, then start a new game thread
     */
    public void runServer() {
        System.out.println("Server running.. waiting for 2 players");
        try {
            while (true) {
                GameServer theGameServer = new GameServer();
                Socket playerOne = serverSocket.accept();
                ServerPlayer xPlayer = new ServerPlayer(LETTER_X, playerOne);
                xPlayer.getPlayerOut().println(LETTER_X);
                theGameServer.setxPlayer(xPlayer);
                System.out.println("Player 1 Connected");

                xPlayer.getPlayerOut().println("Waiting for Ppponent");

                Socket playerTwo = serverSocket.accept();
                ServerPlayer oPlayer = new ServerPlayer(LETTER_O, playerTwo);
                oPlayer.getPlayerOut().println(LETTER_O);
                theGameServer.setoPlayer(oPlayer);
                System.out.println("Player 2 Connected");

                Board newBoard = new Board();
                theGameServer.setTheBoard(newBoard);
                xPlayer.setTheBoard(newBoard);
                oPlayer.setTheBoard(newBoard);
                System.out.println("Board Set.");

                pool.execute(theGameServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to run the server
     * @param args
     */
    public static void main(String[] args) {
        TicTacToeServerV2 server = new TicTacToeServerV2();
        server.runServer();
    }
}
