public class State {
    int[][] board;
    int x;
    int y;
    int cost;
    int depth;
    State parent;

    public State(int[][] board, int x, int y, int cost, int depth, State parent) {
        this.board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, 3);
        }
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.depth = depth;
        this.parent = parent;
    }
}
