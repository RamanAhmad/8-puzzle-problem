import java.util.*;

public class Utils {
    Scanner sc = new Scanner(System.in);

    // Ausgabe der Lösung oder einer Meldung, wenn keine Lösung gefunden wurde.
    public void printSolutionOrNoSolution(State goalState) {
        if (goalState != null) {
            printSolutionPath(goalState);
        } else {
            System.out.println("No solution found.");
        }
    }

    // Druckt den Lösungsweg von der Startposition bis zum Zielzustand.
    public void printSolutionPath(State goalState) {
        Stack<State> path = new Stack<>();
        State currentState = goalState;
        while (currentState != null) {
            path.push(currentState);
            currentState = currentState.parent;
        }

        while (!path.isEmpty()) {
            State state = path.pop();
            showResult(state.board);
        }
    }

    // Zeigt das Puzzle-Board an.
    public void showResult(int[][] arr) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Zeigt eine Übersicht der Heuristik-Ergebnisse.
    public static void displayChoiceInfo(List<ChoiceInfo> choiceInfoList) {
        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n", "Choice", "Algorithm", "Heuristic", "Execution Time (ms)", "Memory Used (Bytes)");
        for (ChoiceInfo choiceInfo : choiceInfoList) {
            System.out.printf("%-20d\t%-20s\t%-20s\t%-20d\t%-20d\n",
                    choiceInfo.choice(), choiceInfo.algorithmName(), choiceInfo.heuristicName(),
                    choiceInfo.duration(), choiceInfo.memoryUsed());
        }
    }

    // ChoiceInfo ist ein Datenhalter für die Ergebnisse der Algorithmen.
    public record ChoiceInfo(int choice, String algorithmName, String heuristicName, long duration, long memoryUsed) {
    }

    // Erzeugt ein zufälliges Board für das Puzzle.
    public int[][] generateRandomBoard() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        int[][] randomBoard = new int[3][3];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                randomBoard[i][j] = numbers.get(k++);
            }
        }
        return randomBoard;
    }

    // Überprüft, ob ein gegebenes Puzzle-Board lösbar ist.
    public boolean isSolvable(int[][] board) {
        int[] arr = new int[9];
        int k = 0;
        for (int[] row : board) {
            for (int num : row) {
                arr[k++] = num;
            }
        }

        int inversions = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (arr[i] != 0 && arr[j] != 0 && arr[i] > arr[j]) {
                    inversions++;
                }
            }
        }

        return inversions % 2 == 0;
    }

    // Gibt die Auswahl für das Anfangsboard zurück.
    public int initialBoardOption() {
        System.out.println();

        System.out.println("Choose Initial Board");
        System.out.println("11. For solvable puzzle");
        System.out.println("22. For not solvable puzzle");
        System.out.println("-".repeat(41));
        String input = sc.nextLine();
        return Integer.parseInt(input);
    }

    // Gibt die Heuristik-Optionen zurück.
    public int heuristicOptions() {
        System.out.println();

        System.out.println("Choose heuristic function:");
        System.out.println("0. Even And Odd Numbers Heuristic");
        System.out.println("1. Manhattan Distance");
        System.out.println("2. Misplaced Tiles");
        System.out.println("3. Linear Conflict and Manhattan Distance");
        System.out.println("4. Linear Conflict and Our Heuristic");
        System.out.println("-".repeat(41));
        System.out.println("5. Show Results");
        String input = sc.nextLine();
        return Integer.parseInt(input);
    }

    // Gibt das Anfangsboard basierend auf der Benutzereingabe zurück.
    public int[][] initialBoard(int input) {
        if (input == 11) {
            return new int[][]{
                    {1, 8, 2},
                    {0, 4, 3},
                    {7, 6, 5}
            };
        }

        return new int[][]{
                {1, 5, 7},
                {8, 0, 6},
                {3, 2, 4}
        };
    }

    // Gibt den Namen der Heuristik basierend auf der Auswahl zurück.
    public String getHeuristicName(int choice) {
        return switch (choice) {
            case 0 -> "EvenOdd Numbers Heuristic";
            case 1 -> "Manhattan Distance";
            case 2 -> "Misplaced Tiles";
            case 3 -> "Linear Conflict and Manhattan Distance";
            case 4 -> "Linear Conflict and EvenOdd Numbers Heuristic";
            default -> "Unknown Heuristic";
        };
    }
}
