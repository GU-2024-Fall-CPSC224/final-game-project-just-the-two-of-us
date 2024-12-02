package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Drawing extends JComponent {
    private final Board board;
    private String gameStateMessage = "Red's Turn";
    private String errorMessage;

    public Drawing(Board board) {
        this.board = board;
    }

    public void setGameState(String message) {
        this.gameStateMessage = message;
        this.errorMessage = null; // Clear error on valid state change
    }

    public void setError(String message) {
        this.errorMessage = message;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawBoard(g2);
        drawPieces(g2);
        drawMessages(g2);
    }

    private void drawBoard(Graphics2D g2) {
        g2.setColor(Color.blue);
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

    private void drawMessages(Graphics2D g2) {
        g2.setFont(new Font("TimesRoman", Font.BOLD, 20));

        if (gameStateMessage != null) {
            g2.setColor(Color.lightGray);
            g2.fillRect(282, 25, 200, 40);

            g2.setColor(Color.black);
            g2.drawString(gameStateMessage, 292, 50);
        }

        if (errorMessage != null) {
            g2.setColor(Color.black);
            g2.drawString("Error: " + errorMessage, 20, 400);
        }
    }
}
