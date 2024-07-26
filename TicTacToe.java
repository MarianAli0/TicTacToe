import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';

    public TicTacToe() {
        // Setting up the JFrame
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        
        // Initialize the buttons
        initializeButtons();
    }

    private void initializeButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this); // Registering the action listener
                add(buttons[row][col]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
        if (buttonClicked.getText().equals("")) {
            buttonClicked.setText(String.valueOf(currentPlayer));
            buttonClicked.setEnabled(false);
            if (checkForWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(null, "The game is a draw!");
                resetBoard();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Toggle the player
            }
        }
    }

    private boolean checkForWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[i][2].getText().equals(String.valueOf(currentPlayer))) ||
                (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                 buttons[2][i].getText().equals(String.valueOf(currentPlayer)))) {
                return true;
            }
        }

        // Check diagonals
        if ((buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[2][2].getText().equals(String.valueOf(currentPlayer))) ||
            (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
             buttons[2][0].getText().equals(String.valueOf(currentPlayer)))) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = 'X'; // Reset the current player
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
