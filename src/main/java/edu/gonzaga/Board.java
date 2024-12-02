package edu.gonzaga;

public class Board {
    Integer[][] grid = new Integer[7][6];
    Integer emptySpaces;

    public void setBoard() {
        emptySpaces = 42;
        for (Integer x = 0; x < grid.length; x++) {
            for (Integer y = 0; y < grid[0].length; y++) {
                grid[x][y] = 0;
            }
        }

        /*for (Integer x = 0; x < grid.length; x++) {
            if (x != 1) {
                for (Integer y = 0; y < grid[0].length; y++) {
                    grid[x][y] = 2;
                    emptySpaces--;
                }
            }
        }*/
    }

    public Boolean placePiece(Integer column, Integer team) {
        if (grid[column][(grid[0].length - 1)] == 0) {
            Integer placement = 0;
            for (Integer i = 0; i < grid[column].length; i++) {
                if (grid[column][i] != 0) {
                    placement++;
                }
            }
            emptySpaces--;
            grid[column][placement] = team;
        } else {
            return false;
        }
        return true;
    }

    public void printGrid() {
        for (Integer ii = (grid[0].length - 1); ii >= 0; ii--) {
            for (Integer i = 0; i < grid.length; i++) {
                System.out.print(grid[i][ii] + ", ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public Boolean checkWin(Integer column, Integer team) {
        Integer yCord = grid[column].length - 1;

        // Get y cord for most recent placed piece
        for (Integer y = grid[column].length - 1; y >= 0; y--) {
            if (grid[column][y] == 0) {
                yCord--;
            } else {
                if (grid[column][yCord] != team) {
                    return false;
                }
                break;
            }
        }

        //Check diag
        System.out.println("Diag Count at: " + countDiagonal(column, yCord, team, 0));
        System.out.println("Diag Count at: " + countDiagonal(column, yCord, team, 1));
        System.out.println("Count at: " + countVertical(column, yCord, team));
        System.out.println("Count at: " + countHorizontal(column, yCord, team));

        if (4 <= countDiagonal(column, yCord, team, 0))
            return true;
        if (4 <= countDiagonal(column, yCord, team, 1))
            return true;
        if (4 <= countVertical(column, yCord, team))
            return true;
        if (4 <= countHorizontal(column, yCord, team))
            return true;

        return false;
    }

    public Integer countDiagonal (Integer column, Integer yCord, Integer team, Integer diagonalChoice) {
        Integer[] movingValues = new Integer[] {1, -1};
        Integer count = 1;
        Integer x = 0;
        Integer y = 0;
        for (Integer i = 0; i < 2; i++) {
            x = column;
            y = yCord;
            while (true) {
                if (x - movingValues[i] < 0 || x - movingValues[i] >= grid.length) {
                    break;
                }
                if ((y - movingValues[i] < 0 || y - movingValues[i] >= grid[x].length) && diagonalChoice == 0) {
                    break;
                }
                if ((y + movingValues[i] < 0 || y + movingValues[i] >= grid[x].length) && diagonalChoice == 1) {
                    break;
                }

                if (diagonalChoice == 0)
                    y = y - movingValues[i];
                else
                    y = y + movingValues[i];
                x = x - movingValues[i];

                if (grid[x][y] == team) {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public Integer countVertical(Integer column, Integer yCord, Integer team) {
        Integer[] movingValues = new Integer[] {1, -1};
        Integer count = 1;
        Integer y = 0;
        for (Integer i = 0; i < 2; i++) {
            y = yCord;
            while (true) {
                if ((y - movingValues[i] < 0 || y - movingValues[i] >= grid[column].length)) {
                    break;
                }
                y = y - movingValues[i];
                if (grid[column][y] == team) {
                    count++;
                } else {
                    break;
                }
            }
        }
        return count;
    }

    /**
    * Returns number of horizontal pieces of that team
    *
    * @return Integer horizontal count
    */
    public Integer countHorizontal(Integer column, Integer yCord, Integer team) {
        Integer[] movingValues = new Integer[] {1, -1};
        Integer count = 1;
        Integer x = 0;
        for (Integer i = 0; i < 2; i++) {
            x = column;
            while (true) {
                if (x - movingValues[i] < 0 || x - movingValues[i] >= grid.length) {
                    break;
                }
                x = x - movingValues[i];
                if (grid[x][yCord] == team) {
                    count++;
                } else {
                    break;
                }
            }
        }
        return count;
    }
}


