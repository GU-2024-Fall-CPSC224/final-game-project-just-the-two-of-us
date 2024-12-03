package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    private String getPlayerColorName(int team) {
        Color color = board.getPlayerColor(team);
        if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.BLUE)) {
            return "Blue";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        } else if (color.equals(Color.YELLOW)) {
            return "Yellow";
        } else {
            return "Unknown Color";
        }
    }

    private final Board board;
    private final Drawing drawing;
    private JLabel gameStateLabel;

    public UI(Board board) {
        this.board = board;
        this.drawing = new Drawing(board);
    }

    public void start() {
        JFrame frame = new JFrame("CONNECT 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new CardLayout());

        // Start Screen
        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.setBackground(new Color(30, 144, 255)); // Consistent vibrant blue

        JLabel titleLabel = new JLabel("CONNECT 4", JLabel.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 60)); // Larger, more modern font
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Add padding around the title

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 32)); // Larger font for emphasis
        startButton.setBackground(new Color(0, 191, 255)); // Sky blue for a more inviting button
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(250, 80)); // Larger button size
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3)); // Add white border to button
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(135, 206, 250)); // Lighter blue on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(0, 191, 255));
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                initPlayerInfoScreen(frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        JLabel subtitleLabel = new JLabel("By: Just The Two Of Us", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 24)); // Add a subtitle below the title
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.add(startButton);

        startPanel.add(titleLabel, BorderLayout.NORTH);
        startPanel.add(subtitleLabel, BorderLayout.CENTER);
        startPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(startPanel);
        frame.setVisible(true);
    }

    private void initPlayerInfoScreen(JFrame frame) {
        JPanel playerInfoPanel = new JPanel(new GridBagLayout());
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        playerInfoPanel.setBackground(new Color(30, 144, 255)); // Consistent vibrant blue background
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel player1Label = new JLabel("Player 1 Name:");
        player1Label.setFont(new Font("Arial", Font.BOLD, 20));
        player1Label.setForeground(Color.WHITE);
        JTextField player1NameField = new JTextField(15);
    
        JLabel player2Label = new JLabel("Player 2 Name:");
        player2Label.setFont(new Font("Arial", Font.BOLD, 20));
        player2Label.setForeground(Color.WHITE);
        JTextField player2NameField = new JTextField(15);
    
        JLabel player1ColorLabel = new JLabel("Player 1 Color:");
        player1ColorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        player1ColorLabel.setForeground(Color.WHITE);
        JComboBox<String> player1ColorBox = new JComboBox<>(new String[]{"Red", "Blue", "Green", "Yellow"});
        player1ColorBox.setFont(new Font("Arial", Font.PLAIN, 18));
        player1ColorBox.setPreferredSize(new Dimension(150, 30));
    
        JLabel player2ColorLabel = new JLabel("Player 2 Color:");
        player2ColorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        player2ColorLabel.setForeground(Color.WHITE);
        JComboBox<String> player2ColorBox = new JComboBox<>(new String[]{"Red", "Blue", "Green", "Yellow"});
        player2ColorBox.setFont(new Font("Arial", Font.PLAIN, 18));
        player2ColorBox.setPreferredSize(new Dimension(150, 30));
    
        JLabel rowsLabel = new JLabel("Number of Rows:");
        rowsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rowsLabel.setForeground(Color.WHITE);
        JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(6, 4, 10, 1));
    
        JLabel columnsLabel = new JLabel("Number of Columns:");
        columnsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        columnsLabel.setForeground(Color.WHITE);
        JSpinner columnsSpinner = new JSpinner(new SpinnerNumberModel(7, 4, 10, 1));
    
        JLabel winConditionLabel = new JLabel("Pieces in a Row to Win:");
        winConditionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        winConditionLabel.setForeground(Color.WHITE);
        JSpinner winConditionSpinner = new JSpinner(new SpinnerNumberModel(4, 3, 10, 1));
    
        JButton proceedButton = new JButton("Proceed");
        proceedButton.setFont(new Font("Arial", Font.BOLD, 24));
        proceedButton.setBackground(new Color(0, 191, 255)); // Sky blue for consistency
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFocusPainted(false);
        proceedButton.setPreferredSize(new Dimension(200, 50));
        proceedButton.addActionListener(e -> {
            String player1Name = player1NameField.getText();
            String player2Name = player2NameField.getText();
            String player1Color = (String) player1ColorBox.getSelectedItem();
            String player2Color = (String) player2ColorBox.getSelectedItem();
            int rows = (int) rowsSpinner.getValue();
            int columns = (int) columnsSpinner.getValue();
            int winCondition = (int) winConditionSpinner.getValue();
    
            if (player1Name.isEmpty() || player2Name.isEmpty() || player1Color.equals(player2Color)) {
                JOptionPane.showMessageDialog(frame, "Please provide valid names and select different colors for each player.");
            } else {
                board.setBoardSize(rows, columns);
                board.setWinCondition(winCondition);
                board.setPlayerDetails(player1Name, player1Color, player2Name, player2Color);
                frame.getContentPane().removeAll();
                initGameScreen(frame);
                frame.revalidate();
                frame.repaint();
            }
        });
    
        // Adding components to playerInfoPanel
        gbc.gridx = 0; gbc.gridy = 0; playerInfoPanel.add(player1Label, gbc);
        gbc.gridx = 1; gbc.gridy = 0; playerInfoPanel.add(player1NameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; playerInfoPanel.add(player2Label, gbc);
        gbc.gridx = 1; gbc.gridy = 1; playerInfoPanel.add(player2NameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; playerInfoPanel.add(player1ColorLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; playerInfoPanel.add(player1ColorBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3; playerInfoPanel.add(player2ColorLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; playerInfoPanel.add(player2ColorBox, gbc);
        gbc.gridx = 0; gbc.gridy = 4; playerInfoPanel.add(rowsLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; playerInfoPanel.add(rowsSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 5; playerInfoPanel.add(columnsLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; playerInfoPanel.add(columnsSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 6; playerInfoPanel.add(winConditionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 6; playerInfoPanel.add(winConditionSpinner, gbc);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(playerInfoPanel, BorderLayout.CENTER);
        mainPanel.add(proceedButton, BorderLayout.SOUTH);
        mainPanel.setBackground(new Color(30, 144, 255)); // Consistent vibrant blue background
    
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void initGameScreen(JFrame frame) {
        drawing.setGameState(board.getPlayerDetails(1) + "'s Turn");
    
        // Create a panel to hold the game state label
        gameStateLabel = new JLabel(board.getPlayerDetails(1) + "'s Turn", JLabel.CENTER);
        gameStateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameStateLabel.setForeground(Color.WHITE);
    
        // Create a panel to hold the game board drawing
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 144, 255)); // Consistent vibrant blue background
    
        JPanel drawingPanel = new JPanel(new BorderLayout());
        drawingPanel.setOpaque(false);
        drawingPanel.add(drawing, BorderLayout.CENTER);
    
        // Create a panel for the buttons that will be at the bottom of the board
        int columns = board.getGrid().length; // Get the number of columns from the board configuration
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, columns, 10, 10)); // Adjust the layout based on the number of columns
        buttonsPanel.setOpaque(false);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setPreferredSize(new Dimension(columns * 50, 50)); // Adjust width dynamically
    
        for (int i = 0; i < columns; i++) {
            final int column = i; // For the lambda below
            JButton button = new JButton(String.valueOf(i + 1));
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setPreferredSize(new Dimension(50, 50));
            button.setBackground(new Color(0, 191, 255)); // Sky blue for consistency
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(true);
    
            // Handle button press
            button.addActionListener(e -> handleButtonPress(column, frame));
    
            buttonsPanel.add(button);
        }
    
        mainPanel.add(gameStateLabel, BorderLayout.NORTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void handleButtonPress(int column, JFrame frame) {
        int team = board.getEmptySpaces() % 2 == 0 ? 1 : 2; // Alternate turns
        if (board.placePiece(column, team)) {
            if (board.checkWin(column, team)) {
                drawing.setGameState(team == 1 ? board.getPlayerDetails(1) + " WINS!" : board.getPlayerDetails(2) + " WINS!");
                gameStateLabel.setText(team == 1 ? board.getPlayerDetails(1) + " WINS!" : board.getPlayerDetails(2) + " WINS!");
                showWinScreen(frame, team == 1 ? board.getPlayerDetails(1) + " WINS!" : board.getPlayerDetails(2) + " WINS!");
            } else if (board.getEmptySpaces() == 0) {
                drawing.setGameState("IT'S A TIE!");
                gameStateLabel.setText("IT'S A TIE!");
                showWinScreen(frame, "IT'S A TIE!");
            } else {
                drawing.setGameState(team == 1 ? board.getPlayerDetails(2) + "'s Turn" : board.getPlayerDetails(1) + "'s Turn");
                gameStateLabel.setText(team == 1 ? board.getPlayerDetails(2) + "'s Turn" : board.getPlayerDetails(1) + "'s Turn");
            }
        } else {
            drawing.setError("Column " + (column + 1) + " is full. Try another!");
        }
        drawing.repaint();
    }

    private void showWinScreen(JFrame frame, String message) {
        JPanel winPanel = new JPanel(new BorderLayout());
        winPanel.setBackground(new Color(30, 144, 255)); // Consistent vibrant blue background

        JLabel winLabel = new JLabel(message, JLabel.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 48));
        winLabel.setForeground(Color.WHITE);
        winLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 28));
        restartButton.setBackground(new Color(0, 191, 255)); // Sky blue for consistency
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setPreferredSize(new Dimension(200, 60));
        restartButton.addActionListener(e -> {
            board.setBoard();
            frame.getContentPane().removeAll();
            initPlayerInfoScreen(frame);  
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