import java.util.PriorityQueue;

public class EightPuzzle {
    Heuristics heuristics = new Heuristics();
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};

    public State searchGoalState(PriorityQueue<State> pq, int heuristic) {
        State goalState = null;
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int[][] board = cur.board;

            if (calculateCost(board, heuristic) == 0) {
                goalState = cur;
                break;
            }

            int[] pos = new int[2];
            findZero(board, pos);
            int x = pos[0];
            int y = pos[1];
            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                    int[][] newBoard = swapTiles(board, x, y, nx, ny);
                    State newState = new State(newBoard, nx, ny, calculateCost(newBoard, heuristic) + cur.depth + 1, cur.depth + 1, cur);
                    pq.add(newState);
                }
            }
        }
        return goalState;
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