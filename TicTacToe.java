import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    // Array to hold buttons representing the grid
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X'; // Current player, 'X' or 'O'
    private boolean againstComputer = false; // Flag to determine if playing against computer
    private Random random = new Random(); // Random number generator for computer moves

    public TicTacToe() {
        setTitle("Tic Tac Toe"); // Set the title of the window
        setSize(400, 400); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application exits when the window is closed
        setLayout(new GridLayout(3, 3)); // Set the layout manager to a 3x3 grid

        initializeButtons(); // Initialize the buttons on the grid
        selectMode(); // Prompt the user to select the game mode
    }

    private void selectMode() {
        // Options for the game mode
        String[] options = {"Two Players", "Against Computer"};
        
        // Show a dialog to select the game mode
        int choice = JOptionPane.showOptionDialog(
            this,
            "Choose Game Mode",
            "Game Mode",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );

        // Set the flag based on user selection
        againstComputer = (choice == 1);
    }

    private void initializeButtons() {
        // Initialize each button in the 3x3 grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60)); // Set font size for button text
                buttons[row][col].setFocusPainted(false); // Remove focus paint
                buttons[row][col].addActionListener(this); // Add action listener to the button
                add(buttons[row][col]); // Add button to the frame
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the button that was clicked
        JButton buttonClicked = (JButton) e.getSource();
        
        // Check if the button has not been clicked yet
        if (buttonClicked.getText().equals("")) {
            buttonClicked.setText(String.valueOf(currentPlayer)); // Set the text to the current player's symbol
            buttonClicked.setEnabled(false); // Disable the button
            
            // Check for a win or draw
            if (checkForWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                resetBoard(); // Reset the board for a new game
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(null, "The game is a draw!");
                resetBoard(); // Reset the board for a new game
            } else {
                // Switch to the other player
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                
                // If playing against the computer and it's the computer's turn, make a move
                if (againstComputer && currentPlayer == 'O') {
                    makeComputerMove();
                }
            }
        }
    }

    private void makeComputerMove() {
        boolean moveMade = false;
        
        // Loop until a valid move is made
        while (!moveMade) {
            int row = random.nextInt(3); // Randomly select a row
            int col = random.nextInt(3); // Randomly select a column
            
            // Check if the selected cell is empty
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText("O"); // Set the text to 'O'
                buttons[row][col].setEnabled(false); // Disable the button
                moveMade = true; // Exit the loop
            }
        }
        
        // Check for a win or draw after the computer's move
        if (checkForWin()) {
            JOptionPane.showMessageDialog(null, "Player O wins!");
            resetBoard(); // Reset the board for a new game
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(null, "The game is a draw!");
            resetBoard(); // Reset the board for a new game
        } else {
            currentPlayer = 'X'; // Switch back to the human player
        }
    }

    private boolean checkForWin() {
        // Check all rows and columns for a win
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[i][2].getText().equals(String.valueOf(currentPlayer))) ||
                (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[2][i].getText().equals(String.valueOf(currentPlayer)))) {
                return true; // Return true if a win is detected
            }
        }

        // Check both diagonals for a win
        if ((buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[2][2].getText().equals(String.valueOf(currentPlayer))) ||
            (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[2][0].getText().equals(String.valueOf(currentPlayer)))) {
            return true; // Return true if a win is detected
        }

        return false; // No win detected
    }

    private boolean isBoardFull() {
        // Check if all cells are filled
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false; // Return false if an empty cell is found
                }
            }
        }
        return true; // All cells are filled
    }

    private void resetBoard() {
        // Reset all buttons to their initial state
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = 'X'; // Reset the current player to 'X'
    }

    public static void main(String[] args) {
        // Run the game on the event dispatch thread
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true); // Make the game window visible
        });
    }
}
