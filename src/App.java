import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JTextField nameField, guessField;
    private JTextArea outputArea;
    private JButton startButton, guessButton;
    private String userName;
    private int myNumber, attempts, totalAttempts, roundsWon;
    private boolean isPlaying;

    public App() {
        setTitle("Number Guess Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        getContentPane().setBackground(new Color(255, 204, 153)); // Light orange background

        JLabel nameLabel = new JLabel("Please Enter your name:");
        nameField = new JTextField(15);
        startButton = new JButton("Start Game");

        JLabel guessLabel = new JLabel("Guess my number (1-100):");
        guessField = new JTextField(5);
        guessButton = new JButton("Guess");
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        add(nameLabel);
        add(nameField);
        add(startButton);
        add(guessLabel);
        add(guessField);
        add(guessButton);
        add(new JScrollPane(outputArea));

        guessButton.setEnabled(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guessNumber();
            }
        });
    }

    private void startGame() {
        userName = nameField.getText();
        outputArea.setText(userName + ", Hello and welcome to the Number Guess Game!\n");
        myNumber = (int) (Math.random() * 100) + 1;
        attempts = 0;
        isPlaying = true;
        guessButton.setEnabled(true);
        startButton.setEnabled(false);
        nameField.setEditable(false);
    }

    private void guessNumber() {
        if (!isPlaying) return;

        try {
            int userNumber = Integer.parseInt(guessField.getText());
            attempts++;
            totalAttempts++;
            outputArea.append("Attempt " + attempts + " of 5\n");

            if (userNumber == myNumber) {
                outputArea.append("WOOOOHOOOO..... " + userName + ", you guessed the correct number!\n");
                roundsWon++;
                isPlaying = false;
            } else if (userNumber > myNumber) {
                outputArea.append(userName + ", YOUR NUMBER IS TOO LARGE\n");
            } else {
                outputArea.append(userName + ", YOUR NUMBER IS TOO SMALL\n");
            }

            if (attempts == 5 && isPlaying) {
                outputArea.append("Sorry " + userName + ", you've used all your attempts. The correct number was: " + myNumber + "\n");
                isPlaying = false;
            }

            if (!isPlaying) {
                int playAgain = JOptionPane.showConfirmDialog(this, userName + ", Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
                if (playAgain == JOptionPane.YES_OPTION) {
                    startButton.setEnabled(true);
                    guessButton.setEnabled(false);
                    nameField.setEditable(true);
                    nameField.setText("");
                } else {
                    outputArea.append("Total attempts: " + totalAttempts + "\n");
                    outputArea.append("Rounds won: " + roundsWon + "\n");
                }
            }

        } catch (NumberFormatException e) {
            outputArea.append("Please enter a valid number!\n");
        }

        guessField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}
