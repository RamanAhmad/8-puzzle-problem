public class EightPuzzleIDAStar {
    Heuristics heuristics = new Heuristics();
    int[] dx = {-1, 1, 0, 0};  // Bewegungen für Nachbarn
    int[] dy = {0, 0, -1, 1};  // Bewegungen für Nachbarn

    public State searchGoalState(State initialState, int heuristic) {
        int threshold = initialState.cost;  // Startschwelle
        while (true) {
            int temp = search(initialState, 0, threshold, heuristic);  // Beginn der rekursiven Suche
            if (temp == Integer.MAX_VALUE) {
                return null;  // Keine Lösung gefunden
            } else if (temp == -1) {
                return initialState;  // Lösung gefunden
            }
            threshold = temp;  // Schwellenwert für den nächsten Iterationslauf aktualisieren
        }
    }

    private int search(State currentState, int g, int threshold, int heuristic) {
        int f = g + calculateCost(currentState.board, heuristic);  // Berechne f(n) = g(n) + h(n)
        if (f > threshold) return f;  // Schwellenwert überschritten, zur nächsten Iteration zurückkehren

        if (calculateCost(currentState.board, heuristic) == 0) {
            return -1;  // Lösung gefunden
        }

        int min = Integer.MAX_VALUE;  // Minimaler neuer Schwellenwert
        int[] pos = new int[2];
        findZero(currentState.board, pos);
        int x = pos[0], y = pos[1];

        for (int k = 0; k < 4; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                int[][] newBoard = swapTiles(currentState.board, x, y, nx, ny);
                State newState = new State(newBoard, nx, ny, g + 1, g + 1, currentState);
                int temp = search(newState, g + 1, threshold, heuristic);
                if (temp == -1) {
                    return -1;  // Lösung gefunden
                }
                if (temp < min) {
                    min = temp;  // Minimalen neuen Schwellenwert finden
                }
            }
        }
        return min;  // Neuen Schwellenwert für die nächste Iteration zurückgeben
    }

    public int[][] swapTiles(int[][] board, int x, int y, int nx, int ny) {
        int[][] newBoard = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, 3);
        }
        newBoard[x][y] = newBoard[nx][ny];
        newBoard[nx][ny] = 0;
        return newBoard;
    }

    void findZero(int[][] arr, int[] pos) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                    return;
                }
            }
        }
    }

    public int calculateCost(int[][] board, int heuristic) {
        return switch (heuristic) {
            case 0 -> heuristics.evenOddNumberHeuristic(board);
            case 1 -> heuristics.manhattanDistance(board);
            case 2 -> heuristics.misplacedTiles(board);
            case 3 -> heuristics.linearConflict(board) + heuristics.evenOddNumberHeuristic(board);
            case 4 -> heuristics.linearConflict(board) + heuristics.manhattanDistance(board);
            default -> throw new IllegalArgumentException("Invalid choice of heuristic function.");
        };
    }
}
