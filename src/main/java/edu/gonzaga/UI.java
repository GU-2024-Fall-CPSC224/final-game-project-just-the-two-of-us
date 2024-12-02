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
        frame.setSize(750, 550);
        frame.setLayout(new CardLayout());

        // Start Screen
        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.setBackground(new Color(70, 130, 180));

        JLabel titleLabel = new JLabel("CONNECT 4", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);

        JLabel creditsLabel = new JLabel("Made by Just The Two Of Us", JLabel.CENTER);
        creditsLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        creditsLabel.setForeground(Color.WHITE);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.setBackground(new Color(255, 215, 0));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                initGameScreen(frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        startPanel.add(titleLabel, BorderLayout.NORTH);
        startPanel.add(startButton, BorderLayout.CENTER);
        startPanel.add(creditsLabel, BorderLayout.SOUTH);

        frame.add(startPanel);
        frame.setVisible(true);
    }

    private void initGameScreen(JFrame frame) {
        drawing.setGameState("Red's Turn");
        // Create a panel to hold the game board drawing
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background for the game screen

        JPanel drawingPanel = new JPanel(new BorderLayout());
        drawingPanel.setOpaque(false);
        drawingPanel.add(drawing, BorderLayout.CENTER);

        // Create a panel for the buttons that will be at the bottom of the board
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 7, 10, 10)); // Add padding between buttons
        buttonsPanel.setOpaque(false);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setPreferredSize(new Dimension(386, 50));

        for (int i = 0; i < 7; i++) {
            final int column = i; // For the lambda below
            JButton button = new JButton(String.valueOf(i + 1));
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setPreferredSize(new Dimension(50, 50));
            button.setBackground(new Color(255, 215, 0));
            button.setForeground(Color.BLACK);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(true);

            // Handle button press
            button.addActionListener(e -> handleButtonPress(column, frame));

            buttonsPanel.add(button);
        }

        mainPanel.add(drawingPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void handleButtonPress(int column, JFrame frame) {
        int team = board.getEmptySpaces() % 2 == 0 ? 1 : 2; // Alternate turns
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
        winPanel.setBackground(new Color(34, 139, 34)); // Dark green background

        JLabel winLabel = new JLabel(message, JLabel.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 48));
        winLabel.setForeground(Color.WHITE);
        winLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 28));
        restartButton.setBackground(new Color(255, 215, 0));
        restartButton.setForeground(Color.BLACK);
        restartButton.setFocusPainted(false);
        restartButton.setPreferredSize(new Dimension(200, 60));
        restartButton.addActionListener(e -> {
            board.setBoard();
            frame.getContentPane().removeAll();
            initGameScreen(frame);
            frame.revalidate();
            frame.repaint();
        });

        winPanel.add(winLabel, BorderLayout.CENTER);
        winPanel.add(restartButton, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(winPanel);
        frame.revalidate();
        frame.repaint();
    }
}