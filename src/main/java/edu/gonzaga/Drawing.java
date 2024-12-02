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
        Graphics2D g2 = (Graphics2D) g;
        drawBoard(g2);
        drawPieces(g2);
        drawMessages(g2);
        drawTurnIndicator(g2);
        if (hoveredColumn != null) {
            drawHoverOverlay(g2, hoveredColumn);
        }
    }

    private void drawBoard(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 139)); // Dark blue for the board background
        g2.fillRect(182, 75, 386, 340);

        g2.setColor(Color.white);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                int x = 182 + 25 + i * 50;
                int y = 75 + 340 - 25 - 36 - j * 50;
                g2.fill(new Ellipse2D.Double(x, y, 36, 36));
            }
        }
    }

    private void drawPieces(Graphics2D g2) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Integer team = board.getGrid()[i][j];
                if (team == null || team == 0) continue;

                int x = 182 + 25 + i * 50 + 2;
                int y = 75 + 340 - 25 - 36 - j * 50 + 2;

                // Set the color of the piece
                g2.setColor(team == 1 ? Color.red : Color.yellow);
                g2.fill(new Ellipse2D.Double(x, y, 32, 32));

                // Add black outline
                g2.setColor(Color.black);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Ellipse2D.Double(x, y, 32, 32));
            }
        }
    }

    private void drawHoverOverlay(Graphics2D g2, int column) {
        g2.setColor(new Color(255, 255, 255, 50));
        int x = 182 + 25 + column * 50;
        g2.fillRect(x, 75, 50, 340); // Adjusted width to match column size
    }

    private void drawMessages(Graphics2D g2) {
        g2.setFont(new Font("TimesRoman", Font.BOLD, 20));

        if (errorMessage != null) {
            g2.setColor(Color.red);
            g2.drawString(errorMessage, 10, 500);
        }
    }

    private void drawTurnIndicator(Graphics2D g2) {
        if (gameStateMessage.contains("Red's Turn")) {
            g2.setColor(Color.red);
        } else if (gameStateMessage.contains("Yellow's Turn")) {
            g2.setColor(Color.yellow);
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