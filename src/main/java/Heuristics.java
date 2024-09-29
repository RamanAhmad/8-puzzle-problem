/**
 * The methode {@link Heuristics} includes all heuristics
 */

public class Heuristics {

    // Method to calculate Manhattan distance heuristic
    public int manhattanDistance(int[][] board) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) continue;
                int y = (board[i][j] - 1) % 3;
                int x = (board[i][j] - 1) / 3;
                result += Math.abs(x - i) + Math.abs(y - j);
            }
        }
        return result;
    }

    // Method to calculate Tiles out of place heuristic
    public int misplacedTiles(int[][] board) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0 && board[i][j] != i * 3 + j + 1) {
                    result++;
                }
            }
        }
        return result;
    }

    // The inadmissible heuristic
    public int randomHeuristic(int[][] board) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    result++;
                }
                if (board[i][j] == i * 3 + j + 1) {
                    result--;
                }
            }
        }
        return result;
    }

    // The admissible heuristic for the assignment
    public int evenOddNumberHeuristic(int[][] board) {
        int cost = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int current = board[i][j];
                if (current == 0 || current == i * 3 + j + 1) continue;
                cost += checkingNumber(current, i, j);
            }
        }
        return cost;
    }

    private int checkingNumber(int number, int i, int j) {
        return (number & 1) == 0 ? evenNumberCost(number, i, j) : oddNumberCost(number, i, j);
    }

    private int evenNumberCost(int number, int i, int j) {
        int[][] evenNumberRules = {
                {1, 2, 1},
                {2, 1, 2},
                {3, 2, 1}
        };

        int cost = evenNumberRules[i][j];

        if ((number == 4 || number == 8) && i == 0 && j == 2) return 3;
        if ((number == 4 || number == 8) && i == 2 && j == 0) return 1;
        if (number <= 4 && i == 2 && j == 2) return 3;
        if (number <= 4 && i == 0 && j == 0) return 1;

        return cost;
    }

    private int oddNumberCost(int number, int i, int j) {
        int[][] oddNumberRules = {
                {2, 1, 4},
                {1, 2, 3},
                {2, 3, 4}
        };

        int cost = oddNumberRules[i][j];

        if (number == 7 && i == 0 && j == 2) return 4;
        if (number == 3 && i == 2 && j == 0) return 4;
        if (number == 1 && i == 2 && j == 2) return 4;

        return cost;
    }


    // Method to calculate Linear Conflict heuristic
    public int linearConflict(int[][] board) {
        int conflict = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = board[i][j];
                if (value != 0 && (value - 1) / 3 == i) {
                    for (int k = j + 1; k < 3; k++) {
                        int nextValue = board[i][k];
                        if (nextValue != 0 && (nextValue - 1) / 3 == i && value > nextValue) {
                            conflict++;
                        }
                    }
                }
            }
        }
        return conflict;
    }
}

