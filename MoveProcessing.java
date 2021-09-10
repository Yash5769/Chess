import java.util.*;

public class MoveProcessing
{

  /*
    for board a 12x12 grid is used with top left being 0 and increasing rightwards and bottom right being 143

    for type: lower case representations are used:
    0-king
    1-Knight
    2-queen
    3-pawn
    4-bishop
    5-rook

    for colour :
    0-White
    1-Black

    White king:0000 = 0
    White Knight:0010 = 2
    White Queen:0100 = 4
    White Pawn:0110 = 6
    White Bishop:1000 = 8
    White Rook:1010 = 10
    
    Black king:0001 = 1
    Black Knight:0011 = 3
    Black Queen:0101 = 5
    Black Pawn:0111 = 7
    Black Bishop:1001 = 9
    Black Rook:1011 = 11
  */


  /*
    Valid moves is used to remove off board moves
    eg: a rook on 27 would try to move to 15 but it will be removed
  */
  private static int[] Valid_moves = 
  {1,1,1,1,1,1,1,1,1,1,1,1,
  1,1,1,1,1,1,1,1,1,1,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,0,0,0,0,0,0,0,0,1,1,
  1,1,1,1,1,1,1,1,1,1,1,1,
  1,1,1,1,1,1,1,1,1,1,1,1};


  /* 
    Vectors are one move in each direction for sliding pieces
  */
  private static int[] bishopVectors = {13,11,-13,-11};
  private static int[] rookVectors = {12,-12,1,-1};
  private static int[] queenVectors = {13,11,12,1,-13,-11,-12,-1};

  /*
    Moves of non sliding pieces
  */
  private static int[] kingMoves = {13,11,12,1,-13,-11,-12,-1};
  private static int[] pawnMoves = {12,24};
  private static int[] knightMoves = {14,10,-10,-14,25,23,-25,-23};

  public static ArrayList<Move> generateMoves(int[] board,int colour)
  {
    ArrayList<Move> moves = new ArrayList<Move>();
    for(int i =0;i<144;i++)
    {
      // is square is not empty and colour matches
      if(board[i]!=-1 && board[i]%2==colour)
      {
        //King
        if(board[i]/2==0)
        {
          for(int j =0;j<kingMoves.length;j++)
          {
            int curpos = i;
            curpos+=kingMoves[j];
            if(Valid_moves[curpos]==0)
            {
              if(board[curpos]==-1)
              {
                moves.add(new Move(i,curpos,board[i]));
              }
              else if(board[curpos]%2 != colour && board[curpos]/2 != 0)
              {
                moves.add(new Move(i,curpos,true,board[curpos],board[i]));
              }
            } 
          }
        }
        //Knight
        else if(board[i]/2==1)
        {
          for(int j =0;j<knightMoves.length;j++)
          {
            int curpos = i;
            curpos+=knightMoves[j];
            if(Valid_moves[curpos]==0)
            {
              if(board[curpos]==-1)
              {
                moves.add(new Move(i,curpos,board[i]));
              }
              else if(board[curpos]%2 != colour && board[curpos]/2 != 0)
              {
                moves.add(new Move(i,curpos,true,board[curpos],board[i]));
              }
            } 
          }
        }
        //Queen
        else if(board[i]/2==2){
          //looping through all directions
          for(int j =0; j< queenVectors.length;j++)
          {
            int curPos = i;
            curPos+=queenVectors[j];
            while(Valid_moves[curPos]==0)
            {
              //the square is unoccupied
              if(board[curPos] == -1)
              {
                moves.add(new Move(i,curPos,board[i]));
              }
              //the square is occupied by same colour
              else if(board[curPos]%2==colour||board[curPos]/2 ==0)
              {
                break;
              }
              //the square is occupied by enemy
              else if(board[curPos]%2!=colour)
              {
                moves.add(new Move(i,curPos,true,board[curPos],board[i]));
                break;
              }
              curPos+=queenVectors[j];
            }
          }
        }
        //Pawn
        else if(board[i]/2==3)
        {
          int curpos= i;
          if(colour == 0)
          {
            curpos+=12;
          }
          else
          {
            curpos -=12;
          }
          if(board[curpos]== -1)
          {
            moves.add(new Move(i,curpos,board[i]));
          }
          curpos +=1;
          if(board[curpos] != -1 && board[curpos]%2 != colour)
          {
            moves.add(new Move(i,curpos,true,board[curpos],board[i]));
          }
          curpos -=2;
          if(board[curpos] != -1 && board[curpos]%2 != colour)
          {
            moves.add(new Move(i,curpos,true,board[curpos],board[i]));
          }
        }
        //Bishop
        else if(board[i]/2==4){
          //looping through all directions
          for(int j =0; j< bishopVectors.length;j++)
          {
            int curPos = i;
            curPos+=bishopVectors[j];
            while(Valid_moves[curPos]==0)
            {
              //the square is unoccupied
              if(board[curPos] == -1)
              {
                moves.add(new Move(i,curPos,board[i]));
              }
              //the square is occupied by same colour
              else if(board[curPos]%2==colour||board[curPos]/2 ==0)
              {
                break;
              }
              //the square is occupied by enemy
              else if(board[curPos]%2!=colour)
              {
                moves.add(new Move(i,curPos,true,board[curPos],board[i]));
                break;
              }
              curPos+=bishopVectors[j];
            }
          }
        }
        //Rook
        else if(board[i]/2==2){
          //looping through all directions
          for(int j =0; j< rookVectors.length;j++)
          {
            int curPos = i;
            curPos+=rookVectors[j];
            //checking if the move is on board
            while(Valid_moves[curPos]==0)
            {
              //the square is unoccupied
              if(board[curPos] == -1)
              {
                moves.add(new Move(i,curPos,board[i]));
              }
              //the square is occupied by same colour
              else if(board[curPos]%2==colour||board[curPos]/2 ==0)
              {
                break;
              }
              //the square is occupied by enemy
              else if(board[curPos]%2!=colour)
              {
                moves.add(new Move(i,curPos,true,board[curPos],board[i]));
                break;
              }
              curPos+=rookVectors[j];
            }
          }
        }
      }
    }
    return moves;
  }  
//Changing the board to make moves
  public static int[] makeMove(int[] currboard,Move move)
  {
    int[] processboard = currboard;
    if(move.hasCaptured==false)
    {
       processboard[move.to]=processboard[move.from];
       processboard[move.from]=-1;
    }
    else if(move.hasCaptured==true)
    {
       processboard[move.to]=processboard[move.from];
       processboard[move.from]=-1;
    }

    return processboard;
  }
//undo the move
  public static int[] unmakeMove(int[] currboard,Move move)
  {
    int[] processboard = currboard;
    if(move.hasCaptured==false)
    {
       
       processboard[move.from]=processboard[move.to];
       processboard[move.to]=-1;
    }
    else if(move.hasCaptured==true)
    {
      int deadPiece = move.capturedPiece;
      processboard[move.from]=processboard[move.to];
      processboard[move.to]=deadPiece;
    }

    return processboard;
  }
}