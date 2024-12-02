package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    private final Board board;
    private final Drawing drawing;

    public UI(Board board) {
        this.board = board;
        this.drawing = new Drawing(board);
    }

    public void start() {
        JFrame frame = new JFrame("CONNECT 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Start Screen
        JPanel startPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("CONNECT 4", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                initGameScreen(frame);
                frame.revalidate();
                frame.repaint();
            }
        });
        startPanel.add(titleLabel, BorderLayout.CENTER);
        startPanel.add(startButton, BorderLayout.SOUTH);

        frame.add(startPanel);
        frame.setSize(750, 550);
        frame.setVisible(true);
    }

    private void initGameScreen(JFrame frame) {
        drawing.setGameState("Yellow's Turn");
        // Create buttons for column selection
        JPanel buttonsPanel = new JPanel();
        for (int i = 0; i < 7; i++) {
            final int column = i; // Lambda requires the variable to be final or effectively final
            JButton button = new JButton(Integer.toString(i + 1));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonPress(column, frame);
                }
            });
            buttonsPanel.add(button);
        }

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(drawing, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void handleButtonPress(int column, JFrame frame) {
        int team = board.getEmptySpaces() % 2 == 0 ? 2 : 1; // Alternate turns
        if (board.placePiece(column, team)) {
            if (board.checkWin(column, team)) {
                drawing.setGameState(team == 1 ? "RED WINS!" : "YELLOW WINS!");
                showWinScreen(frame, team == 1 ? "RED WINS!" : "YELLOW WINS!");
            } else if (board.getEmptySpaces() == 0) {
                drawing.setGameState("IT'S A TIE!");
                showWinScreen(frame, "IT'S A TIE!");
            } else {
                drawing.setGameState(team == 1 ? "Yellow's Turn" : "Red's Turn");
            }
        } else {
            drawing.setError("Column " + (column + 1) + " is full. Try another!");
        }
        drawing.repaint();
    }

    private void showWinScreen(JFrame frame, String message) {
        JPanel winPanel = new JPanel(new BorderLayout());
        JLabel winLabel = new JLabel(message, JLabel.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 32));
        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setBoard();
                frame.getContentPane().removeAll();
                initGameScreen(frame);
                frame.revalidate();
                frame.repaint();
            }
        });
        winPanel.add(winLabel, BorderLayout.CENTER);
        winPanel.add(restartButton, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(winPanel);
        frame.revalidate();
        frame.repaint();
    }
}
