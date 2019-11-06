
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The View class for the 'Start Game' frame. This frame is used to start a game by taking in the player names and
 * opponent type.
 */
public class StartView extends JFrame {
    private JLabel playerOne, playerTwo, nameOneLabel, nameTwoLabel, playerTypeOneLabel, playerTypeTwoLabel;
    private JTextField nameOneField, nameTwoField;
    private JComboBox playerTypeOneCombo, playerTypeTwoCombo;
    private JButton startGame;
    // Player types currently available in the program. Easy = Random, Medium = Blocking, Hard = Smart
    private final String[] playerTypes = {"Human", "AI Easy", "AI Medium", "AI Hard"};

    /**
     * Constructor for the Container object. This frame uses a BorderLayout.
     */
    public StartView() {
        super("Start Game");
        setLayout(new BorderLayout());
        // Ensure program doesn't close entirely
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Prevent re-sizing
        setResizable(false);
        addElements();
        pack();
    }

    /**
     * Display the frame to the User
     */
    public void run() {
        setSize(600, 130);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Add start game listener.
     *
     * @param e Action Listener
     */
    public void addStartGameListener(ActionListener e) {
        startGame.addActionListener(e);
    }

    /**
     * Gets player one name. By default, it is 'Player 1' if no inputs.
     *
     * @return the player 1 name
     */
    public String getPlayerOneName() {
        String name = nameOneField.getText();
        if (!name.isBlank()) {
            return name;
        } else {
            return "Player 1";
        }
    }

    /**
     * Gets player two name. By default, it is 'Player 2' if no inputs.
     *
     * @return the player 2 name
     */
    public String getPlayerTwoName() {
        String name = nameTwoField.getText();
        if (!name.isBlank()) {
            return name;
        } else {
            return "Player 2";
        }
    }

    /**
     * Gets player one type.
     *
     * @return the player one type
     */
    public String getPlayerOneType() {
        return playerTypeOneCombo.getSelectedItem().toString();
    }

    /**
     * Gets player two type.
     *
     * @return the player two type
     */
    public String getPlayerTwoType() {
        return playerTypeTwoCombo.getSelectedItem().toString();
    }

    /**
     * Helper method to add elements to the Border Layout.
     */
    private void addElements() {
        add("West", playerOnePanel());
        add("East", playerTwoPanel());
        startGame = new JButton("START");
        add("South", startGame);
    }

    /**
     * Helper method to populate the left pane using a GridLayout (Player 1)
     * @return JPanel object
     */
    private JPanel playerOnePanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 2));
        playerOne = new JLabel("Enter Player 1 Details:");
        nameOneLabel = new JLabel("Name: ");
        nameOneField = new JTextField();
        playerTypeOneLabel = new JLabel("Player Type: ");
        playerTypeOneCombo = new JComboBox(playerTypes);
        // For now, Player 1 is only able to be a HUMAN - selection is disabled
        playerTypeOneCombo.setEnabled(false);

        playerTypeOneCombo.setSelectedIndex(0);

        leftPanel.add(playerOne);
        leftPanel.add(new JLabel(""));
        leftPanel.add(nameOneLabel);
        leftPanel.add(nameOneField);
        leftPanel.add(playerTypeOneLabel);
        leftPanel.add(playerTypeOneCombo);

        return leftPanel;
    }

    /**
     * Helper method to populate the right pane using a GridLayout (Player 2)
     * @return JPanel object
     */
    private JPanel playerTwoPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3, 2));
        playerTwo = new JLabel("Enter Player 2 Details:");
        nameTwoLabel = new JLabel("Name: ");
        nameTwoField = new JTextField();
        playerTypeTwoLabel = new JLabel("Player Type: ");

        // Player 2 can be human or AI. By default, 'Human' is the selection.
        playerTypeTwoCombo = new JComboBox(playerTypes);
        playerTypeTwoCombo.setSelectedIndex(0);

        rightPanel.add(playerTwo);
        rightPanel.add(new JLabel(""));
        rightPanel.add(nameTwoLabel);
        rightPanel.add(nameTwoField);
        rightPanel.add(playerTypeTwoLabel);
        rightPanel.add(playerTypeTwoCombo);

        return rightPanel;
    }

}
