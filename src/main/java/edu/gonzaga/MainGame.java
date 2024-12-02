/*
 * Final project main driver class
 * 
 * 
 * Project Description: Connect 4
 * 
 * 
 * Contributors: Thomas Domser, Mateo Merrin
 * 
 * 
 * Copyright: 2023
 */
package edu.gonzaga;


/** Main program class for launching your team's program. */
public class MainGame {
    public static void main(String[] args) {
        Board board = new Board();
        board.setBoard();

        // Launch the UI and pass the board object
        UI gameUI = new UI(board);
        gameUI.start();
    }
}
