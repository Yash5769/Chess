import java.util.*;
public class Main {
  public static boolean debugMode = false;
  public static void main(String[] args) {
    System.out.println("Hello world!");
    int[] board = Board.generateBoard();
    Board.printBoardAnalysis(board);
    Board.FindBest(board, 0, 5);
  }
}
