package edu.gonzaga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void testSetBoard() {
        Board board = new Board();
        board.setBoard();
        for (Integer[] row : board.getGrid()) {
            for (Integer val : row) {
                assertEquals(val, 0);
            }
        }
    }

    @Test
    void testPlacePiece() {
        Board board = new Board();
        board.setBoard();
        board.placePiece(0, 2);
        board.placePiece(3, 2);
        board.placePiece(0, 1);

        //Check team 2 marker at 0,0
        assertEquals(board.getGrid()[0][0], 2);
        //Check team 1 marker at 0,1
        assertEquals(board.getGrid()[0][1], 1);
        //Check team 2 marker at 3,0
        assertEquals(board.getGrid()[3][0], 2);
    }


    @Test
    void testCountDiagonal() {
        Board board = new Board();
        board.setBoard();
        for (int i = 0; i < 4; i++) {
            for (int ii = 0; ii <= i; ii++) {
                board.placePiece(i, 2);
            }
        }
        board.printGrid();
        Integer result1 = board.countDiagonal(0, 0, 2, 0);
        Integer result2 = board.countDiagonal(3, 3, 2, 0);

        Integer result3 = board.countDiagonal(0, 0, 2, 1);
        Integer result4 = board.countDiagonal(3, 3, 2, 1);

        Integer result5 = board.countDiagonal(1, 1, 2, 0);
        Integer result6 = board.countDiagonal(1, 1, 2, 1);

        Integer result7 = board.countDiagonal(1, 1, 1, 1);

        assertEquals(result1, 4);
        assertEquals(result2, 4);
        assertEquals(result3, 1);
        assertEquals(result4, 1);
        assertEquals(result5, 4);
        assertEquals(result6, 2);
        assertEquals(result7, 0);
    }


    @Test
    void testCountHorizontal() {
        Board board = new Board();
        board.setBoard();

        for (int i = 0; i < 4; i++) {
            board.placePiece(i, 2);
        }

        Integer result1 = board.countHorizontal(0, 0, 2);
        Integer result2 = board.countHorizontal(2, 0, 2);
        Integer result3 = board.countHorizontal(2, 0, 1);

        assertEquals(result1, 4);
        assertEquals(result2, 4);
        assertEquals(result3, 0);
    }


    @Test
    void testCountVertical() {
        Board board = new Board();
        board.setBoard();

        for (int i = 0; i < 4; i++) {
            board.placePiece(1, 2);
        }

        Integer result1 = board.countVertical(1, 0, 2);
        Integer result2 = board.countVertical(1, 2, 2);
        Integer result3 = board.countVertical(1, 0, 1);

        assertEquals(result1, 4);
        assertEquals(result2, 4);
        assertEquals(result3, 0);

    }
}
