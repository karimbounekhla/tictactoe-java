
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerPlayer implements Constants {
    private char mark;
    private BufferedReader playerIn;
    private PrintWriter playerOut;
    private Socket aSocket;
    private Board board;

    public ServerPlayer(char mark, Socket socket) {
        this.mark = mark;
        this.aSocket = socket;
        try {
            playerIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            playerOut = new PrintWriter(aSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to check if a move (row and col combination) is valid, and if so - add the mark to the board
     * @param row row
     * @param col col
     * @return true if move valid, false if notne
     */
    protected boolean isMoveValid(int row, int col) {
        if ((row >= 0 && row < 3) && (col >= 0 && col < 3) && this.board.getMark(row, col) == SPACE_CHAR) {
            // If move valid, add mark to board
            board.addMark(row, col, mark);
            return true;
        }
        return false;
    }

    /**
     * Check if the game is a tie
     * @return true if tie, false if not tie
     */
    public boolean gameIsTie() {
        return board.isFull();
    }

    /**
     * Helper method to check if a Player has won, and display the winner.
     * @return true if a Player has won, false is not
     */
    protected boolean isWinner() {
        if (board.xWins()) {
            // Display winner based on whether current Player is X or O mark
            if (this.mark == LETTER_X) {
                return true;
            } else {
                return false;
            }
        }

        if (board.oWins()) {
            if (this.mark == LETTER_O) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void setTheBoard(Board theBoard) {
        this.board = theBoard;
    }

    public BufferedReader getPlayerIn() {
        return playerIn;
    }

    public PrintWriter getPlayerOut() {
        return playerOut;
    }
}
