import java.io.IOException;

public class GameServer implements Constants, Runnable {
    ServerPlayer xPlayer;
    ServerPlayer oPlayer;
    Board theBoard;

    /**
     * Method to run the game, sending appropriate messages over socket to both players.
     */
    @Override
    public void run() {
        try {
            while (true) {
                // X player turn
                System.out.println("X Player making a move now...");
                while (true) {
                    String playerInput = xPlayer.getPlayerIn().readLine();
                    if (playerInput.startsWith("move")) {
                        String[] move = playerInput.split(",");
                        System.out.println(move[1] + " " + move[2]);
                        boolean moveValid = xPlayer.isMoveValid(Integer.parseInt(move[1]), Integer.parseInt(move[2]));
                        if (moveValid) {
                            System.out.println("X Player made valid move");
                            xPlayer.getPlayerOut().println("validmove," + move[1] + "," + move[2]);
                            oPlayer.getPlayerOut().println("opponentmove," + move[1] + "," + move[2]);
                            break;
                        } else {
                            xPlayer.getPlayerOut().println("invalid");
                        }
                    }
                }

                if (checkWinner() || checkTie()) break;

                // O Player turn
                System.out.println("O Player making a move now...");

                while (true) {
                    String playerInput = oPlayer.getPlayerIn().readLine();
                    if (playerInput.startsWith("move")) {
                        String[] move = playerInput.split(",");
                        System.out.println(move[1] + " " + move[2]);
                        boolean moveValid = oPlayer.isMoveValid(Integer.parseInt(move[1]), Integer.parseInt(move[2]));
                        if (moveValid) {
                            System.out.println("O Player made valid move");
                            oPlayer.getPlayerOut().println("validmove," + move[1] + "," + move[2]);
                            xPlayer.getPlayerOut().println("opponentmove," + move[1] + "," + move[2]);
                            break;
                        } else {
                            oPlayer.getPlayerOut().println("invalid");
                        }
                    }
                }

                if (checkWinner() || checkTie()) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the game is tied
     * @return true if tied, false if not
     */
    private boolean checkTie() {
        if (xPlayer.gameIsTie()) {
            xPlayer.getPlayerOut().println("tie");
            oPlayer.getPlayerOut().println("tie");
            return true;
        }
        return false;
    }

    /**
     * Check for winner (Player X or player O), and transmits message over socket
     * @return true if winner has been found
     */
    private boolean checkWinner() {
        if (xPlayer.isWinner()) {
            xPlayer.getPlayerOut().println("winner");
            oPlayer.getPlayerOut().println("loser");
            return true;
        } else if (oPlayer.isWinner()) {
            xPlayer.getPlayerOut().println("loser");
            oPlayer.getPlayerOut().println("winner");
            return true;
        }
        return false;
    }

    /**
     * Set X Player
     * @param xPlayer X Player
     */
    public void setxPlayer(ServerPlayer xPlayer) {
        this.xPlayer = xPlayer;
    }

    /**
     * Set O Player
     * @param oPlayer O Player
     */
    public void setoPlayer(ServerPlayer oPlayer) {
        this.oPlayer = oPlayer;
    }

    /**
     * Set the board object
     * @param theBoard Board object
     */
    public void setTheBoard(Board theBoard) {
        this.theBoard = theBoard;
    }
}
