
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class represents the Main Game GUI view for the Tic Tac Toe game.
 */
public class GameView extends JFrame {
    // Using an array of buttons for the tic tac toe grid
    private JButton[][] grid;
    private JLabel title, messageLabel;
    private JTextField messages;
    private JButton startGame;


    /**
     * Instantiates a new Game view.
     */
    public GameView() {
        super("Tic Tac Toe");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        addElements();
    }

    /**
     * Run the game.
     */
    public void run() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Add listener to the 'Start' button.
     *
     * @param e Action Listener
     */
    public void addStartGameListener(ActionListener e) {
        startGame.addActionListener(e);
    }

    /**
     * Add listener to a game button on the grid.
     *
     * @param row the row (0-2)
     * @param col the col (0-2)
     * @param e   Action Listener
     */
    public void addGridListener(int row, int col, ActionListener e) {
        grid[row][col].addActionListener(e);
    }

    /**
     * Sets message on the window.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        messages.setText(message);
    }

    /**
     * Add mark to grid.
     *
     * @param row  the row (0-2)
     * @param col  the col (0-2)
     * @param mark the mark
     */
    public void addMarkToGrid(int row, int col, char mark) {
        grid[row][col].setText(String.valueOf(mark));
    }

    /**
     * Resets the grid - when a new game is started.
     */
    public void resetGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setEnabled(true);
                grid[i][j].setText(String.valueOf(Constants.SPACE_CHAR));
            }
        }
    }

    /**
     * Game over message when the game has ended. This also freezes the grid using a helper method.
     *
     * @param message the message
     */
    public void gameOver(String message) {
        setMessage(message + " Play Again.");
        JOptionPane.showMessageDialog(this, message, "Game Over!",
                JOptionPane.INFORMATION_MESSAGE);
        freezeGrid();
    }

    /**
     * Freezes the grid to display the result but prevent further inputs when the game is done.
     */
    private void freezeGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setEnabled(false);
            }
        }
    }

    /**
     * Helper method to add container elements to the Border Layout
     */
    private void addElements() {
        title = new JLabel("Welcome to Tic Tac Toe.");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        add("North", title);
        add("Center", createGrid());
        add("South", controlPanel());
    }

    /**
     * Helper method that generates a 3x3 grid of buttons
     * @return JPanel object
     */
    private JPanel createGrid() {
        JPanel gameGrid = new JPanel(new GridLayout(3,3));
        grid = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                setGrid(gameGrid,i, j);
            }
        }
        return gameGrid;
    }

    /**
     * Helper method that sets each button of the grid (empty buttons at first)
     * @param gameGrid JPanel object contained the grid
     * @param i row (0-2)
     * @param j col (0-2)
     */
    private void setGrid(JPanel gameGrid, int i, int j) {
        grid[i][j] = new JButton(" ");
        // This sets the Action Command for that button to be the row and column - this is used in the
        // listener to know which button is pressed.
        grid[i][j].setActionCommand(i+""+j);
        // Set the Font to be large, and disable button initially.
        grid[i][j].setFont(new Font("Arial", Font.BOLD, 120));
        grid[i][j].setEnabled(false);
        gameGrid.add(grid[i][j]);
    }

    /**
     * Helper method to populate the control panel with Swing objects.
     * @return JPanel object
     */
    private JPanel controlPanel() {
        // Instantiate GUI objects
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(2, 2));
        messageLabel = new JLabel("Message Window");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messages = new JTextField(20);
        messages.setEditable(false);
        messages.setFont(new Font("Arial", Font.PLAIN, 14));
        setMessage("Press Start to Play.");
        startGame = new JButton("START GAME");

        messagePanel.add(messageLabel);
        messagePanel.add(new JLabel(" "));
        messagePanel.add(messages);
        messagePanel.add(startGame);
        return messagePanel;
    }
}
