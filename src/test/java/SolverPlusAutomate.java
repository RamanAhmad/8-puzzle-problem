import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SolverPlusAutomate {

    private static final int RUNS = 100;

    public static void main(String[] args) {
        EightPuzzle eightPuzzle = new EightPuzzle();
        EightPuzzleIDAStar eightPuzzleIDAStar = new EightPuzzleIDAStar();
        Utils utils = new Utils();

        List<Utils.ChoiceInfo> choiceInfoList = new ArrayList<>();
        int[][] initialBoard;

        initialBoard = utils.initialBoard(11);

        for (int heuristic = 0; heuristic <= 3; heuristic++) {
            String heuristicName = utils.getHeuristicName(heuristic);
            String algorithmName = "A*";

            for (int run = 0; run < RUNS; run++) {
                long startTime = System.nanoTime();
                long memoryBefore = 0;
                long memoryAfter = 0;

                System.gc();
                memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                State goalState;
                PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
                int[] pos = new int[2];
                eightPuzzle.findZero(initialBoard, pos);
                State initialState = new State(initialBoard, pos[0], pos[1], eightPuzzle.calculateCost(initialBoard, heuristic), 0, null);
                pq.add(initialState);
                goalState = eightPuzzle.searchGoalState(pq, heuristic);

                System.gc();
                memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                long endTime = System.nanoTime();
                long memoryUsed = Math.abs(memoryAfter - memoryBefore);
                long duration = (endTime - startTime) / 1000000;

                choiceInfoList.add(new Utils.ChoiceInfo(run, algorithmName, heuristicName, duration, memoryUsed));
            }
        }
        saveResultsToCSV(choiceInfoList, "AStar.csv");

        choiceInfoList.clear();
        for (int heuristic = 0; heuristic <= 4; heuristic++) {
            String heuristicName = utils.getHeuristicName(heuristic);
            String algorithmName = "IDA*";

            for (int run = 0; run < RUNS; run++) {
                long startTime = System.nanoTime();
                long memoryBefore = 0;
                long memoryAfter = 0;

                System.gc();
                memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                State goalState;
                int[] pos = new int[2];
                eightPuzzle.findZero(initialBoard, pos);
                State initialStateIDA = new State(initialBoard, pos[0], pos[1], eightPuzzleIDAStar.calculateCost(initialBoard, heuristic), 0, null);
                goalState = eightPuzzleIDAStar.searchGoalState(initialStateIDA, heuristic);

                System.gc();
                memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                long endTime = System.nanoTime();
                long memoryUsed = Math.abs(memoryAfter - memoryBefore);
                long duration = (endTime - startTime) / 1000000;

                choiceInfoList.add(new Utils.ChoiceInfo(run, algorithmName, heuristicName, duration, memoryUsed));
            }
        }
        saveResultsToCSV(choiceInfoList, "IDAStar.csv");
    }

    // Methode zum Speichern der Ergebnisse in einer CSV-Datei
    private static void saveResultsToCSV(List<Utils.ChoiceInfo> choiceInfoList, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Run,Algorithm,Heuristic,Execution Time (ms),Memory Used (Bytes)\n");

            for (Utils.ChoiceInfo info : choiceInfoList) {
                writer.append(String.valueOf(info.choice())).append(",")
                        .append(info.algorithmName()).append(",")
                        .append(info.heuristicName()).append(",")
                        .append(String.valueOf(info.duration())).append(",")
                        .append(String.valueOf(info.memoryUsed())).append("\n");
            }

            System.out.println("Results saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
