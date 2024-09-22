import java.util.*;

public class Solver {
    public static void main(String[] args) {
        EightPuzzle eightPuzzle = new EightPuzzle();
        EightPuzzleIDAStar eightPuzzleIDAStar = new EightPuzzleIDAStar();
        Utils utils = new Utils();
        List<Utils.ChoiceInfo> choiceInfoList = new ArrayList<>();
        int[][] initialBoard;
        int inputInitialBoard = utils.initialBoardOption();

        if (inputInitialBoard == 22) {
            initialBoard = utils.initialBoard(22);
            System.out.println("No solutions available - inversion count problem!");
        } else if (inputInitialBoard == 11) {
            initialBoard = utils.initialBoard(11);

            while (true) {
                int choice = utils.heuristicOptions();

                if (choice == 5) {
                    Utils.displayChoiceInfo(choiceInfoList);
                    break;
                }

                System.out.println("Wähle den Algorithmus:");
                System.out.println("1. A* Algorithmus");
                System.out.println("2. IDA* Algorithmus");
                int algorithmChoice = Integer.parseInt(utils.sc.nextLine());

                var heuristicName = switch (choice) {
                    case 0 -> "Even And Odd";
                    case 1 -> "Manhattan Distance";
                    case 2 -> "Misplaced Tiles";
                    case 3 -> "Linear Conflict and OH";
                    default -> "LC and MD";
                };

                // Algorithmusname speichern
                String algorithmName = (algorithmChoice == 1) ? "A*" : "IDA*";

                long startTime = System.nanoTime();
                long memoryBefore = 0;
                long memoryAfter = 0;

                System.gc();
                memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                State goalState;
                if (algorithmChoice == 1) {
                    // Verwende A*
                    PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
                    int[] pos = new int[2];
                    eightPuzzle.findZero(initialBoard, pos);
                    State initialState = new State(initialBoard, pos[0], pos[1], eightPuzzle.calculateCost(initialBoard, choice), 0, null);
                    pq.add(initialState);

                    goalState = eightPuzzle.searchGoalState(pq, choice);
                } else {
                    // Verwende IDA*
                    int[] pos = new int[2];
                    eightPuzzle.findZero(initialBoard, pos);
                    State initialStateIDA = new State(initialBoard, pos[0], pos[1], eightPuzzleIDAStar.calculateCost(initialBoard, choice), 0, null);
                    goalState = eightPuzzleIDAStar.searchGoalState(initialStateIDA, choice);
                }

                System.gc();
                memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                long endTime = System.nanoTime();
                long memoryUsed = Math.abs(memoryAfter - memoryBefore);
                long duration = (endTime - startTime) / 1000000;
                utils.printSolutionOrNoSolution(goalState);

                // Speichere die Algorithmus- und Heuristikinformationen
                choiceInfoList.add(new Utils.ChoiceInfo(choice, heuristicName, algorithmName, duration, memoryUsed));
            }
        }
    }
}
