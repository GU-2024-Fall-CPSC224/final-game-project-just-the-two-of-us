package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Drawing extends JComponent {
    private final Board board;
    private String gameStateMessage = "Red's Turn";
    private String errorMessage;
    private Integer hoveredColumn = null;
    private JLabel gameStateLabel;

    private static final int CELL_SIZE = 50;
    private static final int PIECE_SIZE = 36;
    private static final int PIECE_MARGIN = 7;

    public Drawing(Board board) {
        this.board = board;
    }

    public void setHoveredColumn(Integer column) {
        this.hoveredColumn = column;
        repaint();
    }

    public void setGameState(String message) {
        this.gameStateMessage = message;
        if (gameStateLabel != null) {
            gameStateLabel.setText(message);
        }
        this.errorMessage = null; // Clear error on valid state change
    }

    public void setError(String message) {
        this.errorMessage = message;
    }

    public void setGameStateLabel(JLabel gameStateLabel) {
        this.gameStateLabel = gameStateLabel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass to ensure the component is correctly redrawn.
        Graphics2D g2 = (Graphics2D) g;
        
        // Enable anti-aliasing for smoother graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawBackground(g2);
        drawBoard(g2);
        drawPieces(g2);
        drawMessages(g2);
        drawTurnIndicator(g2);
        if (hoveredColumn != null) {
            drawHoverOverlay(g2, hoveredColumn);
        }
    }

    private void drawBackground(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
    
        GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 235), width, height, new Color(70, 130, 180));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, width, height);
    }

    private void drawBoard(Graphics2D g2) {
        int columns = board.getGrid().length;
        int rows = board.getGrid()[0].length;
        int boardWidth = columns * CELL_SIZE;
        int boardHeight = rows * CELL_SIZE;
    
        // Center the board
        int centerX = (getWidth() - boardWidth) / 2;
        int centerY = (getHeight() - boardHeight) / 2;
    
        // Draw board background with a border
        g2.setColor(new Color(0, 0, 139)); // Dark blue for the board background
        g2.fillRoundRect(centerX - 5, centerY - 5, boardWidth + 10, boardHeight + 10, 20, 20);
        g2.setColor(Color.WHITE);
    
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                int x = centerX + (CELL_SIZE - PIECE_SIZE) / 2 + i * CELL_SIZE;
                int y = centerY + boardHeight - (CELL_SIZE + PIECE_SIZE) / 2 - j * CELL_SIZE;
                g2.fill(new Ellipse2D.Double(x, y, PIECE_SIZE, PIECE_SIZE));
            }
        }
    }

    private void drawPieces(Graphics2D g2) {
        int columns = board.getGrid().length;
        int rows = board.getGrid()[0].length;
        int boardHeight = rows * CELL_SIZE;

        // Center the board
        int centerX = (getWidth() - columns * CELL_SIZE) / 2;
        int centerY = (getHeight() - boardHeight) / 2;

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Integer team = board.getGrid()[i][j];
                if (team == null || team == 0) continue;

                int x = centerX + (CELL_SIZE - PIECE_SIZE) / 2 + i * CELL_SIZE + PIECE_MARGIN;
                int y = centerY + boardHeight - (CELL_SIZE + PIECE_SIZE) / 2 - j * CELL_SIZE + PIECE_MARGIN;

                // Set the color of the piece
                g2.setColor(board.getPlayerColor(team));
                g2.fill(new Ellipse2D.Double(x, y, PIECE_SIZE - 2 * PIECE_MARGIN, PIECE_SIZE - 2 * PIECE_MARGIN));

                // Add black outline
                g2.setColor(Color.black);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Ellipse2D.Double(x, y, PIECE_SIZE - 2 * PIECE_MARGIN, PIECE_SIZE - 2 * PIECE_MARGIN));
            }
        }
    }

    private void drawHoverOverlay(Graphics2D g2, int column) {
        int rows = board.getGrid()[0].length;
        int boardHeight = rows * CELL_SIZE;

        // Center the board
        int centerX = (getWidth() - board.getGrid().length * CELL_SIZE) / 2;
        int centerY = (getHeight() - boardHeight) / 2;

        g2.setColor(new Color(255, 255, 255, 50));
        int x = centerX + column * CELL_SIZE;
        g2.fillRect(x, centerY, CELL_SIZE, boardHeight); // Adjusted width to match column size
    }

    private void drawMessages(Graphics2D g2) {
        g2.setFont(new Font("TimesRoman", Font.BOLD, 20));

        if (errorMessage != null) {
            g2.setColor(Color.red);
            g2.drawString(errorMessage, 10, 700); // Adjusted to fit larger window
        }
    }

    private void drawTurnIndicator(Graphics2D g2) {
        if (gameStateMessage.contains("Red's Turn") || gameStateMessage.contains("Yellow's Turn")) {
            int team = gameStateMessage.contains("Red's Turn") ? 1 : 2;
            g2.setColor(board.getPlayerColor(team));
        } else {
            return;
        }
        int chipX = 310;
        int chipY = 20;
        g2.fill(new Ellipse2D.Double(chipX, chipY, 30, 30));

        // Add black outline to the chip
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Ellipse2D.Double(chipX, chipY, 30, 30));

        // Draw turn message
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g2.getFontMetrics();
        int textX = chipX + 40;
        int textY = chipY + (30 - metrics.getHeight()) / 2 + metrics.getAscent();
        g2.drawString(gameStateMessage, textX, textY);
    }
}
