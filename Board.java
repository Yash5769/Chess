import java.util.*;
/*
This class handles:
    1:convert FEN to int[]
    2:convert int[] to FEN
    3:Print/Display Board
    4:Pseudo Legal to Legal moves
    rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR

    y coord = 26/12 = 2
    x cord = 26%12 = 2
*/

public class Board {
  public static int[] defaultBoard = 
  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

  public static int[] generateBoard(String FEN) {
    // Dictionary charToPiece = new Hashtable<String,Integer>();
    HashMap<Character, Integer> charToPiece = new HashMap<>();
    charToPiece.put('K', 0);
    charToPiece.put('N', 2);
    charToPiece.put('Q', 4);
    charToPiece.put('P', 6);
    charToPiece.put('B', 8);
    charToPiece.put('R', 10);
    charToPiece.put('k', 1);
    charToPiece.put('n', 3);
    charToPiece.put('q', 5);
    charToPiece.put('p', 7);
    charToPiece.put('b', 9);
    charToPiece.put('r', 11);

    int[] board = defaultBoard;
    int k = 0;
    for(int i=0;i<144;i++)
    {
      int x = (i%12) - 2;
      int y = (i/12) - 2;
      if((x>=0 && x<8) && (y>=0 && y<8))
      {
       
        char fc = FEN.charAt(k++);
        if(fc=='/')
        {
          i--;
          continue;
        }
        else if(Character.isDigit(fc))
        {
          i+=(int)fc-(int)'0';
          i-=1;
        }
        else
          board[i] = charToPiece.get(fc);
      }
    }
    return board;
  }

  public static int[] generateBoard() 
  {
    String FEN="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    return generateBoard(FEN);
  }
  public static void printBoard(int[] board)
  {
    HashMap<Integer, Character> pieceToChar = new HashMap<>();
    pieceToChar.put(0,'K');
    pieceToChar.put(2,'N');
    pieceToChar.put(4,'Q');
    pieceToChar.put(6,'P');
    pieceToChar.put(8,'B');
    pieceToChar.put(10,'R');
    pieceToChar.put(1,'k');
    pieceToChar.put(3,'n');
    pieceToChar.put(5,'q');
    pieceToChar.put(7,'p');
    pieceToChar.put(9,'b');
    pieceToChar.put(11,'r');
    pieceToChar.put(-1,'0');
    
    for(int i=0;i<144;i++)
    {
      int x = (i%12) - 2;
      int y = (i/12) - 2;
      if((x>=0 && x<8) && (y>=0 && y<8))
      {
        System.out.print(pieceToChar.get(board[i])+" ");
        if(x==7)
        {
          System.out.println();
        }
      }
    }
  }
  
}