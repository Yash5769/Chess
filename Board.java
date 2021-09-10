import java.util.*;
/*
This class handles:
    1:convert FEN to int[]
    2:convert int[] to FEN
    3:Print/Display Board
    4:check if in check
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
          i-=1;
          continue;
        }
        else if(Character.isDigit(fc))
        {
          i+=(int)fc-(int)'0';
          i-=1;
        }
        else
        {
          board[i] = charToPiece.get(fc);
        }
      }
    }

    
    return board;
  }

  public static int[] generateBoard() 
  {
    String FEN="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    return generateBoard(FEN);
  }
  public static void printBoard(int[] Board)
  {
    HashMap<Integer, Character> piecetoChar = new HashMap<>();
    piecetoChar.put(0,'K');
    piecetoChar.put(2,'N');
    piecetoChar.put(4,'Q');
    piecetoChar.put(6,'P');
    piecetoChar.put(8,'B');
    piecetoChar.put(10,'R');
    piecetoChar.put(1,'k');
    piecetoChar.put(3,'n');
    piecetoChar.put(5,'q');
    piecetoChar.put(7,'p');
    piecetoChar.put(9,'b');
    piecetoChar.put(11,'r');
    for(int i=0;i<144;i++)
    {
      int x = (i%12) - 2;
      int y = (i/12) - 2;
      if((x>=0 && x<8) && (y>=0 && y<8))
      {
        System.out.print(Board[i]==-1?'0':piecetoChar.get(Board[i])); 
        System.out.print(" ");
        if(x==7)
        {
          System.out.println();
        }
      }
    }
  }

  public static float evaluatePosition(int[] board)
  {
    float eval = 0;
    int Wbishops = 0;
    int Wknights = 0;
    int Wqueens = 0;
    int Wrooks = 0;
    int Wpawns = 0;
    int isBCheck = isCheck(board,1)?1:0;

    int Bbishops = 0;
    int Bknights = 0;
    int Bqueens = 0;
    int Brooks = 0;
    int Bpawns = 0;
    int isWCheck = isCheck(board,0)?1:0;

    int WMobility = 0;
    int BMobility = 0;

    ArrayList<Move> moves = new ArrayList<Move>();
    moves = MoveProcessing.generatePseudoLegalMoves(board,0); 
    moves = MoveProcessing.legalizeMoves(moves, board);
    WMobility = moves.size();
    if(moves.size() == 0)
    {
      if(isWCheck==1)
        return 100000;
      else
        return 0;
    }
    moves = MoveProcessing.generatePseudoLegalMoves(board,1); 
    moves = MoveProcessing.legalizeMoves(moves, board);
    BMobility = moves.size();
    if(moves.size() == 0)
    {
      if(isBCheck==1)
        return -100000;
      else
        return 0;
    }
    for(int i =0;i<144;i++)
    {
      //White Piece
      if(board[i]%2==0)
      {
        //Knight
        if(board[i]/2 == 1)
          Wknights++;
        //Queen
        else if(board[i]/2 == 2)
          Wqueens++;
        //Pawn
        else if(board[i]/2 == 3)
          Wpawns++;
        //Bishop
        else if(board[i]/2 == 4)
          Wbishops++;
        //Rook
        else if(board[i]/2 == 5)
          Wrooks++;
      }
      //Black Piece
      else
      {
        //Knight
        if(board[i]/2 == 1)
          Bknights++;
        //Queen
        else if(board[i]/2 == 2)
          Bqueens++;
        //Pawn
        else if(board[i]/2 == 3)
          Bpawns++;
        //Bishop
        else if(board[i]/2 == 4)
          Bbishops++;
        //Rook
        else if(board[i]/2 == 5)
          Brooks++;
      }
    }

    eval += Wbishops*3+Wknights*3+Wqueens*9+Wpawns+Wrooks*5+isBCheck*0.2+WMobility*0.1;
    eval -= Bbishops*3+Bknights*3+Bqueens*9+Bpawns+Brooks*5+isWCheck*0.2+BMobility*0.1;

    return eval;
  }
  
  public static boolean isCheck(int[] board,int colour)
  {
    boolean check= false;
    int kingpos = -1;
    for(int i =0 ;i<144;i++)
    {
      if(board[i]==colour)
      {
        kingpos = i;
      }
    }
    //bishop check
    
    for(int i = 0;i<MoveProcessing.bishopVectors.length;i++)
    {
      int curpos = kingpos;
      curpos += MoveProcessing.bishopVectors[i];
      while(MoveProcessing.Valid_moves[curpos]==0)
      {
        if(board[curpos] == 9-colour)
        {
          check = true;
          return check;
        }
        else if(board[curpos] != -1)
        {
          break;
        }
        curpos += MoveProcessing.bishopVectors[i];
      }
    }
    //rook check
    for(int i = 0;i<MoveProcessing.rookVectors.length;i++)
    {
      int curpos = kingpos;
      curpos += MoveProcessing.rookVectors[i];
      while(MoveProcessing.Valid_moves[curpos]==0)
      {
        if(board[curpos] == 11-colour)
        {
          check = true;
          return check;
        }
        else if(board[curpos] != -1)
        {
          break;
        }
        curpos += MoveProcessing.rookVectors[i];
      }
    }
    
    //queen check
    for(int i = 0;i<MoveProcessing.queenVectors.length;i++)
    {
      int curpos = kingpos;
      curpos += MoveProcessing.queenVectors[i];
      while(MoveProcessing.Valid_moves[curpos]==0)
      {
        if(board[curpos] == 5-colour)
        {
          check = true;
          return check;
        }
        else if(board[curpos] != -1)
        {
          break;
        }
        curpos += MoveProcessing.queenVectors[i];
      }
    }

    //knight check
    for(int i = 0;i<MoveProcessing.knightMoves.length;i++)
    {
      int curpos = kingpos;
      curpos += MoveProcessing.knightMoves[i];
      if(board[curpos] == 3-colour)
      {
        check = true;
        return check;
      }
    }

    //pawn check
    int curpos = kingpos;
    if(colour ==0)
    {
      curpos-=12;
    }
    else
    {
      curpos+=12;
    }
    if(board[curpos+1] == 7-colour)
      check = true;
    if(board[curpos-1] == 7-colour)
      check = true;
    return check;
  }

  /*
    prints a overview analysis of board:
    1: prints the board
    2: Generates the moves for White
    3: Generates the moves for Black
    4: Gets the Eval for the position
  */
  public static void printBoardAnalysis(int[] board)
  {
    Board.printBoard(board);
    ArrayList<Move> moves = MoveProcessing.generatePseudoLegalMoves(board,0); 
    moves = MoveProcessing.legalizeMoves(moves, board);
    if(Main.debugMode)
      for(int i =0; i<moves.size();i++)
        System.out.println(moves.get(i));
    System.out.println("Number of available moves for White: " + moves.size());
    moves = MoveProcessing.generatePseudoLegalMoves(board,1); 
    moves = MoveProcessing.legalizeMoves(moves, board);
    if(Main.debugMode)
      for(int i =0; i<moves.size();i++)
        System.out.println(moves.get(i));
    System.out.println("Number of available moves for Black: " + moves.size());
    System.out.println("Current Eval: "+ evaluatePosition(board));
    System.out.println("Is White in check: " + isCheck(board, 0));
    System.out.println("Is Black in check: " + isCheck(board, 1));
  }

  /*
    does the exact same thing as board analysis but prints the time taken for function
  */
  public static void CheckEfficiency(int[] board)
  {
    long startTime,endTime;
    Board.printBoard(board);


    startTime = System.nanoTime();
    ArrayList<Move> moves = MoveProcessing.generatePseudoLegalMoves(board,0); 
    moves = MoveProcessing.legalizeMoves(moves, board);
    endTime = System.nanoTime();
    System.out.println("Time taken to generate White\'s moves"+(((double)endTime-startTime)/1000000)+"ms");
    
    if(Main.debugMode)
      for(int i =0; i<moves.size();i++)
        System.out.println(moves.get(i));
    System.out.println("Number of available moves for White: " + moves.size());

    startTime = System.nanoTime();
    moves = MoveProcessing.generatePseudoLegalMoves(board,1); 
    moves = MoveProcessing.legalizeMoves(moves, board);
    endTime = System.nanoTime();
    System.out.println("Time taken to generate Black\'s moves"+(((double)endTime-startTime)/1000000)+"ms");

    if(Main.debugMode)
      for(int i =0; i<moves.size();i++)
        System.out.println(moves.get(i));

    System.out.println("Number of available moves for Black: " + moves.size());
    startTime = System.nanoTime();
    System.out.println("Current Eval: "+ evaluatePosition(board));
    endTime = System.nanoTime();
    System.out.println("Time taken to Evaluate Position"+(((float)endTime-startTime)/1000000)+"ms");
    
    System.out.println("Is White in check: " + isCheck(board, 0));
    System.out.println("Is Black in check: " + isCheck(board, 1));
  }

  public static int nodes = 0;
  public static long MoveGen = 0;
  public static long CheckGen =0;
  public static Move FindBest(int[] board,int colour,int depth)
  {
    float best = 0;
    Move bestMove = null;
    long startTime = System.nanoTime();
    if(colour == 0)
    {
      ArrayList<Move> moves = new ArrayList<Move>();
      moves = MoveProcessing.generatePseudoLegalMoves(board,0); 
      moves = MoveProcessing.legalizeMoves(moves, board);
      best = -21378364;
      for(int i = 0;i<moves.size();i++)
      {
        board = MoveProcessing.makeMove(board,moves.get(i));
        float cur= minimax(board, depth-1, false,-214748364,214748364);
        if(best<cur)
        {
          best = cur;
          bestMove = moves.get(i);
        }
        board = MoveProcessing.unmakeMove(board,moves.get(i));
      }
    }
    else
    {
      ArrayList<Move> moves = new ArrayList<Move>();
      moves = MoveProcessing.generatePseudoLegalMoves(board,1); 
      moves = MoveProcessing.legalizeMoves(moves, board);
      best = 21378364;
      for(int i = 0;i<moves.size();i++)
      {
        board = MoveProcessing.makeMove(board,moves.get(i));
        float cur= minimax(board, depth-1, true,-214748364,214748364);
        if(best>cur)
        {
          best = cur;
          bestMove = moves.get(i);
        }
        board = MoveProcessing.unmakeMove(board,moves.get(i));
      }
    }
    System.out.println("The Best Move Found is "+bestMove.toString()+"with "+nodes+" nodes explored");
    System.out.println("The Evaluation of best move is "+best);
    long endTime = System.nanoTime();
    System.out.println("Time taken to generate best move"+(((double)endTime-startTime)/1000000)+"ms");
    System.out.println("Time taken generating movelist"+(((double)MoveGen)/1000000)+"ms");
    return bestMove;
    
  }

  public static float minimax(int[] board,int depth, boolean Max,float alpha,float beta)
  {
    
    if(depth == 0)
    {
      nodes++;
      if(Main.debugMode)
      System.out.println("Nodes Explored : "+nodes);
      return evaluatePosition(board);
      
    }
    if(Max)
    {
      float maximum = -214748364;
      ArrayList<Move> moves = new ArrayList<Move>();
      long start = System.nanoTime();
      moves = MoveProcessing.generatePseudoLegalMoves(board,0); 
      moves = MoveProcessing.legalizeMoves(moves, board);
      MoveGen += System.nanoTime()-start;
      Collections.sort(moves,new SortMove());
      if(moves.size() == 0)
      {
        nodes++;
        if(Main.debugMode)
          System.out.println("Nodes Explored : "+nodes);
        return evaluatePosition(board);
        // nodes++;
      }
      for(int i =0;i<moves.size();i++)
      {
        board = MoveProcessing.makeMove(board,moves.get(i));
        float cur= minimax(board, depth-1, !Max,alpha,beta);
        if(maximum<cur)
        {
          maximum = cur;
        }
        if(alpha<maximum)
          alpha = maximum;
        board = MoveProcessing.unmakeMove(board,moves.get(i));
        if(beta<=alpha)
          break;
      }
      return maximum;
    }
    else
    {
      float minimum = +214748364;
      ArrayList<Move> moves = new ArrayList<Move>();
      long start = System.nanoTime();
      moves = MoveProcessing.generatePseudoLegalMoves(board,1); 
      moves = MoveProcessing.legalizeMoves(moves, board);
      MoveGen += System.nanoTime()-start;
      Collections.sort(moves,new SortMove());
      if(moves.size() == 0)
      {
        nodes++;
        if(Main.debugMode)
        System.out.println("Nodes Explored : "+nodes);
        return evaluatePosition(board);
        // nodes++;
      }
      for(int i =0;i<moves.size();i++)
      {
        board = MoveProcessing.makeMove(board,moves.get(i));
        float cur= minimax(board, depth-1, !Max,alpha,beta);
        if(minimum>cur)
        {
          minimum = cur;
        }
        if(beta>minimum)
        {
          beta = minimum;
        }
        board = MoveProcessing.unmakeMove(board,moves.get(i));
        if(beta<=alpha)
          break;
      }
      return minimum;
    }
  }

}
