package edu.gonzaga;

import java.awt.Color;

public class Board {
    Integer[][] grid = new Integer[7][6];
    Integer emptySpaces;
    private String player1Name;
    private String player2Name;
    private Color player1Color;
    private Color player2Color;
    private int winCondition = 4;

    public void setBoardSize(int rows, int columns) {
        grid = new Integer[columns][rows]; // Set grid based on user input
        setBoard();
    }

    public void setBoard() {
        emptySpaces = grid.length * grid[0].length;
        for (Integer x = 0; x < grid.length; x++) {
            for (Integer y = 0; y < grid[0].length; y++) {
                grid[x][y] = 0;
            }
        }
        
    }

    public void setPlayerDetails(String player1Name, String player1Color, String player2Name, String player2Color) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Color = getColorFromString(player1Color);
        this.player2Color = getColorFromString(player2Color);
    }

    public String getPlayerDetails(int team) {
        return team == 1 ? player1Name : player2Name;
    }

    public Color getPlayerColor(int team) {
        return team == 1 ? player1Color : player2Color;
    }

    private Color getColorFromString(String colorName) {
        switch (colorName) {
            case "Red":
                return Color.RED;
            case "Blue":
                return Color.BLUE;
            case "Green":
                return Color.GREEN;
            case "Yellow":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
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

        System.out.println("Diag Count at: " + countDiagonal(column, yCord, team, 0));
        System.out.println("Diag Count at: " + countDiagonal(column, yCord, team, 1));
        System.out.println("Count at: " + countVertical(column, yCord, team));
        System.out.println("Count at: " + countHorizontal(column, yCord, team));
    
        // Check diagonals, vertical, and horizontal
        if (winCondition <= countDiagonal(column, yCord, team, 0))
            return true;
        if (winCondition <= countDiagonal(column, yCord, team, 1))
            return true;
        if (winCondition <= countVertical(column, yCord, team))
            return true;
        if (winCondition <= countHorizontal(column, yCord, team))
            return true;
    
        return false;
    }

    public Integer countDiagonal (Integer column, Integer yCord, Integer team, Integer diagonalChoice) {
        if (grid[column][yCord] != team) {
            return 0;
        }
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
        if (grid[column][yCord] != team) {
            return 0;
        }
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
        if (grid[column][yCord] != team) {
            return 0;
        }
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

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public Integer[][] getGrid() {
        return grid;
    }

    public Integer getEmptySpaces() {
        return emptySpaces;
    }
}