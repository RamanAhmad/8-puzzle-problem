/*
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SolverTest {
    private static void templateTester(int heuristic, String heuristicName) {
        EightPuzzle eightPuzzle = new EightPuzzle();
        Utils utils = new Utils();
        List<Utils.ChoiceInfo> choiceInfoList = new ArrayList<>();
        int[][] initialBoard = utils.initialBoard(11);
        int i = 0;

        while (i < 50) {
            PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
            int[] pos = new int[2];
            eightPuzzle.findZero(initialBoard, pos);

            State initialState = new State(initialBoard, pos[0], pos[1], eightPuzzle.calculateCost(initialBoard, heuristic), 0, null);
            pq.add(initialState);

            long startTime = System.nanoTime();
            long memoryBefore = 0;
            long memoryAfter = 0;

            System.gc();
            memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            State goalState = eightPuzzle.searchGoalState(pq, heuristic);

            System.gc();
            memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            long endTime = System.nanoTime();
            long memoryUsed = memoryAfter - memoryBefore;
            long duration = (endTime - startTime) / 1000000;
            utils.printSolutionOrNoSolution(goalState);
            choiceInfoList.add(new Utils.ChoiceInfo(heuristic, heuristicName, duration, memoryUsed));
            i++;
            Utils.displayChoiceInfo(choiceInfoList);
        }
    }

    public static void main(String[] args) {
        templateTester(0, "Our Heuristic");
//        templateTester(1, "Manhattan Distance");
//        templateTester(2, "Misplaced Tiles");
//        templateTester(3, "Linear Conflict and OH");
//        templateTester(4, "LC and MD");
    }
}
*/
